import graphs.GraphFactory;
import people.AppSettings;
import people.Direction;
import people.Person;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var settings = AppSettings.get_instance();
        var factory = new GraphFactory();
        var graph = factory.Manhattan()
                .WithPeople()
                .Infected(20)
                .Build();

        for(settings.CurrentTime = 0; settings.CurrentTime < 100; settings.CurrentTime++) {
            Thread.sleep(500);
            graph.removeNeighborsEdges();
            for (Person person : graph.people) {
                person.move();
                person.setNeighbors(graph.findAllNeighborsTo(person));
                person.changeStatusIfCan();
            }
        }
    }
}
