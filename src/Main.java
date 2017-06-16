/**
 * Created by Lukas on 20.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        int cap1[][] = new int[][]{
                {0, 10, 15, 0}, // source
                {0, 0, 4, 8},   // node 1
                {0, 0, 0, 10},  // node 2
                {0, 0, 0, 0}    // sink
        };

        int cap2[][] = new int[][]{
                {0, 3, 0, 3, 0, 0, 0}, // a
                {0, 0, 4, 0, 0, 0, 0},   // b
                {3, 0, 0, 1, 2, 0, 0},  // c
                {0, 0, 0, 0, 2, 6, 0},   // d
                {0, 1, 0, 0, 0, 0, 1},   // e
                {0, 0, 0, 0, 0, 0, 9}, // f
                {0, 0, 0, 0, 0, 0, 0} // g
        };

        int cap3[][] = new int[][]{
                {0, 4, 6, 0, 0, 0}, // s
                {0, 0, 0, 3, 0, 0},   // 1
                {0, 4, 0, 4, 3, 0},  // 2
                {0, 0, 0, 0, 0, 7},   // 3
                {0, 0, 0, 0, 0, 5},   // 4
                {0, 0, 0, 0, 0, 0} // t
        };

        int flow4[][] = new int[][]{
                //s, a, b, d, h, f, j, s, t
                {0, 0, 0, 25, 5, 0, 0, 0}, //s
                {20, 0, 10, 0, 0, 3, 0, 0}, //a 1
                {0, 15, 0, 0, 0, 5, 0, 5}, //b 2
                {25, 0, 0, 0, 10, 5, 0, 0}, //d 3
                {15, 0, 0, 0, 0, 0, 5, 0}, //h 4
                {0, 2, 10, 25, 0, 0, 0, 0}, //f 5
                {0, 0, 0, 0, 15, 0, 0, 5}, //j 6
                {0, 0, 0, 0, 0, 20, 25, 0}  //t
        };
        int cap4[][] = new int[][]{
                //s, a, b, d, h, f, j, s, t
                {0, 20, 0, 50, 20, 0, 0, 0}, //s
                {0, 0, 25, 0, 0, 5, 0, 0}, //a 1
                {0, 0, 0, 0, 0, 15, 0, 5}, //b 2
                {0, 0, 0, 0, 10, 30, 0, 0}, //d 3
                {0, 0, 0, 0, 0, 0, 20, 0}, //h 4
                {0, 0, 0, 0, 0, 0, 0, 20}, //f 5
                {0, 0, 0, 0, 0, 0, 0, 30}, //j 6
                {0, 0, 0, 0, 0, 0, 0, 0}  //t
        };

        DirectedGraph g;
        // Random graph with 4 vertices
        // g = new DirectedGraph(4);

        // DirectedGraph with the given capacity matrix. Initial flows are 0.
        // g = new DirectedGraph(cap1)
        // g = new DirectedGraph(cap2)
        // g = new DirectedGraph(cap3)

        // DirectedGraph with the given capacity and flow matrix
        g = new DirectedGraph(4);

        g.printCapacityMatrix();
        System.out.println("Maximaler Flow des Graphen: " + g.getMaxFlow());
    }


}
