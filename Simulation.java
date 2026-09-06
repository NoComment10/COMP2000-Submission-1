import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    Grid grid;
    EntityList<Animal> animals;
    EntityList<Lettuce> lettuce;
    Cell cell;

    // add animals and plants to the stage
    // controls reproduction and movement of animals and plants
    // controls the death of animals


    public Simulation(){
        grid = new Grid();
        lettuce = new EntityList<Lettuce>();
        animals = new EntityList<Animal>();

        // intial addition of animals and plants to the stage
            // for example -> nothing concrete yet
        animals.add(new Rabbit(grid.cells[0][0]));
        animals.add(new Fox(grid.cells[12][13]));
        lettuce.add(new Lettuce(grid.cells[5][5]));

    }


    public void paint(Graphics g) {
        grid.paint(g);
        for (Animal animal : animals) {
            animal.paint(g);
        }
    }


//move animals 



    // check if the target cell is a neighbor of the animal's current cell and valid
    private boolean isNeighbor(Cell target) {
        int dx = target.col - cell.col;
        int dy = target.row - cell.row;
        return (dx <= 1 && dy <= 1) && (dx + dy != 0);
    }

    //move logic
    
    //eventually
    //Animal.move(Cell target)

    public void reproduce(Animal animal) {
        // add new animal to stage 
        // when animal moves on reproduction turn, leaves behind new animal in old cell
    }

    public void eat(Animal animal, Object food) {
        // remove food from stage
        // when animal moves on eating turn, leaves behind new animal in old cell
    }

    public void death(Animal animal) {
        // remove animal from stage
    }
}