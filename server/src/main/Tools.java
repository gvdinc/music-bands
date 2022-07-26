package main;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * class has a number of static functions helping to parse xml and operate with arrays
 *
 * @author Grebenkin Vadim
 */
public class Tools {

    /**
     * function appends new element to existing array
     *
     * @param mass - existing array
     * @param a    - element to add
     * @return - new array with new element in the end
     */
    public static int[] appendInt(int[] mass, int a) {
        int[] res = new int[mass.length + 1];
        if (mass.length > 0) {
            for (int i = 0; i != mass.length; i++) {
                res[i] = mass[i];
            }
        }
        res[res.length - 1] = a;
        return res;
    }

    /**
     * function appends new element to existing array
     *
     * @param mass - existing array
     * @param a    - element to add
     * @return - new array with new element in the end
     */
    public static StringXMLItem[] appendXMLItem(StringXMLItem[] mass, StringXMLItem a) {
        StringXMLItem[] res = new StringXMLItem[mass.length + 1];
        if (mass.length > 0) {
            for (int i = 0; i != mass.length; i++) {
                res[i] = mass[i];
            }
        }
        res[res.length - 1] = a;
        return res;
    }


    /**
     * parses XML code, finding the inner elements
     *
     * @param line - String xml code
     * @return array of pairs of name of teg and its content
     */
    public static StringXMLItem[] regSearch(String line) {
        String regExFull = "<([^>]+)>([\\s\\S]*)<\\/\\1>";
        Pattern pattern = Pattern.compile(regExFull);
        Matcher m = pattern.matcher(line);
        StringXMLItem[] items = new StringXMLItem[0];
        StringXMLItem item = new StringXMLItem();

        while (m.find()) {
            item = new StringXMLItem();
            item.setName(m.group(1));
            item.setContent(m.group(2));
            //System.out.println("regSearch: line found: " + "\nname:"+item.getName()+"\ncontent:"+item.getContent());
            items = Tools.appendXMLItem(items, item);
        }
        //if (items.length == 0) {System.out.println("regSearch: not found in: " + line);}
        return items;
    }

    /**
     * Checking the {@param line} with given regular expression
     *
     * @param line  - text to search to the regEx
     * @param regEx - regular expression
     * @return returns true if there is String responding regular expression
     */
    public static boolean regSearch(String line, String regEx) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher m = pattern.matcher(line);
        return m.find();
    }

    /**
     * method to remove element from array
     *
     * @param mass - existing array
     * @param id   - id of the deleting element
     * @return returns new array without this element, of existing array if element is not found
     */
    public static int[] removeID(int[] mass, Integer id) {
        int[] res = new int[mass.length - 1];
        if (mass.length > 1) {
            int f = 0;
            for (int i = 0; i != mass.length; i++) {
                if (f == mass.length - 1 && mass[i] != id) {
                    return mass;
                }
                if (mass[i] != id) {
                    res[f] = mass[i];
                    f++;
                }
            }
            return res;
        } else {
            if (mass[0] == id) {
                return new int[0];
            } else {
                return res;
            }
        }
    }

    /**
     * trying to reach file by its pass-link
     *
     * @param dataPath - pass-link of xml file
     * @return returns input stream to read from file
     * @throws IOException
     */
    public static BufferedInputStream getInputStream(String dataPath) throws IOException {
        File file = new File(dataPath);
        InputStream is = null;
        try {
            is = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error: xml file not found! (" + dataPath + ")");
            return null;
        }
        BufferedInputStream inputStream = new BufferedInputStream(is);
        if (inputStream.available() > 0) {
            System.out.println("Holder: file found");
        } else {
            System.out.println("Holder: file not found");
            return null;
        }
        return inputStream;
    }
}

