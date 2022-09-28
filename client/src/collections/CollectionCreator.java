package collections;



import client.KeyboardReader;
import common.User;

import java.util.Locale;
import java.util.Objects;

public class CollectionCreator {
    public static MusicBand getClientBand(User user) {
        MusicBand newBand = new MusicBand();

        while (!(newBand.isCorrect())) {
            newBand = new MusicBand();

            // setting name of new band
            newBand.setName(getName());

            // setting coordinates
            newBand.setCoordinates(getCoordinates());

            // setting genre
            newBand.setGenre(getGenre());

            // setting album
            Album album = getBestAlbum();
            if (album != null) {
                newBand.setBestAlbum(album);
            }

            // setting number of participants
            Long numberOfParts = getNumberOfParticipants();
            if (numberOfParts != null) {
                newBand.setNumberOfParticipants(numberOfParts);
            }
            // created
            newBand.setUser(user);
        }

        return newBand;
    }

    protected static String getName() {
        String name;
        name = KeyboardReader.input("(Enter name of music band)");
        while (name == null || Objects.equals(name, "null")) {
            System.out.println("Wrong name");
            name = KeyboardReader.input("(Enter name of music band)");
        }
        return name;
    }

    protected static Coordinates getCoordinates() {
        Coordinates resCord = new Coordinates();
        String[] coordinates = null;
        boolean invalid = false;
        while (!resCord.isCorrect()) {
            if (invalid) coordinates = null;
            while (coordinates == null || coordinates.length != 2) {
                String input = KeyboardReader.input("Input coordinates x and y (float, separator = \" \")");
                coordinates = (input != null) ? input.split(" ") : null;
            }
            resCord.setAll(new Float(coordinates[0]), new Float(coordinates[1]));
            if (!resCord.isCorrect()){invalid = true; System.out.println("Incorrect value of the coordinate!");}
        }
        return resCord;
    }

    protected static String getGenre() {
        String genre = null;
        while (genre == null || (!genre.equals("ROCK") && !genre.equals("PSYCHEDELIC_ROCK") && !genre.equals("RAP") && !genre.equals("HIP_HOP"))) {
            try {
                genre = (KeyboardReader.input("Choose genre (ROCK,\n" +
                        "    PSYCHEDELIC_ROCK,\n" +
                        "    RAP,\n" +
                        "    HIP_HOP)").toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                genre = null;
            }
        }
        return genre;
    }

    protected static Album getBestAlbum() {
        Album resAlbum = null;
        boolean notReady = true;
        String name = null;
        String sales = null;
        name = KeyboardReader.input("Input best Album name,(input \"null\" to deny)");
        if (name == null) return null;

        while (notReady) {
            sales = KeyboardReader.input("Input best Album sales (float)");
            if (sales == null) {
                System.out.println("wrong input");
                break;
            } else {
                Float floatSales = null;
                try {
                    floatSales = new Float(sales);
                } catch (NumberFormatException e) {
                    System.out.println("wrong input, album creation denied");
                }
                if (floatSales == null) {
                    continue;
                }
                resAlbum = new Album().setAll(name, floatSales);
                notReady = false;
            }
        }
        return resAlbum;
    }


    protected static Long getNumberOfParticipants() {
        Long resNumber = null;
        String input = null;
        boolean notReady = true;
        while (input == null || notReady) {
            input = KeyboardReader.input("Input number of participants (input \"null\" to deny)");
            if (input == null) {
                return null;
            }
            try {
                resNumber = new Long(input);
                notReady = false;
            } catch (NumberFormatException e) {
                System.out.println("wrong number - "+ input);
            }

        }
        return resNumber;
    }
}
