//Gagandeep Singh

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // for storing snake body details.
import java.util.Random; // for random food spawn points. 
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        int x;
        int y;

        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    int boardWidth, boardHeight;
    int tileSize = 25;

    // Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    // Fruit
    Tile fruit;

    // random
    Random random;

    // game logic
    Timer gameLoop;
    int velocityX, velocityY;
    boolean gameOver = false;

    SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        fruit = new Tile(10, 10);
        random = new Random();
        placeFruit();

        velocityX = 0;
        velocityY = 1;

        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // grid for visualizing
        // for (int i = 0; i < boardWidth / tileSize; i++) {
        //     // x1, y1, x2, y2
        //     g.drawLine(i * tileSize, 0, i * tileSize, boardHeight); // vertical lines
        //     g.drawLine(0, i * tileSize, boardWidth, i * tileSize); // horizontal lines
        // }

        // fruit
        g.setColor(Color.RED);
        // g.fillRect(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize);
        g.fill3DRect(fruit.x * tileSize, fruit.y * tileSize, tileSize, tileSize, true);

        // snake head
        g.setColor(Color.GREEN);
        // g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // snake body
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }

        // score board
        g.setFont(new Font("Gill Sans", Font.PLAIN, 16));
        if(gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over : " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        } else{
            g.drawString("Score : " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }
    }

    public void placeFruit() {
        fruit.x = random.nextInt(boardWidth / tileSize);
        fruit.y = random.nextInt(boardHeight / tileSize);
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void move() {
        // eating fruit
        if (collision(snakeHead, fruit)) {
            snakeBody.add(new Tile(fruit.x, fruit.y));
            placeFruit();
        }

        // snake body
        for (int i = snakeBody.size() - 1; i >= 0; i--) {
            Tile snakePart = snakeBody.get(i);
            if (i == 0) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            } else {
                Tile prevSnakePart = snakeBody.get(i - 1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }

        // snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        // game over cases
        // snake collision with own body.
        for (int i = 0; i < snakeBody.size(); i++) {
            Tile snakePart = snakeBody.get(i);
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }
        // snake collision with wall.
        if (snakeHead.x * tileSize < 0 || snakeHead.x * tileSize > boardWidth || snakeHead.y * tileSize < 0
                || snakeHead.y * tileSize > boardWidth) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'actionPerformed'");
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    private void restartGame() {
        // reset head and body
        snakeHead = new Tile(5, 5);
        snakeBody.clear();

        // place a new fruit
        placeFruit();

        // reset velocity and flags
        velocityX = 0;
        velocityY = 1;
        gameOver = false;

        // restart timer and ensure the panel has focus for key events
        gameLoop.restart();
        requestFocusInWindow();
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if(gameOver){
            restartGame();
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
            velocityX = 0;
            velocityY = -1;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
            velocityX = 1;
            velocityY = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
            velocityX = 0;
            velocityY = 1;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
            velocityX = -1;
            velocityY = 0;
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'keyReleased'");
    }

}
