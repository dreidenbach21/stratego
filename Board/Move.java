
package com.company;

public class Move
{
    private Piece piece;          // the piece being moved
    private Location source;      // the location being moved from
    private Location destination; // the location being moved to
    private Piece victim;         // any captured piece at the destination

    /**
     * Constructs a new move for moving the given piece to the given 
     * destination.
     * 
     * @param piece  the piece to be moved
     * @param destination  the location the piece is to be moved to 
     */
    public Move(Piece piece, Location destination)
    {
        this.piece = piece;
        this.source = piece.getLocation();
        this.destination = destination;
        this.victim = piece.getBoard().get(destination);

        if (source.equals(destination))
            throw new IllegalArgumentException("Both source and dest are " + source);
    }

    /**
     * Returns the piece being moved.
     * 
     * @return the selected piece 
     */
    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Returns the location being moved from.
     * 
     * @return the location being moved from
     */    
    public Location getSource()
    {
        return source;
    }

    /**
     * Returns the location being moved to.
     * 
     * @return the location being moved to  
     */
    public Location getDestination()
    {
        return destination;
    }

    /**
     * Returns the piece being captured.
     * 
     * @return the captured piece 
     */
    public Piece getVictim()
    {
        return victim;
    }


    /**
     * Returns a string description of the move.
     * 
     * @return information about the original position,
     *         the destination position and victim
     */
    public String toString()
    {
        return piece + " from " + source + " to " + 
               destination + " containing " + victim;
    }


    /**
     * Returns true if this move is equivalent to the given one.
     * 
     * @param x the other move with which to compare this move
     * @return true if both moves are equivalent; otherwise,
     *         false
     */
    public boolean equals(Object x)
    {
        Move other = (Move)x;
        return piece == other.getPiece() && 
               source.equals(other.getSource()) &&
               destination.equals(other.getDestination()) && 
               victim == other.getVictim();
    }


    /**
     * Returns a hash code for this move, such that equivalent 
     * moves have the same hash code.
     * 
     * @return the hashcode for the move
     */
    public int hashCode()
    {
        return piece.hashCode() + source.hashCode() + 
               destination.hashCode();
    }
    
    
    
}