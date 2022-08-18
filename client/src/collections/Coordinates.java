package collections;

import java.io.Serializable;

/**
 * object for {@link MusicBand} contains coordinates of headquarters of the band
 */
public class Coordinates implements Serializable {

    /**
     * coordinate x
     */
    private float x;
    /**
     * coordinate y
     */
    private Float y; //Максимальное значение поля: 938, Поле не может быть null

    /**
     * checking if the {@link #x} and {@link #y} are correct
     */
    public boolean isCorrect() {
        try {
            return this.x > 0 && this.y >= -938 && this.y <= 938;
        } catch (Exception e) {
            System.out.println("exception while checking coordinates");
            return false;
        }
    }

    /**
     * function setts parameters by pure XML String
     */
    public void setFromXML(String line) {
        //System.out.println("Coordinates parsing: "+ line);
        StringXMLItem x = Tools.regSearch(line)[0];
        StringXMLItem y = Tools.regSearch(line)[1];
        try {
            if (x.getName().equalsIgnoreCase("x") && y.getName().equalsIgnoreCase("y")) {
                this.x = new Float(x.getContent());
                this.y = new Float(y.getContent());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * getter for {@link #x}
     */
    public float getX() {
        return x;
    }

    /**
     * getter for {@link #y}
     */
    public Float getY() {
        return y;
    }

    /**
     * setter for {@link #x} and {@link #y}
     */
    public Coordinates setAll(Float x, Float y) {
        this.x = x;
        this.y = y;
        return this;
    }
}
