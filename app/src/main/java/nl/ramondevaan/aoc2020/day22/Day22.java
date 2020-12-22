package nl.ramondevaan.aoc2020.day22;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;

import java.util.*;

public class Day22 {

    private final List<Integer> initialCardsPlayer1;
    private final List<Integer> initialCardsPlayer2;

    public Day22(List<String> lines) {
        Partitioner<String> partitioner = new BlankStringPartitioner();
        List<List<String>> partitions = partitioner.partition(lines);

        Parser<List<String>, List<Integer>> parser = new CardsParser();
        initialCardsPlayer1 = parser.parse(partitions.get(0));
        initialCardsPlayer2 = parser.parse(partitions.get(1));
    }

    public int solve1() {
        Deque<Integer> cardsPlayer1 = new ArrayDeque<>(initialCardsPlayer1);
        Deque<Integer> cardsPlayer2 = new ArrayDeque<>(initialCardsPlayer2);

        while (!cardsPlayer1.isEmpty() && !cardsPlayer2.isEmpty()) {
            int card1 = cardsPlayer1.pop();
            int card2 = cardsPlayer2.pop();

            if (card1 > card2) {
                cardsPlayer1.addLast(card1);
                cardsPlayer1.addLast(card2);
            } else {
                cardsPlayer2.addLast(card2);
                cardsPlayer2.addLast(card1);
            }
        }

        int score1 = score(cardsPlayer1);
        int score2 = score(cardsPlayer2);

        return score1 + score2;
    }

    public int solve2() {
        Result result = playOut(initialCardsPlayer1, initialCardsPlayer2);

        int score1 = score(result.cardsPlayer1);
        int score2 = score(result.cardsPlayer2);

        return score1 + score2;
    }

    private int score(Collection<Integer> cards) {
        int score = 0;
        int multiplier = cards.size();

        for (int card : cards) {
            score += card * multiplier--;
        }

        return score;
    }

    private Result playOut(Collection<Integer> cardsPlayer1, Collection<Integer> cardsPlayer2) {
        return playOut(cardsPlayer1, cardsPlayer2.size(), cardsPlayer2, cardsPlayer2.size());
    }

    private Result playOut(Collection<Integer> cards1, int limit1, Collection<Integer> cards2, int limit2) {
        Deque<Integer> cardsPlayer1 = copy(cards1, limit1);
        Deque<Integer> cardsPlayer2 = copy(cards2, limit2);

        Set<Integer> previousStates = new HashSet<>();
        while (!cardsPlayer1.isEmpty() && !cardsPlayer2.isEmpty()) {
            if (!previousStates.add(hash(cardsPlayer1, cardsPlayer2))) {
                return new Result(cardsPlayer1, cardsPlayer2, true);
            }

            int card1 = cardsPlayer1.pop();
            int card2 = cardsPlayer2.pop();

            boolean player1Wins;
            if (cardsPlayer1.size() >= card1 && cardsPlayer2.size() >= card2) {
                player1Wins = playOut(cardsPlayer1, card1, cardsPlayer2, card2).player1Wins;
            } else {
                player1Wins = card1 > card2;
            }

            if (player1Wins) {
                cardsPlayer1.addLast(card1);
                cardsPlayer1.addLast(card2);
            } else {
                cardsPlayer2.addLast(card2);
                cardsPlayer2.addLast(card1);
            }
        }

        return new Result(cardsPlayer1, cardsPlayer2, cardsPlayer2.isEmpty());
    }

    private int hash(Deque<Integer> cards1, Deque<Integer> cards2) {
        return (score(cards1) << 16) + score(cards2);
    }

    private Deque<Integer> copy(Collection<Integer> cards, int size) {
        Deque<Integer> ret = new ArrayDeque<>(cards);

        int toDelete = ret.size() - size;

        for (int i = 0; i < toDelete; i++) {
            ret.removeLast();
        }

        return ret;
    }
}
