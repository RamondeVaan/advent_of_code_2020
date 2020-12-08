package nl.ramondevaan.aoc2020.day08;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 {

    private final Map<String, OperationHandler> operationHandlerMap;
    private final List<Operation> operations;

    public Day08(List<String> lines) {
        operationHandlerMap = Map.of(
                "nop", new NoOperationHandler(),
                "acc", new AccumulateOperationHandler(),
                "jmp", new JumpOperationHandler()
        );

        Parser<String, Operation> parser = new OperationParser();
        operations = lines.stream().map(parser::parse).collect(Collectors.toList());
    }

    public long solve1() {
        StateMachine stateMachine = new StateMachine(operationHandlerMap, operations);
        StateMachineResult result = stateMachine.run();
        return result.getFinalState().getAccumulator();
    }

    public long solve2() {
        Set<Integer> switchableTypes = IntStream.range(0, operations.size())
                .filter(index -> operations.get(index).getType().matches("jmp|nop"))
                .boxed()
                .collect(Collectors.toSet());

        for(int index : switchableTypes) {
            Operation operation = operations.get(index);
            Operation replacement = new Operation(
                    operation.getType().equals("jmp") ? "nop" : "jmp",
                    operation.getArgument());

            operations.set(index, replacement);
            StateMachine stateMachine = new StateMachine(operationHandlerMap, operations);
            StateMachineResult result = stateMachine.run();
            if(result.getType().equals(StateMachineResultType.SUCCES)) {
                return result.getFinalState().getAccumulator();
            }
            operations.set(index, operation);
        }

        throw new IllegalStateException();
    }
}
