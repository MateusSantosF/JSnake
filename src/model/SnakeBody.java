/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.UUID;
import interfaces.ISnake;

/**
 *
 * @author mateus
 */
public class SnakeBody extends Snake implements ISnake {
    
    private int x;
    private int y;
    private UUID id;
    
    public SnakeBody(int x, int y){
        super();
        this.x = x;
        this.y = y;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
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
