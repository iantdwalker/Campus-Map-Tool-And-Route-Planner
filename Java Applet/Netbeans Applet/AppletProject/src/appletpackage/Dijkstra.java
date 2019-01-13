package appletpackage;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.ArrayList;

public class Dijkstra
{
    /** Generic attributes */
    int[] dist;
    int[] pred;
    boolean[] visited;
    int next;
    int[] n;
    int v;
    int d;
    boolean graphConnected = true;
    ArrayList path;
     
    /** Dijkstra's algorithm to find shortest path from s to all other nodes */
    public int[] execute(MapGraph g, int source)
    {
        graphConnected = true;
        dist = new int[g.getSize()];  // shortest known distance from source
        pred = new int[g.getSize()];  // preceeding node in path
        visited = new boolean[g.getSize()]; // all false initially

        for (int i = 0; i < dist.length; i++)
        {
          dist[i] = Integer.MAX_VALUE;
        }
    
        dist[source] = 0;

        /** Catch any unconnected graphs */
        try
        {
            for (int i = 0; i < dist.length; i++)
            {
                next = minVertex(dist,visited);
                visited[next] = true;
        
                /** The shortest path to next is dist[next] and via pred[next] */
  
                n = g.neighbors(next);
        
                for (int j = 0; j < n.length; j++)
                {
                    v = n[j];
                    d = dist[next] + g.getWeight(next,v);
          
                    if (dist[v] > d)
                    {
                        dist[v] = d;
                        pred[v] = next;
                    }        
                }
            }
        }
        
        catch (ArrayIndexOutOfBoundsException e)
        {
            graphConnected = false;
        }
    
        return pred;
    }
  
    /** Method accessed by the execute method */
    private int minVertex(int[] dist,boolean[] v)
    {
        int x = Integer.MAX_VALUE;
        int y = -1;   // graph not connected, or no unvisited vertices
    
        for (int i = 0; i < dist.length; i++)
        {
            if (!v[i] && dist[i]<x)
            {
                y = i;
                x = dist[i];
            }
        }
    
        return y;
    } 
  
    /** Print out all shortest paths from the source via an ArrayList path */
    public String printPath(MapGraph g,int[] pred,int s,int e)
    {
        path = new ArrayList();
        int x = e;
    
        while (x!=s)
        {
            path.add(0,g.getNodeName(x));
            x = pred[x];
        }
    
        path.add(0,g.getNodeName(s));
    
        return path.toString();
    }
  
    public boolean getGraphConnected()
    {
        return graphConnected;
    }
}