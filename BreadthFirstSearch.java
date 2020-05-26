import java.util.Arrays;
import java.util.ArrayList;

/**
 * An abstraction of data navigable via the 
 * breadth first search algorithm
 */
public class BreadthFirstSearch {
    
    private boolean[] touched;
    private int[] edgeTo;
    private Graph graph;

    /**
     * Constructor initializes instance variables
     * @param graph the preconfigured graph data
     */
    public BreadthFirstSearch(Graph graph) {
        int vertexCount = graph.vertex();
        touched = new boolean[vertexCount];
        edgeTo = new int[vertexCount];
        this.graph = graph;
    }

    /** 
     * Central algorithm for quickest path
     * determination
     * 
     * @param source the starting vertex in a graph
     * to be enabled with breadth first search
     */
    public void bfs(Integer source) {

        Queue<Integer> queue = new Queue<Integer>();
        touched[source] = true;
        queue.enqueue(source);

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            
            for (AdjEdge w : graph.adjacent(v)) {
                int verdex = w.verdex();    
                if (!touched[verdex]) {
                    edgeTo[verdex] = v;
                    touched[verdex] = true;
                    queue.enqueue(verdex);
                }
            } 
        }

    }

    /**
     * Confirms the existence of a route from
     * a source to the provided destination
     * 
     * @param dest the desired destination 
     * @return the vertex has or has not been
     * touched
     */
    public boolean hasRouteTo(int dest) { 
        return touched[dest]; 
    }

    /**
     * Determines the complete path from 
     * a source to a destination
     * 
     * @param source the starting vertex
     * @param dest the final vertex
     * @return an iterable stack of vertices 
     * represented by integers
     */
    public Iterable<Integer> routeTo(int source, int dest) {

        if (!hasRouteTo(dest)) {
            return null;
        }   

        Stack<Integer> path = new Stack<Integer>();

        for (int x = dest; x != source; x = edgeTo[x]) {
            path.push(x);
        }

        path.push(source);
        return path;
    }

    /**
     * Reset all touched vertices to 
     * false
     */
    public void resetTouched() {
        Arrays.fill(touched, false);
    }

    /**
     * print state matrix
     */
    public void printStateMatrix() {

        int row = 0;
        int column = 0;
   
        int counter = 0;
        int nodeOne = 0;
        int nodeTwo = 0;

        while (row < graph.vertex()) {
            
            bfs(row);

            //routeTo method returns null if hasRouteTo
            //returns false
        
            for (int x : routeTo(row, column)) {          
                if (counter == 0) {
                    nodeOne = x; 
                } else if (counter == 1) {
                    nodeTwo = x;
                }
    
                counter++;
            }

            String state = "";
    
            switch (counter) { //node to itself
                case 1: 
                    if (column == 0) {
                        state = "  ";
                    } else {
                        state = "   ";
                    }
                break;
                case 2: 
                    if (column == 0) {
                        state = " " + graph.getNode(nodeOne, nodeTwo).state();
                    } else {
                        state = "  " + graph.getNode(nodeOne, nodeTwo).state();
                    }
                break;

                default: 
                    if (column == 0) {
                        state = "-" + graph.getNode(nodeOne, nodeTwo).state();
                    } else {
                        state = " -" + graph.getNode(nodeOne, nodeTwo).state();
                    }
                break;
            } 
    
            System.out.print(state);



            column++;

            if (column == graph.vertex()) {
                column = 0;
                row++;
                System.out.println();
            }

            counter = 0;
            resetTouched();
        }
    }

    /**
     * Print sequence of edges
     * 
     * @param vertices the list of states which identify
     * vertices
     */
    public void printEdgeTrace(ArrayList<Character> vertices) {

        //perhaps clear and fill holder at higher frequencies?
        //and maybe compose an integer arraylist of vertices
        ArrayList<Integer> holder = new ArrayList<>();

        for (int a = 0; a < vertices.size() - 1; a++) {

            int source = (int) vertices.get(a) - 65;
            int dest = (int) vertices.get(a + 1) - 65;

            bfs(source);

            for (int x : routeTo(source, dest)) {    
                //System.out.print(x);
                holder.add(x);      
            }

            int v = 0;
    
            while (v < holder.size() - 1) {
                Character edge = graph.getNode(holder.get(v),  
                                    holder.get(v + 1)).state();
                System.out.println(edge);
                v++;
            }

            holder.clear();
            resetTouched();  
        }

    }   

    /**
     * Print trace of automaton movement through maze
     * 
     * @param vertices the list of states which identify
     * vertices
     */
    public void printMovementTrace(ArrayList<Integer> vertices) {

        ArrayList<Integer> holder = new ArrayList<Integer>();

        for (int a = 0; a < vertices.size() - 1; a++) {

            int source = vertices.get(a);
            int dest = vertices.get(a + 1);

            bfs(source);

            for (int x : routeTo(source, dest)) {    
                //System.out.print(x);
                holder.add(x);      
            }

            int v = 0;
    
            while (v < holder.size() - 1) {
                Character edge = graph.getNode(holder.get(v), 
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
               
                v++;
            }

            holder.clear();
            resetTouched();  
        }
    }

}
