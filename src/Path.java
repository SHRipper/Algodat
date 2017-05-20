import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 18.05.2017.
 */
public class Path {
    int flow;
    int capacity;
    Node start;
    Node end;

    public Path(Node start, Node end, int capacity){
        this.end = end;
        this.start = start;
        flow = 0;
        this.capacity = capacity;
    }
}
