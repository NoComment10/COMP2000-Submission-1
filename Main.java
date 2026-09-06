import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Dimension;

public class Main extends JFrame {
    public static void main (String[] args) {
        Main window = new Main();
        window.run();
    }


    class Canvas extends JPanel {

        Simulation stage = new Simulation();
        //sets the size of the canvas to 700x700 pixels
        public Canvas() {
            setPreferredSize(new Dimension(700, 700));
        }

        //draws a black rectangle on the canvas
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            stage.paint(g);
        }
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }

    public void run() {
        while (true) {
            this.repaint();
        }
    }
}