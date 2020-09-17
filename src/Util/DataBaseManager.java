package Util;

import Data.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

public class DataBaseManager {

    static final String DRIVER = "org.postgresql.Driver";
//    static final String DATABASE_URL = "jdbc:postgresql://pg:5432/studs";
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static String USERNAME;
    private static String PASSWORD;
    private static final String FILE_WITH_LOGIN_DATA = "login_data";
    private static final String TABLE_NAME = "dragons";
    private static final String USERS_TABLE = "users";
    private static final String PEPPER = "{0/!9D,W#1M";

    //читаем данные аккаунта для входа подключения к бд, ищем драйвер
    static {
        try (FileReader fileReader = new FileReader(FILE_WITH_LOGIN_DATA);
             BufferedReader reader = new BufferedReader(fileReader)) {
            USERNAME = reader.readLine();
            PASSWORD = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Подключение к PostgreSQL JDBC...");
        try {
            Class.forName(DRIVER);
            System.out.println("Драйвер PostgreSQL JDBC успешно подключён.");
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер PostgreSQL JDBC не найден, попробуйте изменить путь.");
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PassEncoder passEncoder;

    public DataBaseManager(String dbUrl, String user, String pass) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, pass);
            passEncoder = new PassEncoder(PEPPER);
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }

    public DataBaseManager(String address, int port, String dbName, String user, String pass) {
        this("jdbc:postgresql://" + address + ":" + port + "/" + dbName, user, pass);
    }

    public DataBaseManager() {
        this(DATABASE_URL, USERNAME, PASSWORD);
    }

    //загрузка коллекции в память
    public Map<Long, Dragon> collectionDownload() throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ds\n" +
                        "    left outer join dragon_killers dk\n" +
                        "    on dk.dragon_id = ds.id\n");
        ResultSet resultSet = statement.executeQuery();
        Hashtable<Long, Dragon> collection = new Hashtable<Long, Dragon>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            LocalDate date = resultSet.getDate("creation_date")
                    .toLocalDate();
            ;
            int age = resultSet.getInt("age");
            Integer wingspan = resultSet.getInt("wingspan");
            Color color = Enum.valueOf(Color.class, resultSet.
                    getString("color"));
            String owner = resultSet.getString("owner");
            String description = resultSet.getString("description");
            Array simple_coordinates = resultSet.getArray("coordinates");
            Long[] array = (Long[])simple_coordinates.getArray();
            Coordinates coordinates = new Coordinates(array[0],array[1]);
            Person person = null;
            if (resultSet.getString("killer") != null) {
                person = new Person();
                String person_name = resultSet.getString("name");
                try {
                    person.setBirthday(resultSet.getDate("birthday"));
                } catch (NullPointerException e) {
                    person.setBirthday(null);
                }
                try {
                    person.setEyeColor(Enum.valueOf(Color.class, resultSet.getString("eyecolor")));
                } catch (NullPointerException e) {
                    person.setEyeColor(null);
                }
                try {
                    person.setHairColor(Enum.valueOf(Color.class, resultSet.getString("haircolor")));
                } catch (NullPointerException e) {
                    person.setHairColor(null);
                }
            }

            Dragon dragon = new Dragon(name, coordinates, date, age, description, wingspan, color, person);
            dragon.setOwner(owner);
            dragon.setId(id);
            collection.put(dragon.getId(),dragon);
        }
        return (Collections.synchronizedMap(collection));
    }


    //добаление нового элемента
    public void insertDragon(Dragon dragon) {
        try {
            String state = "INSERT INTO "
                    + TABLE_NAME + "(name, creation_date, age, description, wingspan, color, killer, owner, coordinates)\n" +
                    "VALUES " +
                    "('" + dragon.getName() + "', " +
                    "'" + dragon.getCreationDate() +
                    "', " + (dragon.getAge() == null? "NULL":dragon.getAge()) +
                    ", '" + dragon.getDescription() +
                    "', " + (dragon.getWingspan() == null ?"NULL":dragon.getWingspan()) +
                    ", '" + dragon.getColor().toString() +
                    "', '" + (dragon.hasKiller() ? dragon.getKiller().getName() : null) +
                    "', '" + dragon.getOwner() +
                    "', '{"+dragon.getCoordinates().getX()+","+dragon.getCoordinates().getY()+"}')";
            PreparedStatement dragonItself = connection.prepareStatement(state);
            dragonItself.executeUpdate();

            Person killer = dragon.getKiller();
            if (killer != null) {
                String hairColor = killer.getHairColor() == null ? "NULL" : "'" + killer.getHairColor() + "'";
                String eyeColor = killer.getEyeColor() == null ? "NULL" : "'" + killer.getEyeColor() + "'";
                PreparedStatement dragonKiller =
                        connection.prepareStatement("INSERT INTO dragon_killers(dragon_id, name, birthday, eyecolor,haircolor)" +
                                "VALUES (currval('generate_id'), '" + killer.getName() + "', '" + killer.getBirthdayInFormat() + "', " + hairColor +
                                ", " + eyeColor + ")");
                dragonKiller.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении элемента в БД.");
            e.printStackTrace();
        }
    }

    // Добавление нового пользователя

    public void userRegistration(UserValidator user) throws SQLException{
            PreparedStatement registration = connection.prepareStatement("INSERT INTO " +
                    USERS_TABLE + " (username, password)\n"+
                    "VALUES "+
                    "('" + user.getUsername() + "' , '" + passEncoder.getHash(user.getPassword()) + "')");
            registration.executeUpdate();
    }

    //вход для зарегистрированных пользователей
    public boolean userLogin(UserValidator user){
        try {
            PreparedStatement login = connection.prepareStatement("SELECT * FROM "
                     + USERS_TABLE + " WHERE username = ? and password = ?");
            login.setString(1, user.getUsername());
            login.setString(2, passEncoder.getHash(user.getPassword()));

            return login.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
