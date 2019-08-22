package com.company;
import java.awt.*;
import java.util.*;
/**
 * The Nine piece
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 */
public class Nine extends Piece
{
    

    /**
     * Constructor for objects of class Nine
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Nine(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName, 9);
    }

    /**
     * return an ArrayList<Location> for listing the locations the Piece can move to
     * 
     * @return a list of all locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> moveLocs = new ArrayList<Location>();
        Location location= this.getLocation();
        int row = location.getRow();
        int col= location.getCol();
        this.move(moveLocs);

        return moveLocs;
    }

}
