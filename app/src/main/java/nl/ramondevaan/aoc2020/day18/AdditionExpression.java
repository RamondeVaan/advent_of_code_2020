package nl.ramondevaan.aoc2020.day18;

public class AdditionExpression implements Expression {

    private Expression parent;
    private Expression left;
    private Expression right;

    @Override
    public ExpressionType getType() {
        return ExpressionType.ADDITION;
    }

    @Override
    public long compute() {
        return left.compute() + right.compute();
    }

    @Override
    public void setParent(Expression expression) {
        this.parent = expression;
    }

    @Override
    public void addLeft(Expression expression) {
        left = expression;
    }

    @Override
    public void addRight(Expression expression) {
        right = expression;
    }

    @Override
    public Expression getParent() {
        return parent;
    }

    @Override
    public Expression getLeft() {
        return left;
    }

    @Override
    public Expression getRight() {
        return right;
    }
}
