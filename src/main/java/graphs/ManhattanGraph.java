package graphs;
import helpers.Key;
import org.apache.commons.math3.util.Pair;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import people.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManhattanGraph extends EnvBaseGraph {

    public ManhattanGraph(int width, int height, double distance) {
        super(width, height, distance);
    }

    @Override
    protected void buildGraph() {
        this.nodeGrid = new NodeGrid();
        for(int xCoordinate = 0, x = 0; xCoordinate <= width; xCoordinate += distance, x++)
        {
            for (int yCooridinate = 0, y = 0; yCooridinate <= height; yCooridinate += distance, y++)
            {
                var node = this.addNode(xCoordinate + "," + yCooridinate);
                node.setAttribute("ui.class", "crossroad");
                node.addAttribute("x", xCoordinate);
                node.addAttribute("y", yCooridinate);

                this.nodeGrid.put(x,y, node);

                try {
                    if(xCoordinate != 0)
                        this.addEdge(node.getId() + "->" +nodeGrid.get(x-1,y).getId() , node, nodeGrid.get(x-1,y), false);
                    if(yCooridinate != 0)
                        this.addEdge(node.getId() + "->" +nodeGrid.get(x,y-1).getId() , node, nodeGrid.get(x,y-1), false);
                }
                catch (Exception e) {
                    System.out.println("x = " + xCoordinate + ", y = " + yCooridinate);
                    throw e;
                }
            }
        }
    }
}
