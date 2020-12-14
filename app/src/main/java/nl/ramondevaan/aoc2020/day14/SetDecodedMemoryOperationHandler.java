package nl.ramondevaan.aoc2020.day14;

import java.util.*;
import java.util.function.Function;
import java.util.stream.*;

import static org.apache.commons.math3.util.CombinatoricsUtils.combinationsIterator;

public class SetDecodedMemoryOperationHandler implements OperationHandler {
    @Override
    public Program apply(Program program, Operation operation) {
        MemoryOperation memoryOperation = (MemoryOperation) operation;

        Map<Long, Long> newMemory = new HashMap<>(program.memory);
        long baseAddress = (memoryOperation.memoryAddress | program.maskWithOnes) & ~program.maskWildcards;

        newMemory.putAll(allBitCombinations(program.maskWildcards)
                .map(offset -> baseAddress | offset)
                .boxed()
                .collect(Collectors.toMap(
                        Function.identity(),
                        i -> memoryOperation.value
                )));

        return program.withMemory(newMemory);
    }

    public static LongStream allBitCombinations(long value) {
        long highestOneBit = Long.highestOneBit(value);
        long[] input = LongStream.iterate(1L, bit -> bit <= highestOneBit, bit -> bit << 1)
                .filter(bit -> (value & bit) > 0)
                .toArray();

        return IntStream.rangeClosed(0, input.length).boxed()
                .flatMap(pick -> combinationsStream(input.length, pick))
                .mapToLong(combination -> Arrays.stream(combination).mapToLong(index -> input[index]).sum());
    }

    private static Stream<int[]> combinationsStream(int size, int pick) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(combinationsIterator(size, pick),
                Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.DISTINCT),
                false);
    }
}
