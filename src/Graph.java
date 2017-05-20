import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lukas on 20.05.2017.
 */
public class Graph {
    List<Node> nodeList = new ArrayList<>();
    List<Path> pathList = new ArrayList<>();
    Node sink, source;
    private int maxFlow = 0;

    public Graph(List<Node> nodes, List<Path> paths){
        this.pathList = paths;
        this.nodeList = nodes;
        for (Node n : nodeList){
            if (n.id == 0) this.source = n;
            if (n.id == Node.count -1) this.sink = n;
        }
    }

    public void addPath(Path p){
        this.pathList.add(p);
    }

    public void addNode(Node n){
        this.nodeList.add(n);

        if (n.id == 0 && source == null){
            this.source = n;
        }else{
            System.out.println("source already specified.");
        }

        if (n.id == Node.count -1 && sink == null){
            this.sink = n;
        }else{
            System.out.println("sink already specified.");
        }
    }

    public List<Path> findPath()
    {
        boolean visited[] = new boolean[nodeList.size()];
        List<Node> queue = new ArrayList<>();
        queue.add(source);
        visited[source.id] = true;
        List<Path> pathToSink = new ArrayList<>();
        boolean pathCreated = false;

        while (!pathCreated){
            Node currentNode;
            while ((currentNode = queue.get(queue.size()-1)) != sink){

                queue.add(currentNode.connections.get(0).end);

            }

        }


        Node endOfPath = pathToSink.get(pathToSink.size()-1).end;
        if (endOfPath.compareTo(sink)){
            return pathToSink;
        }
        return null;
    }

    private int getFlowOf(List<Path> paths){
        int flow = Integer.MAX_VALUE;
        for (Path p : paths){
            flow = Math.min(p.capacity - p.flow,flow);
        }
        return flow;
    }

    private void updateFlow(List<Path> paths, int flow){
        for (Path p : paths){
            if (p.flow == p.capacity){
                p.flow -= flow;
            }else{
                p.flow += flow;
            }
        }
    }

    public int getMaxFlow(){
        // loop for some times
        List<Path> p = findPath();
        int flow = getFlowOf(p);
        updateFlow(p, flow);
        this.maxFlow += flow;
        return 0;
    }


}
