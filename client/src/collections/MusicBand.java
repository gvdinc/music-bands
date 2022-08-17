package collections;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
//name, coordinates, creation, participants, singles, establishment, genre, best Album

/**
 * @author Grebenkin Vadim
 */

public class MusicBand implements Serializable {
    /**
     * Unique id of the band
     */
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
    // Значение этого поля должно генерироваться автоматически
    /**
     * name of the band
     */
    private String name; //Поле не может быть null, Строка не может быть пустой
    /**
     * coordinates of headquarters of the band
     */
    private Coordinates coordinates; //Поле не может быть null
    /**
     * date and time when the band object was added
     */
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    /**
     * number of participants in the band
     */
    private Long numberOfParticipants; //Поле может быть null, Значение поля должно быть больше 0
    /**
     * number of singles of the band
     */
    private Long singlesCount; //Поле может быть null, Значение поля должно быть больше 0
    /**
     * establishment date of the band
     */
    private LocalDateTime establishmentDate; //Поле может быть null
    /**
     * music genre of the band
     */
    private MusicGenre genre; //Поле не может быть null
    /**
     * best album of band
     */
    private Album bestAlbum; //Поле может быть null

    /**
     * Constructor
     */
    public MusicBand() {
        generateCreationDate();
    }

    /**
     * checks if current {@link MusicBand} object is correct
     */
    public boolean isCorrect() {
        boolean isCorrect = true;
        if (this.name == null || this.name.length() <= 0) {
            System.out.println("MusicBand " + this.id + ": no \"name\"!");
            isCorrect = false;
        }
        if (this.coordinates == null || !this.coordinates.isCorrect()) {
            System.out.println("MusicBand " + this.id + ": no \"coordinates\"!");
            isCorrect = false;
        }
        if (this.numberOfParticipants != null && this.numberOfParticipants <= 0) {
            System.out.println("MusicBand " + this.id + ": invalid number of participants!");
            this.numberOfParticipants = null;
        }
        if (this.singlesCount != null && this.singlesCount <= 0) {
            System.out.println("MusicBand " + this.id + ": invalid number of singles!");
            this.numberOfParticipants = null;
        }
        if (this.genre == null) {
            System.out.println("MusicBand " + this.id + ": no \"genre\"!");
            isCorrect = false;
        }
        if (this.bestAlbum != null && (this.bestAlbum.getSales() <= 0 || this.bestAlbum.getName() == null)) {
            System.out.println("MusicBand " + this.id + ": invalid Best Album");
            isCorrect = false;
        }

        if (isCorrect) {
            System.out.println("MusicBand " + this.id + ": Correct");
        }
        return isCorrect;
    }

    /**
     * generator of {@link #creationDate}
     */
    public void generateCreationDate() {
        this.creationDate = new Date();
    }

    /**
     * setter for {@link #establishmentDate}
     */
    public void setEstablishmentDate(String data) {

        data = data.replace(" ", "");
        String[] date = data.split(".");
        if (date.length > 3) {
            String[] time = date[3].split(":");
            if (time.length == 3) {
                LocalDate estDate = LocalDate.of(new Integer(date[2]), new Integer(date[1]), new Integer(date[0]));
                LocalTime estTime = LocalTime.of(new Integer(time[2]), new Integer(time[1]), new Integer(date[0]), 0);
                this.establishmentDate = LocalDateTime.of(estDate, estTime);
            }
        }
    }

    @Deprecated
    private void randomId(int id) {
        this.id = (int) (Math.random() * 1000000);
    }

    /**
     * setter for {@link #bestAlbum}
     */
    public void setBestAlbum(Album bestAlbum) {
        this.bestAlbum = bestAlbum;
    }

    /**
     * setter for {@link #singlesCount}
     */
    public void setSinglesCount(Long singlesCount) {
        this.singlesCount = singlesCount;
    }

    /**
     * setter for {@link #genre}
     */
    public void setGenre(String param) {
        if (param.equalsIgnoreCase("ROCK")) {
            this.genre = MusicGenre.ROCK;
        } else if (param.equalsIgnoreCase("PSYCHEDELIC_ROCK")) {
            this.genre = MusicGenre.PSYCHEDELIC_ROCK;
        } else if (param.equalsIgnoreCase("RAP")) {
            this.genre = MusicGenre.RAP;
        } else if (param.equalsIgnoreCase("HIP_HOP")) {
            this.genre = MusicGenre.HIP_HOP;
        } else {
            System.out.println("MusicBand " + this.id + ": unknown music genre! " + param);
        }
    }

    /**
     * getter for {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * setter for {@link #name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter for {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * setter for {@link #id}
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String res = "MB {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", singlesCount=" + singlesCount +
                ", establishmentDate=" + establishmentDate +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                '}';
        return res;
    }

    /**
     * method returns xml version of this music band
     *
     * @return String line with xml coded {@link MusicBand}
     */
    public String toXMLCode() {
        StringBuilder builder = new StringBuilder();
        if (this.isCorrect()) {
            // name
            builder.append("    <" + this.name + ">" + "\n");
            //params

            builder.append(codeParam("genre", this.genre.toString()));
            builder.append("        <coordinates>\n"
                    + "    " + codeParam("x", String.valueOf(this.coordinates.getX()))
                    + "    " + codeParam("y", String.valueOf(this.coordinates.getY()))
                    + "        </coordinates>\n"
            );
            try {
                builder.append("        <bestAlbum>\n"
                        + "    " + codeParam("name", this.bestAlbum.getName())
                        + "    " + codeParam("sales", String.valueOf(this.bestAlbum.getSales()))
                        + "        </bestAlbum>\n"
                );
            } catch (Exception e) {
            }

            // additional params
            if (this.numberOfParticipants != null)
                builder.append(codeParam("numberOfParticipants", this.numberOfParticipants.toString()));
            if (this.singlesCount != null)
                builder.append(codeParam("singlesCount", this.singlesCount.toString()));

            builder.append("    </" + this.name + ">\n");

        }
        return builder.toString();
    }

    /**
     * getter for {@link #numberOfParticipants}
     */
    public Long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * setter for {@link #numberOfParticipants}
     */
    public void setNumberOfParticipants(Long numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    /**
     * converts parameter into xml code
     */
    private String codeParam(String nameOfParam, String Content) {
        if (!Content.isEmpty())
            return "        <" + nameOfParam + ">" + Content + "</" + nameOfParam + ">\n";
        else
            return "";
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * setter for {@link #coordinates}
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
