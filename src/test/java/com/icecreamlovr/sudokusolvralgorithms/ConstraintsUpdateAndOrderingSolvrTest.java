package com.icecreamlovr.sudokusolvralgorithms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConstraintsUpdateAndOrderingSolvrTest {

  private SudokuSolvr solvr;

  @BeforeEach
  void setup() {
    solvr = new ConstraintsUpdateAndOrderingSolvr();
  }

  @Test
  void validate() {
  }

  @Test
  void solve() {
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
  public void solveHard() {
    int[][] testInput = {
            {0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 6, 0, 0, 7, 8, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 9, 0}};
    int[][] output = {
            {0, 0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 3},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 6, 0, 0, 7, 8, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 9, 0}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isFalse();
    assertThat(testInput).isEqualTo(output);
  }

  @Test
  public void solveHard2() {
    int[][] testInput = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 3, 0, 8, 5},
            {0, 0, 1, 0, 2, 0, 0, 0, 0},
            {0, 0, 0, 5, 0, 7, 0, 0, 0},
            {0, 0, 4, 0, 0, 0, 1, 0, 0},
            {0, 9, 0, 0, 0, 0, 0, 0, 0},
            {5, 0, 0, 0, 0, 0, 0, 7, 3},
            {0, 0, 2, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 4, 0, 0, 0, 9}};
    int[][] output = {
            {9, 8, 7, 6, 5, 4, 3, 2, 1},
            {2, 4, 6, 1, 7, 3, 9, 8, 5},
            {3, 5, 1, 9, 2, 8, 7, 4, 6},
            {1, 2, 8, 5, 3, 7, 6, 9, 4},
            {6, 3, 4, 8, 9, 2, 1, 5, 7},
            {7, 9, 5, 4, 6, 1, 8, 3, 2},
            {5, 1, 9, 2, 8, 6, 4, 7, 3},
            {4, 7, 2, 3, 1, 9, 5, 6, 8},
            {8, 6, 3, 7, 4, 5, 2, 1, 9}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isTrue();
    assertThat(testInput).isEqualTo(output);
  }

  @Test
  public void solveHard3() {
    int[][] testInput = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 3, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isTrue();
  }
}