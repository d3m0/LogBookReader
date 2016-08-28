import Entities.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class LogBookReader {
    private static List<Player> players;
    private static String currentPlayerName;

    public static void main(String... args) throws IOException {
        String[] content = readFile("logbook.lua");
        String[] logbookSection = readLogbookSection(content);
        Map<String, String[]> values = readProperties(logbookSection);
        currentPlayerName = values.get("currentPlayerName")[0];
        String[] playersSection = values.get("players");
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
        for (String line : content) {
            System.out.println(line);
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
            if (content[i].trim().startsWith("[")) {
                String propertyName = getPropertyName(content[i]);
                properties.put(propertyName, getPropertiesByPropertyName(i, propertyName, content));
                i += properties.get(propertyName).length;
            } else {
                i++;
            }
        }
        return properties;
    }

    private static String[] getPropertiesByPropertyName(int i, String propertyName, String[] content) {
        List<String> newArray = new ArrayList<String>();
        int line = 2;

        if (content[i].endsWith(",")) {
            String[] parts = content[i].split("=");
            String result = parts[1].trim().substring(1, parts[1].length() - 3);
            newArray.add(result);
        } else if (content[i + 1].trim().startsWith("{")) {
            while (!content[i].contains("end of [\"" + propertyName + "\"]")) {
                newArray.add(content[line].trim());
                i++;
                line++;
            }
        }
        return newArray.toArray(new String[0]);
    }

    private static String getPropertyName(String line) {
        return line.substring(line.indexOf("[") + 2, line.indexOf("]") - 1);
    }

}
