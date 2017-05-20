import java.util.LinkedList;

/**
 * Created by Lukas on 20.05.2017.
 */
public class Graph {

    int nodeCount; //Number of vertices in graph
    int[][] graph;
    int maxFlow;
    int source, sink;

    /**
     * Create a graph.
     * <p>
     * <b>source</b> is the first row
     * and <b>sink</b> is the last row of the matrix
     *
     * @param graph: int matrix of the edge capacities to each node
     */
    public Graph(int[][] graph) {
        this.graph = graph;
        nodeCount = graph.length;
        maxFlow = 0;
        source = 0;
        sink = nodeCount - 1;
    }


    boolean findPath(int parentNode[]) {
        boolean visited[] = new boolean[nodeCount];
        LinkedList<Integer> queue = new LinkedList<>();
        int parent;

        // init
        queue.add(source);
        visited[source] = true;
        parentNode[source] = -1;

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

    private int calculatePathFlow(int[] parentNode) {
        int flow = Integer.MAX_VALUE;
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            flow = Math.min(flow, graph[parent][node]);
            node = parentNode[node];
        }
        return flow;
    }

    private void updateGraph(int parentNode[], int flow) {
        int node = sink;
        while (node != source) {
            int parent = parentNode[node];
            graph[parent][node] -= flow;
            graph[node][parent] += flow;
            node = parentNode[node];
        }
    }

    int getMaxFlow() {
        int flow;
        int parentNode[] = new int[nodeCount];

        while (findPath(parentNode)) {
            flow = calculatePathFlow(parentNode);
            updateGraph(parentNode, flow);
            this.maxFlow += flow;
        }
        return this.maxFlow;
    }
}
