# Run with "plotting.py" or "python3 plotting.py"
# Requires the matplotlib library


import matplotlib.pyplot as plt

sizes = ['1.000', '10.000', '100.000', '1.000.000', '10.000.000', '100.000.000']
plt.xlabel("Size of n")
plt.ylabel("Time (ms)")

plt.title("Time consumption of sorting array of size n and k=20")
a1_20 = [1.1662, 4.4762, 103.8348, 439.124, 8158.939, 141379.2444]
seq_a2_20 = [0.0902, 0.3506, 0.4311, 1.6851, 20.9167, 241.8072]
plt.plot(sizes, a1_20)
plt.plot(sizes, seq_a2_20)
plt.legend(['A1: Arrays.sort()', 'A2: Sequential insertion'], loc='upper left')
plt.show()

plt.title("Time consumption of sorting array of size n and k=100")
a1_100 = [1.7298, 5.5876, 112.3337, 438.6018, 8832.825, 161710.1742]
seq_a2_100 = [0.8437, 0.422, 0.4301, 1.9494, 21.6442, 299.641]
plt.plot(sizes, a1_100)
plt.plot(sizes, seq_a2_100)
plt.legend(['A1: Arrays.sort()', 'A2: Sequential insertion'], loc='upper left')
plt.show()

plt.title("Speedup for parallelizing A2 for different values of n")
speedup_20 = [0.08069, 0.33705, 0.31743, 1.03208, 2.22605, 1.47033]
speedup_100 = [0.57381, 0.19153, 0.20116, 0.78963, 2.03435, 1.72908]
plt.plot(sizes, speedup_20)
plt.plot(sizes, speedup_100)
plt.legend(['k = 20', 'k = 100'], loc='upper left')
plt.show()
