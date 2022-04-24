package people;

import graphs.GraphFactory;
import org.apache.commons.math3.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InfectionTest {

    @Test
    public static void main(String[] args) {
        var settings = AppSettings.get_instance();
        var factory = new GraphFactory();
        var graph = factory.Manhattan()
                .WithPeople(List.of(new Pair<>(1, 1), new Pair<>(1, 1)))
                .Infected(1)
                .Build();
        var infectedPerson = graph.people.stream()
                .filter(p -> p.status == PersonStatus.Infected)
                .findFirst()
                .get();
        var healthyPerson = graph.people.stream()
                .filter(p -> p.status != PersonStatus.Infected)
                .findFirst()
                .get();

        for(settings.CurrentTime = 0; settings.CurrentTime < 100; settings.CurrentTime++) {
            graph.removeNeighborsEdges();
            var direction = Direction.values()[settings.random.nextInt(Direction.values().length)];
            var distance = settings.random.nextInt(settings.Fixed.getStreetDistance());

            for (Person person : graph.people) {
                person.move(direction, distance);
                person.setNeighbors(graph.findAllNeighborsTo(person));
                person.changeStatusIfCan();
            }
            if(healthyPerson.status == PersonStatus.Infected)
                break;
        }
        Assert.assertSame(healthyPerson.status, PersonStatus.Infected);
        Assert.assertTrue(settings.CurrentTime < 100);

    }
}