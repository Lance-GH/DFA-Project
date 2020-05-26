import java.util.ArrayList;

/**
 * A utility class for the fourth mode
 * of this project which combines 
 * maze traversal and DFA-shortest-path 
 * deduction
 */
public class CompleteTrace {
    
    private Graph dfaGraph;
    private Graph mazeGraph;
    private BreadthFirstSearch bfsDFA; 
    private BreadthFirstSearch bfsMaze; 

    public static Integer[] stateArray;
    private ArrayList<Integer> targets;

    /**
     * Constructor that calls other constructors,
     * for their respective graph initializations
     * and breadth-first-search activation
     * 
     * @param dfa the dfa input file
     * @param mazeGraph the state maze input file
     * @param targets the target-specification input file
     * @param mode the mode which one expects to be four
     */
    public CompleteTrace(In dfa, In mazeGraph, In targets, int mode) {

        dfaGraph = new Graph(dfa, 0);
        this.mazeGraph = new Graph(mazeGraph, 5);
        bfsDFA = new BreadthFirstSearch(dfaGraph);
        bfsMaze = new BreadthFirstSearch(this.mazeGraph);
        
        this.targets = new ArrayList<Integer>();
        
        int rowIndex = (int) targets.readChar() - 65;
        int columnIndex = (int) targets.readChar() - 65;
        int vertex = columnIndex + rowIndex * this.mazeGraph.columns;

        this.targets.add(vertex);
        targets.readLine();

        targets.readLine(); //skip integer

        while (targets.hasNextLine()) {
            rowIndex = (int) targets.readChar() - 65;
            columnIndex = (int) targets.readChar() - 65;
            vertex = columnIndex + rowIndex * this.mazeGraph.columns;
            this.targets.add(vertex);
            targets.readLine();
        }
    }

    /**
     * prints mode four textually to the screen
     */
    public void printModeFour() {

        ArrayList<Integer> holder = new ArrayList<Integer>();

        for (int a = 0; a < targets.size() - 1; a++) {
            //maze
            int source = targets.get(a);
            int dest = targets.get(a + 1);

            bfsMaze.bfs(source);

            for (int x : bfsMaze.routeTo(source, dest)) {    
                //System.out.print(x + " ");
                holder.add(x);      
            }

            int v = 0;

            while (v < holder.size() - 1) {
                ArrayList<Integer> stateHolder = new ArrayList<Integer>();
                int start = stateArray[holder.get(v)];
                int end = stateArray[holder.get(v + 1)];
                bfsDFA.bfs(start);
                for (int x : bfsDFA.routeTo(start, end)) {
                    stateHolder.add(x);
                }
                for (int x = 0; x < stateHolder.size() - 1; x++) {
                    Character state = dfaGraph.getNode(stateHolder.get(x), 
                                        stateHolder.get(x + 1)).state();
                    System.out.println(state);
                }

                stateHolder.clear();
                bfsDFA.resetTouched();

                //print maze
                Character edge = mazeGraph.getNode(holder.get(v), 
                                 holder.get(v + 1)).state();
                switch (edge) {
                    case 'a':
                        System.out.println("up");
                    break;

                    case 'b':
                        System.out.println("right");
                    break;

                    case 'c':
                        System.out.println("down");
                    break;

                    case 'd':
                        System.out.println("left");
                    break;

                    default:

                    break;
                }

                //System.out.println();

                v++;
            }

            holder.clear();
            bfsMaze.resetTouched();
                           
        }

    }

    /**
     * prints state array textually to the screen
     */
    public void printStateArray() {

        for (int a = 0; a < stateArray.length; a++) {
            System.out.print(stateArray[a] + " ");
        }

        System.out.println();
    }

    /**
     * prints targets textually to the screen
     */
    public void printTargets() {

        System.out.println("Targets: ");

        for (int i : targets) {
            System.out.print(i + " ");
        }

        System.out.println();

    }

}
