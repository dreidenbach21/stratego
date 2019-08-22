package com.company;
import com.company.Location;
import java.awt.*;
import java.util.*;
/**
 * The Spy piece.
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 */
public class Spy extends Piece
{
    

    /**
     * Constructor for objects of class Spy
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Spy(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName, 1);
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
