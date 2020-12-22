package nl.ramondevaan.aoc2020.day18;

public class NumberExpression implements Expression {

    private final long number;
    private Expression parent;

    public NumberExpression(long number) {
        this.number = number;
    }

    @Override
    public ExpressionType getType() {
        return ExpressionType.NUMBER;
    }

    @Override
    public long compute() {
        return number;
    }

    @Override
    public void setParent(Expression expression) {
        this.parent = expression;
    }

    @Override
    public void addLeft(Expression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addRight(Expression expression) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Expression getLeft() {
        return null;
    }

    @Override
    public Expression getRight() {
        return null;
    }

    @Override
    public Expression getParent() {
        return parent;
    }
}
