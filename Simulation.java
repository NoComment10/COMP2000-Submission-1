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

        moveAnimal(animals.get(1));
    }


    public void paint(Graphics g) {
        grid.paint(g);
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).paint(g);
        }
        for (int i = 0; i < lettuce.size(); i++) {
            lettuce.get(i).paint(g);
        }
    }


// move animals logic

    public void moveAnimal(Animal animal) {
        List<Cell> possibleMoves = getNeighbors(animal.cell);


        // if statement for all the targetted movement toward food




        //the random movement will be part of the else statement if there is no food in the neighboring cells
        // random movement to a neighboring cell that is not occupied by another animal
        int randomCellIndex = (int) (Math.random() * possibleMoves.size());
        Cell targetCell = possibleMoves.get(randomCellIndex);
        animal.move(targetCell);
        System.out.println("random cell is " + targetCell.col + ", " + targetCell.row);
    }


    // get neighbors of the animal's current cell
    private List<Cell> getNeighbors(Cell current) {

        List<Cell> neighbors = new ArrayList<>();
        
        for(int col = current.col - 1; col <= current.col + 1; col++) {
            for(int row = current.row - 1; row <= current.row + 1; row++) {

                // Skip the current cell
                if(col == current.col && row == current.row) {
                    continue;
                }

                // make sure the neighbor is within the grid bounds
                if(col >= 0 && col < grid.cells.length && 
                    row >= 0 && row < grid.cells[col].length) {

                    neighbors.add(grid.cells[col][row]);
                }
            }
        }
        return neighbors;
    }


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