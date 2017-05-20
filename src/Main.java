/**
 * Created by Lukas on 20.05.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // create a graph matrix
        // numbers indicate capacities

        // source: source, node 1, node 2, sink
        // node 1: source, node 1, node 2, sink
        // node 2: source, node 1, node 2, sink
        //   sink: source, node 1, node 2, sink
        int graph[][] = new int[][]{
                {0, 10, 15, 0}, // source
                {0, 0, 4, 8},   // node 1
                {0, 0, 0, 10},  // node 2
                {0, 0, 0, 0}    // sink
        };
        // source -10-> node 1
        // source -15-> node 2
        // node 1 -8-> sink
        // node 1 -4-> node2
        // node 2 -19-> sink

        Graph g = new Graph(graph);

        System.out.println("maximaler Flow des Graphen: " + g.getMaxFlow());

    }

}
