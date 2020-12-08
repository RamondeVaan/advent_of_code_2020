package nl.ramondevaan.aoc2020.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StateMachine {
    private final Map<OperationType, OperationHandler> operationHandlerMap;
    private final List<Operation> operations;
    private final Set<Integer> executedLines;
    private State currentState;

    public StateMachine(Map<OperationType, OperationHandler> operationHandlerMap, List<Operation> operations) {
        this.operationHandlerMap = Map.copyOf(operationHandlerMap);
        this.operations = List.copyOf(operations);
        this.executedLines = new HashSet<>();
        this.currentState = new State(0, 0L);
    }

    public StateMachineResult run() {
        int programCounter;
        State newState;
        Operation nextOperation;
        OperationHandler handler;

        StateMachineResultType resultType = StateMachineResultType.SUCCES;

        while ((programCounter = currentState.getProgramCounter()) < operations.size()) {
            nextOperation = operations.get(programCounter);
            handler = operationHandlerMap.get(nextOperation.getType());
            newState = handler.apply(currentState, nextOperation.getArgument());
            if (!executedLines.add(programCounter)) {
                resultType = StateMachineResultType.LOOP;
                break;
            }
            currentState = newState;
        }

        return new StateMachineResult(resultType, currentState);
    }
}
