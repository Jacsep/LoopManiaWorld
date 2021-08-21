package unsw.loopmania.Goal;

public class GoalOr extends GoalBinaryOperator {
    public GoalOr(GoalNode left, GoalNode right) {
        super(left, right);
    }

    @Override
    public boolean applyOperator(boolean leftValue, boolean rightValue) {
        return leftValue || rightValue;
    }

}
