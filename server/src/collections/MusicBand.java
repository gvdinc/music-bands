package collections;

import common.User;

import java.io.Serializable;
import java.util.Date;
//name, coordinates, creation, participants, singles, establishment, genre, best Album

/**
 * Object of Music band's data to store in {@link main.CollectionHolder}
 *
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
    //private LocalDateTime establishmentDate; //Поле может быть null
    /**
     * music genre of the band
     */
    private MusicGenre genre; //Поле не может быть null
    /**
     * best album of band
     */
    private Album bestAlbum; //Поле может быть null
    private String username;

    /**
     * Constructor
     */
    public MusicBand() {
        generateCreationDate();
    }

    public MusicBand(Date creationDate) {
        this.creationDate = creationDate;
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
        if (this.numberOfParticipants != null && this.numberOfParticipants < 0) {
            System.out.println("MusicBand " + this.id + ": invalid number of participants!");
            this.numberOfParticipants = null;
        }
        if (this.singlesCount != null && this.singlesCount < 0) {
            System.out.println("MusicBand " + this.id + ": invalid number of singles!");
            this.numberOfParticipants = null;
        }
        if (this.genre == null) {
            System.out.println("MusicBand " + this.id + ": no \"genre\"!");
            isCorrect = false;
        }
        if (this.bestAlbum != null && (this.bestAlbum.getSales() < 0 || this.bestAlbum.getName() == null)) {
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

    @Override
    public String toString() {
        String res = "MB {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", singlesCount=" + singlesCount +
                ", genre=" + genre +
                ", bestAlbum=" + bestAlbum +
                ", user=" + username +
                '}';
        return res;
    }


    public void setBestAlbum(Album bestAlbum) {
        if (bestAlbum.getName() != null && bestAlbum.getSales() > 0) this.bestAlbum = bestAlbum;
    }

    public void setSinglesCount(Long singlesCount) {
        if (singlesCount > 0){ this.singlesCount = singlesCount; }
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumberOfParticipants(Long numberOfParticipants) {
        if (numberOfParticipants > 0) this.numberOfParticipants = numberOfParticipants;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setUser(User user) {
        if (user == null) return;
        this.username = user.getUsername();
    }

    public void setUser(String username) {
        this.username = username;
    }


    public int getId() {
        return id;
    }
    public MusicGenre getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getSinglesCount() {
        return singlesCount;
    }

    public Album getBestAlbum() {
        return bestAlbum;
    }

    public String getUsername() {
        return username;
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
}
