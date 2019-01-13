package applicationpackage;

/**
 * A class that represents a map graph consisting of nodes and edges.
 */
public class MapGraph
{
  private int[][]   _edges;
  private String[]  _nodes;
  private int[]     _answer;

  /**
   * Constructor.
   * @param totalNodes
   */
  public MapGraph(int totalNodes)
  {
    _edges = new int[totalNodes][totalNodes];
    _nodes = new String[totalNodes];
  }

  /**
   * Get's the size of the graph (number of nodes).
   * @return int
   */
  public int getSize()
  {
    return _nodes.length;
  }

  /**
   * Adds a new node to the string array at the specified index.
   * @param name
   * @param index
   */
  public void addNode(int index, String name)
  {
    _nodes[index] = name;
  }

  /**
   * Get's the node name at the specified index.
   * @return String
   * @param index
   */
  public String getNodeName(int index)
  {
    return _nodes[index];
  }

  /**
   * Adds a new edge to the multi-dimensional int array with start and end
   * nodes and a weight.
   * @param weight
   * @param endNode
   * @param startNode
   */
  public void addEdge(int startNode, int endNode, int weight)
  {
    _edges[startNode][endNode] = weight;
  }

  /**
   * Checks whether or not there is an edge between the specified start and
   * end nodes.
   * @return boolean
   * @param endNode
   * @param startNode
   */
  public boolean isEdge(int startNode, int endNode)
  {
    return _edges[startNode][endNode] > 0;
  }
  
  /**
   * Removes the edge between the specified start and end nodes by setting the
   * weight to 0.
   * @param endNode
   * @param startNode
   */
  public void removeEdge(int startNode, int endNode)
  {
    if (this.isEdge(startNode, endNode))
    {
      _edges[startNode][endNode] = 0;
    }   
  }

  /**
   * Returns the weight of a edge between the specified start and end nodes.
   * @return int
   * @param endNode
   * @param startNode
   */
  public int getWeight(int startNode, int endNode)
  {
    return _edges[startNode][endNode];
  }

  /**
   * Gets an int array of the neighbouring nodes to the node specified.
   * @return int[]
   * @param node
   */
  public int[] neighbors(int node)
  {
    int count = 0;

    for (int i = 0; i < _edges[node].length; i++)
    {
      if (_edges[node][i] > 0)
      {
        count++;
      }
    }

    //set the int[] size with the number of neighbouring nodes
    _answer = new int[count];
    count = 0;

    for (int i = 0; i < _edges[node].length; i++)
    {
      if (_edges[node][i] > 0)
      {
        _answer[count++] = i;
      }
    }

    return _answer;
  }
}