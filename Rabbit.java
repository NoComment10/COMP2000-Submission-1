import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Rabbit extends Animal implements Move, Eat {

    public Rabbit(Cell cell) {
        super(cell);

        color = Color.PINK;
        display = new ArrayList<Polygon>();
        Polygon ear1 = new Polygon();
        ear1.addPoint(cell.x + 5, cell.y + 5);
        ear1.addPoint(cell.x + 5, cell.y + 15);
        ear1.addPoint(cell.x + 15, cell.y + 15);
        ear1.addPoint(cell.x + 15, cell.y + 5);
        Polygon ear2 = new Polygon();
        ear2.addPoint(cell.x + 20, cell.y + 5);
        ear2.addPoint(cell.x + 20, cell.y + 15);
        ear2.addPoint(cell.x + 30, cell.y + 15);
        ear2.addPoint(cell.x + 30, cell.y + 5);
        Polygon face = new Polygon();
        face.addPoint(cell.x + 8, cell.y + 7);
        face.addPoint(cell.x + 27, cell.y + 7);
        face.addPoint(cell.x + 27, cell.y + 25);
        face.addPoint(cell.x + 8, cell.y + 25);
        display.add(face);
        display.add(ear1);
        display.add(ear2);
    }

// move interface
    // rabbit will randomly move to a cell that is not occupied by another rabbit
    @Override
    public void move(Cell target) {
        
        int dirX = target.x - cell.x;
        int dirY = target.y - cell.y;
        for(Polygon p : display) {
        for(int i = 0; i < p.npoints; i++) {
            p.xpoints[i] += dirX;
            p.ypoints[i] += dirY;
        }
        p.invalidate();
        }
        cell = target;
    }

// eat interface
    // rabbit can eat lettuce 
    // Simulation will call this method to check if the rabbit can eat the object
    @Override
    public boolean canEat(Object food) {
        return false;
    }

    // reproduce
    @Override
    public Animal reproduce(Cell cell) {
        return new Fox(cell);
    }

}
