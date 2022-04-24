package graphs;

import org.apache.commons.math3.util.Pair;
import people.AppSettings;
import people.Person;
import people.PersonStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GraphFactory {
    private final AppSettings settings;
    private ManhattanGraph worldGraph;
    private Random rand;

    public GraphFactory() {
        this.settings = AppSettings.get_instance();
        this.rand = new Random();
    }


    public GraphFactory Manhattan(int width, int height, double distance)
    {
        this.worldGraph = new ManhattanGraph(width, height, distance);
        return this;
    }
    public GraphFactory Manhattan() {
        return this.Manhattan(settings.Fixed.getEnvSize(),settings.Fixed.getEnvSize(), settings.Fixed.getStreetDistance());
    }
    public GraphFactory WithPeople(int count) {
        if(this.worldGraph == null)
            Manhattan();

        var coordinates = new ArrayList<Pair<Integer, Integer>>(count);
        var maxCol = this.worldGraph.nodeGrid.maxColumns();
        var maxRow = this.worldGraph.nodeGrid.maxRows();

        for(int i = 0; i < count; i++) {
            var xIndex = this.rand.nextInt(maxCol);
            var yIndex = this.rand.nextInt(maxRow);
            coordinates.add(new Pair<>(xIndex, yIndex));
        }

        return WithPeople(coordinates);
    }
    public GraphFactory WithPeople() {
        return WithPeople(settings.Params.getPeopleCount());
    }
    public GraphFactory WithPeople(List<Pair<Integer, Integer>> coordinates) {
        if(this.worldGraph == null)
            Manhattan();
        worldGraph.people = new ArrayList<>();

        for(int i = 0; i < coordinates.size(); i++)
        {
            var xIndex = coordinates.get(i).getFirst();
            var yIndex = coordinates.get(i).getSecond();
            var startNode = this.worldGraph.getNode(xIndex,yIndex);

            var x = startNode.getAttribute("x");
            var y = startNode.getAttribute("y");

            var personNode = worldGraph.addNode("person" + i);
            personNode.addAttribute("x",x);
            personNode.addAttribute("y",y);
            personNode.setAttribute("ui.class", "person");
            var person = new Person(personNode, startNode);
            worldGraph.people.add(person);
        }
        return this;

    }
    public GraphFactory Infected(int count) {
        if(worldGraph.people == null)
            WithPeople();

        worldGraph.people.forEach(p -> p.changeStatus(PersonStatus.Susceptible));
        Collections.shuffle(worldGraph.people);
        var infected = worldGraph.people.subList(0, count);
        infected.forEach(p -> p.changeStatus(PersonStatus.Infected));
        return this;
    }

    public ManhattanGraph Build() {
        this.worldGraph.display(false);
        return this.worldGraph;
    }
}
