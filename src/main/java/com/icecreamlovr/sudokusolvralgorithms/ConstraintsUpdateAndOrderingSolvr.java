package com.icecreamlovr.sudokusolvralgorithms;

public class ConstraintsUpdateAndOrderingSolvr implements SudokuSolvr {
  // The idea is to still apply backtracking, but sort the order of backtracking based on
  // difficulties at each cell, i.e. the number of guesses (constraints) each cell has.
  //
  // For example, for the following Sudoku puzzle, cell[8,8] can not take any value between
  // 1-9, and therefore the Sudoku is unsolvable:
  //      {{0, 0, 0, 0, 0, 0, 0, 0, 1},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 2},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 0},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 4},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 3},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 0},
  //       {5, 0, 6, 0, 0, 7, 8, 0, 0},
  //       {0, 0, 0, 0, 0, 0, 0, 0, 0},
  //       {0, 0, 0, 0, 0, 0, 0, 9, 0}}
  // However, with vanilla backtracking, we start the guess at cell[0,0], and need to reach
  // all the way to the final cell to know the guess is wrong. Backtracking is very
  // inefficient in such scenarios (takes forever to run).
  //
  // On the other hand, if we sort the cells based on the guesses/constraints, we can easily
  // pick cell[8,8] to backtrack first, and determine that this Sudoku puzzle is unsolvable.
  //
  // Several things to note:
  // 1. For sorting, note each cell can only have either 0 or 1,2,3,...,9 constraints. So we
  //    should be able to apply counting sort:
  //      - build 10 buckets (cells with 9,8,7,...0 constraints)
  //      - start from bucket with 9 constraints, if it's empty, move to 8 constraints, etc.
  // 2. Everytime a guess is made on a cell X, we should update the constraints on the cells
  //    X touches (cells in same row/column/square as X).
  // 3. If some of these cells see constraints change because of guess on X, they can simply
  //    be removed from their current constraints bucket and be upgraded to the constraints+1
  //    bucket. For e.g.
  //      - guessing 5 on board[3][2]
  //      - board[3][3] used to have 2 constraints (not7+not9), now it's 3 constraints
  //         (not5+not7+not9)
  //      - remove board[3][3] from bucket 2, and add it to bucket 3.
  // 4. If a guess at X is incorrect (none of the children works) and has to be reverted, we need to
  //    revert the constraints change for the cells it touches as well.
  //      - can either re-calculate constraints based on row/column/square
  //      - or memorize the constraint changes caused by this guess
  //
  // Data structure design
  // A. 10 buckets, each is a hashset storing cells with specific number of constraints
  // B. reverse mapping from each cell number to their current bucket number
  // D. mapping from each cell number to their current list of constraints
  // C. mapping from each cell number to the cells their current guess touches and causes change
  // - A can be an array (size 10) of hashsets of cell numbers.
  // - B, C, D can be stored in an object. There can be an array of 81 such objects.

  @Override
  public void validate(int[][] board) {

  }

  @Override
  public boolean solve(int[][] board) {
    return true;
  }

  private static int getCellNumber(int row, int col) {
    return row * 9 + col;
  }

  private static int getRow(int cellNumber) {
    return cellNumber / 9;
  }

  private static int getCol(int cellNumber) {
    return cellNumber % 9;
  }
}
