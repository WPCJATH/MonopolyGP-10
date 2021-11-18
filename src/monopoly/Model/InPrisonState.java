package monopoly.Model;

/* Possible statements related to prison. */
public enum InPrisonState {
    FREE,
    INJAILROUND1,
    INJAILROUND2,
    INJAILROUND3;

    public static InPrisonState goNextState(InPrisonState current){
        return switch (current) {
            case FREE -> INJAILROUND1;
            case INJAILROUND1 -> INJAILROUND2;
            case INJAILROUND2 -> INJAILROUND3;
            case INJAILROUND3 -> FREE;
        };
    }

    /**
     * @param state one of the states in InPrisonState.
     * @returnWhether user is in jail.
     */
    public static boolean isInJail(InPrisonState state)
    {
        return !state.equals(FREE);
    }

    /**
     * @param state one of the states in InPrisonState.
     * @return whether go out of jail in next round
     */
    public static boolean isOutNextRound(InPrisonState state) {return state==INJAILROUND3;}

    /**
     * @param state a string, must be one of the names of states in InPrisonState.
     * @return  one of the states in InPrisonState corresponding to parsed string
     */
    public static InPrisonState parseState(String state){
        for (InPrisonState s : InPrisonState.values()) {
            if (s.name().equals(state)) {
                return s;
            }
        }
        return null;
    }
}
