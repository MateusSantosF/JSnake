package jsnake;

import enums.Move;
import java.awt.BorderLayout;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import model.Board;

/**
 *
 * @author mateus
 */
public class JSnake {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Board board = new Board(5);
        Scanner sc = new Scanner(System.in);
        boolean lose = false;

        
        
        while (!lose) {

            String direction = sc.next();

            if (direction.toLowerCase().equals("exit")) {
                break;
            }
            lose = board.move(direction);

        }
        if (lose) {
            System.out.println("\n\n\n|==============|\n\tYOUR LOSE\n|==============|");
        }       
        
    }

}
