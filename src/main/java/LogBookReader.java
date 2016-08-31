import Entities.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class LogBookReader {
    private static List<Player> players;
    private static String currentPlayerName;

    public static void main(String... args) throws IOException {
        String[] content = readFile("logbook.lua");
        String[] logbookSection = readLogbookSection(content);
        readAllSections(logbookSection);


//        Map<String, String[]> values = readProperties(logbookSection);
//        currentPlayerName = values.get("\"currentPlayerName\"")[0];
//        String[] playersSection = values.get("\"players\"");
//        Map<String, String[]> playersProperties = readProperties(playersSection);
//        String[] playerSection = playersProperties.get("1");
//        printSection(playerSection);
//        Map<String, String[]> playerProperties = readProperties(playerSection);
    }

    private static void readAllSections(String[] content) {
//        Map<Integer, String> section = new LinkedHashMap<Integer, String>();
        List<Player> players = new ArrayList<Player>();
        Pattern sectionHeaderPattern = Pattern.compile("\\[\"\\w+\"\\]");

        for (int i = 0; i < content.length; i++) {
            System.out.println("Line " + content[i] + " " + sectionHeaderPattern.matcher(content[i]).find());
            if (content[i].trim().equals("[\"")) {
                String propertyName = getPropertyName(content[i - 1]);
                if (propertyName.equals("players")) {
                    System.out.println("Reading players section");
                    if (content[i].trim().equals("[")) {

                    }
                }
//                section.put(i, propertyName);
            }
        }

        // Trace of all opening braces
//        for (Integer lineNum : section.keySet()) {
//            System.out.println(lineNum + " " + section.get(lineNum));
//        }


    }

    private static String[] readFile(String path) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(path));
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        return lines.toArray(new String[0]);
    }

    private static void printSection(String... content) {
        int lineNum = 0;
        for (String line : content) {
            System.out.println(lineNum + "  " + line);
            lineNum++;
        }
    }

    private static String[] readLogbookSection(String... content) {
        List<String> newArray = new ArrayList<String>();

        if (content[0].trim().equals("logbook =")) {
            for (int i = 2; i < content.length - 1; i++) {
                newArray.add(content[i].trim());
            }
        }

        return newArray.toArray(new String[0]);
    }

    private static Map<String, String[]> readProperties(String... content) {
        int i = 0;
        Map<String, String[]> properties = new HashMap<String, String[]>();
        while (i < content.length) {
            System.out.println(content[i]);
            if (content[i].trim().startsWith("[")) {
                String propertyName = getPropertyName(content[i]);
                String[] property = getPropertiesByPropertyName(i, propertyName, content);
                properties.put(propertyName, property);
                if (property.length > 1) {
                    i += property.length + 2;
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }
        return properties;
    }

    private static String[] getPropertiesByPropertyName(int i, String propertyName, String[] content) {
        List<String> newArray = new ArrayList<String>();

        if (content[i].endsWith(",")) {
            newArray.add(getSingleProperty(content[i]));
        } else if (content[i + 1].trim().startsWith("{")) {
            newArray = getMultipleProperties(propertyName, content);
        }
        return newArray.toArray(new String[0]);
    }

    private static List<String> getMultipleProperties(String propertyName, String[] content) {
        List<String> newArray = new ArrayList<String>();
        int finishLine = content.length - 1;
        while (!content[finishLine].trim().startsWith("}, -- end of [" + propertyName + "]")) {
            finishLine--;
        }

        for (int line = 2; line < finishLine; line++) {
            newArray.add(content[line]);
        }
        return newArray;
    }

    private static String getSingleProperty(String s) {
        String[] parts = s.split("=");
        return parts[1].trim().substring(0, parts[1].length() - 2);
    }

    private static String getPropertyName(String line) {
        return line.substring(line.indexOf("[\"") + 1, line.indexOf("\"]"));
    }

}
