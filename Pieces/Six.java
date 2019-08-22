package com.company;
import com.company.Location;
import java.awt.*;
import java.util.*;
/**
 * The Six piece.
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 */
public class Six extends Piece
{
    

    /**
     * Constructor for objects of class Six
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Six(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName, 6);
    }

    /**
     * return an ArrayList<Location> for listing the locations the Piece can move to
     * 
     * @return a List of all possilble Locations
     */
    public ArrayList<Location> destinations()
    {
        ArrayList<Location> moveLocs = new ArrayList<>();
        Location location= this.getLocation();
        int row = location.getRow();
        int col= location.getCol();
        this.move(moveLocs);

        return moveLocs;
    }

}
