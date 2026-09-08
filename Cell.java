import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
public class Cell extends Rectangle {
    static int size= 35;
    int col;
    int row;

    public Cell(int col, int row, int x, int y) {
        super(x, y, size, size);
        this.col = col;
        this.row = row;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, size, size);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, size, size);
    }
}
