/**
 * Represents a neighbouring collective 
 * structure containing an index
 * and an associated transfer state
 */
public class AdjEdge {

    private Integer verdex;
    private Character state;

    /**
     * Initializes structure index and state
     * @param verdex the identifying edge index
     * @param state the associated transfer state
     */
    public AdjEdge(Integer verdex, Character state) {
        this.verdex = verdex;
        this.state = state;
    }

    /**
     * Retrieves the edge's identifying index
     * @return this edge's index
     */
    public Integer verdex() {
        return verdex;
    }

    /**
     * Retrieves the edge's associated state
     * @return this edge's state
     */
    public Character state() {
        return state;
    }

    @Override
    public String toString() { 
        return String.format("Verdex: " + verdex + ", State: " + state); 
    }
}
