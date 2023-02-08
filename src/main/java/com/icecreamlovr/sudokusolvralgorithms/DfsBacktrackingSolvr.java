package com.icecreamlovr.sudokusolvralgorithms;

import java.util.ArrayList;
import java.util.List;

/** The vanilla solving algorithm based on DFS backtracking. */
public class DfsBacktrackingSolvr implements SudokuSolvr {
  private static class Coords {
    private int row;
    private int col;

    public Coords(int row, int col) {
      this.row = row;
      this.col = col;
    }

    @Override
    public String toString() {
      return (row + "," + col);
    }
  }

  @Override
  public void validate(int[][] board) {

  }

  @Override
  public boolean solve(int[][] board) {
    List<Coords> solvingOrder = getSolvingOrder(board);
    return solveRecur(board, solvingOrder, 0);
  }

  // Get an ordered list of coordinates that need to be solved.
  // The algorithm will follow this order to solve the sudoku.
  private static List<Coords> getSolvingOrder(int[][] board) {
    List<Coords> order = new ArrayList<>();
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (board[i][j] == 0) {
          order.add(new Coords(i, j));
        }
      }
    }
    return order;
  }

  // Recursively solves the sudoku using DFS + backtracking.
  // The algorithm will follow a specific order to determine what slot to solve next.
  // The recursive call returns false if it cannot move forward.
  private static boolean solveRecur(int[][] board, List<Coords> solvingOrder, int orderIndex) {
    if (orderIndex == solvingOrder.size()) {
      // Base case: no more slot to solve.
      return true;
    }

    Coords boardSlot = solvingOrder.get(orderIndex);
    List<Integer> guesses = getGuesses(boardSlot, board);
    if (guesses.isEmpty()) {
      // Base case: impossible to solve further.
      return false;
    }

    for (int guess : guesses) {
      // Make a guess, then call solve recursively.
      board[boardSlot.row][boardSlot.col] = guess;
      if (!solveRecur(board, solvingOrder, orderIndex + 1)) {
        // If the child solve call returns false, revert the guess.
        board[boardSlot.row][boardSlot.col] = 0;
      } else {
        return true;
      }
    }
    return false;
  }

  // For a given unsolved slot, gets the list of potential numbers it can be.
  // This is done by going through the row, the column and the 3x3 square this slot is in.
  private static List<Integer> getGuesses(Coords boardSlot, int[][] board) {
    // Create a boolean array for masking.
    boolean[] existingNumbers = new boolean[9];
    int row = boardSlot.row;
    int col = boardSlot.col;

    // Go through the rows and columns.
    for (int i = 0; i < 9; i++) {
      int currentRow = board[row][i];
      if (currentRow != 0) {
        existingNumbers[currentRow - 1] = true;
      }
      int currentCol = board[i][col];
      if (currentCol != 0) {
        existingNumbers[currentCol - 1] = true;
      }
    }

    // Go through the 3x3 square.
    for (int i = row / 3 * 3; i <= row / 3 * 3 + 2; i++) {
      for (int j = col / 3 * 3; j <= col / 3 * 3 + 2; j++) {
        int current = board[i][j];
        if (current != 0) {
          existingNumbers[current - 1] = true;
        }
      }
    }

    // Transform the boolean mask array to list of numbers.
    List<Integer> possibleGuesses = new ArrayList<>();
    for (int i = 0; i < existingNumbers.length; i++) {
      if (!existingNumbers[i]) {
        possibleGuesses.add(i + 1);
      }
    }
    return possibleGuesses;
  }
}
