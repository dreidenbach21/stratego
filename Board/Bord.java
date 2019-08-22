package com.company;

import com.company.TileEngine.TERenderer;
import com.company.TileEngine.TETile;
import com.company.TileEngine.Tileset;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Bord {
    private int row = 10;
    private int col = 10;
    private TETile[][] world;
    private Piece[][] grid;
    public int[][] data;
    // 2-10 bomb 66 flag 999 lake 10000 make sure thes values match piece values
    public int flags = 2;
    public int redPiece = 40;
    public int blackPiece = 40;
    //private HashMap<Color,ArrayList<Piece>> map;


    public Bord() {
        world = new TETile[row][col];
        data = new int[row][col];
        grid = new Piece[row][col];
        boolean green = true;
        for (int r = 0; r < row; r++) {
            green = !green;
            for (int c = 0; c < col; c++) {
                if (green) {
                    world[r][c] = new TETile(' ', new Color(153, 102, 70), new Color(153, 102, 70), "green");
                } else {
                    world[r][c] = new TETile(' ', new Color(102, 255, 102), new Color(51, 150, 0), "yellow");
                }
                green = !green;
            }
        }

    }

    public int getDataX(Location loc) {
        return loc.getRow();
    }

    public int getDataY(Location loc) {
        return col - loc.getCol() - 1;
    }

    public Piece get(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        return grid[getDataX(loc)][getDataY(loc)];
    }

    public Piece put(Location loc, Piece obj) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");
        // Add the object to the grid.
        Piece oldOccupant = get(loc);
        grid[getDataX(loc)][getDataY(loc)] = obj;
        world[loc.getRow()][loc.getCol()] = obj.getTet();
        data[getDataX(loc)][getDataY(loc)] = obj.getValue();
        return oldOccupant;
    }

    public Piece remove(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");

        // removes the object from the grid.
        Piece r = get(loc);
        TETile replace = null;
        if(loc.getRow()%2 == loc.getCol()%2) {
           replace = new TETile(' ', new Color(102, 255, 102), new Color(51, 150, 0), "yellow");
        }
        else {
            replace = new TETile(' ', new Color(153, 102, 70), new Color(153, 102, 70), "green");
        }
        world[loc.getRow()][loc.getCol()] = replace;
        grid[getDataX(loc)][getDataY(loc)] = null;
        data[getDataX(loc)][getDataY(loc)] = 0;
        return r;
    }

    public ArrayList<Piece> getNeighbors(Location loc) {
        ArrayList<Piece> neighbors = new ArrayList<>();
        for (Location neighborLoc : getOccupiedAdjacentLocations(loc))
            neighbors.add(get(neighborLoc));
        return neighbors;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Gets all valid adjacent locations.
     *
     * @param loc the current location in the grid for which valid
     *            adjacent locations are being retrieved
     * @return an ArrayList of all valid adjacent locations
     */
    public ArrayList<Location> getValidAdjacentLocations(Location loc) {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (int i = loc.getRow()-1; i <= loc.getRow()+1; i++) {
            for(int c = loc.getCol()-1; c <= loc.getCol()+1; c++) {
                if (i != 0 && c != 0) {
                    Location neighborLoc = new Location(i, c);
                    if (isValid(neighborLoc)) {
                        locs.add(neighborLoc);
                    }
                }
            }
            }
        return locs;
    }

    /**
     * Gets all adjacent locations that do not contain an object.
     *
     * @param loc the current location in the grid for which empty
     *            adjacent locations are being retrieved
     * @return an ArrayList of all empty/unoccupied adjacent locations
     */
    public ArrayList<Location> getEmptyAdjacentLocations(Location loc) {
        ArrayList<Location> locs = new ArrayList<>();
        for (Location neighborLoc : getValidAdjacentLocations(loc)) {
            if (get(neighborLoc) == null)
                locs.add(neighborLoc);
        }
        return locs;
    }

    /**
     * Gets all adjacent locations that do contain an object.
     *
     * @param loc the current location in the grid for which occupied
     *            adjacent locations are being retrieved
     * @return an ArrayList of all occupied adjacent locations
     */
    public ArrayList<Location> getOccupiedAdjacentLocations(Location loc) {
        ArrayList<Location> locs = new ArrayList<Location>();
        for (Location neighborLoc : getValidAdjacentLocations(loc)) {
            if (get(neighborLoc) != null)
                locs.add(neighborLoc);
        }
        return locs;
    }

    public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < row &&
                0 <= loc.getCol() && loc.getCol() < col;
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> theLocations = new ArrayList<Location>();
        // looks at all grid locations
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // if there's an object at this location, puts it in the array
                Location loc = new Location(r, c);
                if (get(loc) != null)
                    theLocations.add(loc);
            }
        }
        return theLocations;
    }


    public void undoMove(Move move) {
        Piece piece = move.getPiece();
        Location source = move.getSource();
        Location dest = move.getDestination();
        Piece victim = move.getVictim();

        piece.moveTo(source, this);

        if (victim != null)
            victim.putSelfInGrid(piece.getBoard(), dest);

    }

    public void executeMove(Move move) {
        Piece piece = move.getPiece();
        Location source = move.getSource();
        Location s = piece.getLocation();
        Piece test = get(source);
        Location dest = move.getDestination();
        Piece victim = move.getVictim();
        if(victim != null && victim.getValue()== 0){
            flags--;
            System.out.println("You captured the Enemy Flag");
        }

        piece.moveTo(dest, this);


    }
    public Move finish(ArrayList<Move> moves) {
        for(Move m: moves){
            if(m.getVictim()!= null && m.getVictim().getValue() == 0) {
                return m;
            }
        }
        return null;
    }

    /**
     * This gets all possible moves for a certain color
     * @param color
     * @return
     */
    public ArrayList<Move> allMoves(Color color) {
        //Move(Piece piece, Location destination)
        ArrayList<Move> moves = new ArrayList<Move>();
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Location loc = new Location(r, c);
                Piece temp = get(loc);
                if (temp != null && temp.getColor().equals(color)) {
                    ArrayList<Location> locs = temp.destinations();
                    for (int i = 0; i < locs.size(); i++) {
                        if(get(locs.get(i))!= null && get(locs.get(i)).getValue() == 100000) {
                            continue;
                        }
                        Move x = new Move(temp, locs.get(i));
                        moves.add(x);
                    }
                }
            }
        }
        return moves;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public Piece[][] getGrid() {
        return grid;
    }

    public int getFlags() {
        return flags;
    }
    public Piece copyPiece(Piece p, Bord b) {
        if(p == null) {
            return null;
        }
        Piece cop = null;
        if(p.getValue() == 0) {
            cop = new Flag(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 2) {
            cop = new Two(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 3) {
            cop = new Three(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 4) {
            cop = new Four(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 5) {
            cop = new Five(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 100000) {
            cop = new Lake(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 6) {
            cop = new Six(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 7) {
            cop = new Seven(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 8) {
            cop = new Eight(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 9) {
            cop = new Nine(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 10) {
            cop = new Ten(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 1) {
            cop = new Spy(p.getColor(),p.getImageFileName());
        }
        else if(p.getValue() == 100) {
            cop = new Bomb(p.getColor(),p.getImageFileName());
        }

        Location a = p.getLocation();
        cop.setLocation(a);

        cop.setBoard(b);
        return cop;

    }

    public Bord copy() {
        Bord n = new Bord();
        n.flags = flags;
        for(int i = 0; i < world.length; i++){
            n.world[i] = Arrays.copyOf(world[i],world[i].length);
        }
        for(int i = 0; i < data.length; i++){
            n.data[i] = Arrays.copyOf(data[i],data[i].length);
        }
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] != null){
                    Piece a = get(new Location(i,j));
                }
                n.grid[i][j] = copyPiece(grid[i][j], n);
            }
        }
        return n;
    }

    @Override
    public boolean equals(Object obj) {
        Bord b = (Bord) obj;
        return (b.grid.equals(grid) && b.data.equals(data) && b.world.equals(world));
    }

    public static void main(String[] args) {
        TERenderer terr = new TERenderer();
        terr.initialize(10, 10, 0, 0);
        Bord board = new Bord();
        terr.renderFrame(board.world);
    }
}
