package commands.others;

import collections.MusicBand;
import common.Command;
import common.Commands;
import main.CollectionHolder;

import java.util.Stack;
import java.util.function.Predicate;

/**
 * удалить из коллекции все элементы, меньшие, чем заданный
 */
public class CRemoveLower extends Command {


    public CRemoveLower(Commands type, String param) {
        super(type, param);
    }

    @Override
    public void execute(CollectionHolder cHolder) {
        Long numberOfParticipants = this.getReceivedBand().getNumberOfParticipants();
        Stack<Integer> queueToDelete = new Stack<>();
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < numberOfParticipants;
        cHolder.getMapStream().filter(numberFilter).forEach(mB -> {
            queueToDelete.push(mB.getId());
        });
        queueToDelete.forEach(cHolder::deleteElement);

    }

}
