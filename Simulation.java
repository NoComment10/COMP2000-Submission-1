import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class Simulation {
    Grid grid;
    Cell cell;
    EntityList<Animal> animals;
    EntityList<Lettuce> lettuce;  
    
    // lettuce information
    int lettuceGrowthRate = 3; // number of turns it takes to grow
    int turnsSinceLastLettuceGrowth = 0; 

    public Simulation(){
        grid = new Grid();
        lettuce = new EntityList<Lettuce>();
        animals = new EntityList<Animal>();

        // intial addition of animals and plants to the stage
        // animals added in unorganised pattern so that there is no early turn predator or prey advantage
        animals.add(new Fox(grid.cells[12][12]));
        animals.add(new Rabbit(grid.cells[14][7]));
        animals.add(new Rabbit(grid.cells[2][2]));
        animals.add(new Fox(grid.cells[8][5]));
        animals.add(new Rabbit(grid.cells[9][6]));
        animals.add(new Rabbit(grid.cells[4][7]));
        animals.add(new Fox(grid.cells[10][2]));
        animals.add(new Fox(grid.cells[4][1]));
        animals.add(new Rabbit(grid.cells[6][14]));

        // lettuce added to board
        lettuce.add(new Lettuce(grid.cells[4][4]));
        lettuce.add(new Lettuce(grid.cells[2][14]));
        lettuce.add(new Lettuce(grid.cells[10][7]));
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

        //check for food in neighboring cells or partner in same cell
        Cell foodCell = findFood(animal);
        Cell partnerCell = findPartner(animal);

        // if statement for all targetted movement

        if(partnerCell != null) {   // checks for reproduction
            
            Animal partner = getAnimal(partnerCell, animal);

            if(animal.cell == partnerCell) {
                reproduce(animal, partner);
                System.out.println(animal + " has reproduced");

            } else {
                Cell targetCell = bestMove(possibleMoves, partnerCell);

                //move toward partner
                animal.move(targetCell);
                System.out.println(animal + " moved toward mate");

                // check if reached partner after movement
                if(targetCell == partnerCell) {
                    reproduce(animal, partner);  
                    System.out.println(animal + " has reproduced");

                } else {
                    //increment up if not reproduced
                    animal.movesSinceReproduction++;
                }
            }

        } else if(foodCell != null) {  // checks food
            
            Cell targetCell = bestMove(possibleMoves, foodCell);

            animal.move(targetCell);
            System.out.println(animal + " has moved toward food");

            if(targetCell == foodCell) {
                Object food = getFoodAt(foodCell, animal);
                eat(animal, food);

                System.out.println(animal + " ate food.");
            } else {

                //increment up if not eaten
                animal.movesWithoutFood++;
            }

            // increment up reproduction counter
            animal.movesSinceReproduction++;

            System.out.println(animal + "moved to " + foodCell.col + ", " + foodCell.row);


        } else {   // random movement to a neighboring cell 
            int randomCellIndex = (int) (Math.random() * possibleMoves.size());
            Cell targetCell = possibleMoves.get(randomCellIndex);
            
            animal.move(targetCell);

            // increment moves
            animal.movesSinceReproduction++;
            animal.movesWithoutFood++;

            System.out.println(animal + "moved to random cell. This is " + targetCell.col + ", " + targetCell.row);
        }

        // checks for starvation and removes animal from stage when dead
        checkForDeath(animal);
        
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
    // get food at specific cell from relevant EntityList for given animal
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
            if (a.cell == cell && 
                animal.canEat(a) &&
                a.isAlive) {

                return a;
            }
        }
        return null;
    }

    // check if there is food in neighboring cells
        // if there is food, move towards that cell
    public Cell findFood(Animal animal) {

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


    // remove food from stage
    public void eat(Animal animal, Object food) {
        
        if(food == null) {
            return;
        }

        if(animal.canEat(food)) {
            if(food instanceof Lettuce) {
                lettuce.remove((Lettuce) food);

                // reset moves without food since animal has eaten
                animal.movesWithoutFood = 0;
                            
            } else if(food instanceof Animal) {

                Animal prey = (Animal) food;
                prey.isAlive = false;
                // animals.remove((Animal) food);

                // reset moves without food since animal has eaten
                animal.movesWithoutFood = 0;
            }
        }
    }




//plant growth logic
    public void growLettuce() {

        boolean gridFull = true;

        // check if there is at least one empty cell in the grid
        for(int col = 0; col < grid.cells.length; col++) {
            for(int row = 0; row < grid.cells[col].length; row++) {
                Cell cell = grid.cells[col][row];

                // check if the cell is occupied
                boolean cellOccupied = isCellOccupied(cell);

                // if the cell is not occupied, add new lettuce to that cell
                if(!cellOccupied) {
                    gridFull = false;
                    break;
                }
            }
        }

        while(!gridFull) {

            // randomly select a cell in the grid
            int col = (int) (Math.random() * grid.cells.length);
            int row = (int) (Math.random() * grid.cells[col].length);
            Cell cell = grid.cells[col][row];

            // check if cell is occupied
            boolean cellOccupied = isCellOccupied(cell);

            // if the cell is not occupied, add new lettuce to that cell
            if(!cellOccupied) {
                lettuce.add(new Lettuce(cell));
                turnsSinceLastLettuceGrowth = 0;
                return;
            }
        }
    }

    private boolean isCellOccupied(Cell cell) {

        for(int i = 0; i < lettuce.size(); i++) {
            if(lettuce.get(i).cell == cell) {
                return true;
            }
        }

        for(int i = 0; i < animals.size(); i++) {
            if(animals.get(i).cell == cell) {
                return true;
            }
        }

        return false;
    }

    



// reproduction logic
    public void reproduce(Animal animal1, Animal animal2) {
        // add new animal to stage 
        Cell oldCell = animal1.cell;
        List<Cell> possibleMoves = getNeighbors(oldCell);
        int randomCellIndex;
        Cell targetCell;

        //move parents away

        randomCellIndex = (int) (Math.random() * possibleMoves.size());
        targetCell = possibleMoves.get(randomCellIndex);
        animal1.move(targetCell);

        randomCellIndex = (int) (Math.random() * possibleMoves.size());
        targetCell = possibleMoves.get(randomCellIndex);        
        animal2.move(targetCell);

        Animal newAnimal = animal1.reproduce(oldCell);
        animals.add(newAnimal);

        // reset moves since reproduction for both parents
        animal1.movesSinceReproduction = 0;
        animal2.movesSinceReproduction = 0;


    }


    // find compatible partner within 3 cells
    public Cell findPartner(Animal animal) {

        Cell current = animal.cell;

        for(int col = current.col - 3; col <= current.col + 3; col++) {
            for(int row = current.row - 3; row <= current.row + 3; row++) {


                //make sure within grid brounds
                if(col >= 0 && col < grid.cells.length && 
                    row >= 0 && row < grid.cells[col].length) {
                    
                    Animal mate = getAnimal(grid.cells[col][row], animal);

                    if(mate != null &&
                        animal != mate &&
                        animal.getClass() == mate.getClass() &&
                        animal.movesSinceReproduction >= animal.reproductionThreshold &&
                        mate.movesSinceReproduction >= mate.reproductionThreshold
                    ){
                        return grid.cells[col][row];
                    }
                }
            }
        }
        return null;

    }

    // get animal in cell
    public Animal getAnimal(Cell cell, Animal animal){
        for(int i = 0; i < animals.size(); i++) {
            Animal select = animals.get(i);

            if(select.getClass() == animal.getClass() &&
                select.cell == cell &&
                select.isAlive){

                return select;
            }
        }
        
        return null;
    }


        
// death logic
    public void checkForDeath(Animal animal) {
        // remove animal from stage
        if(animal.movesWithoutFood >= 10) {
            
            System.out.println(animal + " died of starvation at " + animal.cell.col + ", " + animal.cell.row);
            animal.isAlive = false;
            // animals.remove(animal);
        }
    }

    public void removeDeadAnimals() {
        for(int i = animals.size() - 1; i >= 0; i--) {
            Animal animal = animals.get(i);
            if(!animal.isAlive) {
                animals.remove(animal);
            }
        }
    }


// one full turn
    public void runTurn() {

        System.out.println();
        System.out.println("NEW TURN START");
        System.out.println();

        //plant growth logic 
        turnsSinceLastLettuceGrowth++;
        if(turnsSinceLastLettuceGrowth == lettuceGrowthRate) {
            growLettuce();
        }

        // animal turn logic
        int animalsAtStart = animals.size();

        for(int i = 0; i < animalsAtStart; i++){
            Animal animal = animals.get(i);

            //checks if animal is eaten or starved
            if(animal.isAlive){
                moveAnimal(animals.get(i));
            }
        }

        // remove all animals that died in current turn
        removeDeadAnimals();
  
    }
}