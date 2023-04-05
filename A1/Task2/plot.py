import matplotlib.pyplot as plt

first = open("images/task2_BPrio_const.m", "r")
second = open("images/task2_BPrio_exp.m", "r")
third = open("images/task2_APrio_const.m", "r")

files = [first, second, third]
labels = ["Constant delay", "Exponential delay", "A-jobs Priority"]

for i in range(3):
    xs, ys = [], []

    for line in files[i]:
        y, x = line.split(" ")
        xs.append(float(x))
        ys.append(float(y))

    plt.plot(xs, ys, label=labels[i])

plt.ylabel("Nbr of A + Nbr of B")
plt.xlabel("Time")
leg = plt.legend(loc='upper right')
plt.savefig("images/task2.png")