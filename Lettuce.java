import java.awt.Color;
import java.awt.Graphics;

public class Lettuce {
    Cell cell;

    public Lettuce(Cell cell) {
        this.cell = cell;
    }

    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(cell.x, cell.y, Cell.size, Cell.size);
    }

}