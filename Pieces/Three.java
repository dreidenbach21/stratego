package com.company;
import com.company.Location;
import java.awt.*;
import java.util.*;
/**
 * The Bomb piece.
 * 
 * @author Danny Reidenbach 
 * @version May 11 2017
 */
public class Three extends Piece
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Three
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Three(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,3);
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
