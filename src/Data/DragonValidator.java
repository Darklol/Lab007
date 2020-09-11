package Data;

import com.sun.istack.internal.Nullable;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Scanner;

public class DragonValidator implements Serializable {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Integer age; //Значение поля должно быть больше 0, Поле может быть null
    private String description; //Поле не может быть null
    private Integer wingspan; //Значение поля должно быть больше 0, Поле может быть null
    private Color color; //Поле не может быть null
    private Person killer; //Поле может быть null


    public DragonValidator() {
    }

    public void validate(Scanner sc, PrintStream printStream) {
        boolean wrongInput = true;
        printStream.println("Введите имя дракона:");
        while (wrongInput) {
            this.name = (sc.nextLine());
            if (!name.equals("")) {
                wrongInput = false;
            } else {
                printStream.println("Поле не может быть пустым. Попробуйте ещё раз.");
            }
        }
        printStream.println();
        wrongInput = true;
        printStream.println("Введите координаты дракона:");
        setCoordinates();
        printStream.println("Введите координату x");
        while (wrongInput) {
            try {
                coordinates.setX(Long.parseLong(sc.nextLine()));
                wrongInput = false;
            } catch (NumberFormatException e) {
                printStream.println("Ошибка ввода! Попробуйте ввести ещё раз.");
            }
        }
        printStream.println("Введите координату y");
        wrongInput = true;
        while (wrongInput) {
            try {
                coordinates.setY(Long.parseLong(sc.nextLine()));
                if (coordinates.getY() > -324) {
                    wrongInput = false;
                } else {
                    printStream.println("Значение поля должно быть больше -324! Попробуйте ввести ещё раз.");
                }
            } catch (NumberFormatException e) {
                printStream.println("Ошибка ввода! Попробуйте ввести ещё раз.");
            }
        }
        printStream.println("Введите возраст дракона:");
        wrongInput = true;
        while (wrongInput) {
            try {
                String temp = sc.nextLine();
                if (!temp.equals("")) {
                    this.age = Integer.parseInt(temp);
                    if (age > 0) {
                        wrongInput = false;
                    } else {
                        printStream.println("Значение поля должно быть больше 0 либо null! Попробуйте ввести ещё раз.");
                    }
                } else {
                    age = null;
                    wrongInput = false;
                }
            } catch (IllegalArgumentException e) {
                printStream.println("Ошибка ввода! Попробуйте ввести ещё раз.");
            }
        }
        printStream.println();
        this.setCreationDate();
        printStream.println("Введите описание дракона: ");
        description = sc.nextLine();
        printStream.println();
        printStream.println("Введите размах крыльев дракона:");
        wrongInput = true;
        while (wrongInput) {
            try {
                String temp = sc.nextLine();
                if (!temp.equals("")) {
                    this.wingspan = Integer.parseInt(temp);
                    if (wingspan > 0) {
                        wrongInput = false;
                    } else {
                        printStream.println("Значение поля должно быть больше 0 либо null! Попробуйте ввести ещё раз.");
                    }
                } else {
                    wingspan = null;
                    wrongInput = false;
                }
            } catch (IllegalArgumentException e) {
                printStream.println("Ошибка ввода! Попробуйте ввести ещё раз.");
            }
        }
        printStream.println();
        printStream.println("Введите цвет дракона:");
        printStream.println("Доступные значения: BLACK, BLUE, YELLOW, ORANGE, BROWN");
        wrongInput = true;
        while (wrongInput) {
            String temp = sc.nextLine();
            try {
                color = Enum.valueOf(Color.class, temp);
                wrongInput = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
            }
        }
        printStream.println();
        printStream.println("Дракон побеждён?\n(Введите yes или no)");
        wrongInput = true;
        while (wrongInput) {
            String temp = sc.nextLine();
            if (temp.trim().equals("yes")) {
                String name = null;
                LocalDate birthday = null;
                Color eyeColor = null;
                Color hairColor = null;
                printStream.println("Введите данные убийцы дракона");
                printStream.println("Введите имя человека:");
                while (wrongInput) {
                    name = (sc.nextLine());
                    if (!name.equals("")) {
                        wrongInput = false;
                    } else {
                        printStream.println("Поле не может быть пустым. Попробуйте ещё раз.");
                    }
                }
                printStream.println();
                printStream.println("Введите день рождения человека (в формате dd-mm-yyyy): ");
                wrongInput = true;
                while (wrongInput) {
                    temp = sc.nextLine();
                    if (temp.equals("")) {
                        break;
                    }
                    try {
                        birthday = new SimpleDateFormat("dd-MM-yyyy").parse(temp)
                                .toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        wrongInput = false;
                    } catch (ParseException | NumberFormatException e) {
                        printStream.println("Неверный формат даты! Попробуйте ввести ещё раз. Формат даты: dd-mm-yyyy");
                    }

                }
                printStream.println();
                printStream.println("Введите цвет глаз человека:");
                printStream.println("Доступные значения: BLACK, BLUE, YELLOW, ORANGE, BROWN, NULL");
                wrongInput = true;
                while (wrongInput) {
                    temp = sc.nextLine();
                    if (!temp.equals("NULL")) {
                        try {
                            eyeColor = Enum.valueOf(Color.class, temp);
                            wrongInput = false;
                        } catch (IllegalArgumentException e) {
                            printStream.println("Ошибка ввода! Попробуйте ввести ещё раз.");
                        }
                    } else {
                        eyeColor = null;
                        wrongInput = false;
                    }
                }
                printStream.println();
                printStream.println("Введите цвет волос человека:");
                printStream.println("Доступные значения: BLACK, BLUE, YELLOW, ORANGE, BROWN, NULL");
                wrongInput = true;
                while (wrongInput) {
                    temp = sc.nextLine();
                    if (!temp.equals("NULL")) {
                        try {
                            hairColor = Enum.valueOf(Color.class, temp);
                            wrongInput = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Ошибка ввода! Попробуйте ввести ещё раз.");
                        }
                    } else {
                        hairColor = null;
                        wrongInput = false;
                    }
                }
                printStream.println("Данные о человеке введены.");
                killer = new Person(name, birthday, eyeColor, hairColor);
                wrongInput = false;
                System.out.println();
            } else {
                if (temp.trim().equals("no")) {
                    wrongInput = false;
                } else {
                    printStream.println("Ошибка ввода! Пожалуйста, введите yes или no)");
                }
            }
        }
        printStream.println("Данные о драконе введены.");
    }

    public void setCoordinates() {
        coordinates = new Coordinates();
    }

    public void setCreationDate() {
        creationDate = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
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

    public Color getColor() {
        return color;
    }

    public Person getKiller() {
        return killer;
    }
}
