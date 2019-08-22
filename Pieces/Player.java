package com.company;
import java.awt.*;
import java.util.*;
/**
 * represent one of the two players in a game of chess
 * 
 * @author Danny Reidenbach
 * @version May 11 2017
 * 
 * I give permission to Harker CS Teachers to use this
 */
public abstract class Player
{
    // instance variables - replace the example below with your own
    //A Player should keep track of a Board, the name of the Player, and the Player's Color

    private Bord board;
    private String name;
    private Color color;

    /**
     * Constructor for objects of class Player
     * 
     * @param board is the board to be played on
     * @param name is the player name
     * @param color is the pice color
     */
    public Player(Bord board, String name, Color color)
    {
        this.board = board;
        this.name = name;
        this.color = color;

    }
    
    /**
     * Gets the Board which the player is on
     * 
     * @return the board to be played on
     */
    
    public Bord getBoard()
    {
        return board;
    }
    
    /**
     * Gets the name of the player
     * 
     * @return the name of the player
     */
    
    public String getName()
    {
        return name;
    }
    
    /**
     * Gets the piece color of the player
     * 
     * @return the color of the piece
     */
    
    public Color getColor()
    {
        return color;
    }
    
    /**
     * Gets the next move of the selected player
     * 
     * @return the next move
     */
    public abstract Move nextMove();
    
    

   
}
