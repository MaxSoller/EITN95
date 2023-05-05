import matplotlib.pyplot as plt
import numpy as np

def confidenceInterval(xs, filename):
    N = len(xs)
    mean = np.mean(xs)
    var = np.sum((xs - mean)**2) / N
    std = np.sqrt(var)

    confidence_interval = 1.96 * (std / np.sqrt(N))
    print("Confidence interval for ", filename, ": ", confidence_interval)


filename = "data/task1_6"

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

confidenceInterval(xs, filename)


