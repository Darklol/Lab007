package Data;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * класс Dragon, элементы этого класса наполняют коллекцию
 */
@Getter
public class Dragon implements Comparable<Dragon>, Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null
    private String description; //Поле не может быть null
    private Integer wingspan; //Значение поля должно быть больше 0, Поле может быть null
    private Color color; //Поле не может быть null
    private Person killer; //Поле может быть null

    @Getter @Setter
    private String owner;

    /**
     * Более читабельный вид toString()
     * @return
     */
    @Override
    public String toString() {
        return "Дракон \n" +
                "id: " + id + "\n" +
                "имя: " + name + "\n" +
                "Координаты:" + coordinates + "\n" +
                "Дата создания:" + creationDate + "\n" +
                "Возраст:" + age + "\n" +
                "Описание:" + description + "\n" +
                "Размах крыльев:" + wingspan + "\n" +
                "Цвет:" + color + "\n" +
                "Убийца:" + killer + "\n\n" +
                "Владелец: " + owner;
    }

    /**
     * Конструктор, в котором вводятся значения полей с клавиатуры.
     * Тут же происходит проверка правильности значений.
     * @param
     */
    public Dragon(DragonValidator validator){
        age = validator.getAge();
        name = validator.getName();
        id = validator.getId();
        coordinates = validator.getCoordinates();
        creationDate = validator.getCreationDate();
        description = validator.getDescription();
        wingspan = validator.getWingspan();
        color = validator.getColor();
        killer = validator.getKiller();
        owner = validator.getOwnerName();
    }

    public Dragon(String name, Coordinates coordinates, LocalDate creationDate,
                  Integer age, String description, Integer wingspan, Color color, Person killer){
        this.age = age;
        this.killer = killer;
        this.color = color;
        this.wingspan = wingspan;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
    }
//    public Dragon(long id) {
//        boolean wrongInput = true;
//        this.id = id;
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Введите имя дракона:");
//        while (wrongInput) {
//            this.name = (sc.nextLine());
//            if (!name.equals("")) {
//                wrongInput = false;
//            } else {
//                System.out.println("Поле не может быть пустым. Попробуйте ещё раз.");
//            }
//        }
//        System.out.println();
//        wrongInput = true;
//        System.out.println("Введите координаты дракона:");
//        setCoordinates();
//        System.out.println("Введите координату x");
//        while (wrongInput) {
//            try {
//                coordinates.setX(Long.parseLong(sc.nextLine()));
//                wrongInput = false;
//            } catch (NumberFormatException e) {
//                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
//            }
//        }
//        System.out.println("Введите координату y");
//        wrongInput = true;
//        while (wrongInput) {
//            try {
//                coordinates.setY(Long.parseLong(sc.nextLine()));
//                if (coordinates.getY() > -324) {
//                    wrongInput = false;
//                } else {
//                    System.out.println("Значение поля должно быть больше -324! Попробуйте ввести ещё раз.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
//            }
//        }
//        System.out.println("Введите возраст дракона:");
//        wrongInput = true;
//        while (wrongInput) {
//            try {
//                String temp = sc.nextLine();
//                if (!temp.equals("")) {
//                    this.age = Integer.parseInt(temp);
//                    if (age > 0) {
//                        wrongInput = false;
//                    } else {
//                        System.out.println("Значение поля должно быть больше 0 либо null! Попробуйте ввести ещё раз.");
//                    }
//                } else {
//                    age = null;
//                    wrongInput = false;
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
//            }
//        }
//        System.out.println();
//        this.setCreationDate();
//        System.out.println("Введите описание дракона: ");
//        description = sc.nextLine();
//        System.out.println();
//        System.out.println("Введите размах крыльев дракона:");
//        wrongInput = true;
//        while (wrongInput) {
//            try {
//                String temp = sc.nextLine();
//                if (!temp.equals("")) {
//                    this.wingspan = Integer.parseInt(temp);
//                    if (wingspan > 0) {
//                        wrongInput = false;
//                    } else {
//                        System.out.println("Значение поля должно быть больше 0 либо null! Попробуйте ввести ещё раз.");
//                    }
//                } else {
//                    wingspan = null;
//                    wrongInput = false;
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
//            }
//        }
//        System.out.println();
//        System.out.println("Введите цвет дракона:");
//        System.out.println("Доступные значения: BLACK, BLUE, YELLOW, ORANGE, BROWN");
//        wrongInput = true;
//        while (wrongInput) {
//            String temp = sc.nextLine();
//            try {
//                color = Enum.valueOf(Color.class, temp);
//                wrongInput = false;
//            } catch (IllegalArgumentException e) {
//                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
//            }
//        }
//        System.out.println();
//        System.out.println("Дракон побеждён?\n(Введите yes или no");
//        wrongInput = true;
//        while (wrongInput) {
//            String temp = sc.nextLine();
//            if (temp.trim().equals("yes")) {
//                wrongInput = false;
//                System.out.println("Введите данные убийцы дракона");
//                killer = new Person("string", null, null, null);
//            } else {
//                if (temp.trim().equals("no")) {
//                    wrongInput = false;
//                } else {
//                    System.out.println("Ошибка ввода! Пожалуйста, введите yes или no)");
//                }
//            }
//        }
//        System.out.println("Данные о драконе введены.");
//    }

    public boolean hasKiller(){
        return killer == null ? false : true;
    }

    public String getCreatinoDateInFormat(){
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(creationDate);
    }
    /**
     * Метод, который генерирует значение, по которому сравниваются драконы
     * @return
     */
    public long makeValue() {
        return (id - age + 42);
    }

    /**
     * Устанавливает дату создания дракона на текущую
     */
//    public void setCreationDate() {
//        this.creationDate = LocalDate.now();
//    }
//
//    public void setCreationDate(LocalDate date) {
//        this.creationDate = date;
//    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWingspan() {
        return wingspan;
    }

    @Override
    public int compareTo(Dragon o) {
        return Long.compare(makeValue(),o.makeValue());
    }
}