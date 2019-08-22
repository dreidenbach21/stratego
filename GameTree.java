package com.company;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class GameTree {

    private class GameNode {
        private Bord board;
        private int value;
        private ArrayList<GameNode> children;
        private Boolean max;
        private Move move;

        public GameNode(Bord board, Boolean max, Move move) {
            this.board = board;
            children = new ArrayList<>();
            this.max = max;
            this.move = move;
        }

        public void addChild(Bord board, Move move, int parent) {
            int val = 0;
            int start = move.getPiece().getValue();
            Piece vic = move.getVictim();
            if (vic == null) {
                val = start;
            } else if (vic.getValue() == 0) {
                val = 10 * start;
            } else if (vic.getValue() == 100 && move.getPiece().getValue() != 3) {
                val = -10 * start;
            } else if (vic.getValue() == 100 && move.getPiece().getValue() == 3) {
                val = 3 * start;
            } else if (move.getPiece().getColor().equals(Color.BLACK) && board.redPiece == 1) {
                val = 10 * start;
            } else if (move.getPiece().getColor().equals(Color.RED) && board.blackPiece == 1) {
                val = 10 * start;
            } else if (start < vic.getValue()) {
                val = start - vic.getValue();
            } else {
                val = start + vic.getValue();
            }
            if(move.getDestination().getCol() < move.getSource().getCol() && move.getPiece().getColor().equals(Color.RED)) {
                val +=5; // this incentives enemy forward progress;
            }
//            if(!this.max) {//this is for path sum
//                val*= -1;
//            }
            System.out.println(move.getPiece().getColor());
            board.executeMove(move);
            GameNode a = new GameNode(board, !this.max, move);
            a.value = val + parent;
            children.add(a);
        }
    }

    public GameNode root;

    public GameTree(Bord board) {
        root = new GameNode(board, true, null);
    }

    public Move getGood(GameTree t, Stack<Move> stack) {
        Collections.shuffle(t.root.children);
        int max = t.root.children.get(0).value;
        Move maxx = t.root.children.get(0).move;
        for (GameNode n : root.children) {
            int b = n.value;
            if (b > max) {
                max = b;
                maxx = n.move;
            }
        }
        if(!stack.empty()){
            Move old = stack.peek();
            Move oldMax = maxx;
            if (old.getDestination().equals(maxx.getSource()) && old.getSource().equals(maxx.getDestination())) { // cycle
                for (GameNode n : root.children) {
                    int b = n.value;
                    if (b >= max && !n.move.equals(oldMax)) {
                        max = b;
                        maxx = n.move;
                    }
                }
            }
        }
        return maxx;
    }

    public GameTree createGameTree(Bord board, Color color1, Color color2) {
        GameTree tree = new GameTree(board);
        Bord b = board.copy();
        gameTreeHelper(b, color1, color2, tree.root, 3);
        return tree;
    }

    public int getMoveSum(GameNode root) {
        if (root == null) {
            return 0;
        }
        if (root.children.size() == 0) {
            return root.value;
        }
        int child = root.value;
        for (GameNode n : root.children) {
            child += getMoveSum(n);
        }
        root.value = child;
        return child;
    }

    public int getMove(GameNode root, int bestMax, int bestMin) { // starter best max -100000000
        if (root == null) {
            return 0;
        }
        if (root.children.size() == 0) {
            return root.value;
        }
        int val = 0;
        if (root.max) {
            val = -10000000;
        } else {
            val = 10000000; //min
        }
        //int a = -1;
        for (GameNode n : root.children) {
            // a+=1;
            int child = getMove(n, bestMax, bestMin);
            //System.out.println(child + " :" + a);
            if (root.max) { // max value
                val = Math.max(child, val);
                bestMax = Math.max(bestMax,val);
                if(bestMin <= bestMax) { // alpha beta pruning
                    break;
                }
            } else {
                val = Math.min(child, val);
                bestMin = Math.min(bestMin,val);
                if(bestMin <= bestMax) {
                    break;
                }
            }
        }
        root.value = val;
        return val;
    }

    public int getMoveMinimaxSum(GameNode root) {
        if (root == null) {
            return 0;
        }
        if (root.children.size() == 0) {
            return root.value;
        }
        int val = 0;
        if (root.max) {
            val = -10000000;
        } else {
            val = 10000000; //min
        }
        int a = root.value;
        for (GameNode n : root.children) {
            // a+=1;
            int child = getMoveMinimaxSum(n);
            //System.out.println(child + " :" + a);
            if (root.max) {
                val = Math.max(child, val);
            } else {
                val = Math.min(child, val);
            }
        }
        root.value += val;
        return root.value;
    }


    private void gameTreeHelper(Bord board, Color color1, Color color2, GameNode root, int depth) {
        if (depth == 0 || board.flags < 2) {
            //
            // System.out.println("depth: " + depth);
            return;
        }
        ArrayList<Move> moves = board.allMoves(color1);
        Move fin = board.finish(moves);
        if (fin != null) {
            Bord b = board.copy();
            moves = b.allMoves(color1);
            fin = b.finish(moves);
            root.addChild(b, fin, root.value);
            return;
        }
        for (int i = 0; i < moves.size(); i++) {
            Bord b = board.copy();
            moves = b.allMoves(color1);
            //Collections.shuffle(moves);
//           if(b == board) {
//               System.out.println("Error");
//           }

            System.out.println(board.getFlags() + " Flag number");
            System.out.println(moves.get(i));

            root.addChild(b, moves.get(i), root.value);
            //Bord bb = b;
            GameNode child = root.children.get(i);
            System.out.println("depth: " + depth);

            gameTreeHelper(b, color2, color1, child, depth - 1);
        }
    }


}
