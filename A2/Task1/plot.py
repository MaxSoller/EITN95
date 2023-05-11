import matplotlib.pyplot as plt
import numpy as np
import math

def confidenceInterval(customers, filename):
    N = len(customers)
    mean = np.mean(customers)
    var = np.sum(pow((customers - mean), 2)) / N
    std = np.sqrt(var)
    confidence_interval = 1.96 * (std / np.sqrt(N))

    print("Mean:", mean, "var:", var, "std:", std)
    print("Confidence interval for ", filename, ": ", confidence_interval)


filename = "data/task1_5"

file = open(filename + ".txt", "r")

xs, ys = [], []

for line in file:
    y, x = line.strip().split(", ")
    xs.append(float(x))
    ys.append(float(y))


plt.plot(xs, ys)


plt.ylabel("Customers")
plt.xlabel("Time")
plt.savefig(filename + ".png")

confidenceInterval(ys, filename)


