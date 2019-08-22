package com.company;
import java.awt.*;
import java.util.*;
/**
 * The Flag piece
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 */
public class Flag extends Piece
{

    /**
     * Constructor for objects of class Flag
     * 
     * @param col is the color of the piece
     * @param fileName is the image that will display
     */
    public Flag(Color col, String fileName)
    {
        // initialise instance variables
        super(col, fileName,0);
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
