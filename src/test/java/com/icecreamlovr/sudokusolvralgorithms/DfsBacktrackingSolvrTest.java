package com.icecreamlovr.sudokusolvralgorithms;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DfsBacktrackingSolvrTest {

  private SudokuSolvr solvr;

  @BeforeEach
  void setup() {
    solvr = new DfsBacktrackingSolvr();
  }

  @Test
  void validate() {
  }

  @Test
  void solveSuccess() {
    int[][] testInput = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}};
    int[][] output = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isTrue();
    assertThat(testInput).isEqualTo(output);
  }

  @Test
  public void solveFail() {
    int[][] testInput = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 4, 0, 0, 1, 0, 2, 0, 0},
            {0, 0, 0, 0, 7, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 8, 0, 0, 0}};
    int[][] output = {
            {1, 2, 3, 4, 5, 6, 7, 8, 9},
            {4, 5, 6, 7, 8, 9, 1, 2, 3},
            {7, 8, 9, 1, 2, 3, 4, 5, 6},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 4, 0, 0, 1, 0, 2, 0, 0},
            {0, 0, 0, 0, 7, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 8, 0, 0, 0}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isFalse();
    assertThat(testInput).isEqualTo(output);
  }

//  @Test
//  public void solveHard() {
//    // This takes forever to run.
//    int[][] testInput = {
//            {0, 0, 0, 0, 0, 0, 0, 0, 1},
//            {0, 0, 0, 0, 0, 0, 0, 0, 2},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 3},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {5, 0, 6, 0, 0, 7, 8, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 9, 0}};
//    int[][] output = {
//            {0, 0, 0, 0, 0, 0, 0, 0, 1},
//            {0, 0, 0, 0, 0, 0, 0, 0, 2},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 4},
//            {0, 0, 0, 0, 0, 0, 0, 0, 3},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {5, 0, 6, 0, 0, 7, 8, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 0, 0},
//            {0, 0, 0, 0, 0, 0, 0, 9, 0}};
//    boolean solved = solvr.solve(testInput);
//    assertThat(solved).isFalse();
//    assertThat(testInput).isEqualTo(output);
//  }
}