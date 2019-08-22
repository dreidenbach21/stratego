package com.company;
import java.awt.*;
import java.util.*;
/**
 * The Bomb piece
 * 
 * @author Danny Reidenbahc 
 * @version May 11 2017
 */
public class Bomb extends Piece
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Bomb
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Bomb(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,100);
    }

    /**
     * return an ArrayList<Location> for listing the locations the Piece can move to
     * 
     * @return an empty array list since it cant move
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> moveLocs = new ArrayList<Location>();
       

        return moveLocs;
    }
}
