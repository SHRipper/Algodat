import java.util.LinkedList;

/**
 * Created by Lukas on 20.05.2017.
 */
public class Graph {

    private int[][] graph;
    private int[] parentNode;
    private int nodeCount;
    private int maxFlow;
    private int source, sink;

    private int count;
    private long startTime;


    /**
     * Creates a graph.
     * <p>
     * <b>source</b> is the first row
     * and <b>sink</b> is the last row of the matrix
     *
     * @param graph integer matrix of the edge capacities to each node <br>
     *              <b>Example:</b> <br><br>
     *              source -10-> node 1 <br>
     *              source -15-> node 2 <br>
     *              node 1 -8-> sink <br>
     *              node 1 -4-> node 2 <br>
     *              node 2 -19-> sink <br>
     *              <p>
     *              source: {0, 10, 15, 0} <br>
     *              node 1: {0, 0, 4, 8} <br>
     *              node 2: {0, 0, 0, 10} <br>
     *              sink: {0, 0, 0, 0} <br>
     */
    public Graph(int[][] graph) {
        this.graph = graph;
        nodeCount = graph.length;
        parentNode = new int[nodeCount];
        startTime = System.nanoTime();
        maxFlow = 0;
        source = 0;
        sink = nodeCount - 1;
    }

    /**
     * Uses the BFS (breath first search) algorithm
     * to determine a path from source-node to sink-node.
     * <p>
     * The resulting path is saved to the parentNode array.
     *
     * @return <b>true</b> if such a path exists and <b>false</b> if not.
     */
    private boolean findPath() {
        boolean visited[] = new boolean[nodeCount];
        LinkedList<Integer> queue = new LinkedList<>();
        int parent;

        // init
        queue.add(source);
        visited[source] = true;
        parentNode[source] = -1;

        // Breath first search algorithm
        while (queue.size() != 0) {
            parent = queue.poll();
            for (int node = source; node < nodeCount; node++) {
                if (!visited[node] && graph[parent][node] > 0) {
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
            flow = Math.min(flow, graph[parent][node]);
            node = parentNode[node];
        }
        return flow;
    }

    /**
     * Updates the graph with the given flow value along the path which is saved in the parentNode array.
     *
     * @param flow The calculated flow value of the path in the parentNode array.
     */
    private void updateGraph(int flow) {
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            graph[parent][node] -= flow;
            graph[node][parent] += flow;
            node = parentNode[node];
        }
    }

    /**
     * Used the Ford-Fulkerson algorithm to calculate the maximum flow of this graph.
     *
     * @return The maximum flow value.
     */
    public int getMaxFlow() {
        int flow;

        // loop as long as there exists a path from source to sink
        while (findPath()) {
            count++;
            flow = calculatePathFlow();
            updateGraph(flow);
            this.maxFlow += flow;
        }
        System.out.println("count: " + count);
        System.out.println("Laufzeit in ms: " + (System.nanoTime() - startTime) / 1000);
        return this.maxFlow;
    }
}
