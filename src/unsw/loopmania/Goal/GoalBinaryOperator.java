package unsw.loopmania.Goal;

public abstract class GoalBinaryOperator implements GoalNode {
    private GoalNode left, right;

    public GoalBinaryOperator(GoalNode left, GoalNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean getValue() {
        return applyOperator(left.getValue(), right.getValue());
    }

    abstract public boolean applyOperator(boolean leftValue, boolean rightValue);
}
