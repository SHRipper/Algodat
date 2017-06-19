/**
 * Created by Lukas on 20.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        int adm1[][] = new int[][]{
                {0, 10, 15, 0}, // source := 0
                {0, 0, 4, 8},   // 1
                {0, 0, 0, 10},  // 2
                {0, 0, 0, 0}    // sink := 3
        };

        int adm2[][] = new int[][]{
                {0, 3, 0, 3, 0, 0, 0}, // source := 0
                {0, 0, 4, 0, 0, 0, 0}, // 1
                {3, 0, 0, 1, 2, 0, 0}, // 2
                {0, 0, 0, 0, 2, 6, 0}, // 3
                {0, 1, 0, 0, 0, 0, 1}, // 4
                {0, 0, 0, 0, 0, 0, 9}, // 5
                {0, 0, 0, 0, 0, 0, 0}  // sink := 6
        };

        int adm3[][] = new int[][]{
                {0, 4, 6, 0, 0, 0}, // source := 0
                {0, 0, 0, 3, 0, 0}, // 1
                {0, 4, 0, 4, 3, 0}, // 2
                {0, 0, 0, 0, 0, 7}, // 3
                {0, 0, 0, 0, 0, 5}, // 4
                {0, 0, 0, 0, 0, 0}  // sink := 5
        };

        int residual4[][] = new int[][]{
                {0, 0, 0, 25, 5, 0, 0, 0},  // source := 6
                {20, 0, 10, 0, 0, 5, 0, 0}, // 0
                {0, 15, 0, 0, 0, 0, 0, 5},  // 1
                {25, 0, 0, 0, 10, 5, 0, 0}, // 2
                {15, 0, 0, 0, 0, 0, 5, 0},  // 3
                {0, 0, 15, 25, 0, 0, 0, 0}, // 4
                {0, 0, 0, 0, 15, 0, 0, 5},  // 5
                {0, 0, 0, 0, 0, 20, 25, 0}  // sink := 7
        };
        int adm4[][] = new int[][]{
                {0, 20, 0, 50, 20, 0, 0, 0}, // source := 6
                {0, 0, 25, 0, 0, 5, 0, 0},   // 0
                {0, 0, 0, 0, 0, 15, 0, 5},   // 1
                {0, 0, 0, 0, 10, 30, 0, 0},  // 2
                {0, 0, 0, 0, 0, 0, 20, 0},   // 3
                {0, 0, 0, 0, 0, 0, 0, 20},   // 4
                {0, 0, 0, 0, 0, 0, 0, 30},   // 5
                {0, 0, 0, 0, 0, 0, 0, 0}     // sink := 7
        };

        DirectedGraph g;

        // DirectedGraph with the given capacity matrix. Initial flows are 0.
        g = new DirectedGraph(adm1);
        // g = new DirectedGraph(adm2);
        // g = new DirectedGraph(adm3);
        // g = new DirectedGraph(adm4);

        // DirectedGraph with the given capacity and flow matrix
        // g = new DirectedGraph(adm4,residual4);

        // Random graph with 4 vertices
        // g = new DirectedGraph(20);

        g.printMatrix(DirectedGraph.MatrixType.CAPACITY);
        g.printMaxFlow();
        g.printMatrix(DirectedGraph.MatrixType.RESIDUAL);

    }


}
