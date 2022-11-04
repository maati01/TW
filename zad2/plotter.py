import sys

import numpy as np
from matplotlib import pyplot as plt


def read_result(version, conf, path):
    result = []
    with open(f'{path}results/{version}_{conf}.txt') as my_file:
        for line in my_file:
            result.append(float(line.strip()))

    return result


def generate_plots(conf, path, isJava):
    values = conf.split("_")
    labels = [i for i in range(1, int(values[0]) + 1)]
    if not isJava: asym_means = read_result("asym", conf, path)
    both_forks_means = read_result("both_forks", conf, path)
    conductor_means = read_result("conductor", conf, path)

    x = np.arange(int(values[0]))
    width = 0.75

    fig, ax = plt.subplots()
    if not isJava: ax.bar(x - width / 3, asym_means, width / 3, label='asym')
    ax.bar(x, both_forks_means, width / 3, label='both_forks')
    ax.bar(x + width / 3, conductor_means, width / 3, label='conductor')

    ax.set_ylabel('average time [ms]')
    ax.set_title(f'philosophers: {values[0]}   iterations: {values[1]}')
    ax.set_xticks(x, labels)
    ax.legend()

    fig.tight_layout()

    plt.savefig(f'{path}plots/{conf}.png')


if __name__ == '__main__':
    args = sys.argv
    arg = args[1]

    versions = ["both_forks", "conductor"]

    if arg not in ["js", "java"] or len(args) != 2:
        print("Invalid argument!")
        exit(1)

    isJava = False
    if arg == "js":
        versions += "asym"
        path = "js/"
    else:
        path = "java/src/main/java/agh/tw/"
        isJava = True

    configurations = ["5_50", "5_200", "10_10", "20_5", "15_100"]

    for conf in configurations:
        generate_plots(conf,path, isJava)
