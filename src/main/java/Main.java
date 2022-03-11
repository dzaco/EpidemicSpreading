import graphs.GraphFactory;
import graphs.ManhattanGraph;
import people.AppSettings;
import people.Direction;
import people.Person;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var factory = new GraphFactory();
        var graph = factory.Manhattan()
                .WithPeople()
                .Infected(10)
                .Get();
        graph.display(false);

        for(int time = 0; time < 100; time++) {
            Thread.sleep(200);
            graph.people.forEach(person -> {
                person.move();
            });
        }
    }
}
