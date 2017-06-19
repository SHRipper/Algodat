import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Lukas on 20.05.2017.<br><br>
 * <p>
 * This class is used for creating a directed graph.
 */
public class DirectedGraph {

    /**
     * Adjacency matrix of the directed graph.
     * <br><br>
     * <b>Example:</b> <br><br>
     * source -10-> node 1 <br>
     * source -15-> node 2 <br>
     * node 1 -8-> sink <br>
     * node 1 -4-> node 2 <br>
     * node 2 -19-> sink <br>
     * <p>
     * source: {0, 10, 15, 0} <br>
     * node 1: {0, 0, 4, 8} <br>
     * node 2: {0, 0, 0, 10} <br>
     * sink: {0, 0, 0, 0} <br>
     */
    private int[][] adjacency;

    /**
     * Matrix of the residual graph.
     */
    private int[][] residual;

    /**
     * The array which is used to backtrack the path from source to sink.
     */
    private int[] parentNode;

    /**
     * Number of vertices in the directed graph (including source and sink).
     */
    private int vertexCount;

    /**
     * Maximum residual value of the directed graph.
     */
    private int maxFlow;

    /**
     * Stores the integer values of the source and sink node.
     */
    private int source, sink;

    /**
     * Indicates if the directed graph was initialized with a residual graph.
     * Initially false.
     * <p>
     * If it was, then the calculated maximum flow is just the margin of which the
     * flow of the graph could be increased to end up at the maximum.
     */
    private boolean hasInitFlow = false;

    public enum MatrixType {CAPACITY, RESIDUAL}

    /**
     * Creates a directed graph with the given adjacency matrix.
     * The initial residual of each edge is zero.
     * <p>
     * <b>source</b> is the first row
     * and <b>sink</b> is the last row of the matrix
     *
     * @param adjacency Integer matrix of the edge capacities to each node <br>
     */
    public DirectedGraph(int[][] adjacency) {
        this.adjacency = adjacency;
        vertexCount = adjacency.length;
        parentNode = new int[vertexCount];
        maxFlow = 0;
        source = 0;
        sink = vertexCount - 1;
        initializeResidualMatrix();
    }

    /**
     * Creates a directed graph with the given adjacency matrix.
     * The initial flow of each edge is determined by the given residual matrix.
     *
     * @param adjacency integer matrix of the edge adjacency to each vertices
     * @param residual  integer matrix of the flow between the vertices
     */
    public DirectedGraph(int[][] adjacency, int[][] residual) {
        this(adjacency);
        this.residual = residual;
        this.hasInitFlow = true;
    }

    /**
     * Creates a graph with a random adjacency matrix.
     * The and initial residual for each edge is zero.
     *
     * @param vertexCount Value for how many vertices the created graph should have.
     *                    Has to be at least 3. If the given value is less than 3 the value is automatically set to 3.
     */
    public DirectedGraph(int vertexCount) {
        if (vertexCount < 3) {
            vertexCount = 3;
        }
        this.vertexCount = vertexCount;
        this.adjacency = generateRandomCapacityMatrix();
        parentNode = new int[this.vertexCount];
        maxFlow = 0;
        source = 0;
        sink = this.vertexCount - 1;
        initializeResidualMatrix();
    }

    /**
     * Initializes the residual matrix by copying the values of the adjacency matrix
     */
    private void initializeResidualMatrix() {
        this.residual = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                residual[i][j] = adjacency[i][j];
            }
        }
    }

    /**
     * Generates a random adjacency matrix with a probablity of ~0.8 to create an edge between two vertices,
     * if the following conditions are met:<br>
     * <ul>
     * <li>There is no direct connection between source and sink.</li>
     * <li>There can not be a connection where start and end of the edge is the same vertex.</li>
     * <li>The maximum edges between two vertices is 1.</li>
     * </ul>
     * <br>
     * If an edge between two vertices is created the capacity is chosen randomly out of [1,20].
     *
     * @return The created adjacency matrix.
     */
    private int[][] generateRandomCapacityMatrix() {
        Random rnd = new Random();
        int matrix[][] = new int[vertexCount][vertexCount];
        for (int row = 0; row < vertexCount - 1; row++) {
            for (int col = 0; col < vertexCount; col++) {

                // Probability of ~0.8 for prob being true
                boolean prob = rnd.nextDouble() <= 0.8;
                boolean directSourceSinkConn = (row == 0 && col == vertexCount - 1);
                boolean selfConn = col != row;
                boolean connExists = matrix[col][row] == 0;

                if (prob && selfConn && connExists && !directSourceSinkConn) {
                    // generate edge with random capacity out of [1,20]
                    int capacity = rnd.nextInt(20) + 1;
                    matrix[row][col] = capacity;
                }
            }
        }

        return matrix;
    }

    /**
     * Prints the chosen matrix of the graph to the console.
     * Each column is seperated by ','.
     * Each row starts with '{' and ends with '}',
     * so that it can be pasted as an array initialization somewhere else.
     *
     * @param type The type of the matrix that should be printed
     */
    public void printMatrix(MatrixType type) {
        int[][] matrix = {};
        switch (type) {
            case CAPACITY:
                matrix = this.adjacency;
                System.out.println("Adjacency Matrix:\n");
                break;

            case RESIDUAL:
                matrix = this.residual;
                System.out.println("Residual Matrix:\n");
                break;
        }

        for (int i = 0; i < vertexCount; i++) {
            System.out.print("{");
            for (int j = 0; j < vertexCount; j++) {
                System.out.print(matrix[i][j]);
                if (j != vertexCount - 1) System.out.print(", ");
            }
            if (i == vertexCount - 1) {
                System.out.println("}");
            } else {
                System.out.println("},");
            }
        }
        System.out.println();
    }

    /**
     * Uses the BFS (breadth first search) algorithm
     * to determine the shortest path from source to sink.
     * <p>
     * The parents of each vertex is saved in the parentNode array,
     * which can then be used to recreate the path.
     *
     * @return <b>true</b> if such a path exists and <b>false</b> if not.
     */
    private boolean findPath() {
        boolean visited[] = new boolean[vertexCount];
        LinkedList<Integer> queue = new LinkedList<>();
        int parent;

        // init
        queue.add(source);
        visited[source] = true;
        parentNode[source] = -1;

        // breadth first search algorithm
        while (queue.size() != 0) {
            parent = queue.poll();
            for (int node = source; node < vertexCount; node++) {

                // true for example: 2 -(10,5)-> 5, edge from 2 to 5 is not empty
                //                   then the path from 5 to 2 is possible
                // false otherwise
                boolean reversePath_notEmpty = adjacency[node][parent] > 0 && residual[parent][node] > 0;
                boolean regularPath_notFull = adjacency[parent][node] > 0 && residual[node][parent] < adjacency[parent][node];

                if (!visited[node] && (regularPath_notFull || reversePath_notEmpty)) {
                    queue.add(node);
                    parentNode[node] = parent;
                    visited[node] = true;
                }
            }
        }
        return visited[sink];
    }

    /**
     * Calculates the total flow of the path, that is saved in the parentNode array.
     *
     * @return The flow of the path.
     */
    private int calculatePathFlow() {
        int flow = Integer.MAX_VALUE;
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            if (adjacency[node][parent] > 0 && (adjacency[node][parent] - this.residual[parent][node]) >= 0) {
                // edge was chosen against the direction of the edge -> the flow can be the reverse flow
                // e.g. 2 -(15,10)-> 5 , then there can be a flow of 10 from 5 to 2
                flow = Math.min(flow, residual[parent][node]);
            } else {
                // normal case
                flow = Math.min(flow, adjacency[parent][node] - this.residual[node][parent]);
            }
            node = parentNode[node];
        }
        return flow;
    }

    /**
     * Updates the graph with the given flow value along the path which is saved in the parentNode array.
     *
     * @param flow The calculated flow value of the chosen path in the parentNode array.
     */
    private void updateGraph(int flow) {
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            this.residual[parent][node] -= flow;
            this.residual[node][parent] += flow;
            node = parentNode[node];
        }
    }

    /**
     * Uses the Ford-Fulkerson algorithm to calculate the maximum flow of this graph.
     * Edmonds-Karp extension is used to determine the shortest path from source to sink
     * in every iteration with the breadth-first-search algorithm.
     *
     * The calculated value of the maximum flow is printed to the console.
     */
    public void printMaxFlow() {
        int flow;

        // loop as long as there exists a path from source to sink
        while (findPath()) {
            flow = calculatePathFlow();
            updateGraph(flow);
            this.maxFlow += flow;
        }

        if (hasInitFlow) {
            // The graph was initialized with a residual graph
            // which makes this calculated max flow just the margin of which the
            // flow could be increased.
            System.out.printf("With the given residual graph," +
                    " the flow of the graph could be increased by %s to end up as the maximum flow.\n\n", maxFlow);
        } else {
            // The graph was created just with a adjacency matrix.
            // The calculated max flow is indeed the maximum flow of the graph.
            System.out.printf("The maximum flow of the given graph is %s\n\n", maxFlow);
        }
    }
}
