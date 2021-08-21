package unsw.loopmania.Goal;

public class GoalLeaf implements GoalNode {
    private boolean value;

    public GoalLeaf(boolean value) {
        this.value = value;
    }

    @Override
    public boolean getValue() {
        return value;
    }
}
