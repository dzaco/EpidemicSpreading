package graphs;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManhattanGraphTest {

    @Test
    public void shouldBuildManhattanGraph() {
        var manhattan = new ManhattanGraph(10,10,1);
        manhattan.display();
    }

}