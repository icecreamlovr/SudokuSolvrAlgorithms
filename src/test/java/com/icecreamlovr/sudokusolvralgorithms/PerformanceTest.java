package com.icecreamlovr.sudokusolvralgorithms;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class PerformanceTest {
  private SudokuSolvr solvr;

  private static class Benchmark {
    private String description;
    private long startTime = 0;
    private static final long MEGABYTE = 1024L * 1024L;

    Benchmark(String description) {
      this.description = description;
    }

    public void start() {
      startTime = System.currentTimeMillis();
    }

    public void endAndReport() {
      // Calculate elapsed time.
      long stopTime = System.currentTimeMillis();
      long elapsedTime = stopTime - startTime;

      // Calculate memory usage.
      Runtime runtime = Runtime.getRuntime();
      runtime.gc();
      long memory = runtime.totalMemory() - runtime.freeMemory();

      System.out.println(description);
      System.out.println("Elapsed time in millis: " + elapsedTime);
      System.out.println("Used memory in bytes: " + memory);
      System.out.println("Used memory in megabytes: "
              + memory / MEGABYTE);
      System.out.println();
    }
  }

  @BeforeEach
  void setup() {
    solvr = new ConstraintsUpdateAndOrderingSolvr();
  }

  @Test
  void normal1() {
    Benchmark benchmark = new Benchmark(">>>Test case: normal1");
    benchmark.start();

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

    benchmark.endAndReport();
  }

  @Test
  public void normal2() {
    Benchmark benchmark = new Benchmark(">>>Test case: normal2");
    benchmark.start();

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

    benchmark.endAndReport();
  }

  @Test
  void hard1() {
    Benchmark benchmark = new Benchmark(">>>Test case: hard1");
    benchmark.start();

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

    benchmark.endAndReport();
  }

  @Test
  void hard2() {
    Benchmark benchmark = new Benchmark(">>>Test case: hard2");
    benchmark.start();

    int[][] testInput = {
            {1, 2, 0, 4, 0, 0, 3, 0, 0},
            {3, 0, 0, 0, 1, 0, 0, 5, 0},
            {0, 0, 6, 0, 0, 0, 1, 0, 0},
            {7, 0, 0, 0, 9, 0, 0, 0, 0},
            {0, 4, 0, 6, 0, 3, 0, 0, 0},
            {0, 0, 3, 0, 0, 2, 0, 0, 0},
            {5, 0, 0, 0, 8, 0, 7, 0, 0},
            {0, 0, 7, 0, 0, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 9, 8}};
    int[][] output = {
            {1, 2, 8, 4, 6, 5, 3, 7, 9},
            {3, 7, 4, 2, 1, 9, 8, 5, 6},
            {9, 5, 6, 8, 3, 7, 1, 4, 2},
            {7, 6, 5, 1, 9, 8, 4, 2, 3},
            {2, 4, 9, 6, 7, 3, 5, 8, 1},
            {8, 1, 3, 5, 4, 2, 9, 6, 7},
            {5, 9, 2, 3, 8, 6, 7, 1, 4},
            {4, 8, 7, 9, 2, 1, 6, 3, 5},
            {6, 3, 1, 7, 5, 4, 2, 9, 8}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isTrue();
    assertThat(testInput).isEqualTo(output);

    benchmark.endAndReport();
  }

  @Test
  void hard3() {
    Benchmark benchmark = new Benchmark(">>>Test case: hard3");
    benchmark.start();

    int[][] testInput = {
            {1, 2, 0, 3, 0, 0, 0, 0, 0},
            {3, 4, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 5, 0, 0, 0, 0, 0, 0},
            {6, 0, 2, 4, 0, 0, 5, 0, 0},
            {0, 0, 0, 0, 6, 0, 0, 7, 0},
            {0, 0, 0, 0, 0, 8, 0, 0, 6},
            {0, 0, 4, 2, 0, 0, 3, 0, 0},
            {0, 0, 0, 0, 7, 0, 0, 0, 9},
            {0, 0, 0, 0, 0, 9, 0, 8, 0}};
    int[][] output = {
            {1, 2, 9, 3, 8, 5, 7, 6, 4},
            {3, 4, 8, 6, 2, 7, 1, 9, 5},
            {7, 6, 5, 9, 1, 4, 8, 2, 3},
            {6, 7, 2, 4, 9, 1, 5, 3, 8},
            {4, 8, 3, 5, 6, 2, 9, 7, 1},
            {9, 5, 1, 7, 3, 8, 2, 4, 6},
            {8, 9, 4, 2, 5, 6, 3, 1, 7},
            {2, 1, 6, 8, 7, 3, 4, 5, 9},
            {5, 3, 7, 1, 4, 9, 6, 8, 2}};
    boolean solved = solvr.solve(testInput);
    assertThat(solved).isTrue();
    assertThat(testInput).isEqualTo(output);

    benchmark.endAndReport();
  }
}
