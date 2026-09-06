import java.awt.Graphics;


public class Grid {
  Cell[][] cells = new Cell[15][15];

  public Grid() {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j] = new Cell(
          i, j, 10+Cell.size*i, 10+Cell.size*j
        );
      }
    }
  }


  public void paint(Graphics g) {
    for(int i=0; i<cells.length; i++) {
      for(int j=0; j<cells[i].length; j++) {
        cells[i][j].paint(g);
      }
    }
  }
}
