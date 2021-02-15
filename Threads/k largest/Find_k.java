/**
 * A class that measures the runtimes of the algorithms implemented in the
 * 'k_of_n' class.
 *
 * To use:
 *
 * Compile:
 * javac k_of_n.java Find_k.java
 *
 * Run:
 * java Find_k <n> <k> (where <n> is the size of the global array and <k> is
 * the part to be sorted)
 */

import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

class Find_k {

  public static void main(String[] args) {

    int n, k, seed = 420;
    int runs = 7; // Have to be an odd number


    // Catching excpetions in the case of wrong user input.
    try {
      n = Integer.parseInt(args[0]);
      k = Integer.parseInt(args[1]);
    } catch(ArrayIndexOutOfBoundsException e) {
      System.out.println("\nCorrect use of program is 'java Find_k <n> <k>', where <n> and <k> are integers.\n");
      return;
    } catch(NumberFormatException e) {
      System.out.println("\nPlease send in integers <n> and <k>: 'java Find_k <n> <k>'.\n");
      return;
    }


    k_of_n Default = new k_of_n(n, k, seed);
    k_of_n seq = new k_of_n(n, k, seed);
    k_of_n par = new k_of_n(n, k, seed);

    // Storing the runtimes in arrays.
    double[] runtimeDefault = new double[runs];
    double[] runtimeSeq = new double[runs];
    double[] runtimePar = new double[runs];


    /*
    * Running all algorithms for 'runs' number of times, calculates their runtime
    * and confirms that they have the correct elements and structure.
    */
    System.out.println("\nTesting k_of_n with n=" + n + ", k=" + k + " and cores=" + seq.cores);

    for (int i = 0; i < runs; i++) {

      Integer[] default_a = Default.randomDefault();
      long start = System.nanoTime();
      Arrays.sort(default_a, Collections.reverseOrder());
      long end = System.nanoTime();
      runtimeDefault[i] = (end - start) / 1000000.0;

      seq.randomArray();
      start = System.nanoTime();
      seq.sortSequential();
      end = System.nanoTime();
      runtimeSeq[i] = (end - start) / 1000000.0;
      seq.isCorrect(default_a, seq.a);

      par.randomArray();
      start = System.nanoTime();
      par.sortParallel();
      end = System.nanoTime();;
      runtimePar[i] = (end - start) / 1000000.0;
      par.isCorrect(default_a, par.a);
    }


    // Finding medians, assuming 'runs' is an odd number.
    Arrays.sort(runtimeDefault);
    Arrays.sort(runtimeSeq);
    Arrays.sort(runtimePar);

    double medianDefault = runtimeDefault[runs / 2];
    double medianSeq = runtimeSeq[runs / 2];
    double medianPar = runtimePar[runs / 2];

    // Printing out the runtime and speedup for each algorithm.
    System.out.println("Median of " + runs + " runs:");
    System.out.println("\nArrays.sort()");
    System.out.println("Time\t:\t" + medianDefault + " ms");

    System.out.println("\nSequential Algorithm");
    System.out.println("Time\t:\t" + medianSeq + " ms");

    System.out.println("\nParallel Algorithm");
    System.out.println("Time\t:\t" + medianPar + " ms");
    System.out.println("Speedup\t:\t" + medianSeq / medianPar);
  }
}
