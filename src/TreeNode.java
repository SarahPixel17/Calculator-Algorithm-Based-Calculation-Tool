
import java.util.Stack;
import java.util.*;

public class TreeNode {

    TreeNode left;
    TreeNode right;
    double operand;
    String operator;

    CalculatorSystem calculatorSystem = new CalculatorSystem();

    public TreeNode(double operand) {
        this.operand = operand;
    }

    public TreeNode(String operator, TreeNode left, TreeNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public double getOperand() {
        return operand;
    }

    public static double evaluateExpression(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.operator == null) {
            return root.operand;
        }

        double left = evaluateExpression(root.left);
        double right = evaluateExpression(root.right);
        double result = 0;

        switch (root.operator) {
            case "+":
                result = left + right;
                break;
            case "-":
                result = left - right;
                break;
            case "*":
                result = left * right;
                break;
            case "/":
                if (right != 0) {
                    return left / right;
                } else {
                    return 0.0;
                }
        }

        return result;
    }

}
