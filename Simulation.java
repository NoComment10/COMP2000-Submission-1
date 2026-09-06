import java.awt.Graphics;

public class Simulation {
    Grid grid;
    Cell cell;

    // add animals and plants to the stage
    // controls reproduction and movement of animals and plants
    // controls the death of animals


    public Simulation(){
        grid = new Grid();

    }


    public void paint(Graphics g) {
        grid.paint(g);
    }

}