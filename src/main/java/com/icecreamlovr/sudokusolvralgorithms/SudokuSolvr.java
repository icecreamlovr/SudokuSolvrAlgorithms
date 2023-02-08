package com.icecreamlovr.sudokusolvralgorithms;

public interface SudokuSolvr {
  /** Validates the Sudoku board before solving. */
  void validate(int[][] board);

  /** Solve the Sudoku. The solution is returned in place. */
  boolean solve(int[][] board);
}