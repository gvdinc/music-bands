package commands;

import collections.MusicBand;
import main.CollectionHolder;
import main.CommandExecutor;

import java.util.Objects;
import java.util.Stack;
import java.util.function.Predicate;

/**
 * удалить из коллекции все элементы, меньшие, чем заданный
 */
public class CRemoveLower extends Comand {
    private final CollectionHolder holder;

    public CRemoveLower(CollectionHolder holder) {
        super(holder);
        this.holder = holder;
    }


    @Override
    public void cascadeRun(CommandExecutor commandExecutor, String params) {
        Long input = oldCascadeRun(commandExecutor, params);
        Stack<Integer> queueToDelete = new Stack<>();
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < new Long(input);
        this.holder.getMapStream().filter(numberFilter).forEach(mB -> {
            queueToDelete.push(mB.getId());
        });
        queueToDelete.forEach(a -> {
            this.holder.deleteElement(a);
        });

    }

    @Override
    public void execute(String input) {
    }

    @Deprecated
    public Long oldCascadeRun(CommandExecutor commandExecutor, String params) {

        String input;
        Long numbOfPart = null;

        try {
            input = commandExecutor.getLine("Input number of participants (input \"null\" to deny)");
            if (!Objects.equals(input, "null")) {
                try {
                    numbOfPart = new Long(input);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return numbOfPart;
    }
}
