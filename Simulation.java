import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    Grid grid;
    List<Animal> animals;
    Cell cell;

    // add animals and plants to the stage
    // controls reproduction and movement of animals and plants
    // controls the death of animals


    public Simulation(){
        grid = new Grid();

        animals = new ArrayList<Animal>();

        // intial addition of animals and plants to the stage
            // for example -> nothing concrete yet
        animals.add(new Rabbit(grid.cells[0][0]));
        animals.add(new Fox(grid.cells[12][13]));


    }


    public void paint(Graphics g) {
        grid.paint(g);
        for (Animal animal : animals) {
            animal.paint(g);
        }
    }

}