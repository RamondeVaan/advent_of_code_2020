package nl.ramondevaan.aoc2020.day12;

import nl.ramondevaan.aoc2020.util.Parser;

public class ActionParser implements Parser<String, Action> {
    @Override
    public Action parse(String toParse) {
        String actionTypeString = toParse.substring(0, 1);
        String valueSting = toParse.substring(1);

        ActionType actionType = ActionType.valueOf(actionTypeString);
        int value = Integer.parseInt(valueSting);

        return new Action(actionType, value);
    }
}
