package com.company;
import java.awt.*;
import java.util.*;
/**
 * The Five piece.
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 */
public class Five extends Piece
{

    /**
     * Constructor for objects of class Five
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Five(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,5);
    }

    /**
     * return an ArrayList<Location> for listing the locations the Piece can move to
     * 
     * @return a List of all possilble Locations
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
