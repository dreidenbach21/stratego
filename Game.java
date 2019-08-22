package com.company;

import com.company.TileEngine.TERenderer;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.*;
import java.io.*;


public class Game {

    public void fill(Bord board, TERenderer terr) {
        int count2 = 8;
        int count3 = 5;
        int countSpy = 1;
        int count4 = 4;
        int count5 = 4;
        int count6 = 4;
        int count7 = 3;
        int count8 = 2;
        int count9 = 1;
        int count10 = 1;
        int countBomb = 6;
        int countFlag = 1;
        int count = 0; // Total Pieces
        Location loc = null;
        int yMax = 3;
        while (loc == null && count < 40) {
            while(!StdDraw.isMousePressed()) {
                continue;
            }
            loc = selectPiece(board);
            if (loc == null || loc.getCol() > yMax) {
                StdDraw.pause(500);
                continue;
            }
            System.out.println(loc);
            while (loc != null && count < 40) {
                if (!StdDraw.hasNextKeyTyped()) {
                    StdDraw.pause(500);
                    continue;
                }
                if(board.get(loc) != null){ // to fix the replacement bug
                    continue;
                }
                Character a = StdDraw.nextKeyTyped();
                if (a.toString().equals("2") && count2 > 0) {
                    Piece temp = new Two(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_2.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count2--;
                    loc = null;

                }
                else if (a.toString().equals("3") && count3 > 0) {
                    Piece temp = new Three(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_3.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count3--;
                    loc = null;

                }
                else if (a.toString().equals("4") && count4 > 0) {
                    Piece temp = new Four(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_4.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count4--;
                    loc = null;

                }
                else if (a.toString().equals("5") && count5 > 0) {
                    Piece temp = new Five(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_5.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count5--;
                    loc = null;

                }
                else if (a.toString().equals("6") && count6 > 0) {
                    Piece temp = new Six(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_6.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count6--;
                    loc = null;

                }
                else if (a.toString().equals("7") && count7 > 0) {
                    Piece temp = new Seven(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_7.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count7--;
                    loc = null;

                }
                else if (a.toString().equals("8") && count8 > 0) {
                    Piece temp = new Eight(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_8.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count8--;
                    loc = null;

                }
                else if (a.toString().equals("9") && count9 > 0) {
                    Piece temp = new Nine(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_9.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count9--;
                    loc = null;

                }
                else if (a.toString().equals("1") && count10 > 0) {
                    Piece temp = new Ten(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_10.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    count10--;
                    loc = null;

                }
                else if (a.toString().equals("b") && countBomb > 0) {
                    Piece temp = new Bomb(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/bomb.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    countBomb--;
                    loc = null;

                }
                else if (a.toString().equals("s") && countSpy > 0) {
                    Piece temp = new Spy(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/spy4.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    countSpy--;
                    loc = null;

                }
                else if (a.toString().equals("f") && countFlag > 0) {
                    Piece temp = new Flag(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/flag.png");
                    temp.putSelfInGrid(board, loc);
                    count++;
                    countFlag--;
                    loc = null;

                }
                terr.renderFrame(board.getWorld());
            }
        }
    }

    public Location selectPiece(Bord board) {
        if (StdDraw.isMousePressed()) {
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            int xx = (int) Math.floor(x);
            int yy = (int) Math.floor(y);
            Piece[][] grid = board.getGrid();
            Location loc = new Location(xx, yy);
            if (!board.isValid(loc)) {
                return null;
            }
            return loc;

        }
        return null;

    }

    public void playGame(Bord board) {
        TERenderer terr = new TERenderer();
        terr.initialize(10, 10, 0, 0);
        terr.renderFrame(board.getWorld());
        fill(board,terr);
        boolean opp = false;
        Stack<Move> stack = new Stack<>();

        while (board.getFlags() > 1 && board.redPiece!=0 && board.blackPiece!=0) {
            if (opp) {
                GameTree tree = new GameTree(board);
                tree = tree.createGameTree(board, Color.RED, Color.BLACK);
                tree.getMove(tree.root,-100000000, 100000000);
                Move opo = tree.getGood(tree,stack);
                stack.push(opo);
                if(opo.getVictim()!=null){
                    board.blackPiece--;
                }
                opo.setPiece(board.get(opo.getSource()));
                board.executeMove(opo);
                terr.renderFrame(board.getWorld());
                opp = !opp;
            } else {
                Piece attack = null;
                Location dest = null;
                while (attack == null) {
                    if (!StdDraw.isMousePressed()) {
                        continue;
                    }
                    Location start = selectPiece(board);
                    if (start == null) {
                        StdDraw.pause(500);
                        continue;
                    }
                    attack = board.get(start);
                    if (attack == null) {
                        StdDraw.pause(500);
                        continue;
                    }
                }
                while (StdDraw.isMousePressed()) {
                    System.out.println("pause");
                }
                while (dest == null) {
                    if (!StdDraw.isMousePressed()) {
                        continue;
                    }
                    dest = selectPiece(board);
                    ArrayList<Location> lo = attack.destinations();
                    if (attack.destinations().contains(dest)) {
                        Move m = new Move(attack, dest);
                        if(m.getVictim()!=null){
                            board.redPiece--;
                        }
                        board.executeMove(m);
                        terr.renderFrame(board.getWorld());
                    }
                }
                opp=!opp;
                StdDraw.pause(500);
            }
        }
    }

    public void setupTestBoard(Bord board) {
        //set up entire board with all pieces
        int count2 = 8;
        int count3 = 5;
        int countSpy = 1;
        int count4 = 4;
        int count5 = 4;
        int count6 = 4;
        int count7 = 3;
        int count8 = 2;
        int count9 = 1;
        int count10 = 1;
        int countBomb = 6;
        int countFlag = 1;

        Piece two1 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two2 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two3 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two4 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two5 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two6 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two7 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece two8 = new Two(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        two1.putSelfInGrid(board,new Location(2,9));
        two2.putSelfInGrid(board,new Location(1,9));
        two3.putSelfInGrid(board,new Location(0,9));
        two4.putSelfInGrid(board,new Location(0,8));
        two5.putSelfInGrid(board,new Location(1,8));
        two6.putSelfInGrid(board,new Location(2,8));
        two7.putSelfInGrid(board,new Location(3,8));
        two8.putSelfInGrid(board,new Location(4,8));

        Piece three1 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece three2 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece three3 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece three4 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece three5 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        three1.putSelfInGrid(board,new Location(5,8));
        three2.putSelfInGrid(board,new Location(6,8));
        three3.putSelfInGrid(board,new Location(7,8));
        three4.putSelfInGrid(board,new Location(8,8));
        three5.putSelfInGrid(board,new Location(9,8));

        Piece four1 = new Four(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece four2 = new Four(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece four3 = new Four(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece four4 = new Four(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        four1.putSelfInGrid(board,new Location(0,7));
        four2.putSelfInGrid(board,new Location(1,7));
        four3.putSelfInGrid(board,new Location(2,7));
        four4.putSelfInGrid(board,new Location(3,7));


        Piece five1 = new Five(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece five2 = new Five(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece five3 = new Five(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece five4 = new Five(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        five1.putSelfInGrid(board,new Location(4,7));
        five2.putSelfInGrid(board,new Location(5,7));
        five3.putSelfInGrid(board,new Location(6,7));
        five4.putSelfInGrid(board,new Location(7,7));

        Piece six1 = new Six(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece six2 = new Six(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece six3 = new Six(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece six4 = new Six(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        six1.putSelfInGrid(board,new Location(8,7));
        six2.putSelfInGrid(board,new Location(9,7));
        six3.putSelfInGrid(board,new Location(0,6));
        six4.putSelfInGrid(board,new Location(1,6));

        Piece seven1 = new Seven(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece seven2 = new Seven(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece seven3 = new Seven(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        seven1.putSelfInGrid(board,new Location(2,6));
        seven2.putSelfInGrid(board,new Location(3,6));
        seven3.putSelfInGrid(board,new Location(4,6));

        Piece eight1 = new Eight(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece eight2 = new Eight(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        eight1.putSelfInGrid(board,new Location(5,6));
        eight2.putSelfInGrid(board,new Location(6,6));

        Piece nine1 = new Nine(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        nine1.putSelfInGrid(board,new Location(7,6));

        Piece ten1 = new Ten(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        ten1.putSelfInGrid(board,new Location(8,6));

        Piece spy = new Spy(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        spy.putSelfInGrid(board,new Location(9,6));

        Piece bomb1 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece bomb2 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece bomb3 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece bomb4 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece bomb5 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        Piece bomb6 = new Bomb(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        bomb1.putSelfInGrid(board,new Location(8,9));
        bomb2.putSelfInGrid(board,new Location(7,9));
        bomb3.putSelfInGrid(board,new Location(6,9));
        bomb4.putSelfInGrid(board,new Location(5,9));
        bomb5.putSelfInGrid(board,new Location(4,9));
        bomb6.putSelfInGrid(board,new Location(3,9));

        Piece flag1 = new Flag(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue5.png");
        flag1.putSelfInGrid(board,new Location(9,9));
    }

    /**
     * Plays the Game and sets up the board
     *
     * @param args runs the a game
     */
    public static void main(String[] args) {
        // team red and team blue

        //option 1
        // start the game with only enemy board have piece counters
        //and go through and ask the user what piece they want where
        //so go through the grid upside down and depending of the user input
        // place the piece in the given location and take away from the counter

        //option 2
        //make different strategies for the AI and have a random 
        //number select the strategy to play against
        Game game = new Game();

        Bord board = new Bord();

        //RandomPlayer brandy = new RandomPlayer(board, "Brandy", Color.BLUE);
        //int rand = (int)Math.random()*3;
        //make different strategies
        //6 bombs
        int rand = 1;
        //(int)(Math.random()*2) +1;
        //returns random number 1 or 2

        // cut enemy code

        //Lakes
        Piece lake1 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake2 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake3 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake4 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake5 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake6 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake7 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");
        Piece lake8 = new Lake(Color.ORANGE, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/lake0.png");

//        Piece red2 = new Three(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_3.png");
////        Piece red3 = new Three(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_3.png");
////        Piece red4 = new Three(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_3.png");
////        Piece red5 = new Three(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/red_3.png");
//        Piece flag1 = new Flag(Color.BLACK, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/flag.png");
//        Piece flag2 = new Flag(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/flag.png");
//        Piece blue2 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue4.png");
//        Piece blue3 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue4.png");
//        Piece blue4 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue4.png");
//        Piece blue5 = new Three(Color.RED, "/Users/dannyreidenbach/Desktop/cs/Stratego/src/com/company/Pics/blue4.png");
//        red2.putSelfInGrid(board, new Location(4, 4));
////        red3.putSelfInGrid(board, new Location(5, 5));
////        red4.putSelfInGrid(board, new Location(4, 8));
////        red5.putSelfInGrid(board, new Location(3, 9));
//
//        flag1.putSelfInGrid(board ,new Location(0,0));
//
//        flag2.putSelfInGrid(board ,new Location(9,9));
//        blue2.putSelfInGrid(board, new Location(9, 8));
//        blue3.putSelfInGrid(board, new Location(7, 6));
//        blue4.putSelfInGrid(board, new Location(8, 8));
//        blue5.putSelfInGrid(board, new Location(8, 9));
        game.setupTestBoard(board);


        lake1.putSelfInGrid(board,new Location(2,4));
        lake2.putSelfInGrid(board,new Location(2,5));
        lake3.putSelfInGrid(board,new Location(3,4));
        lake4.putSelfInGrid(board,new Location(3,5));

        lake5.putSelfInGrid(board,new Location(6,4));
        lake6.putSelfInGrid(board,new Location(6,5));
        lake7.putSelfInGrid(board,new Location(7,4));
        lake8.putSelfInGrid(board,new Location(7,5));

//        GameTree tree = new GameTree(board);
//        tree = tree.createGameTree(board,Color.BLACK,Color.RED);
//        int m = tree.getMove(tree.root);
//        int a = 66;

        game.playGame(board);


        //testing
        //         Piece testBomb = new Bomb(Color.BLUE, "bomb.png");
        //         testBomb.putSelfInGrid(board, new Location(4,0));
        //         Piece test = new Three(Color.RED,"red_3.png");
        //         test.putSelfInGrid(board, new Location(5,0));

        //BoardDisplay display = new BoardDisplay(board);


        //userFill( board, display);

        //HumanPlayer danny = new HumanPlayer(display, board, "Danny", Color.RED);
        //play(board, display, danny, brandy);
    }

    /**
     * fills the user's board
     * Has choices of flag 2 3 4 5 6 7 8 9 10 Spy and bombs
     * updates piece counter after each selection
     *
     * @param board   the board to be played on
     * @param display the display to show the game
     */
    private static void userFill(Bord board, BoardDisplay display) {
        int count2 = 8;
        int count3 = 5;
        int countSpy = 1;
        int count4 = 4;
        int count5 = 4;
        int count6 = 4;
        int count7 = 3;
        int count8 = 2;
        int count9 = 1;
        int count10 = 1;
        int countBomb = 6;
        int countFlag = 1;
        int pieces = 40;
        // i may not need a total piece counter if im doing a for loop
        Scanner in = new Scanner(System.in);
        System.out.println("Type two, three, four, five, six, seven, eight, nine, ten, spy, bomb, or flag for piece selection");

        for (int r = 9; r >= 0; r--) {
            for (int c = 0; c < 10; c++) {
                if (pieces > 0) {
                    System.out.println(" Enter the piece you would like to place ");
                    String x = in.next();
                    if (x.equals("flag")) {
                        if (countFlag > 0) {
                            pieces--;
                            countFlag--;
                            System.out.println("You have " + countFlag + " flags left");
                            Piece red = new Flag(Color.RED, "flag.png");
                            red.putSelfInGrid(board, new Location(r, c));

                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }

                    } else if (x.equals("ten")) {
                        if (count10 > 0) {
                            pieces--;
                            count10--;
                            System.out.println("You have " + count10 + " ten's left");
                            Piece red = new Ten(Color.RED, "red_10.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("two")) {
                        if (count2 > 0) {
                            pieces--;
                            count2--;
                            System.out.println("You have " + count2 + " two's left");
                            Piece red = new Two(Color.RED, "red_2.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("three")) {
                        if (count3 > 0) {
                            pieces--;
                            count3--;
                            System.out.println("You have " + count3 + " three's left");
                            Piece red = new Three(Color.RED, "red_3.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("four")) {
                        if (count4 > 0) {
                            pieces--;
                            count4--;
                            System.out.println("You have " + count4 + " four's left");
                            Piece red = new Four(Color.RED, "red_4.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("five")) {
                        if (count5 > 0) {
                            pieces--;
                            count5--;
                            System.out.println("You have " + count5 + " five's left");
                            Piece red = new Five(Color.RED, "red_5.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("six")) {
                        if (count6 > 0) {
                            pieces--;
                            count6--;
                            System.out.println("You have " + count6 + " six's left");
                            Piece red = new Six(Color.RED, "red_6.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("seven")) {
                        if (count7 > 0) {
                            pieces--;
                            count7--;
                            System.out.println("You have " + count7 + " seven's left");
                            Piece red = new Seven(Color.RED, "red_7.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("eight")) {
                        if (count8 > 0) {
                            pieces--;
                            count8--;
                            System.out.println("You have " + count8 + " eights's left");
                            Piece red = new Eight(Color.RED, "red_8.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("nine")) {
                        if (count9 > 0) {
                            pieces--;
                            count9--;
                            System.out.println("You have " + count9 + " nine's left");
                            Piece red = new Nine(Color.RED, "red_9.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("spy")) {
                        if (countSpy > 0) {
                            pieces--;
                            countSpy--;
                            System.out.println("You have " + countSpy + " Spy's left");
                            Piece red = new Spy(Color.RED, "spy.jpg");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else if (x.equals("bomb")) {
                        if (countBomb > 0) {
                            pieces--;
                            countBomb--;
                            System.out.println("You have " + countBomb + " bomb's left");
                            Piece red = new Bomb(Color.RED, "bomb.png");
                            red.putSelfInGrid(board, new Location(r, c));
                            display.showBoard();
                        } else {
                            System.out.println(" You are out of that piece please type a new one");
                            c--;
                        }
                    } else {
                        System.out.println("Please re type that piece");
                        c--;

                    }

                } else {

                    break;
                }
            }
        }
        System.out.println("The game begins, your move");

    }

    /**
     * Executes the Move and swtiches players
     *
     * @param board   the board to be played on
     * @param display the display to show the game
     * @param player  the current player
     */
    private static void nextTurn(Board board, BoardDisplay display, Player player) {
        display.setTitle(player.getName()); //1
        Move moveA = player.nextMove();//2
        board.executeMove(moveA);//3
        display.clearColors();//4
        display.setColor(moveA.getPiece().getLocation(), Color.YELLOW);//5

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Repeatedly asks players for move until 1 flag is missing
     *
     * @param board   the board to be played on
     * @param display the display to show the game
     * @param white   is the first player
     * @param black   is the second player
     */
    public static void play(Board board, BoardDisplay display, Player white, Player black) {
        boolean game = false;

        Player ten = white;
        while (game == false) {
            int count = 0;
            nextTurn(board, display, ten);

            if (ten.equals(black)) {
                ten = white;
            } else {
                ten = black;
            }

            for (int r = 0; r < board.getNumRows(); r++) {
                for (int c = 0; c < board.getNumCols(); c++) {
                    Piece temp = board.get(new Location(r, c));
                    if (temp != null) {
                        if (temp.getValue() == 0) // checks for flags
                        {
                            count++;
                        }

                    }
                }
            }

            if (count != 2) {
                game = true;
            }
        }

        if (ten.equals(black)) {
            ten = white;
        } else {
            ten = black;
        }

        System.out.print(ten.getName() + " is the Winner!!!!!");
    }

}
