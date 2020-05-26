import java.util.ArrayList;

/**
 * Represents a graph; vertices
 * connected or not by edges
 */
public class Graph {

    private int vertex;
    private int edge;
    private Bag<AdjEdge>[] adjacent;

    //I feel this should be in a different
    //file
    public ArrayList<Character> twoByTwo;
    public ArrayList<Integer> vertices;

    public String skipped = "";
    public int rows;
    public int columns;

    /**
     * Graph constructor type one
     * 
     * @param in the input file
     * @param mode the mode
     */
    public Graph(In in, int mode) {     
        switch (mode) {
            case 0:
                setGraphViaDFA(in);
            break;

            case 1:
                setGraphViaMaze(in);
            break;

            case 5:
                setGraphViaMazeTwo(in);
            break;

            default:
            break;
        } 


    } 

    /**
     * Graph constructor type two
     * 
     * @param dfaOrMaze the dfa or maze input file
     * @param target the target specification file
     * @param mode the mode
     */
    public Graph(In dfaOrMaze, In target, int mode) {

        twoByTwo = new ArrayList<Character>();

        switch (mode) {   
            case 2:
                setGraphViaDFA(dfaOrMaze);

                twoByTwo.add(target.readChar());
                target.readLine();
                target.readLine(); //skip integer      

                while (target.hasNextLine()) {
                    twoByTwo.add(target.readChar());
                    target.readLine();
                }
            break;

            case 3:
                vertices = new ArrayList<Integer>();              
                setGraphViaMaze(dfaOrMaze);

                int rowIndex = (int) target.readChar() - 65;
                int columnIndex = (int) target.readChar() - 65;
                int vertex = columnIndex + rowIndex * columns;
                
                vertices.add(vertex);
                target.readLine();
                target.readLine(); //skip integer

                while (target.hasNextLine()) {
                    rowIndex = (int) target.readChar() - 65;
                    columnIndex = (int) target.readChar() - 65;
                    vertex = columnIndex + rowIndex * columns;
                    vertices.add(vertex);
                    target.readLine();
                }
            break;

            default:

            break;
        }
    }

    /**
     * Graph constructor type three
     * 
     * @param dfa the dfa input file
     * @param mazeGraph the maze input file
     * @param targets the target specification file
     * @param mode the mode
     */
    public Graph(In dfa, In mazeGraph, In targets, int mode) {
        CompleteTrace ct = new CompleteTrace(dfa, mazeGraph, targets, mode);
        ct.printModeFour();
    }

    /**
     * Retrieves vertex
     * @return the vertex
     */
    public int vertex() { 
        return vertex; 
    }

    /**
     * Retrieves edge
     * @return the edge
     */
    public int edge() { 
        return edge; 
    }

    /**
     * Inserts a new edge into the graph
     * 
     * @param v the adjacency list index
     * @param w the adjacent edge and vertex
     */
    public void addEdge(int v, AdjEdge w) {
        adjacent[v].add(w);
        //adjacent[w].add(v); -used for undirected graph
        edge++;
    }

    /**
     * Retrieves vth adjacency list in bag
     * 
     * @param v the index
     * @return the vth adjacency list
     */
    public Iterable<AdjEdge> adjacent(int v) {
        return adjacent[v];
    }

    /**
     * Retrieves the structure
     * identified with a specific index
     * 
     * @param v the vth adjacency list
     * @param w the edge's identifying index
     * @return the edge and vertex
     */
    public AdjEdge getNode(int v, int w) {

        AdjEdge edge = null;

        for (AdjEdge ae : adjacent[v]) {
            if (ae.verdex() == w) {
                edge = ae;
                break;
            }
        }
        
        return edge;
    }

    /**
     * prints contents of the bag
     */
    public void printGraphInterpretation() {

        for (int v = 0; v < vertex(); v++) {

            System.out.print(v + ": ");
            for (AdjEdge ae : adjacent[v]) {
                System.out.print(ae.toString() + "; ");
            }

            System.out.println();
        }

    }

    /**
     * Configures graph via dfa input file
     * 
     * @param in the dfa input file
     */
    public void setGraphViaDFA(In in) {

        this.vertex = in.readInt();

        adjacent = (Bag<AdjEdge>[]) new Bag[vertex];
        
        for (int v = 0; v < vertex; v++) {
            adjacent[v] = new Bag<AdjEdge>();
        }

        this.edge = in.readInt();

        skipped = in.readLine(); //hypothetically skip first line

        while (in.hasNextLine()) {
            String line = in.readLine();
            int v = (int) line.charAt(0) - 65; 
            int w = (int) line.charAt(4) - 65;

            AdjEdge adjacentEdge = new AdjEdge(w, line.charAt(2));          
            addEdge(v, adjacentEdge);
        }
    }

    /**
     * Configures the graph via the maze input
     * 
     * @param in input maze file
     */
    public void setGraphViaMaze(In in) {

        rows = in.readInt();
        columns = in.readInt();
        skipped = in.readLine(); //hypothetically continue to second line
        
        this.vertex = rows * columns;

        adjacent = (Bag<AdjEdge>[]) new Bag[vertex];
        
        for (int v = 0; v < vertex; v++) {
            adjacent[v] = new Bag<AdjEdge>();
        }
        
        this.edge = 0;

        ArrayList<String> maze = new ArrayList<String>();

        while (in.hasNextLine()) {
            String line = in.readLine();
            maze.add(line);
        }

        int j = 0;

        for (int i = 1; i < maze.size() - 1; i += 2) {

            for (int w = 1; w < maze.get(i).length(); w += 4) {

                if (maze.get(i).charAt(w - 1) != '|') {
                    addEdge(j, new AdjEdge(j - 1, 'd'));
                } 

                if (maze.get(i).charAt(w + 3) != '|') {
                    addEdge(j, new AdjEdge(j + 1, 'b'));
                }

                if (maze.get(i - 1).charAt(w) != '-') {
                    addEdge(j, new AdjEdge(j - columns, 'a'));
                }

                if (maze.get(i + 1).charAt(w) != '-') {
                    addEdge(j, new AdjEdge(j + columns, 'c'));
                }

                j++;
            }
        }
    }


    /**
     * Configures the graph via the maze input
     * 
     * @param in input maze file
     */
    public void setGraphViaMazeTwo(In in) {

        rows = in.readInt();
        columns = in.readInt();
        in.readLine(); //hypothetically continue to second line
        
        this.vertex = rows * columns;

        adjacent = (Bag<AdjEdge>[]) new Bag[vertex];
        
        for (int v = 0; v < vertex; v++) {
            adjacent[v] = new Bag<AdjEdge>();
        }
        
        this.edge = 0;

        ArrayList<String> maze = new ArrayList<String>();

        int a = 0;

        CompleteTrace.stateArray = new Integer[this.vertex];

        while (in.hasNextLine()) {

            String line = in.readLine();
            maze.add(line);
        }

        int j = 0;

        for (int i = 1; i < maze.size() - 1; i += 2) {

            for (int w = 1; w < maze.get(i).length(); w += 4) {

                if (maze.get(i).charAt(w - 1) != '|') {
                    addEdge(j, new AdjEdge(j - 1, 'd'));
                } 

                if (maze.get(i).charAt(w + 3) != '|') {
                    addEdge(j, new AdjEdge(j + 1, 'b'));
                }

                if (maze.get(i - 1).charAt(w) != '-') {
                    addEdge(j, new AdjEdge(j - columns, 'a'));
                }

                if (maze.get(i + 1).charAt(w) != '-') {
                    addEdge(j, new AdjEdge(j + columns, 'c'));
                }

                Character state = maze.get(i).charAt(w + 1);
                if (Character.isUpperCase(state)) { //fearful redundancy
                    CompleteTrace.stateArray[j] = (int) state - 65;
                }

                j++;
            }
        }

    }
}
