package collections;

import java.io.Serializable;

/**
 * Album object for {@link MusicBand}
 */
public class Album implements Serializable {
    /**
     * The name of Album
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * Profit, got by the sales of album ($)
     */
    private float sales; //Значение поля должно быть больше 0

    public Album(){
        super();
    }
    public Album(String name, float sales){
        this.name = name;
        this.sales = sales;
    }
    /**
     * getter for {@link #sales}
     */
    public float getSales() {
        return sales;
    }

    /**
     * getter for {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * function setts parameters by pure XML String
     */
    public void setFromXML(String line) {
        if (!line.isEmpty()) {
            //System.out.println("Album parsing: " + line);
            StringXMLItem name = Tools.regSearch(line)[0];
            StringXMLItem sales = Tools.regSearch(line)[1];
            if (name.getName().equalsIgnoreCase("name") && sales.getName().equalsIgnoreCase("sales")) {

                if (!name.getContent().isEmpty() && !sales.getContent().isEmpty()) {
                    this.name = name.getContent();
                    this.sales = new Float(sales.getContent());
                }

            }
        }
    }

    @Override
    public String toString() {
        if (this.name != null && this.sales > 0) {
            return "Album{" +
                    "name='" + name + '\'' +
                    ", sales=" + sales +
                    '}';
        } else {
            return "null";
        }
    }

    /**
     * setter for {@link #name} and {@link #sales}
     *
     * @param name  - {@link #name}
     * @param sales - {@link #sales}
     */
    public Album setAll(String name, Float sales) {
        this.name = name;
        this.sales = sales;
        return this;
    }
}
