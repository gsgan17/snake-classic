// Gagandeep Singh

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth= 500;
        int boardHeight= 500;

        JFrame frame = new JFrame("Snake Classic");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // center
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
