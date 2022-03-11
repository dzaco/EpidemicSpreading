package graphs;

import org.graphstream.graph.Node;

import java.util.HashMap;
import java.util.Map;

public class NodeGrid {
    private Map<Integer, Map<Integer, Node>> grid;
    public NodeGrid() {
        this.grid = new HashMap<Integer, Map<Integer, Node>>();
    }

    public Node get(int x, int y) {
        var ymap = this.grid.get(x);
        if(ymap == null)
            return null;
        else
            return ymap.get(y);

    }

    public void put(int x, int y, Node node) {
        var ymap = grid.get(x);

        if(ymap == null) {
            ymap = new HashMap<Integer,Node>();
            grid.put(x, ymap);
        }

        ymap.put(y, node);
    }

    public int maxColumns() {
        return this.grid.size();
    }

    public int maxRows() {
        var row = this.grid.get(1);
        if(row == null)
            return 0;
        else
            return row.size();
    }
}
