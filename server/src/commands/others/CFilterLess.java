package commands.others;

import collections.MusicBand;
import commands.Command;
import common.CTransitPack;
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

    public CFilterLess(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public boolean execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return false;
        }
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < new Long(this.getParam());
        cHolder.getMapStream().filter(numberFilter).forEach(System.out::println);
        return true;
    }

}
