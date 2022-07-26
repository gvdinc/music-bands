package main.Comparators;

import collections.MusicBand;

import java.util.Comparator;
import java.util.Map;

public class ComparatorID implements Comparator<Map.Entry<Integer, MusicBand>> {
    @Override
    public int compare(Map.Entry<Integer, MusicBand> o1, Map.Entry<Integer, MusicBand> o2) {
        return Integer.compare(o1.getValue().getId(), o2.getValue().getId());
    }
}
