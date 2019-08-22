package com.company;

import com.company.TileEngine.TETile;

import java.awt.*;
import java.util.*;


public class Piece {

    private Bord board;       // the board this piece is on
    private Location location; // the location of this piece 
    // on the board
    private Color color;       // the color of the piece
    private int value;         //the approximate value of this
    // piece in a game of chess
    private String imageFileName; // the file used to display

    private TETile tet;
    // this piece

//    public TETile(char character, Color textColor, Color backgroundColor, String description,
//                  String filepath) {
//        this.character = character;
//        this.textColor = textColor;
//        this.backgroundColor = backgroundColor;
//        this.description = description;
//        this.filepath = filepath;
//    }

    /**
     * Constructs a new Piece with a color, value and
     * image information.
     *
     * @param col      color of the piece
     * @param fileName the image file name for piece
     * @param val      the weight for the piece
     */
    public Piece(Color col, String fileName, int val) {
        tet = new TETile(' ', Color.WHITE, Color.WHITE, "piece", fileName);
        color = col;
        imageFileName = fileName;
        value = val;
    }

    public TETile getTet() {
        return tet;
    }

    /**
     * Returns the board this piece is on.
     *
     * @return the board this piece belongs to
     */
    public Bord getBoard() {
        return board;
    }

    /**
     * Returns the location of this piece on the board.
     *
     * @return the location this piece belongs to on the board
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Returns the color of this piece.
     *
     * @return the color this piece belongs to on the board
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the name of the file used to display this piece.
     *
     * @return the file name for this piece
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * Returns a number representing the relative value of this
     * piece.
     *
     * @return the value of this piece
     */
    public int getValue() {
        return value;
    }

    /**
     * Puts this piece into a board. If there is another piece at the
     * given location, it is removed. <br />
     *
     * @param brd the board into which this piece should be placed
     * @param loc the location into which the piece should be placed
     * @precondition (1) this piece is not contained in a grid
     * (2) <code>loc</code> is valid in <code>gr</code>
     */
    public void putSelfInGrid(Bord brd, Location loc) {
        if (board != null)
            throw new IllegalStateException(
                    "This piece is already contained in a board.");

        Piece piece = brd.get(loc);
        if (piece != null)
            piece.removeSelfFromGrid();
        brd.put(loc, this);
        //brd.data[brd.getDataX(loc)][brd.getDataY(loc)] = value;
        board = brd;
        location = loc;
    }

    /**
     * Removes this piece from its board.
     *
     * @precondition this piece is contained in a board
     */
    public void removeSelfFromGrid() {
        if (board == null)
            throw new IllegalStateException(
                    "This piece is not contained in a board.");
        if (board.get(location) != this)
            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");

        board.remove(location);
        board = null;
        location = null;
    }

    public void setLocation(Location loc){
        location = loc;
    }
    public void setBoard(Bord b){
        board = b;
    }
    /**
     * Moves this piece to a new location. If there is another piece
     * at the given location, it is removed. <br />
     *
     * @param newLocation the new location
     * @precondition (1) this piece is contained in a grid
     * (2) <code>newLocation</code> is valid in the
     * grid of this piece
     */
    public void moveTo(Location newLocation, Bord board) {
        int count = 0;
        if (board == null)
            throw new IllegalStateException(
                    "This piece is not on a board.");
        if (board.get(location) != this)

            throw new IllegalStateException(
                    "The board contains a different piece at location "
                            + location + ".");
        if (!board.isValid(newLocation))
            throw new IllegalArgumentException(
                    "Location " + newLocation + " is not valid.");

        if (newLocation.equals(location))
            return;

        board.remove(location);
        Piece other = board.get(newLocation);
        if (other != null) {
            if (other.getValue() == 100000) { // Hits a Lake
                board.put(location, this);
                return;
            } else if (this.getValue() == 3 && other.getValue() == 100) { // three beats bombs
                other.removeSelfFromGrid();
                location = newLocation;
                board.put(location, this);

            } else if (this.getValue() == 1 && other.getValue() == 10) { // spy gets ten
                other.removeSelfFromGrid();
                location = newLocation;
                board.put(location, this);
            } else if (this.getValue() >= other.getValue()) {
                other.removeSelfFromGrid();
                location = newLocation;
                board.put(location, this);


            } else {
                board.remove(location);
                if (other.getValue() == 100) { // bomb kills piece
                    other.removeSelfFromGrid();
                }
            }

        } else {
            location = newLocation;
            board.put(location, this);
        }
    }

    /**
     * return true if dest is valid, and is either (a) empty or (b)
     * occupied by a Piece of a different color
     *
     * @param dest is the destination to be checked
     * @return true if the destination is valid, otherwise;
     * false
     */
    public boolean isValidDestination(Location dest) {
        Color c = getColor();
        if (board.isValid(dest)) {
            if (board.get(dest) == null) {
                return true;
            } else if (!board.get(dest).getColor().equals(c)) {
                return true;
            }
        }

        return false;

    }

    /**
     * Gets all locations
     *
     * @return an ArrayList<Location> for listing the locations the Piece can move to
     */
    public ArrayList<Location> destinations() {
        return null;
    }

    /**
     * Gets the locations for all moves in a straight line
     *
     * @param dests     is the list of all posible locations
     * @param direction is the direction in which the piece is facing
     */
    public void sweep(ArrayList<Location> dests) {
        int row = location.getRow();
        int col = location.getCol();
        int x = board.getRow();
        int y = board.getCol();
        int xLim = -1;
        int yLim =-1;
        // left
        for (int xx = row-1; xx >=0; xx--) {
            Location loc = new Location(xx,col);
            if(board.isValid(loc)){
                Piece temp = board.get(loc);
                if(temp == null) {
                    dests.add(loc);
                }
                else if(temp != null && board.get(loc).getColor() != this.color) {
                    dests.add(loc);
                    break;
                }
                else if(temp != null) {
                    break;
                }
            }
        }

        // right
        for (int xx = row+1; xx < 10; xx++) {
            Location loc = new Location(xx,col);
            if(board.isValid(loc)){
                Piece temp = board.get(loc);
                if(temp == null) {
                    dests.add(loc);
                }
                else if(temp != null && board.get(loc).getColor() != this.color) {
                    dests.add(loc);
                    break;
                }
                else if(temp != null) {
                    break;
                }
            }
        }

        // up
        for (int xx = col+1; xx < 10; xx++) {
            Location loc = new Location(row,xx);
            if(board.isValid(loc)){
                Piece temp = board.get(loc);
                if(temp == null) {
                    dests.add(loc);
                }
                else if(temp != null && board.get(loc).getColor() != this.color) {
                    dests.add(loc);
                    break;
                }
                else if(temp != null) {
                    break;
                }
            }
        }

        // down
        for (int xx = col-1; xx >= 0; xx--) {
            Location loc = new Location(row,xx);
            if(board.isValid(loc)){
                Piece temp = board.get(loc);
                if(temp == null) {
                    dests.add(loc);
                }
                else if(temp != null && board.get(loc).getColor() != this.color) {
                    dests.add(loc);
                    break;
                }
                else if(temp != null) {
                    break;
                }
            }
        }
//        for (int r = 0; r < x; r++) {
//            for (int c = 0; c < y; c++) {
//                if (r == row && c == col) {
//                    continue;
//                }
//                if ((r == row || c == col)) {
//                    Location loc = new Location(r, c);
//                    if (board.isValid(loc)) {
//                        if (board.isValid(loc)) {
//                            Piece temp = board.get(loc);
//                            if (temp != null) {
//                                Color tem = temp.getColor();
//                                if (!this.getColor().equals(tem)) {
//                                    dests.add(loc);
//                                    if(loc.getRow() == row) {
//                                        yLim = loc.getCol();
//                                    }
//                                    else {
//                                        xLim = loc.getRow();
//                                    }
//                                    break;
//                                } else {
//                                    break;
//                                }
//                            } else {
//                                dests.add(loc);
//                            }
//                        }
//                    }
//
//                }
//            }
//        }

//        if (direction == Location.NORTH) {
//            for (int i = 1; i <= Math.abs(row); i++) {
//                Location klocs = new Location(row - i, col);
//                if (board.isValid(klocs)) {
//                    Piece temp = board.get(klocs);
//                    if (temp != null) {
//                        Color tem = temp.getColor();
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(klocs);
//                            break;
//                        } else {
//                            break;
//                        }
//                    } else {
//                        dests.add(klocs);
//                    }
//                }
//            }
//        }
//
//        if (direction == Location.SOUTH) {
//            for (int i = 1; i <= Math.abs(x - row); i++) {
//                Location klocs = new Location(row + i, col);
//                if (board.isValid(klocs)) {
//                    Piece temp = board.get(klocs);
//                    if (temp != null) {
//                        Color tem = temp.getColor();
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(klocs);
//                            break;
//                        } else {
//                            break;
//                        }
//                    } else {
//                        dests.add(klocs);
//                    }
//                }
//            }
//        }
//
//        if (direction == Location.EAST) {
//            for (int i = 1; i <= Math.abs(y - col); i++) {
//                Location klocs = new Location(row, col + i);
//                if (board.isValid(klocs)) {
//                    Piece temp = board.get(klocs);
//                    if (temp != null) {
//                        Color tem = temp.getColor();
//
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(klocs);
//                            break;
//                        } else {
//                            break;
//                        }
//                    } else {
//                        dests.add(klocs);
//                    }
//                }
//            }
//        }
//
//        if (direction == Location.WEST) {
//            for (int i = 1; i <= Math.abs(y - col); i++) {
//                Location klocs = new Location(row, col - i);
//                if (board.isValid(klocs)) {
//                    Piece temp = board.get(klocs);
//                    if (temp != null) {
//                        Color tem = temp.getColor();
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(klocs);
//                            break;
//                        } else {
//                            break;
//                        }
//                    } else {
//                        dests.add(klocs);
//                    }
//                }
//            }
//        }
    }

    /**
     * Gets all the possible locations for movement in a diagonal
     *
     * @param dests     is the list of all posible locations
     * @param direction is the direction in which the piece is facing
     */
    public void diagSweep(ArrayList<Location> dests, int direction) {
        int row = location.getRow();
        int col = location.getCol();
        int x = board.getRow();
        int y = board.getCol();

        if (direction == Location.NORTHEAST) {
            for (int i = 1; i <= Math.abs(row); i++) {
                Location klocs = new Location(row - i, col + i);
                if (board.isValid(klocs)) {
                    Piece temp = board.get(klocs);
                    if (temp != null) {
                        Color tem = temp.getColor();
                        if (!this.getColor().equals(tem)) {
                            dests.add(klocs);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        dests.add(klocs);
                    }
                }
            }
        }

        if (direction == Location.NORTHWEST) {
            for (int i = 1; i <= Math.abs(row); i++) {
                Location klocs = new Location(row - i, col - i);
                if (board.isValid(klocs)) {
                    Piece temp = board.get(klocs);
                    if (temp != null) {
                        Color tem = temp.getColor();
                        if (!this.getColor().equals(tem)) {
                            dests.add(klocs);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        dests.add(klocs);
                    }
                }
            }
        }

        if (direction == Location.SOUTHEAST) {
            for (int i = 1; i <= Math.abs(row - x); i++) {
                Location klocs = new Location(row + i, col + i);
                if (board.isValid(klocs)) {
                    Piece temp = board.get(klocs);
                    if (temp != null) {
                        Color tem = temp.getColor();
                        if (!this.getColor().equals(tem)) {
                            dests.add(klocs);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        dests.add(klocs);
                    }
                }
            }
        }

        if (direction == Location.SOUTHWEST) {
            for (int i = 1; i <= Math.abs(row - x); i++) {
                Location klocs = new Location(row + i, col - i);
                if (board.isValid(klocs)) {
                    Piece temp = board.get(klocs);
                    if (temp != null) {
                        Color tem = temp.getColor();
                        if (!this.getColor().equals(tem)) {
                            dests.add(klocs);
                            break;
                        } else {
                            break;
                        }
                    } else {
                        dests.add(klocs);
                    }
                }
            }
        }

    }

    /**
     * Gets all the locations for movements in a L
     *
     * @param dests     is the list of all posible locations
     * @param direction is the direction in which the piece is facing
     */
    public void lSweep(ArrayList<Location> dests, int direction) {
        int row = location.getRow();
        int col = location.getCol();
        int x = board.getRow();
        int y = board.getCol();

        if (direction == Location.NORTH) {

            Location left = new Location(row - 2, col - 1);
            if (board.isValid(left)) {
                Piece temp = board.get(left);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(left);
                    }

                } else {
                    dests.add(left);
                }
            }

            Location right = new Location(row - 2, col + 1);
            if (board.isValid(right)) {
                Piece temp = board.get(right);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(right);
                    }

                } else {
                    dests.add(right);
                }
            }

        }

        if (direction == Location.SOUTH) {

            Location left = new Location(row + 2, col - 1);
            if (board.isValid(left)) {
                Piece temp = board.get(left);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(left);
                    }

                } else {
                    dests.add(left);
                }
            }

            Location right = new Location(row + 2, col + 1);
            if (board.isValid(right)) {
                Piece temp = board.get(right);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(right);
                    }

                } else {
                    dests.add(right);
                }
            }

        }

        if (direction == Location.WEST) {

            Location up = new Location(row - 1, col - 2);
            if (board.isValid(up)) {
                Piece temp = board.get(up);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(up);
                    }

                } else {
                    dests.add(up);
                }
            }

            Location down = new Location(row + 1, col - 2);
            if (board.isValid(down)) {
                Piece temp = board.get(down);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(down);
                    }

                } else {
                    dests.add(down);
                }
            }

        }

        if (direction == Location.EAST) {

            Location up = new Location(row - 1, col + 2);
            if (board.isValid(up)) {
                Piece temp = board.get(up);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(up);
                    }

                } else {
                    dests.add(up);
                }
            }

            Location down = new Location(row + 1, col + 2);
            if (board.isValid(down)) {
                Piece temp = board.get(down);
                if (temp != null) {
                    Color tem = temp.getColor();
                    if (!this.getColor().equals(tem)) {
                        dests.add(down);
                    }

                } else {
                    dests.add(down);
                }
            }

        }
    }

    /**
     * Moves the piece to the designated new location
     *
     * @param dests     is the list of all posible locations
     * @param direction is the direction in which the piece is facing
     */
    public void move(ArrayList<Location> dests) {
        int row = location.getRow();
        int col = location.getCol();
        int x = board.getRow();
        int y = board.getCol();

        Location up = new Location(row,col+1);
        Location down = new Location(row,col-1);
        Location left = new Location(row-1,col);
        Location right =  new Location(row+1,col);

        if(board.isValid(up)) {
            Piece temp = board.get(up);
            if(temp == null) {
                dests.add(up);
            }
            else if(temp.getColor() != color) {
                dests.add(up);
            }
        }
        if(board.isValid(down)) {
            Piece temp = board.get(down);
            if(temp == null) {
                dests.add(down);
            }
            else if(temp.getColor() != color) {
                dests.add(down);
            }
        }
        if(board.isValid(left)) {
            Piece temp = board.get(left);
            if(temp == null) {
                dests.add(left);
            }
            else if(temp.getColor() != color) {
                dests.add(left);
            }
        }
        if(board.isValid(right)) {
            Piece temp = board.get(right);
            if(temp == null) {
                dests.add(right);
            }
            else if(temp.getColor() != color) {
                dests.add(right);
            }
        }



//        if (direction == Location.NORTH) {
//
//            Location a = new Location(row - 1, col);
//            if (board.isValid(a)) {
//                Piece temp = board.get(a);
//                if (temp != null) {
//                    Color tem = temp.getColor();
//                    if (!temp.getColor().equals(Color.BLACK)) {
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(a);
//                        }
//
//                    }
//                } else {
//                    dests.add(a);
//                }
//
//            }
//        }
//        if (direction == Location.SOUTH) {
//
//            Location a = new Location(row + 1, col);
//            if (board.isValid(a)) {
//                Piece temp = board.get(a);
//                if (temp != null) {
//                    Color tem = temp.getColor();
//                    if (!temp.getColor().equals(Color.BLACK)) {
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(a);
//                        }
//
//                    }
//                } else {
//                    dests.add(a);
//                }
//
//            }
//        }
//        if (direction == Location.EAST) {
//
//            Location a = new Location(row, col + 1);
//            if (board.isValid(a)) {
//                Piece temp = board.get(a);
//                if (temp != null) {
//                    Color tem = temp.getColor();
//                    if (!temp.getColor().equals(Color.BLACK)) {
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(a);
//                        }
//
//                    }
//                } else {
//                    dests.add(a);
//                }
//            }
//        }
//        if (direction == Location.WEST) {
//
//            Location a = new Location(row, col - 1);
//            if (board.isValid(a)) {
//                Piece temp = board.get(a);
//                if (temp != null) {
//                    Color tem = temp.getColor();
//                    if (!temp.getColor().equals(Color.BLACK)) {
//                        if (!this.getColor().equals(tem)) {
//                            dests.add(a);
//                        }
//
//                    }
//                } else {
//                    dests.add(a);
//                }
//
//            }
//        }
    }

}