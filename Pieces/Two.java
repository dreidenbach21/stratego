package com.company;
import com.company.Location;

import java.awt.*;
import java.util.*;
/**
 * The Two piece
 * 
 * @author Danny Reidenbach 
 * @version May 11 2017
 */
public class Two extends Piece
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Two
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Two(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,2);
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
        this.sweep(moveLocs);
//        this.sweep(moveLocs, Location.SOUTH);
//        this.sweep(moveLocs, Location.EAST);
//        this.sweep(moveLocs, Location.WEST);

        return moveLocs;
    }
}
