import Entities.Award;
import Entities.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
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
    }

    private static void readAllSections(String[] content) {
        LinkedList<String> sections = new LinkedList<String>();
        List<Player> players = new ArrayList<Player>();
        Player player = null;
        Award award = null;
        String currentSectionHeader = null;
        Integer currentSectionEntityNumber = null;
        HashMap<Integer, List<Award>> awards = new HashMap<Integer, List<Award>>();

        for (int i = 0; i < content.length; i++) {
            String line = content[i];

            if (isSectionHeader(line)) {
                String sectionHeaderName = getSectionHeaderName(line);
                System.out.println("Section ['" + sectionHeaderName + "']");
                sections.add(sectionHeaderName);
                currentSectionHeader = sectionHeaderName;
            }

            if (isSectionEntity(line)) {
                Integer sectionEntityNumber = getSectionEntityNumber(line);
                System.out.println("Current Section Header: " + currentSectionHeader);
                System.out.println("Current Section Entity Number: " + sectionEntityNumber);

                if ("players".equals(currentSectionHeader)) {
                    player = new Player();
                }

                if ("awards".equals(currentSectionHeader)) {
                    currentSectionEntityNumber = sectionEntityNumber;

                    if (awards.keySet().contains(currentSectionEntityNumber)) {
                        System.out.println("Using existing awardList");
                    } else {
                        awards.put(currentSectionEntityNumber, new ArrayList<Award>());
                    }

                    for (int j : awards.keySet()) {
                        System.out.println("Keys stored: " + j);
                    }
                }
            }

            if (isSectionVariable(line)) {
                String sectionVariableName = getSectionVariableName(line);
                String sectionVariableValue = getSectionVariableValue(line);
                System.out.println("Section variable name: " + sectionVariableName + " = " + sectionVariableValue);
                if ("players".equals(currentSectionHeader)) {
                    if ("invulnerable".equals(sectionVariableName)) {
                        player.setInvulnerability(Boolean.valueOf(sectionVariableValue));
                    }
                }

                if ("awards".equals(currentSectionHeader)) {
                    award = new Award();
                    award.setAwardName(sectionVariableValue);

                    if (awards.keySet().contains(currentSectionEntityNumber)) {
                        awards.get(currentSectionEntityNumber).add(award);
                    }
                }
            }

            if (isSectionEntityEnd(line)) {
                String sectionEntityEndMessage = getSectionEntityEndMessage(line);
                System.out.println("End of ['" + sectionEntityEndMessage + "']");

            }
        }

        System.out.println(awards);
        System.out.println(player);
    }

    private static String getSectionEntityEndMessage(String line) {
        Pattern propertyNamePattern = Pattern.compile(".*\\bend of\\b \\[\"?(\\w+|\\d+)\"?\\]");
        Matcher m = propertyNamePattern.matcher(line);
        String result = null;
        while (m.find()) {
            result = m.group(1);
        }

        return result;
    }

    private static boolean isSectionEntityEnd(String line) {
        return line.startsWith("}, -- end of [");
    }

    private static Integer getSectionEntityNumber(String line) {
        Pattern propertyNamePattern = Pattern.compile("\\[(\\d+)\\].");
        Matcher m = propertyNamePattern.matcher(line);
        String result = null;
        while (m.find()) {
            result = m.group(1);
        }

        return Integer.valueOf(result);
    }

    private static String getSectionVariableValue(String line) {
        String[] parts = line.split("=");
        return parts[1].trim().substring(0, parts[1].length() - 2);
    }

    private static String getSectionVariableName(String line) {
        Pattern propertyNamePattern = Pattern.compile("\\[\"?(\\w+|\\d+)\"?\\].");
        Matcher m = propertyNamePattern.matcher(line);
        String result = null;
        while (m.find()) {
            result = m.group(1);
        }

        return result;
    }

    private static boolean isSectionVariable(String line) {
        return Pattern.compile("\\[\"?\\w+\"?\\]").matcher(line).find() && line.endsWith(",");
    }

    private static boolean isSectionEntity(String line) {
        return (!line.startsWith("},") && Pattern.compile("\\[\\d\\]").matcher(line).find() && !line.endsWith(","));
    }

    private static boolean isSectionHeader(String line) {
        return (!line.startsWith("},") && Pattern.compile("\\[\"\\w+\"\\]").matcher(line).find() && !line.endsWith(","));
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
                String propertyName = getSectionHeaderName(content[i]);
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

    private static String getSectionHeaderName(String line) {
        Pattern propertyNamePattern = Pattern.compile("\\[\"(\\w+)\"\\].");
        Matcher m = propertyNamePattern.matcher(line);
        String result = null;
        while (m.find()) {
            result = m.group(1);
        }

        return result;
    }

}
