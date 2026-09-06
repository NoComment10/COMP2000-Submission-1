import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Fox extends Animal implements Move {

    public Fox(Cell cell) {
        super(cell);

        color = Color.ORANGE;
        display = new ArrayList<Polygon>();
        Polygon ear1 = new Polygon();
        ear1.addPoint(cell.x + 5, cell.y + 5);
        ear1.addPoint(cell.x + 15, cell.y + 5);
        ear1.addPoint(cell.x + 5, cell.y + 15);
        Polygon ear2 = new Polygon();
        ear2.addPoint(cell.x + 20, cell.y + 5);
        ear2.addPoint(cell.x + 30, cell.y + 5);
        ear2.addPoint(cell.x + 30, cell.y + 15);
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
    // if rabbit is in vicinity of fox, it will chase the rabbit??? probably not
    @Override
    public void move(Cell target) {
    
        int dirX = target.x - cell.x;
        int dirY = target.y - cell.y;

        // move the graphics
        for(Polygon p : display) {
        for(int i = 0; i < p.npoints; i++) {
            p.xpoints[i] += dirX;
            p.ypoints[i] += dirY;
        }
        p.invalidate();
        }

        // update the cell reference
        cell = target;
    }
    
}
