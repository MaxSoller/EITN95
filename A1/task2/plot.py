import matplotlib.pyplot as plt

f = open("task2_BPrio_exp.m", "r")
xs, ys = [], []

for line in f:
    y, x = line.split(" ")
    xs.append(float(x))
    ys.append(float(y))

plt.plot(xs, ys)
plt.ylabel("Nbr of A + Nbr of B")
plt.xlabel("Time")
plt.savefig("task2_BPrio_exp.png")