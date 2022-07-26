package commands;

import collections.MusicBand;
import main.CollectionHolder;
import main.Tools;

import java.util.function.Predicate;

/**
 * вывести элементы, значение поля numberOfParticipants которых меньше заданного
 */
public class CFilterLess extends Comand {
    private final CollectionHolder holder;

    public CFilterLess(CollectionHolder holder) {
        super(holder);
        this.holder = holder;
    }

    @Deprecated
    public void oldExecute(String input) {
        if (Tools.regSearch(input, "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return;
        }
        int[] mass = this.holder.getIDs();
        Long participants = new Long(input);
        for (int i = 0; i != mass.length; i++) {
            if (this.holder.getNumberOfParticipants(mass[i]) != null) {
                if (this.holder.getNumberOfParticipants(mass[i]) < (participants)) {
                    this.holder.readMapElement(mass[i]);
                }
            }
        }
    }

    @Override
    public void execute(String input) { // updated with stream CommandExecutor
        if (Tools.regSearch(input, "\\D")) {
            System.out.println("!!!wrong number of participants!!!");
            return;
        }
        Predicate<MusicBand> numberFilter = musicBand -> musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants() < new Long(input);
        this.holder.getMapStream().filter(numberFilter).forEach(System.out::println);
    }
}
