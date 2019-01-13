package appletpackage;

public class MapGraph
{
    /** Generic attributes */
    int[][] edges;
    String[] nodes;
    int[] answer;
    
    public MapGraph(int n)
    {
        edges  = new int[n][n];
        nodes = new String[n];
    }

    public int getSize()
    {
        return nodes.length;
    }

    public void addNode(int vertex, String name)
    {
        nodes[vertex] = name;
    }
  
    public String getNodeName(int vertex)
    {
        return nodes[vertex];
    }

    public void addEdge(int start, int end, int w)
    {
        edges[start][end] = w;
    }
  
    public boolean isEdge(int start, int end)
    {
        return edges[start][end] > 0;
    } 
  
    public void removeEdge(int start, int end)
    {
        edges[start][end] = 0;
    }
  
    public int getWeight(int start, int end)
    {
        return edges[start][end];
    }

    public int[] neighbors(int vertex)
    {
        int count = 0;

        for (int i = 0; i < edges[vertex].length; i++)
        {
            if (edges[vertex][i] > 0) count++;
        }

        answer = new int[count];
        count = 0;

        for (int i = 0; i < edges[vertex].length; i++)
        {
            if (edges[vertex][i] > 0) answer[count++] = i;
        }

        return answer;
    }
}