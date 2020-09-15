package Util;

import Data.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
            System.out.println("Username: "+ USERNAME + "\nPassword: "+ PASSWORD);
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
    public Set<Dragon> collectionDownload() throws SQLException {
        PreparedStatement statement =
                connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " ds\n" +
                        "    inner join coordinates dc\n" +
                        "on ds.id = dc.dragon_id\n" +
                        "    left outer join dragon_killers dk\n" +
                        "    on dk.dragon_id = ds.id\n");
        ResultSet resultSet = statement.executeQuery();
        HashSet<Dragon> hs = new HashSet<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("dragon_name");
            LocalDate date = resultSet.getDate("creation_date")
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            ;
            int age = resultSet.getInt("age");
            Integer wingspan = resultSet.getInt("wingspan");
            Color color = Enum.valueOf(Color.class, resultSet.
                    getString("color"));
            String owner = resultSet.getString("owner");
            String description = resultSet.getString("description");
            Coordinates coordinates = new Coordinates(resultSet.getInt("coordinate_x"),
                    resultSet.getLong("coordinate_y"));
            Person person = null;
            if (resultSet.getString("killer") != null) {
                person = new Person(
                        resultSet.getString("killer"),
                        resultSet.getDate("birthday"),
                        Enum.valueOf(Color.class, resultSet.getString("eyecolor")),
                        Enum.valueOf(Color.class, resultSet.getString("haircolor")));

            }
            Dragon dragon = new Dragon(name, coordinates, date, age, description, wingspan, color, person);
            dragon.setOwner(owner);
            dragon.setId(id);
            hs.add(dragon);
        }
        return (Collections.synchronizedSet(hs));
    }


    //добаление нового элемента
    public void insertDragon(Dragon dragon) {
        try {
            String state = "INSERT INTO "
                    + TABLE_NAME + "(name, creation_date, age, description, wingspan, color, killer)\n" +
                    "VALUES " +
                    "('" + dragon.getName() + "', " +
                    "'" + dragon.getCreationDate() +
                    "', '" + dragon.getAge() +
                    "', '" + dragon.getDescription() +
                    "', '" + dragon.getWingspan() +
                    "', '" + dragon.getColor().toString() +
                    "', '" + (dragon.hasKiller() ? dragon.getKiller().getName() : null) + "')";
            PreparedStatement dragonItself = connection.prepareStatement(state);
            dragonItself.executeUpdate();
            PreparedStatement dragonCoords = connection.prepareStatement("INSERT INTO coordinates(dragon_id,coordinate_x,coordinate_y)" +
                    "VALUES (currval('dragons_id_seq'), " + dragon.getCoordinates().getX() + ", "
                    + dragon.getCoordinates().getY() + ")");
            dragonCoords.executeUpdate();

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

    public Boolean userRegistration(String login, String password){
        try {
            PreparedStatement registration = connection.prepareStatement("INSERT INTO " +
                    USERS_TABLE + "(username, password)\n "+
                    "VALUES "+
                    "('"+login + "' , '" + passEncoder.getHash(password) + "')");
            return registration.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //вход для зарегистрированных пользователей
    public Boolean userLogin(String username, String password){
        try {
            PreparedStatement login = connection.prepareStatement("SELECT EXISTS" +
                    "(SELECT (username, password) FROM " + USERS_TABLE + " WHERE " +
                    "('"+ username + "', '" + passEncoder.getHash(password)+"')");
            return login.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //удаление элемента по id


    //добавление нового пользователя


    //ищем пользователя


    //ищем пользователя только по имени


    //генерируем id с помощью sequence


    //удаляем все элементы, принадлежащие пользователю


    //обновляем поля элемента

}
