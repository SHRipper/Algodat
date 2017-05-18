import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 18.05.2017.
 */
public class Node {
    //List<Path> paths = new ArrayList<>();
    int id;
    static int count = 0;

    public Node(int id){
        this.count++;
        this.id = id;
    }

    public static void connect(Node start, Node end, int capacity){
        Path p = new Path(start, end, capacity);
    }
}
