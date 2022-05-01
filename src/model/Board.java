package model;

import enums.Move;
import interfaces.ISnake;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author mateus
 */
public class Board {

    List<SnakeBody> snakeBody = new ArrayList<>();
    private int BOARD_SIZE;
    private ISnake[][] BOARD;
    private Food currentFood;
    private Move lastMove;

    public Board(int size) {

        this.BOARD_SIZE = size;
        BOARD = createBoard();
        createSnakeRandom();
        createFoodRandom();
        showConsole();

    }

    private static class Walkable implements ISnake {

        int x;
        int y;

        public Walkable(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        @Override
        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

    }

    private static class Food implements ISnake {

        int x;
        int y;

        public Food(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int getX() {
            return x;
        }

        @Override
        public int getY() {
            return y;
        }

    }

    private ISnake[][] createBoard() {

        ISnake[][] board = new ISnake[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new Walkable(i, j);
            }
        }

        return board;
    }

    private void createSnakeRandom() {

        int x = new Random().nextInt(BOARD_SIZE);
        int y = new Random().nextInt(BOARD_SIZE);

        SnakeBody head = new SnakeBody(x, y);
        head.setIsHead(true);
        snakeBody.add(head);
    }

    private void createFoodRandom() {
        int x = new Random().nextInt(BOARD_SIZE);
        int y = new Random().nextInt(BOARD_SIZE);

        while (!isValidPosition(x, y)) {
            x = new Random().nextInt(BOARD_SIZE);
            y = new Random().nextInt(BOARD_SIZE);
        }

        currentFood = new Food(x, y);
    }

    private int validY(int y) {

        if (y >= BOARD_SIZE) {
            return 0;
        } else if (y < 0) {
            return BOARD_SIZE - 1;
        }
        return y;
    }

    private int validX(int x) {
        if (x >= BOARD_SIZE) {
            return 0;
        } else if (x < 0) {
            return BOARD_SIZE - 1;
        }

        return x;
    }

    private boolean isValidPosition(int x, int y) {
        return !(BOARD[x][y] instanceof SnakeBody);
    }

    private void showConsole() {

        BOARD = createBoard();
        snakeBody.forEach((t) -> {
            BOARD[t.getX()][t.getY()] = t;
        });
        BOARD[currentFood.getX()][currentFood.getY()] = currentFood;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (BOARD[i][j] instanceof SnakeBody) {
                    SnakeBody body = (SnakeBody) BOARD[i][j];
                    if (body.isIsHead()) {

                        System.out.print("[@]");
                    } else {
                        System.out.print("[#]");
                    }
                } else if (BOARD[i][j] instanceof Food) {
                    System.out.print("[*]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println("");
        }
    }

    public boolean move(String move) {
        
        Move direction = Move.INVALID;
        
        if(move.toLowerCase().equals("bottom")){
               direction = Move.BOTTOM;
            
        }else if(move.toLowerCase().equals("top")){
               direction = Move.TOP;
            
        }else if(move.toLowerCase().equals("left")){
            direction = Move.LEFT;
            
        }else if(move.toLowerCase().equals("right")){
            direction = Move.RIGHT;
        }
        
        if (direction == Move.INVALID) {
            return false;
        }

        lastMove = direction;
        if (isBody(getHead())) {
            return true;
        }
        switch (direction) {
            case LEFT:
                moveHorizontal(-1);
                break;
            case RIGHT:
                moveHorizontal(1);
                break;
            case TOP:
                moveVertical(-1);
                break;
            case BOTTOM:
                moveVertical(1);
                break;
            default:
                break;
        }
        return false;
    }

    private void moveHorizontal(int y) {

        SnakeBody head = getHead();
        int previousX = head.getX();
        int previousY = head.getY();

        if (isFood(head.getX(), validY(head.getY() + y))) {
            incrementBody();
        } else {
            head.setY(validY(head.getY() + y));
            swap(head);
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            SnakeBody current = snakeBody.get(i);
            int auxX = current.getX();
            int auxY = current.getY();
            current.setX(previousX);
            current.setY(previousY);
            swap(current);
            previousX = auxX;
            previousY = auxY;
        }

        showConsole();
    }

    private void moveVertical(int x) {

        SnakeBody head = getHead();
        int previousX = head.getX();
        int previousY = head.getY();

        if (isFood(validX(head.getX() + x), head.getY())) {
            incrementBody();
        } else {
            head.setX(validY(head.getX() + x));
            swap(head);
        }
        for (int i = 1; i < snakeBody.size(); i++) {
            SnakeBody current = snakeBody.get(i);
            int auxX = current.getX();
            int auxY = current.getY();
            current.setX(previousX);
            current.setY(previousY);
            swap(current);
            previousX = auxX;
            previousY = auxY;
        }
        showConsole();
    }

    private boolean isFood(int x, int y) {
        boolean food = (BOARD[x][y] instanceof Food);

        if (food) {
            BOARD[x][y] = new Walkable(x, y);
            createFoodRandom();
        }
        return food;
    }

    private boolean isBody(SnakeBody head) {

        if (lastMove == Move.LEFT || lastMove == Move.RIGHT) {
            return BOARD[head.getX()][validY(head.getY() + moveDirection())] instanceof SnakeBody;
        } else {
            return BOARD[validX(head.getX() + moveDirection())][head.getY()] instanceof SnakeBody;
        }
    }

    private SnakeBody getHead() {
        SnakeBody head = null;

        for (SnakeBody b : snakeBody) {
            if (b.isIsHead()) {
                head = b;
            }
        }

        return head;
    }

    private void swap(SnakeBody body) {

        for (int i = 0; i < snakeBody.size(); i++) {
            if (snakeBody.get(i).getId() == body.getId()) {
                snakeBody.set(i, body);
                break;
            }
        }
    }

    private void incrementBody() {
        SnakeBody currentHead = getHead();
        SnakeBody newBody = new SnakeBody(currentHead.getX(), currentHead.getY());
        newBody.setIsHead(false);
        if (lastMove == Move.LEFT || lastMove == Move.RIGHT) {
            currentHead.setY(validY(currentHead.getY() + moveDirection()));
        } else {
            currentHead.setX(validX(currentHead.getX() + moveDirection()));
        }

        swap(currentHead);
        snakeBody.add(newBody);
    }

    private int moveDirection() {

        switch (lastMove) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            case TOP:
                return -1;
            case BOTTOM:
                return 1;
        }
        return 0;
    }
}
