package applicationpackage;

import java.lang.*;
import java.util.*;

/**
 * A class that runs Dijkstra's algorithm on a MapGraph object.
 */
public class Dijkstra
{
  private int[]     _shortestDistances;
  private int[]     _preceedingNodes;  
  private boolean[] _visited;
  private int       _next;
  private boolean   _graphConnected;
  private ArrayList _path;
  
  private int[]     n;
  private int       v;
  private int       d;
  
  /**
   * Constructor.
   */
  public Dijkstra()
  {
    _graphConnected = false;
  }

  /**
   * Find the shortest path from sourceNode to all other nodes.
   * @return int[]
   * @param sourceNode
   * @param graph
   */
  public int[] execute(MapGraph graph, int sourceNode)
  {    
    //shortest known distance from source
    _shortestDistances      = new int[graph.getSize()];
    
    //preceeding nodes in path
    _preceedingNodes        = new int[graph.getSize()];
    
    //all false initially
    _visited                = new boolean[graph.getSize()];

    for (int i = 0; i < _shortestDistances.length; i++)
    {
      _shortestDistances[i] = Integer.MAX_VALUE;
    }

    _shortestDistances[sourceNode] = 0;

    //catch any unconnected graphs
    try
    {
      for (int i = 0; i < _shortestDistances.length; i++)
      {
        _next           = this.minVertex(_shortestDistances, _visited);
        _visited[_next] = true;

        /** shortest path to _next is _shortestDistances[_next] and via
        _preceedingNodes[_next] */

        n = graph.neighbors(_next);

        for (int j = 0; j < n.length; j++)
        {
          v = n[j];
          d = _shortestDistances[_next] + graph.getWeight(_next, v);

          if (_shortestDistances[v] > d)
          {
            _shortestDistances[v] = d;
            _preceedingNodes[v]   = _next;
          }
        }
      }
      _graphConnected = true;
    }
    catch (ArrayIndexOutOfBoundsException ex)
    {
      _graphConnected = false;
    }

    return _preceedingNodes;
  }

  /**
   * Private method accessed by the execute method.
   * @return int
   * @param visited
   * @param shortestDistances
   */
  private int minVertex(int[] shortestDistances, boolean[] visited)
  {
    int x = Integer.MAX_VALUE;
    
    //initially graph not connected, or no unvisited vertices
    int y = -1;

    for (int i = 0; i < shortestDistances.length; i++)
    {
      if (!visited[i] && shortestDistances[i] < x)
      {
        y = i;
        x = shortestDistances[i];
      }
    }

    return y;
  }

  /**
   * Gets all shortest paths from the source via an ArrayList path String.
   * @return String
   * @param nextNode
   * @param startNode
   * @param preceedingNodes
   * @param graph
   */
  public String GetRoute(MapGraph graph, int[] preceedingNodes, int startNode, 
  int nextNode)
  {
    _path  = new ArrayList();
    
    while (nextNode != startNode)
    {
      _path.add(0, graph.getNodeName(nextNode));
      nextNode = preceedingNodes[nextNode];
    }

    _path.add(0, graph.getNodeName(startNode));

    return _path.toString();
  }

  /**
   * Get a boolean value indicating whether or not the graph is connected.
   * @return boolean
   */
  public boolean getGraphConnected()
  {
    return _graphConnected;
  }
}