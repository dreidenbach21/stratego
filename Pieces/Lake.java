package com.company;
import java.awt.*;
import java.util.*;
/**
 * The Lake piece.
 * 
 * @author Danny Reidenbach 
 * @version May 11 2017
 */
public class Lake extends Piece {
    /**
     * Constructor for objects of class Lake
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Lake(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,100000);
    }

    /**
     * return an ArrayList<Location> for listing the locations the Piece can move to
     * 
     * @return an empty list since it cannot move
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> moveLocs = new ArrayList<Location>();
     
        return moveLocs;
    }
}
