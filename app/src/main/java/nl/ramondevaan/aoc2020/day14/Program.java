package nl.ramondevaan.aoc2020.day14;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.Map;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Program {
    public final long maskWithZeros;
    public final long maskWithOnes;
    public final long maskWildcards;
    @With
    public final Map<Long, Long> memory;

    public Program(long maskWithZeros, long maskWithOnes, Map<Long, Long> memory) {
        this.maskWithZeros = maskWithZeros;
        this.maskWithOnes = maskWithOnes;
        this.maskWildcards = maskWithZeros ^ maskWithOnes;
        this.memory = memory;
    }
}
