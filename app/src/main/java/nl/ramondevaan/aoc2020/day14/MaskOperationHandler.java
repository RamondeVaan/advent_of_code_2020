package nl.ramondevaan.aoc2020.day14;

public class MaskOperationHandler implements OperationHandler {
    @Override
    public Program apply(Program program, Operation operation) {
        MaskOperation maskOperation = (MaskOperation) operation;

        return new Program(maskOperation.maskWithZeros, maskOperation.maskWithOnes, program.memory);
    }
}
