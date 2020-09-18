package App;

import Data.Dragon;
import Data.DragonCollection;
import Data.DragonValidator;
import Util.DataBaseManager;
import Util.UserValidator;
import com.google.gson.JsonSyntaxException;
import lombok.Getter;

import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * По шаблону "команда", Ресивер - это класс, который содержит в себе методы для исполнения каждой команды
 */
public class Receiver {

    private DragonCollection collection;
    private DragonValidator validator;
    @Getter
    private DataBaseManager dataBaseManager;

    /**
     * Стандартный конструктор
     * инициализирует коллекцию и выставляет дату
     */
    public Receiver() {
        collection = new DragonCollection();
        dataBaseManager = new DataBaseManager();
    }

    public Receiver(String virtualReceiver) {
        collection = new DragonCollection();
    }

    /**
     * Метод для реализации команды Help
     */
    public String help(UserValidator  user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        StringBuilder builder = new StringBuilder("Описание всех доступных команд: \n");
        RegisteredCommands.getCommandsName().values()
                .forEach(e -> builder.append(e.commandName()).append(" : ").append(e.manual()).append("\n"));
        return builder.toString();
    }

    /**
     * Метод для реализации команды info
     */
    public String info(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        StringBuilder builder = new StringBuilder();
        builder.append("Информация о коллекции: \n")
                .append("Коллекция типа: Hashtable\n")
                .append("Дата инициализации: " + collection.getCreationDate() + "\n")
                .append("Количество элементов: " + collection.getCollection().size() + "\n");
        return builder.toString();
    }

    /**
     * Метод для реализации команды show
     */
    public String show(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        StringBuilder builder = new StringBuilder();
        builder.append("Элементы коллекции:\n");
        collection.getCollection().values().stream()
                .sorted().forEach(e -> builder.append(e.toString()).append("\n"));
        return builder.toString();
    }

    /**
     * Метод для реализации команды insert
     *
     * @param
     */
//    public String insert(Long id) {
//        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
//        if (id <= 0) return "ID не может быть меньше нуля!";
//        if (collection.getCollection().containsKey(id)) return "Дракон с таким ID уже существует!";
//        collection.getCollection().put(id, new Dragon(validator));
//        return "Дракон успешно добавлен!";
//    }
    public String insert(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        Dragon dragon = new Dragon(validator);
        collection.getCollection().put(validator.getId(), new Dragon(validator));
        dataBaseManager.insertDragon(dragon);
        return "Дракон успешно добавлен!";
    }

    /**
     * Метод для реализации команды update
     *
     * @param id
     */
    public String update(Long id, UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        if (id <= 0) return "ID не может быть меньше нуля!";
        if (!collection.getCollection().containsKey(id)) return "Такого дракона в коллекции нет!";
        collection.getCollection().replace(id, new Dragon(validator));
        return "Данные о драконе успешно изменены!";
    }

    /**
     * Метод для реализации команды remove
     *
     * @param id
     */
    public String remove(Long id, UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        if (id <= 0) return "ID не может быть меньше нуля!";
        if (!collection.getCollection().containsKey(id)) return "Невозможно удалить то, чего нет.";
        if (collection.getCollection().get(id).getOwner().equals(user.getUsername())) {
            collection.getCollection().remove(id);
        return "Дракон успешно удалён!";}
        return "Дракон вам не принадлежит!";
    }

    /**
     * Метод для реализации команды clear
     */
    public String clear(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (!collection.getCollection().isEmpty()) {
            collection.getCollection().clear();
            return "Коллекция успешно очищена.";
        } else {
            return "Коллекция уже пуста!";
        }
    }

    /**
     * Метод для реализации команды save
     */
//    public void save() throws IOException {
//        if (!collection.getCollection().isEmpty()) {
//            Gson gson = new Gson();
//            List<String> list = new ArrayList<>();
//            Set<Long> set = collection.getCollection().keySet();
//            Iterator<Long> iterator = set.iterator();
//            for (int i = 0; i < set.size(); i++) {
//                String temp = gson.toJson(collection.getCollection().get(iterator.next()));
//                list.add(temp);
//            }
//            Iterator<Long> newIterator = set.iterator();
//            Iterator<String> listIterator = list.iterator();
//            File file = new File("output.json");
//            System.out.println("Сохранение коллекции в файл " + file.getAbsolutePath());
//            PrintWriter printWriter = new PrintWriter(file);
//            printWriter.write("{\n");
//            while (listIterator.hasNext()) {
//                printWriter.write("\t\"" + newIterator.next() + "\":");
//                printWriter.write(listIterator.next());
//                if (listIterator.hasNext()) {
//                    printWriter.write(",\n");
//                } else {
//                    printWriter.write("\n}");
//                }
//            }
//            printWriter.flush();
//            printWriter.close();
//        } else {
//            System.out.println("Запись пустой коллекции в файл невозможна");
//        }
//    }

    public void getCollectionFromDataBase() throws SQLException {
        Map<Long, Dragon> map = dataBaseManager.collectionDownload();
        collection.setCollection((map instanceof ConcurrentHashMap) ?
                (ConcurrentHashMap<Long, Dragon>) map : new ConcurrentHashMap<Long, Dragon>(map));
    }

    /**
     * Метод для считывания коллекции из файла
     *
     * @param path
     * @throws IOException
     * @throws JsonSyntaxException
     */
//    public void getFile(String path) throws IOException, JsonSyntaxException {
//        StringBuilder string = new StringBuilder();
//        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File(path)));
//        byte[] contents = new byte[1024];
//        int bytesRead = 0;
//        String strFileContents = "";
//        while ((bytesRead = stream.read(contents)) != -1) {
//            strFileContents += new String(contents, 0, bytesRead);
//        }
//        if (!strFileContents.trim().equals("")) {
//            Gson gson = new Gson();
//            Type collectionType = new TypeToken<Hashtable<Long, Dragon>>() {
//            }.getType();
//            //проверка правильности введённых значений
//            Hashtable<Long, Dragon> temp = gson.fromJson(strFileContents, collectionType);
//            Set<Long> keySet = temp.keySet();
//            Set<Long> remove = new TreeSet<>();
//            for (Long key : keySet) {
//                if ((key <= 0) || (!key.equals(temp.get(key).getId())) ||
//                        (temp.get(key).getCoordinates().getY() < -324) || (temp.get(key).getCoordinates() == null) ||
//                        (temp.get(key).getName().equals("")) || (temp.get(key).getDescription() == null) ||
//                        (temp.get(key).getAge() != null && temp.get(key).getAge() < 0) ||
//                        (temp.get(key).getWingspan() != null && temp.get(key).getWingspan() < 0) ||
//                        (temp.get(key).getCreationDate() == null) || (temp.get(key).getColor() == null)) {
//                    if (temp.get(key).getKiller() != null) {
//                        if (!temp.get(key).getKiller().getName().equals("")) {
//                            remove.add(key);
//                        }
//                    }
//
//                    remove.add(key);
//                } else {
//                    if (temp.get(key).getKiller() != null) {
//                        if (temp.get(key).getKiller().getName().equals("")) {
//                            remove.add(key);
//                        }
//                    }
//                }
//            }
//            if (!remove.isEmpty()) {
//                System.out.println("Внимание! Обнаружено неверное значение в поле элемента коллекции, " +
//                        "все элемены с неправильными значениями будут удалены.");
//                for (Long key : remove) {
//                    temp.remove(key);
//                }
//            }
//            collection.setCollection(temp);
//        } else {
//            System.out.println("Коллекция не была загружена.\n" +
//                    "Файл пуст. \n\n" +
//                    "Запуск программы без данных из файла.");
//        }
//    }

    /**
     * Метод для реализации команды execute_script
     *
     * @param path
     * @throws IOException
     */
    public String executeScript(String path, UserValidator user) throws IOException {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File(path)));
        byte[] contents = new byte[1024];
        int bytesRead = 0;
        String strFileContents = "";
        while ((bytesRead = stream.read(contents)) != -1) {
            strFileContents += new String(contents, 0, bytesRead);
        }
        Scanner scanner = new Scanner(strFileContents);
        String line = new String();
        String[] argument = new String[1];
        RegisteredCommands commands = new RegisteredCommands();
        Receiver virtualReceiver = new Receiver();
        DragonValidator virtualValidator = new DragonValidator();
        virtualReceiver.setValidator(virtualValidator);
        virtualReceiver.setCollection(collection);
        StringBuilder builder = new StringBuilder();
        OutputStream empty = new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        };
        builder.append("Приступаю к выполнению скрипта ").append(path).append("\n");
        while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split("\\s+");
            if (temp.length > 0) line = temp[0];
            if (temp.length >= 2) argument[0] = temp[1];
            if (commands.getCommandsName().containsKey(line) && !line.equals("execute_script")) {
                commands.getCommandsName().get(line).setReceiver(virtualReceiver);
                try {
                    if (commands.getCommandsWithDragons().contains(line)) {
                        virtualValidator.setId(Long.parseLong(argument[0]));
                        virtualValidator.setOwnerName(user.getUsername());
                        virtualValidator.validate(scanner, new PrintStream(empty));

                    }
                    builder.append(commands.getCommandsName().get(line).execute(argument));
                } catch (NoSuchElementException e) {
                    builder.delete(0, builder.length());
                    builder.append("Ошибка при добавлении дракона. Скрипт остановлен\n");
                }
            }
        }
        builder.append("Выполнение скрипта завершено.\n");
        return builder.toString();
    }

    /**
     * Метод для реализации команды exit
     */
    public void exit() {
        System.out.println("Завершение работы сервера.");
        System.exit(0);
    }

    /**
     * Метод для реализации команды remove_if_greater
     *
     * @param id
     */
    public String removeGreater(Long id, UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        if (id <= 0) return "ID не может быть меньше нуля!";
        StringBuilder check = new StringBuilder();
        List<Dragon> values = new ArrayList<>(collection.getCollection().values());
        values.stream()
                .filter(e -> e.makeValue() > collection.getCollection().get(id).makeValue())
                .filter(e -> e.getOwner().equals(user.getUsername()))
                .forEach(e -> {
                    remove(e.getId(), user);
                    check.append("yes");
                });
        return ((check.length() == 0) ? "В коллекции нет элементов с ID больше данного" :
                "Все элементы с ID больше данного удалены");
    }

    /**
     * Метод для реализации команды remove_greater_key
     *
     * @param id
     */
    public String removeGreaterKey(Long id, UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        if (id <= 0) return "ID не может быть меньше нуля!";
        List<Dragon> list = new ArrayList<Dragon>(collection.getCollection().values());
        StringBuilder check = new StringBuilder();
        list.stream()
                .filter(e -> e.getId() > id)
                .filter(e -> e.getOwner().equals(user.getUsername()))
                .forEach(e -> {
                    remove(e.getId(), user);
                    check.append("yes");
                });
        return ((check.length() == 0) ? "В коллекции нет элементов с ID больше данного, принадлежащих вам"
                : "Все элементы с ID больше данного удалены");

    }

    /**
     * Метод для реализации команды replace_if_greater
     *
     * @param id
     */
    public String replaceGreater(Long id, UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        if (id <= 0) return "ID не может быть меньше нуля!";
        Dragon dragon = new Dragon(validator);
        StringBuilder builder = new StringBuilder();
        collection.getCollection().entrySet().stream()
                .filter(e -> e.getKey().equals(id))
                .filter(e -> e.getValue().makeValue() < dragon.makeValue())
                .filter(e -> e.getValue().getOwner().equals(user.getUsername()))
                .forEach(e -> {
                    e.setValue(dragon);
                    builder.append("yes");
                });
        return ((builder.length() == 0) ? "Замены не произошло."
                : "Замена успешно произошла!");
    }

    /**
     * Метод для реализации команды min_by_name
     */
    public String minByName(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        return collection.getCollection()
                .values()
                .stream()
                .min(Comparator.comparing(Dragon::getName))
                .get().toString();
    }

    /**
     * Метод для реализации команды print_unique_killer
     */
    public String printUniqueKiller(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        StringBuilder builder = new StringBuilder("Вывод всех уникальных полей killer:\n");
        collection.getCollection().values().stream()
                .map(Dragon::getKiller)
                .distinct()
                .filter(Objects::nonNull)
                .forEach(e -> builder.append(e.toString()).append("\n"));
        return builder.toString();

    }

    /**
     * Метод для реализации команды print_field_ascending_description
     */
    public String printFieldAscendDesc(UserValidator user) {
        if (user == null)
            return "Вы не можете выполнять команды без авторизации!";
        if (collection.getCollection().isEmpty()) return "Коллекция пуста!";
        StringBuilder builder = new StringBuilder("Вывод полей description всех " +
                "драконов в коллекции в порядке возрастания:");
        collection.getCollection().entrySet()
                .stream().map(Map.Entry::getValue)
                .map(Dragon::getDescription)
                .sorted().forEach(e -> builder.append(e).append("\n"));
        return builder.toString();
    }

    /**
     * Регистрация нового пользователя
     */
    public String registration(UserValidator user) {
        try {
            dataBaseManager.userRegistration(user);
            return "Регистрация прошла успешно!";
        } catch (SQLException e) {
            return "Пользователь с таким именем уже зарегистрирован!";
        }
    }

    /**
     * Авторизация пользователя
     */
    public String login(UserValidator user) {
        if (dataBaseManager.userLogin(user)) {
            return "Вход осуществлён!";
        } else {
            return "Ошибка входа! Неверный логин или пароль.";
        }
    }

    public void setValidator(DragonValidator validator) {
        this.validator = validator;
    }

    /**
     * Getters and setters
     */

    public void setCollection(DragonCollection collection) {
        this.collection = collection;
    }
}
