package commands;

import collections.Album;
import collections.Coordinates;
import collections.MusicBand;
import main.CollectionHolder;
import main.CommandExecutor;
import main.Tools;

import java.util.Objects;

/**
 * заменить значение по ключу, если новое значение меньше старого
 */
public class CReplaceIfLower extends Comand {
    private final CollectionHolder cHolder;

    public CReplaceIfLower(CollectionHolder holder) {
        super(holder);
        this.cHolder = holder;
    }

    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) {
        System.out.println(params);
        if (Tools.regSearch(params, "\\D") || params.isEmpty()) {
            System.out.println("!!!wrong id!!!");
            return;
        }
        MusicBand newBand = new MusicBand();
        String input;
        String[] album;
        String[] coordinates;

        while (!(newBand.isCorrect())) {
            newBand = new MusicBand();

            try {
                newBand.setName(commandExecutor.getLine("(Enter name of music band)"));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            try {
                newBand.setGenre(commandExecutor.getLine("Choose genre (ROCK,\n" +
                        "    PSYCHEDELIC_ROCK,\n" +
                        "    RAP,\n" +
                        "    HIP_HOP)"));
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            try {
                System.out.println("Input coordinates x and y (float, separator = \" \")");
                coordinates = commandExecutor.getLine().split(" ");
                newBand.setCoordinates(new Coordinates().setAll(new Float(coordinates[0]), new Float(coordinates[1])));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }

            try {
                System.out.println("Input best Album (String name and float sales), separator = \" \", (input \"null\" to deny)");
                album = commandExecutor.getLine().split(" ");
                if (!Objects.equals(album[0], "null") && !album[0].isEmpty()) {
                    newBand.setBestAlbum(new Album().setAll(album[0], new Float(album[1])));
                } else {
                    newBand.setBestAlbum(null);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }

            try {
                input = commandExecutor.getLine("Input number of participants (input \"null\" to deny)");
                if (!Objects.equals(input, "null") && !input.isEmpty()) {
                    newBand.setNumberOfParticipants(new Long(input));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            try {
                input = commandExecutor.getLine("Input number of singles (input \"null\" to deny)");
                if (!Objects.equals(input, "null") && !input.isEmpty()) {
                    newBand.setSinglesCount(new Long(input));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (newBand.isCorrect()) {
                int id;
                try {
                    id = new Integer(params);
                } catch (NumberFormatException e) {
                    System.out.println("!!!wrong id!!!");
                    return;
                }

                newBand.setId(id);
                if (newBand.getNumberOfParticipants() < this.cHolder.getNumberOfParticipants(id)) {
                    cHolder.replaceGroup(newBand);
                    System.out.println("finished");
                    break;
                } else {
                    System.out.println("Element with this id has equals or bigger amount of participants");
                }

            }

            System.out.println("Repeat creation");

        }
    }

    @Override
    public void execute(String input) {

    }


}
