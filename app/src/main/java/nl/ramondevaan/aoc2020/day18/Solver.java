package nl.ramondevaan.aoc2020.day18;

import nl.ramondevaan.aoc2020.day18.token.*;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.*;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;

public class Solver {

    public long solve(List<Token> tokens) {
        List<Token> copy = new ArrayList<>(tokens);
        List<MutablePair<Integer, Integer>> parenthesesMutablePairIndices = findParenthesesMutablePairIndices(copy);

        for (int index = 0; index < parenthesesMutablePairIndices.size(); index++) {
            MutablePair<Integer, Integer> pair = parenthesesMutablePairIndices.get(index);
            int start = pair.left;
            int end = pair.right;
            int diff = pair.getRight() - pair.getLeft() - 1;
            for (int nextIndex = index + 1; nextIndex < parenthesesMutablePairIndices.size(); nextIndex++) {
                MutablePair<Integer, Integer> nextMutablePair = parenthesesMutablePairIndices.get(nextIndex);
                int nextStart = nextMutablePair.left;
                int nextEnd = nextMutablePair.right;
                if (nextEnd > start) {
                    nextMutablePair.setRight(nextEnd - diff);
                }
                if (nextStart > end) {
                    nextMutablePair.setLeft(nextStart - diff);
                }
            }
            List<Token> subList = copy.subList(start, end);
            subList.remove(subList.size() - 1);
            subList.remove(0);
            simplify(subList, AddToken.class, Long::sum);
            simplify(subList, MultiplyToken.class, (left, right) -> left * right);
        }

        simplify(copy, AddToken.class, Long::sum);
        simplify(copy, MultiplyToken.class, (left, right) -> left * right);

        return ((NumberToken) copy.get(0)).getValue();
    }

    private void simplify(List<Token> tokens, Class<? extends Token> clazz, LongBinaryOperator operator) {
        int index = 0;

        while ((index = find(tokens, clazz, index)) != -1) {
            int indexMinusOne = index - 1;
            long left = ((NumberToken) tokens.remove(indexMinusOne)).getValue();
            tokens.remove(indexMinusOne);
            long right = ((NumberToken) tokens.remove(indexMinusOne)).getValue();
            NumberToken token = new NumberToken(operator.applyAsLong(left, right));
            tokens.add(indexMinusOne, token);
        }
    }

    private int find(List<Token> tokens, Class<? extends Token> clazz, int from) {
        for (int index = from; index < tokens.size(); index++) {
            if (clazz.isInstance(tokens.get(index))) {
                return index;
            }
        }

        return -1;
    }

    private List<MutablePair<Integer, Integer>> findParenthesesMutablePairIndices(List<Token> tokens) {
        Map<Integer, List<MutablePair<Integer, Integer>>> map = new HashMap<>();
        Deque<Integer> startIndices = new ArrayDeque<>();

        for (int index = 0; index < tokens.size(); index++) {
            Token token = tokens.get(index);

            if (token instanceof ParenthesesOpenToken) {
                startIndices.add(index);
            } else if (token instanceof ParenthesesCloseToken) {
                MutablePair<Integer, Integer> pair = MutablePair.of(startIndices.removeLast(), index + 1);
                int depth = startIndices.size();
                List<MutablePair<Integer, Integer>> list = map.getOrDefault(depth, new ArrayList<>());
                list.add(pair);
                map.put(depth, list);
            }
        }

        return map.entrySet().stream()
                .sorted(Comparator.<Map.Entry<Integer,
                        List<MutablePair<Integer, Integer>>>>comparingInt(Map.Entry::getKey).reversed())
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toUnmodifiableList());
    }
}
