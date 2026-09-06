import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.List;

public abstract class Animal implements Move {
    Color color;
    List<Polygon> display;
    Cell cell;
    int movesWithoutFood;
    int movesSinceReproduction;

    public Animal(Cell cell) {
        this.cell = cell;
    }

    public void paint(Graphics g) {
        for (Polygon p : display) {
            g.setColor(color);
            g.fillPolygon(p);
            g.setColor(Color.GRAY);
            g.drawPolygon(p);
        }
    }

    public abstract Animal reproduce(Cell cell);

}
