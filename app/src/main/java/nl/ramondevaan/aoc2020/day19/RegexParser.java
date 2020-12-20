package nl.ramondevaan.aoc2020.day19;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexParser implements Parser<List<String>, String> {

    private static final String DIGIT_REGEX = "\\d+";
    private static final Pattern DIGIT_PATTERN = Pattern.compile(DIGIT_REGEX);

    @Override
    public String parse(List<String> lines) {
        Map<Integer, String> valueLines = new HashMap<>();
        Map<Integer, String> referenceLines = new HashMap<>();
        Multimap<Integer, Integer> referencedBy = HashMultimap.create();
        Multimap<Integer, Integer> references = HashMultimap.create();

        for (String line : lines) {
            int colonIndex = line.indexOf(':');
            int index = Integer.parseInt(line.substring(0, colonIndex));
            String value = line.substring(colonIndex + 2);
            if (value.startsWith("\"")) {
                valueLines.put(index, value.substring(1, value.length() - 1));
            } else {
                Matcher matcher = DIGIT_PATTERN.matcher(value);
                while (matcher.find()) {
                    int referenceIndex = Integer.parseInt(matcher.group());
                    referencedBy.put(referenceIndex, index);
                    references.put(index, referenceIndex);
                }
                referenceLines.put(index, "(?:" + value + ")");
            }
        }

        Map<Integer, String> lastValueLines = Map.copyOf(valueLines);
        Map<Integer, String> nextValueLines;

        while (lastValueLines.size() > 0) {
            nextValueLines = new HashMap<>();

            for (Map.Entry<Integer, String> entry : lastValueLines.entrySet()) {
                for (Integer referencer : referencedBy.get(entry.getKey())) {
                    String regex = "[^\\d]" + entry.getKey() + "[^\\d]";
                    Pattern pattern = Pattern.compile(regex);
                    String referencerLine = referenceLines.remove(referencer);
                    String result = "";
                    //TODO: result
//                            pattern.matcher(referencerLine).replaceAll()referencerLine.replaceAll(,
//                            entry.getValue());
                    references.remove(referencer, entry.getKey());
                    if (references.get(referencer).size() == 0) {
                        String full = result.replace(" ", "");
                        nextValueLines.put(referencer, full);
                        valueLines.put(referencer, full);
                    }
                }
            }

            lastValueLines = nextValueLines;
        }

        return null;
    }
}
