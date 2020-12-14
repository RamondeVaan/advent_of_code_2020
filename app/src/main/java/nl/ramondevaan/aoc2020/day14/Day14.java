package nl.ramondevaan.aoc2020.day14;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 {

    private final Program initialProgram;
    private final List<Operation> operations;

    public Day14(List<String> lines) {
        initialProgram = new Program(0L, 0xfffffffffL, Map.of());

        Parser<String, Operation> parser = new OperationParser();
        this.operations = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        return solve(Map.of(
                MaskOperation.class, new MaskOperationHandler(),
                MemoryOperation.class, new SetMemoryOperationHandler()
        ));
    }

    public long solve2() {
        return solve(Map.of(
                MaskOperation.class, new MaskOperationHandler(),
                MemoryOperation.class, new SetDecodedMemoryOperationHandler()
        ));
    }

    public long solve(Map<Class<? extends Operation>, OperationHandler> handlerMap) {
        Program program = initialProgram;

        for (Operation operation : operations) {
            program = handlerMap.get(operation.getClass()).apply(program, operation);
        }

        return program.memory.values().stream().mapToLong(value -> value).sum();
    }
}
