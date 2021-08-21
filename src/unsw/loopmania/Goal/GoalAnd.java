package unsw.loopmania.Goal;

public class GoalAnd extends GoalBinaryOperator {
    public GoalAnd(GoalNode left, GoalNode right) {
        super(left, right);
    }

    @Override
    public boolean applyOperator(boolean leftValue, boolean rightValue) {
        return leftValue && rightValue;
    }

}
