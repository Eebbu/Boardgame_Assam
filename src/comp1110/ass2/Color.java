package comp1110.ass2;

public enum Color {
    RED('r', javafx.scene.paint.Color.rgb(134, 23, 0)), YELLOW('y', javafx.scene.paint.Color.rgb(213, 155, 0)), CYAN('c', javafx.scene.paint.Color.rgb(38, 96, 119)), PURPLE('p', javafx.scene.paint.Color.PURPLE), NOCOLOR('n', javafx.scene.paint.Color.GREY);

    // Color can be R Y C P
    public final char value;
    public final javafx.scene.paint.Color fillColor;

    Color(char value, javafx.scene.paint.Color fillColor) {
        this.value = value;
        this.fillColor = fillColor;
    }

    public javafx.scene.paint.Color getFillColor() {
        return fillColor;
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }

    public static Color fromChar(char colorChar) {
        for (Color color : Color.values()) {
            if (color.value == colorChar) {
                return color;
            }
        }
        // Handle invalid color character
        throw new IllegalArgumentException("Invalid color character: " + colorChar);
    }
}
