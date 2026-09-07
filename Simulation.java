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
        animals.add(new Rabbit(grid.cells[2][2]));
        animals.add(new Fox(grid.cells[12][12]));

        animals.add(new Rabbit(grid.cells[10][10]));
        lettuce.add(new Lettuce(grid.cells[4][4]));

        moveAnimal(animals.get(0));
        moveAnimal(animals.get(0));


        moveAnimal(animals.get(1));
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

        Cell foodCell = detectFood(animal);
        // if statement for all the targetted movement toward food

        if(foodCell != null) {
            
            Cell targetCell = bestMove(possibleMoves, foodCell);

            animal.move(targetCell);

            if(targetCell == foodCell) {
                Object food = getFoodAt(foodCell, animal);
                eat(animal, food);
            }

            System.out.println("target cell is " + targetCell.col + ", " + targetCell.row);
            System.out.println("move to " + foodCell.col + ", " + foodCell.row);

        } else {
            //the random movement will be part of the else statement if there is no food in the neighboring cells
        // random movement to a neighboring cell that is not occupied by another animal
        int randomCellIndex = (int) (Math.random() * possibleMoves.size());
        Cell targetCell = possibleMoves.get(randomCellIndex);

        animal.move(targetCell);

        System.out.println("random cell is " + targetCell.col + ", " + targetCell.row);
        }
        
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

    // get the best move for the animal based on the food cell
    private Cell bestMove(List<Cell> possibleMoves, Cell foodCell) {
        // check if there is food in the neighboring cells
        Cell bestCell = possibleMoves.get(0);
        int bestDistance = distance(bestCell, foodCell);

        for (Cell cell : possibleMoves) {
            int currentDistance = distance(cell, foodCell);

            if (currentDistance < bestDistance) {
                bestCell = cell;
                bestDistance = currentDistance;
            }
        }

        return bestCell;
    }

    private int distance(Cell first, Cell second) {
        int dx = Math.abs(first.col - second.col);
        int dy = Math.abs(first.row - second.row);
        
        return Math.max(dx, dy);
    }

// eat
    // detect food in neighboring cells

    private Object getFoodAt(Cell cell, Animal animal) {
        // check if there is food in the cell
        for(int i = 0; i < lettuce.size(); i++) {
            Lettuce l = lettuce.get(i);
            if (l.cell == cell && animal.canEat(l)) {
                return l;
            }
        }

        for(int i = 0; i < animals.size(); i++) {
            Animal a = animals.get(i);
            if (a.cell == cell && animal.canEat(a)) {
                return a;
            }
        }
        return null;
    }

    public Cell detectFood(Animal animal) {

        // check if there is food in neighboring cells
        // if there is food, move towards that cell

        Cell current = animal.cell;

        for(int col = current.col - 2; col <= current.col + 2; col++) {
            for(int row = current.row - 2; row <= current.row + 2; row++) {

                // Skip the current cell
                if(col == current.col && row == current.row) {
                    continue;
                }

                // make sure it is within the grid bounds
                if(col >= 0 && col < grid.cells.length && 
                    row >= 0 && row < grid.cells[col].length) {
                    
                    Object food = getFoodAt(grid.cells[col][row], animal);

                    if(food != null) {
                        return grid.cells[col][row];
                    }
                }
            }
        }
        return null;
    }


    public void eat(Animal animal, Object food) {
        // remove food from stage
        if(food == null) {
            return;
        }

        if(animal.canEat(food)) {
            if(food instanceof Lettuce) {
                lettuce.remove((Lettuce) food);
                System.out.println(animal + " ate lettuce at " + ((Lettuce) food).cell.col + ", " + ((Lettuce) food).cell.row);
            } else if(food instanceof Animal) {
                animals.remove((Animal) food);
                System.out.println(animal + " ate " + food + " at " + ((Animal) food).cell.col + ", " + ((Animal) food).cell.row);
            }
        }
    }



// reproduction logic
    public void reproduce(Animal animal) {
        // add new animal to stage 
        // when animal moves on reproduction turn, leaves behind new animal in old cell
    }


    public void death(Animal animal) {
        // remove animal from stage
    }
}