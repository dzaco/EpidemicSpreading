package people;

import org.graphstream.graph.Node;

import java.sql.PreparedStatement;

public class Person {
    public PersonStatus status;
    public Node node;
    public int lastInfectionTime;
    public Rules rules;
    private boolean test;

    public Person(Node node) {
        this.node = node;
        this.rules = new Rules();
        this.node.addAttribute("person", this);
    }
    public Person(Node node, boolean test) {
        this.status = PersonStatus.Susceptible;
        this.node = node;
        this.lastInfectionTime = 0;
        this.rules = new Rules(test);
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
}
