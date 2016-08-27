import Entities.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class LogBookReader {
    private static List<Player> players;
    private static String currentPlayerName;

    public static void main(String... args) throws IOException {
//        File newFile = new File("test.txt");
//        String path = newFile.getAbsolutePath();

        Scanner sc = new Scanner(new File("logbook.lua"));
        List<String> lines = new ArrayList<String>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }

        String[] content = lines.toArray(new String[0]);

        String[] logbookSection = readLogbookSection(content);
        printSection(logbookSection);
    }

    private static void printSection(String... content) {
        for (String line : content) {
            System.out.println(line);
        }
    }

    private static String[] readLogbookSection(String... content) {
        List<String> newArray = new ArrayList<String>();

        if (content[0].equals("logbook = ")) {
            for (int i = 2; i < content.length - 1; i++) {
                newArray.add(content[i]);
            }
        }

        return newArray.toArray(new String[0]);
    }

    private static void readProperties(String... content) {
        if (content[0].trim().startsWith("[")) {
            // TODO: read property name and save text to property
        }
    }
}
