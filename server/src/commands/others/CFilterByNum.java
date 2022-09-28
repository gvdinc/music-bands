package commands.others;

import collections.MusicBand;
import commands.Command;

import common.CTransitPack;
import common.Commands;
import common.ReplyPack;
import main.CollectionHolder;
import main.Tools;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * вывести элементы, значение поля numberOfParticipants которых равно заданному
 */
public class CFilterByNum extends Command {

    public CFilterByNum(Commands type, String param) {
        super(type, param);
    }

    public CFilterByNum(CTransitPack transitPack) {
        super(transitPack);
    }

    @Override
    public ReplyPack execute(CollectionHolder cHolder) {
        if (Tools.regSearch(this.getParam(), "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return new ReplyPack(Commands.FILTER_NUM, false);
        }
        Predicate<MusicBand> numberFilter = musicBand -> Objects.equals(musicBand.getNumberOfParticipants(), new Long(this.getParam()));
        StringBuilder builder = new StringBuilder();
        cHolder.getMapStream().filter(numberFilter).forEach(builder::append);
        return new ReplyPack(Commands.FILTER_NUM, true, builder.toString());
    }

}
