package nl.ramondevaan.aoc2020.day08;

import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day08 {

    private final Map<OperationType, OperationHandler> operationHandlerMap;
    private final List<Operation> operations;

    public Day08(List<String> lines) {
        operationHandlerMap = Map.of(
                OperationType.NOP, (state, argument) -> state.increaseProgramCounter(1),
                OperationType.ACC, (state, argument) -> state.increment(1, argument),
                OperationType.JMP, State::increaseProgramCounter
        );

        Parser<String, Operation> parser = new OperationParser();
        operations = lines.stream().map(parser::parse).collect(Collectors.toUnmodifiableList());
    }

    public long solve1() {
        StateMachine stateMachine = new StateMachine(operationHandlerMap, operations);
        StateMachineResult result = stateMachine.run();
        return result.getFinalState().getAccumulator();
    }

    public long solve2() {
        Set<Integer> switchableTypes = IntStream.range(0, operations.size())
                .filter(index -> {
                    OperationType type = operations.get(index).getType();
                    return type.equals(OperationType.JMP) || type.equals(OperationType.NOP);
                })
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        List<Operation> modifiableOperations = new ArrayList<>(operations);

        for (int index : switchableTypes) {
            Operation operation = modifiableOperations.get(index);
            Operation replacement = new Operation(
                    operation.getType().equals(OperationType.JMP) ? OperationType.NOP : OperationType.JMP,
                    operation.getArgument());

            modifiableOperations.set(index, replacement);
            StateMachine stateMachine = new StateMachine(operationHandlerMap, modifiableOperations);
            StateMachineResult result = stateMachine.run();
            if (result.getType().equals(StateMachineResultType.SUCCES)) {
                return result.getFinalState().getAccumulator();
            }
            modifiableOperations.set(index, operation);
        }

        throw new IllegalStateException();
    }
}
