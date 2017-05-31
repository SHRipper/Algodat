/**
 * Created by Lukas on 20.05.2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // create a graph matrix
        // numbers indicate capacities
        int adm1[][] = new int[][]{
                {0, 10, 15, 0}, // source
                {0, 0, 4, 8},   // node 1
                {0, 0, 0, 10},  // node 2
                {0, 0, 0, 0}    // sink
        };

        int adm2[][] = new int[][]{
                {0, 3, 0, 3, 0, 0, 0}, // a
                {0, 0, 4, 0, 0, 0, 0},   // b
                {3, 0, 0, 1, 2, 0, 0},  // c
                {0, 0, 0, 0, 2, 6, 0},   // d
                {0, 1, 0, 0, 0, 0, 1},   // e
                {0, 0, 0, 0, 0, 0, 9}, // f
                {0, 0, 0, 0, 0, 0, 0} // g
        };

        int adm3[][] = new int[][]{
                {0, 4, 6, 0, 0, 0}, // s
                {0, 0, 0, 3, 0, 0},   // 1
                {0, 4, 0, 4, 3, 0},  // 2
                {0, 0, 0, 0, 0, 7},   // 3
                {0, 0, 0, 0, 0, 5},   // 4
                {0, 0, 0, 0, 0, 0} // t
        };

        Graph g = new Graph(adm2);

        System.out.println("Maximaler Flow des Graphen: " + g.getMaxFlow());

    }

}
