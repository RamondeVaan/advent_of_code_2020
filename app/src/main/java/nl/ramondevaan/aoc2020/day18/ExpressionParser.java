package nl.ramondevaan.aoc2020.day18;

import lombok.Value;
import nl.ramondevaan.aoc2020.util.Parser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;

@Value
public class ExpressionParser implements Parser<String, Long> {

    Map<Character, Operator> operatorMap;

    @Override
    public Long parse(String toParse) {
        ExpressionParserImpl impl = new ExpressionParserImpl(operatorMap, toParse);
        return impl.parse();
    }

    private static class ExpressionParserImpl {
        Map<Character, Operator> operatorMap;

        Deque<Long> numberStack;
        Deque<Character> operatorStack;

        String toParse;
        int nextIndex;
        Character current;

        public ExpressionParserImpl(Map<Character, Operator> operatorMap, String toParse) {
            this.operatorMap = operatorMap;

            numberStack = new ArrayDeque<>();
            operatorStack = new ArrayDeque<>();

            this.toParse = toParse;
        }

        public long parse() {
            next();
            while (current != null) {
                readNext();
            }

            while (!operatorStack.isEmpty()) {
                pushToOutput(operatorStack.pop());
            }

            return numberStack.pop();
        }

        private void readNext() {
            while (Objects.equals(current, ' ')) {
                next();
            }
            if (current == null) {
                return;
            }
            if (Character.isDigit(current)) {
                parseNumber();
                return;
            } else if (current == '(') {
                operatorStack.push(current);
            } else if (current == ')') {
                char operatorChar;

                while ((operatorChar = operatorStack.pop()) != '(') {
                    pushToOutput(operatorChar);
                }
            } else {
                parseOperator();
            }
            next();
        }

        private void parseNumber() {
            StringBuilder sb = new StringBuilder();
            sb.append(current);

            Character next;
            while ((next = next()) != null && Character.isDigit(next)) {
                sb.append(current);
            }

            String s = sb.toString();
            long value = Long.parseLong(s);
            numberStack.push(value);
        }

        private void parseOperator() {
            int operatorPrecedence = operatorMap.get(current).getPrecedence();
            int topOperatorPrecedence;

            while (!operatorStack.isEmpty()) {
                char top = operatorStack.getFirst();
                if (top == '(') {
                    break;
                }
                topOperatorPrecedence = operatorMap.get(top).getPrecedence();
                if (operatorPrecedence > topOperatorPrecedence) {
                    break;
                }
                pushToOutput(operatorStack.pop());
            }

            operatorStack.push(current);
        }

        private void pushToOutput(char operatorChar) {
            Operator operator = operatorMap.get(operatorChar);
            long right = numberStack.pop();
            long left = numberStack.pop();
            long result = operator.apply(left, right);

            numberStack.push(result);
        }

        private Character next() {
            if (nextIndex < toParse.length()) {
                return current = toParse.charAt(nextIndex++);
            }
            return current = null;
        }
    }
}
