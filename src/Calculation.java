
import java.util.Stack;

public class Calculation {

    public static void main(String[] args) {
        CalculatorSystem calculatorGUI = new CalculatorSystem();
        calculatorGUI.setVisible(true);
    }

    public double calculate(String expression) {
        String postfix = infixToPostfix(expression);
        TreeNode root = buildTree(postfix);
        return TreeNode.evaluateExpression(root);
    }

    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                postfix.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(" ").append(stack.pop());
                }
                stack.pop();
            } else {
                postfix.append(" ");
                while (!stack.isEmpty() && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                    postfix.append(stack.pop()).append(" ");
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(" ").append(stack.pop());
        }

        return postfix.toString().trim();
    }

    private TreeNode buildTree(String postfix) {
        Stack<TreeNode> stack = new Stack<>();

        for (String token : postfix.split("\\s+")) {
            if (isNumber(token)) {
                stack.push(new TreeNode(Double.parseDouble(token)));
            } else if (isOperator(token)) {
                if ("/".equals(token)) {
                    TreeNode divisor = stack.pop();
                    TreeNode dividend = stack.pop();

                    if (divisor.getOperand() != 0) {
                        stack.push(new TreeNode(token, dividend, divisor));
                    } else {
                        // Handle division by zero error
                        return null;
                    }
                } else if ("+".equals(token) || "-".equals(token)) {
                    // Change the order of popping operands for subtraction
                    TreeNode rightOperand = stack.pop();
                    TreeNode leftOperand = stack.pop();

                    stack.push(new TreeNode(token, leftOperand, rightOperand));
                } else {
                    stack.push(new TreeNode(token, stack.pop(), stack.pop()));
                }
            }
        }

        return stack.pop();
    }

    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0; // for '('
        }
    }

    private boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String str) {
        return str.equals("+") || 
               str.equals("-") || 
               str.equals("*") || 
               str.equals("/");
    }
}
