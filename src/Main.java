import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 18.05.2017.
 */
public class Main {

    public static void main(String[] args){

        // create Nodes
        Node source = new Node(0);
        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node sink = new Node(3);
        System.out.println("node count = " + Node.count);

        // create Node list
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(source);
        nodeList.add(n1);
        nodeList.add(n2);
        nodeList.add(sink);

        // create Paths
        Path p1 = Node.connect(source, n1,10);
        Path p2 = Node.connect(source, n2,15);
        Path p3 = Node.connect(n1, n2,4);
        Path p4 = Node.connect(n1,sink,8);
        Path p5 = Node.connect(n2,sink,10);

        // create Path list
        List<Path> pathList = new ArrayList<>();
        pathList.add(p1);
        pathList.add(p2);
        pathList.add(p3);
        pathList.add(p4);
        pathList.add(p5);

        Graph graph = new Graph(nodeList, pathList);

    }
}
