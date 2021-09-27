
package Minesweeper;

import java.util.Arrays;


public class Minesweeper {
    public static void main(String[] args) {
        int[][] board = {
            {0,0,-1,-1},
            {-1,0,-1,0},
            {0,-1,0,0}
        };
        
        System.out.println("\n______Board______");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]+",");
            }
            System.out.println("");
        }
        
        int[][] resultBoard = countMines(board);
        
        
        
        System.out.println("\n______Result Board______");
        for (int i = 0; i < resultBoard.length; i++) {
            for (int j = 0; j < resultBoard[i].length; j++) {
                System.out.print(resultBoard[i][j]+",");
            }
            System.out.println("");
        }
    }
    
    public static int[][] countMines(int[][] board){
        int[][] resultBoard = board.clone();
        
        
        for (int i = 0; i < board.length; i++) {
            
            for (int j = 0; j < board[i].length; j++) {
                int mines = 0;
                if(!(board[i][j]==-1)){
                    System.out.println("\n_____Position ("+i+", "+j+")______");
                    if(checkAhead(board,i,j)){ mines++; }
                    if(checkBehind(board,i,j)){ mines++; }
                    if(checkAbove(board,i,j)){ mines++; }
                    if(checkDown(board,i,j)){ mines++; }
                    if(checkAheadDown(board,i,j)){ mines++; }
                    if(checkBehindDown(board,i,j)){ mines++; }
                    if(checkAheadAbove(board,i,j)){ mines++; }
                    if(checkBehindAbove(board,i,j)){ mines++; }
                    resultBoard[i][j] = mines;
                }
                mines = 0;
            }
        }
        return resultBoard;
    }
    
    public static boolean checkAhead(int[][] board, int i, int j){
        boolean result = false;
        if(j+1 == board[i].length){
            System.out.println("checkAhead(passed)");
            return result;
        }
        if(board[i][j+1]==-1){ result = true; }
        System.out.println("checkAhead: "+result);
        return result;
    }
    public static boolean checkBehind(int[][] board, int i, int j){
        boolean result = false;
        if(j==0){
            System.out.println("checkBehind(passed)");
            return result;
        }
        if(board[i][j-1]==-1){ result = true; }
        System.out.println("checkBehind: "+result);
        return result;
    }
    public static boolean checkAbove(int[][] board, int i, int j){
        boolean result = false;
        if(i == 0){
            System.out.println("checkAbove(passed)");
            return result;
        }
        if(board[i-1][j]==-1){ result = true; }
        System.out.println("checkAbove("+i+", "+j+"): "+result);
        return result;
    }
    public static boolean checkDown(int[][] board, int i, int j){
        boolean result = false;
        if((i+1) == board.length){
            System.out.println("checkDown(passed)");
            return result;
        }
        if(board[i+1][j]==-1){ result = true; }
        System.out.println("checkDown: "+result);
        return result;
    }
    public static boolean checkAheadDown(int[][] board, int i, int j){
        boolean result = false;
        if((i+1 == board.length) || (j+1 == board[0].length)){
            System.out.println("checkAheadDown(passed)");
            return result;
        }
        if(board[i+1][j+1]==-1){ result = true; }
        System.out.println("checkAheadDown: "+result);
        return result;
    }
    public static boolean checkBehindDown(int[][] board, int i, int j){
        boolean result = false;
        if((j == 0) || (i+1 == board.length)){
            System.out.println("checkBehindDown(passed)");
            return result;
        }
        if(board[i+1][j-1]==-1){ result = true; }
        System.out.println("checkBehindDown: "+result);
        return result;
    }
    public static boolean checkAheadAbove(int[][] board, int i, int j){
        boolean result = false;
        if((i == 0) || (j+1 == board[i].length)){
            System.out.println("checkAheadAbove(passed)");
            return result;
        }
        if(board[i-1][j+1]==-1){ result = true; }
        System.out.println("checkAheadAbove: "+result);
        return result;
    }
    public static boolean checkBehindAbove(int[][] board, int i, int j){
        boolean result = false;
        if((i == 0) || (j == 0)){
            System.out.println("checkBehindAbove(passed)");
            return result;
        }
        if(board[i-1][j-1]==-1){ result = true; }
        System.out.println("checkBehindAbove: "+result);
        return result;
    }
}
