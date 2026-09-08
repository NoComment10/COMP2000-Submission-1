import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.util.Scanner;
import javax.swing.Timer;


public class Main extends JFrame {
    Timer timer;
    Simulation stage = new Simulation();

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfTurns;

        System.out.println("Please type how many turns the simulation should last (positive integer answer only)");
        while(true) {
            String input = scanner.nextLine();

            try {
                numberOfTurns = Integer.parseInt(input);
                
                if(numberOfTurns > 0) {
                    break;
                }
            }
            catch(NumberFormatException e) {
                //not valid
            }
            System.out.println("Invalid input. Please type a positive integer to select how many turns the simulation should last");
        }
        Main window = new Main();
        window.run(numberOfTurns);
        scanner.close();
    }


    class Canvas extends JPanel {

        //sets the size of the canvas to 700x700 pixels
        public Canvas() {
            setPreferredSize(new Dimension(700, 700));
        }

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

    public void run(int numberOfTurns) {
        int[] turns ={0};

        timer = new Timer (750, e -> {
            stage.runTurn();
            repaint();
            turns[0]++;

            if(turns[0] >= numberOfTurns) {
                timer.stop();
            }
        });
        timer.start();
    }
}