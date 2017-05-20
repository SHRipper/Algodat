import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 20.05.2017.
 */
public class Graph {
    List<Node> nodeList = new ArrayList<>();
    List<Path> pathList = new ArrayList<>();
    Node sink, source;

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

    public Path findPath()
    {
        /*
        muss noch überarbeitet werden
         */

        boolean visited[] = new boolean[nodeList.size()];
        for(int i=0; i<nodeList.size(); ++i)
            visited[i]=false;

        // Create a queue, enqueue source vertex and mark
        // source vertex as visited
        List<Integer> queue = new ArrayList<>();
        queue.add(s);
        visited[s] = true;
        parent[s]=-1;

        // Standard BFS Loop
        while (queue.size()!=0)
        {
            int u = queue.poll();

            for (int i=0; i<; i++)
            {
                if (visited[i]==false && rGraph[u][v] > 0)
                {
                    queue.add(i);
                    parent[i] = u;
                    visited[i] = true;
                }
            }
        }

        // If we reached sink in BFS starting from source, then
        // return true, else false
        return (visited[t] == true);
    }

    public int getMaxFlow(){
        return 0;
    }


}
