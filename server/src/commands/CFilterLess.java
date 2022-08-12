package commands;

import collections.MusicBand;
import common.Commands;
import main.CollectionHolder;
import main.Tools;

import java.util.function.Predicate;

/**
 * вывести элементы, значение поля numberOfParticipants которых меньше заданного
 */
public class CFilterLess extends Command {

    public CFilterLess(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return;
        }
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < new Long(this.getParam());
        cHolder.getMapStream().filter(numberFilter).forEach(System.out::println);
    }

}
