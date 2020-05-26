/**
 * Class containing the main thread
 */
public class Robot {

    /**
     * main thread
     * 
     * @param args array of strings used as input
     */
    public static void main(String[] args) {

        int mode = Integer.parseInt(args[0]);
        
        if (mode == 0) {

            In dfa = new In(args[1]);
            Graph graph = new Graph(dfa, mode);
            BreadthFirstSearch bfp = new BreadthFirstSearch(graph);
            bfp.printStateMatrix();

        } else if (mode == 1) {

            In maze =  new In(args[1]);
            Graph graph = new Graph(maze, mode);
            BreadthFirstSearch bfp = new BreadthFirstSearch(graph);
            bfp.printStateMatrix();

        } else if (mode == 2) {

            In targets = new In(args[1]);
            In dfa = new In(args[2]);
            Graph graph = new Graph(dfa, targets, mode);
            BreadthFirstSearch bfp = new BreadthFirstSearch(graph);
            bfp.printEdgeTrace(graph.twoByTwo);

        } else if (mode == 3) {

            In maze = new In(args[2]);
            In targets = new In(args[1]);
            Graph graph = new Graph(maze, targets, mode);
            BreadthFirstSearch bfp = new BreadthFirstSearch(graph);
            bfp.printMovementTrace(graph.vertices);
            
        } else if (mode == 4) {
                
            In targets = new In(args[1]); 
            In dfa = new In(args[2]);
            In maze =  new In(args[3]);
            Graph graph = new Graph(dfa, maze, targets, mode);

        }

        //System.out.println((System.currentTimeMillis() - start)/1000.0);
    }
}
