package unsw.loopmania.States;

public interface AttackState {
    /**
     * Attack strategy to switching between normal attacking state and critical
     * attack states
     * @param hasShield - boolean to indicate whether a shield is equipped
     * @return integer containing damage or -1 to switch to normal attack state or -2 to switch
     * to critical attack state
     */
    public int attack(boolean hasShield);
}
