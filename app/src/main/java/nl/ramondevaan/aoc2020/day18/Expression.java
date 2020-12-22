package nl.ramondevaan.aoc2020.day18;

public interface Expression {

    ExpressionType getType();

    long compute();

    void setParent(Expression expression);

    void addLeft(Expression expression);

    void addRight(Expression expression);

    Expression getParent();

    Expression getLeft();

    Expression getRight();
}
