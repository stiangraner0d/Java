/*
 * A class containing one sequential algorithm and one parallel algorithm
 * which sorts the k largest integers in an integer array of size n.
 */

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;

class k_of_n {

  int[] a;
  int n, k, seed;
  int cores = Runtime.getRuntime().availableProcessors();
  CyclicBarrier cb = new CyclicBarrier(cores + 1);

  /*
   * k_of_n constructor initialiszes 'n', 'k', 'seed' and sets the global array 'a'.
   * @param n     The size of the array 'a'.
   * @param k     The size of the part of 'a' that will be sorted.
   * @param seed  An integer that determines the random array sequence.
   */
  k_of_n (int n, int k, int seed) {
    this.n = n;
    this.k = k;
    this.seed = seed;
    randomArray();
  }


  /*
  * Worker-thread that finds numbers larger than a[k-1] in a given part of 'a'
  * and sorts them into a[0...k-1].
  */
  class Worker implements Runnable {

    int ind, start, end, localMin;

    /*
     * @param ind   The id of the thread (not used, but good practice to have).
     * @param start The first index the thread is to check.
     * @param end   The last index the thread is to check.
     */
    public Worker(int ind, int start, int end) {
      this.ind = ind;
      this.start = start;
      this.end = end;
    }

    public void run() {
      localMin = a[k-1];

      for (int i = start; i < end; i++) {
        if (localMin < a[i]) {
          updateArray(i);
          localMin = a[k-1];
        }
      }

      try {
        cb.await();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }


  /*
   * Updates the global array 'a' to sort a number into a[0...k-1].
   * @param num   The number to sort into the k largest values in a.
   */
  synchronized void updateArray(int num) {
    int j = k-1;

    while (j >= 0 && a[num] > a[j]) {
      a[j+1] = a[j];
      j--;
    }
    a[j+1] = a[num];
  }


  // Sorts the k largest elements in the global array 'a' in parallel.
  void sortParallel() {
    int sizeOfSegment = (a.length - k) / cores;
    insertSort(0, k-1);

    for (int i = 0; i < cores - 1; i++) {
      (new Thread(new Worker(i, (i * sizeOfSegment)+k, ((i + 1) * sizeOfSegment)+k))).start();
    }
    (new Thread(new Worker(cores - 1, ((cores - 1) * sizeOfSegment)+k, a.length))).start();

    try {
      cb.await();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }


  // Sequentially sorts the k largest elements in the global array 'a'.
  void sortSequential() {
    insertSort(0, k-1);

    for (int i = k; i < a.length; i++) {
      if (a[i] > a[k-1]) {
        int j = k-1;

        while (j >= 0 && a[i] > a[j]) {
          a[j+1] = a[j];
          j--;
        }
        a[j+1] = a[i];
      }
    }
  }


  // Sorts a[left...right] in descending order with the insertion algorithm.
  void insertSort(int left, int right) {
    int i, swap;

    for (int j = left; j < right; j++) {
      swap = a[j + 1];
      i = j;

      while (i >= left && a[i] < swap) {
        a[i + 1] = a[i];
        i--;
      }
      a[i + 1] = swap;
    }
  }


  // Raises an error if the k first elements of arrays 'x' and 'y' does not match.
  void isCorrect(Integer[] x, int[] y) {
    for (int i = 0; i < k; i++) {
      if (x[i] != y[i]) {
        throw new java.lang.Error("\nERROR: \tThe k first elements does not match.");
      }
    }
  }


  // Places numbers from a random number sequence inside the global array 'a'.
  void randomArray() {
    Random rd = new Random(seed);
    a = new int[n];

    for (int i = 0; i < n; i++) {
      a[i] = rd.nextInt(n);
    }
  }

  // Creates a new random Integer array for Default Arrays.sort().
  Integer[] randomDefault() {
    Random rd = new Random(seed);
    Integer[] Default = new Integer[n];

    for (int i = 0; i < n; i++) {
      Default[i] = rd.nextInt(n);
    }
    return Default;
  }
}
