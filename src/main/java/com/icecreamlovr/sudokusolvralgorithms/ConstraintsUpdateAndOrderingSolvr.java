package com.icecreamlovr.sudokusolvralgorithms;

import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
  // pick cell[8,8] to process first, and determine that this Sudoku puzzle is unsolvable.
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
  // C. mapping from each cell number to their current list of constraints
  // D. mapping from each cell number to the cells their current guess touches and causes change
  // - A can be an array (size 10) of hashsets of cell numbers.
  // - B, C, D can be stored in an object. There can be an array of 81 such objects.

  @Override
  public void validate(int[][] board) {

  }

  // Class that holds current constraints and bucket number for one cell.
  private static class Constraints {
    // Bucket identifier.
    // Also denotes how many constraints the cell currently has.
    private int bucketNumber;

    // Bitmap of constraints. Stored as boolean array of size 9.
    private boolean[] constraints;

    public Constraints() {
      this.bucketNumber = 0;
      this.constraints = new boolean[9];
    }

    public int getBucketNumber() {
      return bucketNumber;
    }

    public boolean addConstraint(int digit) {
      if (constraints[digit - 1]) {
        return false;
      }
      constraints[digit - 1] = true;
      bucketNumber += 1;
      return true;
    }

    public boolean removeConstraint(int digit) {
      if (!constraints[digit - 1]) {
        return false;
      }
      constraints[digit - 1] = false;
      bucketNumber -= 1;
      return true;
    }

    public boolean hasConstraint(int digit) {
      return constraints[digit - 1];
    }

    public List<Integer> getConstraints() {
      List<Integer> constraints = new LinkedList<>();
      for (int i = 1; i <= 9; i++) {
        if (hasConstraint(i)) {
          constraints.add(i);
        }
      }
      return constraints;
    }
  }

  @Override
  public boolean solve(int[][] board) {
    List<Integer>[] allNeighbors = getInitialNeighborsInfo(board);
    Constraints[] allConstraints = getInitialConstraintsInfo(board);
    List<Set<Integer>> buckets = getInitialBuckets(allConstraints);
    printAllContext(board, allNeighbors, allConstraints, buckets);

    return false;
  }

  // Preprocess the initial board and get neighbors information for each EMPTY cell.
  private static List<Integer>[] getInitialNeighborsInfo(int[][] board) {
    List<Integer>[] allNeighbors = new List[81];
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        int cellNumber = getCellNumber(row, col);
        // Only attach neighbors for empty cells.
        if (board[row][col] != 0) {
          allNeighbors[cellNumber] = null;
          continue;
        }

        List<Integer> neighbors = new LinkedList<>();

        // 1.Empty cells within the same row.
        for (int otherCol = 0; otherCol < 9; otherCol++) {
          if (board[row][otherCol] != 0 || otherCol == col) {
            continue;
          }
          neighbors.add(getCellNumber(row, otherCol));
        }
        // 2. within the same column.
        for (int otherRow = 0; otherRow < 9; otherRow++) {
          if (board[otherRow][col] != 0 || otherRow == row) {
            continue;
          }
          neighbors.add(getCellNumber(otherRow, col));
        }
        // 3. within the same 3x3 square.
        int baseRow = row / 3 * 3;
        int baseCol = col / 3 * 3;
        for (int squareRow = baseRow; squareRow < baseRow + 3; squareRow++) {
          for (int squareCol = baseCol; squareCol < baseCol + 3; squareCol++) {
            if (board[squareRow][squareCol] != 0 || squareRow == row || squareCol == col) {
              // Note if squareRow == row, the neighbor should already have been added,
              // so we don't add it again. Same for squareCol == col.
              continue;
            }
            neighbors.add(getCellNumber(squareRow, squareCol));
          }
        }

        allNeighbors[cellNumber] = neighbors;
      }
    }

    return allNeighbors;
  }

  // Preprocess the board and get constraints information of all empty cells.
  // Return a Constraints[] array of size 81, mapped to each cell.
  // For empty cells, their Constraints in the array will be initialized with the initial constraints.
  // For non-empty cells, their Constraints will be null.
  private Constraints[] getInitialConstraintsInfo(int[][] board) {
    Constraints[] allConstraints = new Constraints[81];

    // Initialize Constraints only for empty Cells.
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        if (board[row][col] == 0) {
          allConstraints[getCellNumber(row, col)] = new Constraints();
        }
      }
    }

    // Calculate the initial constraints using non-empty cells.
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        // Empty cells don't apply constraint to others.
        if (board[row][col] == 0) {
          continue;
        }

        // Non-empty cells: apply constraints to others
        int constraint = board[row][col];

        // 1. apply constraints to other empty cells within the same row.
        for (int otherCol = 0; otherCol < 9; otherCol++) {
          if (board[row][otherCol] != 0) {
            continue;
          }
          allConstraints[getCellNumber(row, otherCol)].addConstraint(constraint);
        }
        // 2. within the same column.
        for (int otherRow = 0; otherRow < 9; otherRow++) {
          if (board[otherRow][col] != 0) {
            continue;
          }
          allConstraints[getCellNumber(otherRow, col)].addConstraint(constraint);
        }
        // 3. within the same 3x3 square.
        int baseRow = row / 3 * 3;
        int baseCol = col / 3 * 3;
        for (int squareRow = baseRow; squareRow < baseRow + 3; squareRow++) {
          for (int squareCol = baseCol; squareCol < baseCol + 3; squareCol++) {
            if (board[squareRow][squareCol] != 0) {
              continue;
            }
            allConstraints[getCellNumber(squareRow, squareCol)].addConstraint(constraint);
          }
        }
      }
    }

    return allConstraints;
  }

  // Process the initial cell constraints info and create the initial buckets.
  // Return a list of 10 buckets. Each bucket is a set of cells, where the number
  // of constraints on the cell is equal to the bucket's index.
  private static List<Set<Integer>> getInitialBuckets(Constraints[] allConstraints) {
    List<Set<Integer>> buckets = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      Set<Integer> bucket = new HashSet<>();
      buckets.add(bucket);
    }

    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        int cellNumber = getCellNumber(row, col);
        if (allConstraints[cellNumber] == null) {
          continue;
        }
        int bucketNumber = allConstraints[cellNumber].getBucketNumber();
        buckets.get(bucketNumber).add(cellNumber);
      }
    }
    return buckets;
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

  private static void printAllContext(
          int[][] board,
          List<Integer>[] allNeighbors,
          Constraints[] allConstraints,
          List<Set<Integer>> buckets) {
    printBoard(board);
    printNeighbors(allNeighbors);
    printConstraints(allConstraints);
    printBuckets(buckets);
  }

  private static void printBoard(int[][] board) {
    for (int row = 0; row < 9; row++) {
      for (int col = 0; col < 9; col++) {
        int cellNUmber = getCellNumber(row, col);
        String cellNumberDisplay = cellNUmber < 10 ? "0" + cellNUmber : "" + cellNUmber;
        System.out.print(cellNumberDisplay + "|" + board[row][col] + " ");
      }
      System.out.println();
    }
  }

  private static void printNeighbors(List<Integer>[] allNeighbors) {
    for (int row = 0; row < 9; row++) {
      System.out.println("===================Neighbors");
      for (int col = 0; col < 9; col++) {
        int cellNUmber = getCellNumber(row, col);
        System.out.print(row + " " + col + " " + cellNUmber + ": ");
        if (allNeighbors[cellNUmber] == null) {
          System.out.println("NULL");
        } else {
          System.out.println(allNeighbors[cellNUmber]);
        }
      }
    }
  }

  private static void printConstraints(Constraints[] allConstraints) {
    for (int row = 0; row < 9; row++) {
      System.out.println("===================Constraints");
      for (int col = 0; col < 9; col++) {
        int cellNUmber = getCellNumber(row, col);
        System.out.print(row + " " + col + " " + cellNUmber + ": ");
        if (allConstraints[cellNUmber] == null) {
          System.out.println("NULL");
        } else {
          System.out.println(allConstraints[cellNUmber].getConstraints());
        }
      }
    }
  }

  private static void printBuckets(List<Set<Integer>> buckets) {
    for (int i = 0; i < buckets.size(); i++) {
      System.out.print("Bucket " + i + ": ");
      for (Integer cellNumber : buckets.get(i)) {
        System.out.print(" " + cellNumber);
      }
      System.out.println();
    }
  }
}
