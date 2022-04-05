package people;

import org.graphstream.graph.Node;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Person {
    public PersonStatus status;
    public Node node;
    public int lastInfectionTime;
    public Rules rules;
    public List<Person> neighbors;
    private boolean test;
    private Set<Node> crossroadNodes;

    public Person(Node node, Node crossroadStartNode) {
        this.node = node;
        this.neighbors = new LinkedList<>();
        this.rules = new Rules();
        this.node.addAttribute("person", this);
        this.crossroadNodes = new HashSet<>();
        this.crossroadNodes.add(crossroadStartNode);
    }
    public Person(Node node, Node crossroadStartNode, boolean test) {
        this(node, crossroadStartNode);
        this.test = test;
    }

    public void changeStatus() {
        if(!canChangeStatus() && !test)
            return;

        var newStatus = PersonStatus.values()[(status.ordinal() + 1) % PersonStatus.values().length];
        this.changeStatus(newStatus);
    }
    public void changeStatus(PersonStatus newStatus) {
        this.node.removeAttribute("ui.class");
        this.status = newStatus;
        this.node.addAttribute("ui.class", "person, " + status.name());
    }

    public boolean canChangeStatus() {
        var result = false;
        switch (this.status) {
            case Susceptible -> result = rules.canChangeStatusToExposed(this);
            case Exposed -> result = rules.canChangeStatusToInfected(this);
            case Infected -> result = rules.canChangeStatusToRecovery(this);
            case Recovered -> result = rules.canChangeStatusToSusceptible(this);
            default -> throw new IllegalStateException("Unexpected value: " + this.status);
        }
        return result;
    }

    public void move(Direction direction, int distance) {
        Integer x = this.node.getAttribute("x");
        Integer y = this.node.getAttribute("y");

        switch (direction) {
            case Up -> {
                var newY = y - distance;
                if(newY > 0)
                    y = newY;
            }
            case Dawn -> {
                var newY = y + distance;
                if(newY < this.rules.settings.Fixed.getEnvSize())
                    y = newY;
            }
            case Right -> {
                var newX = x + distance;
                if(newX < this.rules.settings.Fixed.getEnvSize())
                    x = newX;
            }
            case Left -> {
                var newX = x - distance;
                if(newX > 0)
                    x = newX;
            }
        }

        this.node.addAttribute("x",x);
        this.node.addAttribute("y",y);
    }

    public void move() {
        var direction = Direction.values()[rules.settings.random.nextInt(Direction.values().length)];
        var distance = rules.settings.random.nextInt(rules.settings.Fixed.getStreetDistance());
        this.move(direction, distance);
    }

    public double distanceTo(Person person)
    {
        var thisX = (int)this.node.getAttribute("x");
        var thisY = (int)this.node.getAttribute("y");

        var personX = (int)person.node.getAttribute("x");
        var personY = (int)person.node.getAttribute("y");

        double distY = Math.abs(personY - thisY);
        double distX = Math.abs(personX - thisX);

        return Math.hypot(distY, distX);
    }

    public void setNeighbors(List<Person> neighbors) {
        assert (neighbors != null);
        this.neighbors = neighbors;
        for (Person p : this.neighbors) {
            this.node.getGraph().addEdge("neighbor " + this.node.getId() + "->" + p.node.getId(), this.node, p.node);
        }
    }
}
