package graphs;

import helpers.Key;
import org.apache.commons.math3.util.Pair;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import people.AppSettings;
import people.Person;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class EnvBaseGraph extends SingleGraph {
    protected final int width;
    protected final int height;
    protected final double distance;
    protected NodeGrid nodeGrid;
    public List<Person> people;

    public EnvBaseGraph(int width, int height, double distance) {
        super("Manhattan", false, false);
        assert(width > 0 && height > 0);
        assert(distance > width && distance > height);

        this.width = width;
        this.height = height;
        this.distance = distance;

        this.addAttribute("ui.stylesheet", this.cssString);
        buildGraph();
    }

    protected abstract void buildGraph();

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public double getDistance() {
        return distance;
    }
    public Node getNode(int x,int y) {
        return this.nodeGrid.get(x,y);
    }

    private String cssString = """
            	node.crossroad {
            	    size: 5px;
            	}
            	node.person {
            	    size: 10px;
            	    stroke-mode:none;
            	}
            	node.Susceptible {
            	    fill-color: rgb(0,255,0);
                }
                node.Exposed {
            	    fill-color: rgb(255,150,100);
                }
                node.Infected {
            	    fill-color: rgb(255,0,0);
                }
                node.Recovered {
            	    fill-color: rgb(0,110,0);
                }
            """;

    public List<Person> findAllNeighborsTo(Person person) {
        var neighbors = new LinkedList<Person>();
        var settings = AppSettings.get_instance();
        for (Person p : this.people)
        {
            if(person != p && person.distanceTo(p) <= settings.Fixed.getClosePeopleDistance())
                neighbors.add(p);
        }
        return neighbors;
    }
    public void removeNeighborsEdges() {
        this.getEdgeSet().removeIf(edge -> edge.getId().startsWith("neighbor"));
    }
}
