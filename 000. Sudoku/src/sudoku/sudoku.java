
package sudoku;

import java.util.HashSet;

public class sudoku {
    public static void main(String[] args) {

        int[][] sudoku = {
            {4,5,2,1,7,3,8,9,6},
            {1,6,8,9,2,4,5,7,3},
            {3,7,9,5,6,8,1,2,4},
            {8,1,7,4,9,5,6,3,2},
            {2,4,3,6,8,1,9,5,7},
            {5,9,6,2,3,7,4,8,1},
            {6,8,1,7,5,2,3,4,9},
            {7,3,4,8,1,9,2,6,5},
            {9,2,5,3,4,6,7,1,8}
        };
  
        System.out.println(isSudokuCorrect(sudoku));
    }
    
    public static boolean isSudokuCorrect(int[][] sudoku){
        
        if(isLineCorrect(sudoku) && isColumnCorrect(sudoku) && is3x3Correct(sudoku)){
            return true;
        }else{
            return false;
        }
    
    }
    public static boolean isLineCorrect(int[][] sudoku){
        
        HashSet<Integer> line = new HashSet<>();
        boolean isCorrect = true;
        
        for (int i = 0; i < sudoku.length; i++) {
            line.clear();
            for (int j = 0; j < sudoku[i].length; j++) {
                line.add(sudoku[i][j]);
            }
            if(line.size()!=9){
                isCorrect = false;
                return isCorrect;
            }
        }
        return isCorrect;
    }
    
    
    public static boolean isColumnCorrect(int[][] sudoku){
        HashSet<Integer> column = new HashSet<>();
        boolean isCorrect = true;
        
        for (int i = 0; i < sudoku[0].length; i++) {
            column.clear();
            for (int j = 0; j < sudoku.length; j++) {
                column.add(sudoku[i][j]);
            }
            if(column.size()!=9){
                isCorrect = false;
                return isCorrect;
            }
        }
        return isCorrect;
    }
    
    
    public static boolean is3x3Correct(int[][] sudoku){
        HashSet<Integer> cube1 = new HashSet<>();
        HashSet<Integer> cube2 = new HashSet<>();
        HashSet<Integer> cube3 = new HashSet<>();
        boolean isCorrect = true;
        
        for (int i = 0; i < sudoku.length; i++) {
            if(i == 3 || i == 6){
                if((cube1.size()!=9)||(cube2.size()!=9)||(cube3.size()!=9)){
                    isCorrect = false;
                    return isCorrect;
                }
                
                cube1.clear();
                cube2.clear();
                cube3.clear();
                
                
            }
            for (int j = 0; j < sudoku[i].length; j++) {
                switch(j){
                    case 0: cube1.add(sudoku[i][j]); break;
                    case 1: cube1.add(sudoku[i][j]); break;
                    case 2: cube1.add(sudoku[i][j]); break;
                    case 3: cube2.add(sudoku[i][j]); break;
                    case 4: cube2.add(sudoku[i][j]); break;
                    case 5: cube2.add(sudoku[i][j]); break;
                    case 6: cube3.add(sudoku[i][j]); break;
                    case 7: cube3.add(sudoku[i][j]); break;
                    case 8: cube3.add(sudoku[i][j]); break;    
                }
            }        
        }
        
        if((cube1.size()!=9)||(cube2.size()!=9)||(cube3.size()!=9)){
            isCorrect = false;
            return isCorrect;
        }
        
        return isCorrect;
    
    }

}
