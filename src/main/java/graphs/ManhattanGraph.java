package graphs;
import org.graphstream.graph.Node;

public class ManhattanGraph extends EnvBaseGraph {

    public ManhattanGraph(int width, int height, double distance) {
        super(width, height, distance);
    }

    @Override
    protected void buildGraph() {
        this.nodeGrid = new NodeGrid();
        for(int xCoordinate = 0, x = 0; xCoordinate <= width; xCoordinate += distance, x++)
        {
            for (int yCoordinate = 0, y = 0; yCoordinate <= height; yCoordinate += distance, y++)
            {
                addCrossroadNodeAndFindNeighbors(xCoordinate, yCoordinate, x, y);
            }
        }
    }

    private void addCrossroadNodeAndFindNeighbors(int xCoordinate, int yCoordinate, int x, int y) {
        Node node = createNode(xCoordinate, yCoordinate, x, y);
        createEdges(node, xCoordinate, yCoordinate, x, y);
        setNeighbors(node,x,y);
    }

    private void setNeighbors(Node node, int x, int y) {
        setNeighbor("above", "below", node, x,y-1);
        setNeighbor("below", "above", node, x,y+1);
        setNeighbor("left", "right", node, x-1,y);
        setNeighbor("right", "left", node, x+1,y);
    }
    private void setNeighbor(String nameForNeighbor, String nameForThisName, Node node, int x, int y) {
        var neighbor = nodeGrid.get(x,y);
        if(neighbor != null) {
            node.addAttribute(nameForNeighbor, neighbor);
            neighbor.addAttribute(nameForThisName, node);
        }
    }
    private void createEdges(Node node, int xCoordinate, int yCoordinate, int x, int y) {
        try {
            if(xCoordinate != 0)
                this.addEdge(node.getId() + "->" +nodeGrid.get(x -1, y).getId() , node, nodeGrid.get(x -1, y), false);
            if(yCoordinate != 0)
                this.addEdge(node.getId() + "->" +nodeGrid.get(x, y -1).getId() , node, nodeGrid.get(x, y -1), false);
        }
        catch (Exception e) {
            System.out.println("x = " + xCoordinate + ", y = " + yCoordinate);
            throw e;
        }
    }
    private Node createNode(int xCoordinate, int yCoordinate, int x, int y) {
        var node = this.addNode(xCoordinate + "," + yCoordinate);
        node.setAttribute("ui.class", "crossroad");
        node.addAttribute("x", xCoordinate);
        node.addAttribute("y", yCoordinate);

        this.nodeGrid.put(x, y, node);
        return node;
    }

}
