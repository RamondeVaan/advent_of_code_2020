package nl.ramondevaan.aoc2020.day14;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SetMemoryOperationHandler implements OperationHandler {

    @Override
    public Program apply(Program program, Operation operation) {
        MemoryOperation memoryOperation = (MemoryOperation) operation;

        Map<Long, Long> newMemory = new HashMap<>(program.memory);
        long newValue = (memoryOperation.value | program.maskWithZeros) & program.maskWithOnes;

        newMemory.put(memoryOperation.memoryAddress, newValue);

        return program.withMemory(Collections.unmodifiableMap(newMemory));
    }
}
