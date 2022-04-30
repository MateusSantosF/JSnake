/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import interfaces.ISnake;

/**
 *
 * @author mateus
 */

public class Snake{
    
    
    private boolean isHead;
    private boolean isTail;
    private int size;

    public boolean isIsHead() {
        return isHead;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    
    public void setIsHead(boolean isHead) {
        this.isHead = isHead;
    }

    public boolean isIsTail() {
        return isTail;
    }

    public void setIsTail(boolean isTail) {
        this.isTail = isTail;
    }
    
    
    
    
    
    
}
