package commands.others;

import collections.MusicBand;
import commands.Command;
import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
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
    public ReplyPack execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return new ReplyPack(Commands.FILTER_LESS, false);
        }
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < new Long(this.getParam());
        StringBuilder builder = new StringBuilder();
        cHolder.getMapStream().filter(numberFilter).forEach(builder::append);
        return new ReplyPack(Commands.FILTER_LESS, true, builder.toString());
    }

}
