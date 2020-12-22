package nl.ramondevaan.aoc2020.day22;

import lombok.Value;

import java.util.Collection;

@Value
public class Result {
    public Collection<Integer> cardsPlayer1;
    public Collection<Integer> cardsPlayer2;
    public boolean player1Wins;
}
