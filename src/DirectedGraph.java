import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Lukas on 20.05.2017.<br><br>
 * <p>
 * This class is used for creating a directed graph
 * with capacities and flows (optional) for each edge to calculate the maximum flow.
 */
public class DirectedGraph {

    /**
     * Capacity matrix of the directed graph.
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
    private int[][] capacity;

    /**
     * Flow matrix of the directed graph.
     */
    private int[][] flow;

    /**
     * The array which is used to backtrack the path from source to sink.
     */
    private int[] parentNode;

    /**
     * Number of vertices in the directed graph (including source and sink).
     */
    private int vertexCount;

    /**
     * Maximum flow value of the directed graph.
     */
    private int maxFlow;

    private int source, sink;

    /**
     * Creates a directed graph with the given capacity matrix.
     * The initial flow of each edge is zero.
     * <p>
     * <b>source</b> is the first row
     * and <b>sink</b> is the last row of the matrix
     *
     * @param capacity Integer matrix of the edge capacities to each node <br>
     */
    public DirectedGraph(int[][] capacity) {
        this.capacity = capacity;
        vertexCount = capacity.length;
        parentNode = new int[vertexCount];
        maxFlow = 0;
        source = 0;
        sink = vertexCount - 1;
        this.flow = new int[vertexCount][vertexCount];
    }

    /**
     * Creates a directed graph with the given capacity matrix.
     * The initial flow of each edge is determined by the given flow matrix.
     *
     * @param capacity integer matrix of the edge capacity to each vertices
     * @param flow     nteger matrix of the flow between the vertices
     */
    public DirectedGraph(int[][] capacity, int[][] flow) {
        this(capacity);
        this.flow = flow;
    }

    /**
     * Creates a graph with a random capacity matrix.
     * The and initial flow for each edge is zero.
     *
     * @param vertexCount Value for how many vertices the created graph should have.
     *                    Has to be at least 3. If the given value is less than 3 the value is automatically set to 3.
     */
    public DirectedGraph(int vertexCount) {
        if (vertexCount < 3) {
            vertexCount = 3;
        }
        this.vertexCount = vertexCount;
        this.capacity = generateRandomCapacityMatrix();
        parentNode = new int[this.vertexCount];
        maxFlow = 0;
        source = 0;
        sink = this.vertexCount - 1;
        this.flow = new int[this.vertexCount][this.vertexCount];
    }

    /**
     * Generates a random capacity matrix with a probablity of ~0.8 to create an edge between two vertices,
     * if the following conditions are met:<br>
     * <ul>
     * <li>There is no direct connection between source and sink.</li>
     * <li>There can not be a connection where start and end of the edge is the same vertex.</li>
     * <li>The maximum edges between two vertices is 1.</li>
     * </ul>
     * <br>
     * If an edge between two vertices is created the capacity is chosen randomly out of [1,20].
     *
     * @return The created capacity matrix.
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
     * Prints the capacity matrix of the graph to the console.
     * Each column is seperated by ','.
     * Each row starts with '{' and ends with '}',
     * so that it can be pasted as an array initialization somewhere else.
     */
    public void printCapacityMatrix() {
        for (int i = 0; i < vertexCount; i++) {
            System.out.print("{");
            for (int j = 0; j < vertexCount; j++) {
                System.out.print(capacity[i][j]);
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

                // true for example: 2 -(10,10)-> 5, edge from 2 to 5 is full
                //                   then the path from 5 to 2 is possible
                // false otherwise
                boolean reversePath_isFull = capacity[node][parent] > 0 && flow[parent][node] == capacity[node][parent];
                boolean regularPath_notFull = capacity[parent][node] > 0 && flow[node][parent] < capacity[parent][node];

                if (!visited[node] && (regularPath_notFull || reversePath_isFull)) {
                    queue.add(node);
                    parentNode[node] = parent;
                    visited[node] = true;
                }
            }
        }
        return visited[sink];
    }

    /**
     * Calculates the total flow of the path that is saved in the parentNode array.
     *
     * @return The flow of the path.
     */
    private int calculatePathFlow() {
        int flow = Integer.MAX_VALUE;
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            if (capacity[node][parent] - this.flow[parent][node] == 0) {
                // edge was chosen against the direction of the capacity -> the flow can be the reverse capacity
                // e.g. 2 -(15,15)-> 5 , then there can be a flow of 15 from 5 to 2
                flow = Math.min(flow, capacity[node][parent]);
            } else {
                // normal case
                flow = Math.min(flow, capacity[parent][node] - this.flow[node][parent]);
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
            this.flow[parent][node] -= flow;
            this.flow[node][parent] += flow;
            node = parentNode[node];
        }
    }

    /**
     * Uses the Ford-Fulkerson algorithm to calculate the maximum flow of this graph.
     * Edmonds-Karp extension is used to determine the shortest path from source to sink
     * in every iteration with the breadth-first-search algorithm.
     *
     * @return The maximum flow value for the graph.
     */
    public int getMaxFlow() {
        int flow;

        // loop as long as there exists a path from source to sink
        while (findPath()) {
            flow = calculatePathFlow();
            updateGraph(flow);
            this.maxFlow += flow;
        }
        return this.maxFlow;
    }
}
