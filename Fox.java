import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Fox extends Animal {

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

    
    
}
