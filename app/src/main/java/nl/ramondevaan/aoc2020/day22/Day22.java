package nl.ramondevaan.aoc2020.day22;

import nl.ramondevaan.aoc2020.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2020.util.Parser;
import nl.ramondevaan.aoc2020.util.Partitioner;
import org.apache.commons.lang3.tuple.ImmutableTriple;

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

    public long solve1() {
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

        long score1 = score(cardsPlayer1);
        long score2 = score(cardsPlayer2);

        return score1 + score2;
    }

    public long solve2() {
        ImmutableTriple<Deque<Integer>, Deque<Integer>, Boolean> end = playOut(initialCardsPlayer1,
                initialCardsPlayer1.size(), initialCardsPlayer2, initialCardsPlayer2.size());

        long score1 = score(end.left);
        long score2 = score(end.middle);

        return score1 + score2;
    }

    private long score(Collection<Integer> cards) {
        long score = 0L;
        long multiplier = cards.size();

        for (int card : cards) {
            score += card * multiplier--;
        }

        return score;
    }

    public ImmutableTriple<Deque<Integer>, Deque<Integer>, Boolean> playOut(Collection<Integer> c1, int s1,
                                                                            Collection<Integer> c2, int s2) {
        Deque<Integer> cardsPlayer1 = copy(c1, s1);
        Deque<Integer> cardsPlayer2 = copy(c2, s2);

        Set<Integer> previousStates = new HashSet<>();
        while (!cardsPlayer1.isEmpty() && !cardsPlayer2.isEmpty()) {
            if (!previousStates.add(hash(cardsPlayer1, cardsPlayer2))) {
                return ImmutableTriple.of(cardsPlayer1, cardsPlayer2, true);
            }

            int card1 = cardsPlayer1.pop();
            int card2 = cardsPlayer2.pop();

            boolean playerOneWins;
            if (cardsPlayer1.size() >= card1 && cardsPlayer2.size() >= card2) {
                playerOneWins = playOut(cardsPlayer1, card1, cardsPlayer2, card2).right;
            } else {
                playerOneWins = card1 > card2;
            }

            if (playerOneWins) {
                cardsPlayer1.addLast(card1);
                cardsPlayer1.addLast(card2);
            } else {
                cardsPlayer2.addLast(card2);
                cardsPlayer2.addLast(card1);
            }
        }

        return ImmutableTriple.of(cardsPlayer1, cardsPlayer2, cardsPlayer2.isEmpty());
    }

    private int hash(Deque<Integer> cards1, Deque<Integer> cards2) {
        return Objects.hash(new ArrayList<>(cards1), new ArrayList<>(cards2));
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
