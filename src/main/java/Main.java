import graphs.GraphFactory;
import people.Person;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var factory = new GraphFactory();
        var graph = factory.Manhattan()
                .WithPeople(10)
                .Infected(5)
                .Build();

        for(int time = 0; time < 100; time++) {
            Thread.sleep(500);
            graph.removeNeighborsEdges();
            for (Person person : graph.people) {
                person.move();
            }
            for (Person person : graph.people) {
                person.setNeighbors(graph.findAllNeighborsTo(person));
            }
        }
    }
}
