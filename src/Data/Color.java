package Data;

import lombok.Getter;

import java.io.Serializable;

/**
 * The Enum Color
 */
public enum Color implements Serializable {
    /**
     * Black color
     */
    BLACK ("BLACK"),
    /**
     * Blue color
     */
    BLUE ("BLUE"),
    /**
     * Yellow color
     */
    YELLOW ("YELLOW"),
    /**
     * Orange color
     */
    ORANGE ("ORANGE"),
    /**
     * Brown color
     */
    BROWN ("BROWN");

    Color(String color){ this.color = color;}

    @Getter
    private String color;

    @Override
    public String toString() {
        return getColor();
    }
}