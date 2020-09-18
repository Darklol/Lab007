package Data;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Type Person Class
 */
@Getter @Setter
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private java.util.Date birthday = null; //Поле может быть null
    private Color eyeColor = null; //Поле может быть null
    private Color hairColor = null; //Поле может быть null

    /**
     * Пустой конструктор, нужен для корректного считывания коллекции из файла (костыль)
     */
    public Person(){};

    /**
     * Стандартный конструктор, который вызывается в конструкторе класса Dragon
     * @param
     */
    public Person(String name, Date birthday, Color eyeColor, Color hairColor) {
        this.name = name;
        this.birthday = birthday;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
    }

    public String getBirthdayInFormat(){
        if (birthday == null) return null;
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(birthday);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", eyeColor=" + eyeColor +
                ", hairColor=" + hairColor +
                '}';
    }

    /**
     * Переопределение метода Equals для грамотного сравнения экземпляров
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                Objects.equals(birthday, person.birthday) &&
                eyeColor == person.eyeColor &&
                hairColor == person.hairColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, birthday, eyeColor, hairColor);
    }

    public String getName() {
        return name;
    }
}