package main;

import collections.Album;
import collections.Coordinates;
import collections.MusicBand;

import java.util.Locale;
import java.util.Objects;

public class CollectionCreator {
    public static MusicBand getClientBand(CommandExecutor commandExecutor) {
        MusicBand newBand = new MusicBand();

        while (!(newBand.isCorrect())) {
            newBand = new MusicBand();

            // setting name of new band
            newBand.setName(getName(commandExecutor));

            // setting coordinates
            newBand.setCoordinates(getCoordinates(commandExecutor));

            // setting genre
            newBand.setGenre(getGenre(commandExecutor));

            // setting album
            Album album = getBestAlbum(commandExecutor);
            if (album != null) {
                newBand.setBestAlbum(album);
            }


            // setting number of participants
            Long numberOfParts = getNumberOfParticipants(commandExecutor);
            if (numberOfParts != null) {
                newBand.setNumberOfParticipants(numberOfParts);
            }
            // created
        }

        return newBand;
    }

    protected static String getName(CommandExecutor commandExecutor) {
        String name;
        name = commandExecutor.getLine("(Enter name of music band)");
        while (name == null || Objects.equals(name, "null")) {
            System.out.println("Wrong name");
            name = commandExecutor.getLine("(Enter name of music band)");
        }
        return name;
    }

    protected static Coordinates getCoordinates(CommandExecutor commandExecutor) {
        Coordinates resCord = new Coordinates();
        String[] coordinates = null;
        while (!resCord.isCorrect()) {
            while (coordinates == null || coordinates.length != 2) {
                System.out.println("Input coordinates x and y (float, separator = \" \")");
                coordinates = commandExecutor.getLine().split(" ");
            }
            resCord.setAll(new Float(coordinates[0]), new Float(coordinates[1]));
        }
        return resCord;
    }

    protected static String getGenre(CommandExecutor commandExecutor) {
        String genre = null;
        while (genre == null || (!genre.equals("ROCK") && !genre.equals("PSYCHEDELIC_ROCK") && !genre.equals("RAP") && !genre.equals("HIP_HOP"))) {
            try {
                genre = (commandExecutor.getLine("Choose genre (ROCK,\n" +
                        "    PSYCHEDELIC_ROCK,\n" +
                        "    RAP,\n" +
                        "    HIP_HOP)").toUpperCase(Locale.ROOT));
            } catch (Exception e) {
                genre = null;
            }
        }
        return genre;
    }

    protected static Album getBestAlbum(CommandExecutor commandExecutor) {
        Album resAlbum = null;
        String[] album = new String[1];
        boolean flag = true;
        while (flag) {
            System.out.println("Input best Album (String name and float sales), separator = \" \", (input \"null\" to deny)");
            album = commandExecutor.getLine().split(" ");
            if (Objects.equals(album[0], "null") && !album[0].isEmpty()) {
                return null;
            } else {
                try {
                    resAlbum = new Album().setAll(album[0], new Float(album[1]));
                    flag = false;
                } catch (NumberFormatException e) {
                    flag = true;
                }
            }
        }
        return resAlbum;
    }

    protected static Long getNumberOfParticipants(CommandExecutor commandExecutor) {
        Long resNumber = null;
        String input = "";
        boolean flag = false;
        while (!Objects.equals(input, "null") || input.isEmpty() || flag) {
            input = commandExecutor.getLine("Input number of participants (input \"null\" to deny)");
            if (!Objects.equals(input, "null") && !input.isEmpty()) {
                try {
                    resNumber = new Long(input);
                    flag = false;
                } catch (NumberFormatException e) {
                    flag = true;
                }
            } else if (Objects.equals(input, "null") && !input.isEmpty()) {
                return null;
            }
        }
        return resNumber;
    }
}
