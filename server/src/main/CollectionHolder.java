package main;

import collections.Album;
import collections.Coordinates;
import collections.MusicBand;
import main.Comparators.ComparatorID;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * CollectionHolder stores and operates with data.
 * All data stores in HashMap (elements are MusicBand classes)
 *
 * @author Grebenkin Vadim
 */
public class CollectionHolder {
    /**
     * pass-link to the loading xml file
     */
    private String dataPath;
    private int mapLength = 0;
    /**
     * map is a {@link HashMap} that stores all the music groups data
     */
    private Map<Integer, MusicBand> map = new HashMap<>();
    /**
     * input stream from the client
     */
    private BufferedInputStream inStream;
    /**
     * mass is a sorted array of map's ids
     */
    private int[] mass;
    /**
     * creationDate contents the date of CollectionHolder creation
     */
    private Date creationDate;


    /**
     * Constructor. Automatically sort object after creation
     *
     * @param pass - a pass-link to the xml file with data
     */
    public CollectionHolder(String pass) {
        this.dataPath = pass;
        try {
            this.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.sort(HolderSortTypes.DEFAULT);
    }


    /**
     * Procedure sorts exising array (mass)
     *
     * @param type - (enum) sorting attribute
     */
    public void sort(HolderSortTypes type) {
        if (type == HolderSortTypes.DEFAULT) { // сортировка по id
            this.mass = new int[this.mapLength];
            int i = 0;
            for (Map.Entry<Integer, MusicBand> entry : this.map.entrySet()) {
                if (i < mass.length) {
                    mass[i] = entry.getKey();
                    i++;
                }
            }
        } else if (type == HolderSortTypes.NumberOfParticipants) {
            for (int i = 0; i + 1 < mass.length; ++i) {
                for (int j = 0; j + 1 < mass.length - i; ++j) {
                    if (this.map.get(mass[j + 1]).getNumberOfParticipants() < this.map.get(mass[j]).getNumberOfParticipants()) {
                        int tmp = mass[j];
                        mass[j] = mass[j + 1];
                        mass[j + 1] = tmp;
                    }
                }
            }
        }
    }

    public void ssort(HolderSortTypes type) { // Stream CommandExecutor
        Stream<Map.Entry<Integer, MusicBand>> streamOfBands = this.map.entrySet().stream().sorted();

        switch (type) {
            case DEFAULT:
                System.out.println(streamOfBands.sorted());

//            case NumberOfParticipants:
//                System.out.println(streamOfBands.sorted());

        } // choosing sorting mode
    }


    /**
     * exports existing Map into file result.xlm
     */
    public void exportXML(/*String pass*/) {
        System.out.println("\nexporting xml...");
        try (FileWriter writer = new FileWriter("result.xml")) {
            writer.write(this.toXML());
            writer.flush();
            System.out.println("successfully exported\n");
        } catch (IOException e) {
            System.out.println("export failed");
        }
    }

    /**
     * converts storing map into xml code (String)
     *
     * @return String line with xml coded map
     */
    private String toXML() {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\"?>\n");
        builder.append("<MusicBands>\n");
        for (int bandID : this.mass) {
            builder.append(this.map.get(bandID).toXMLCode());
        }
        builder.append("</MusicBands>\n");
        return builder.toString();
    }

    /**
     * loads data from xml file
     *
     * @throws IOException
     */
    void loadData() throws IOException {
        this.inStream = this.getInputer();
        if (this.inStream != null) {
            this.parse();
            this.creationDate = new Date();
        }
    }

    /**
     * parsing xml data from file
     * as a result fills the map with new data
     *
     * @throws IOException
     */
    private void parse() throws IOException { //преобразует файл в Map
        String line = "";
        this.map = new HashMap<>();
        while (inStream.available() > 0) {
            char currentSymbol = (char) this.inStream.read();
            line += currentSymbol;
        }
        // получаем Строку с файлом


        StringXMLItem[] groups = Tools.regSearch(line);
        groups = Tools.regSearch(groups[0].getContent()); // уровень муз-групп

        StringXMLItem[][] params = new StringXMLItem[groups.length][0];
        for (int i = 0; i != groups.length; i++) {
            params[i] = Tools.regSearch(groups[i].getContent());
        } // уровень параметров

        MusicBand tmpBand;
        for (int i = 0; i != groups.length; i++) {
            tmpBand = new MusicBand();
            tmpBand.setName(groups[i].getName());
            tmpBand.setId(i + 1);
            for (int p = 0; p != params[i].length; p++) {
                String teg = params[i][p].getName();
                String param = params[i][p].getContent();
                try {
                    switch (teg) {
                        case ("numberOfParticipants"):
                            tmpBand.setNumberOfParticipants(new Long(param));
                            break;
                        case ("singlesCount"):
                            tmpBand.setSinglesCount(new Long(param));
                            break;
                        case ("establishmentDate"):
                            tmpBand.setEstablishmentDate(param);
                            break;
                        case ("genre"):
                            tmpBand.setGenre(param);
                            break;
                        case ("coordinates"):
                            Coordinates cord = new Coordinates();
                            cord.setFromXML(param);
                            tmpBand.setCoordinates(cord);
                            break;
                        case ("bestAlbum"):
                            Album album = new Album();
                            album.setFromXML(param);
                            tmpBand.setBestAlbum(album);
                            break;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            if (tmpBand.isCorrect()) {
                this.map.put(tmpBand.getId(), tmpBand);
                this.mapLength++;
                System.out.println("Successfully added " + tmpBand.getId() + " (" + tmpBand.getName() + ")" + '\n');
            }
        }
        //создаём объекты муз-групп


    }

    /**
     * prints the {@link #map} elements into user's console
     *
     * @since 16.05.22 updated: stream CommandExecutor added
     */
    public void readMap() {
        System.out.println("\n" + "current data: " + "(" + this.map.size() + ")");
        this.map.values().forEach(band -> {
            System.out.println(band.getId() + ". " + band);
        });
        if (this.map.size() <= 0) System.out.println("Collection empty");
        System.out.print('\n');
    }

    /**
     * prints the current {@link #map} element into user's console
     *
     * @param bandID - the id of the printing element
     */
    public void readMapElement(int bandID) {
        if (this.map.get(bandID) != null)
            System.out.println(bandID + ". " + this.map.get(bandID).toString());
        else System.out.println("no element");
    }

    /**
     * Prints the information about {@link #map}
     */
    public void mapInfo() {
        System.out.print('\n');
        System.out.println("app.collections in storage " + "(" + this.mass.length + ")");
        System.out.println("creation date, time: " + this.creationDate);
        System.out.println("type of collection: " + this.map.getClass().getName());
        System.out.println("xml file pass: " + this.dataPath);
        System.out.print('\n');
    }

    /**
     * function gets the client's input stream
     *
     * @return client's input stream {@link Scanner}
     * @throws IOException
     */
    public BufferedInputStream getInputer() throws IOException {
        File file = new File(this.dataPath);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: xml file not found! (" + this.dataPath + ")");
            return null;
        }
        BufferedInputStream inputStream = new BufferedInputStream(is);
        if (inputStream.available() > 0) {
            System.out.println("Holder: file found");
        } else {
            System.out.println("Holder: file not found");
        }
        return inputStream;
    }


    /**
     * Checks if the client stream initialised
     *
     * @return returns "true" if client's input stream initialised
     */
    public boolean checkInputStream() {
        return this.inStream != null;
    }


    /**
     * Sets the pass-link to the xml file to the new one
     *
     * @param line - new pass-link to the xml file
     */
    public void setPass(String line) {
        this.dataPath = line;
    }

    /**
     * deletes all the storing MusicBands in {@link #map}
     */
    public void clearMap() {
        this.map.clear();
        mass = new int[0];
        System.out.println("Holder: Collection cleared");
    }

    /**
     * adds new group to the {@link #map}
     *
     * @param newBand - new {@link MusicBand} to be added
     */
    public void addNewGroup(MusicBand newBand) {
        this.mass = Tools.appendInt(this.mass, newBand.getId());
        this.map.put(newBand.getId(), newBand);
        System.out.println("Holder: successfully added");
    }

    /**
     * {@link #mapLength} getter
     */
    public int getMapLength() {
        return mapLength;
    }


    /**
     * adding the new {@link MusicBand} into {@link #map} by its id
     *
     * @param newBand - new element {@link MusicBand} to add to {@link #map}
     * @param id      - the id of the new element
     */
    @Deprecated
    public void addNewGroup(MusicBand newBand, int id) { // update
        this.map.put(newBand.getId(), newBand);
        System.out.println("Holder: successfully added");
    }

    /**
     * deletes {@link #map} element with current id
     *
     * @param id - id of deleting element
     */
    public void deleteElement(String id) {
        Integer ID;
        try {
            ID = new Integer(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return;
        }

        this.map.remove(ID);
        this.mass = Tools.removeID(this.mass, ID);
        //this.sort(HolderSortTypes.DEFAULT);
    }

    public void deleteElement(Integer id) {
        String message = map.get(id).getName();
        this.map.remove(id);
        this.mass = Tools.removeID(this.mass, id);
        System.out.println("Server: group " + message + "deleted");
    }

    /**
     * Prints the {@link #map} element into client's console by its position in sorted array
     *
     * @param position - position of required element
     */
    public void ReadCollectionElement(int position) {
        if (mass.length >= position - 1) {
            System.out.println(this.map.get(mass[position - 1]).toString());
        } else {
            System.out.println("no elements requires these parameters");
        }
    }

    /**
     * Removes all {@link #map} element's which Number of participants is lower than one
     */
    @Deprecated
    public void removeLower(Long numberOfParticipants) {
        if (numberOfParticipants == null) {
            return;
        }
        Vector<Integer> vector = new Vector<Integer>();
        for (int i = 0; i != mass.length; i++) {
            Long current = this.map.get(mass[i]).getNumberOfParticipants();
            if (current != null) {
                if (current < numberOfParticipants) {
                    vector.add(mass[i]);
                }
            }
        }
        // deleting
        for (Integer i : vector) {
            this.deleteElement(i.toString());
        }
    }

    /**
     * getter for {@link #mass}
     */
    public int[] getIDs() {
        return this.mass;
    }

    /**
     * Getter for NumberOfParticipants of the {@link #map} element with current id
     *
     * @param bandID - id of the {@link #map} element
     * @return Number of Participants of the {@link MusicBand} in {@link #map} with bandID
     */
    public Long getNumberOfParticipants(int bandID) {
        if (this.map.get(bandID) != null)
            return this.map.get(bandID).getNumberOfParticipants();
        else return null;
    }

    /**
     * replaces the existing {@link #map} element by a new one
     *
     * @param newBand - new Element
     *                if there is no such element it will add it
     */
    public void replaceGroup(MusicBand newBand) {
        this.map.put(newBand.getId(), newBand);
    }

    public MusicBand getMinGroup() {
        Comparator<Map.Entry<Integer, MusicBand>> comparatorID = new ComparatorID();
        if (!map.isEmpty()) {
            return map.entrySet().stream().min(comparatorID).get().getValue();
        } else return null;
    }

    public Stream<MusicBand> getMapStream() {
        return this.map.values().stream();
    }


}
