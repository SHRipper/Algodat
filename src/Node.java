import src.INodeCompare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 18.05.2017.
 */
public class Node implements INodeCompare {
    int id;
    static int count = 0;

    public Node(int id){
        this.count++;
        this.id = id;
    }

    public static void connect(Node start, Node end, int capacity){
        Path p = new Path(start, end, capacity);

    }

    /**
     * Compares the current Node to another one.
     * @param n: Node that should be compared
     * @return
     *      <b>true</b> if the Node IDs match
     *      <b>false</b> if the Node IDs differ
     */
    @Override
    public boolean compareTo(Node n) {
        return this.id == n.id;
    }
}
