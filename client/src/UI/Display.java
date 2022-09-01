package UI;

import collections.MusicBand;
import common.ReplyPack;

import java.util.Map;

public class Display{
    public static void displayPackData(ReplyPack pack){
        if (pack == null) return;
        System.out.println(pack.getCommandType() + " operation " + (pack.isOperationSucceeded() ? "succeeded" : "failed"));
        if (pack.getMessage() != null ) System.out.println(pack.getMessage().trim());
        System.out.println("--------------------------------------------------------------");

        Map<Integer, MusicBand> map = pack.getMap();
        if (map == null) return;
        System.out.println("\n" + "current data: " + "(" + map.size() + ")");
        map.values().forEach(band -> {
            System.out.println(band.getId() + ". " + band);
        });
        if (map.size() <= 0) System.out.println("Collection empty");
        System.out.print('\n');
    }


}
