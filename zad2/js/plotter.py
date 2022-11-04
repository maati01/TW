import numpy as np
from matplotlib import pyplot as plt


def read_result(version, conf):
    result = []
    with open(f'results/{version}_{conf}.txt') as my_file:
        for line in my_file:
            result.append(float(line.strip()))

    return result


def generate_plots(conf):
    values = conf.split("_")
    labels = [i for i in range(1, int(values[0]) + 1)]
    asym_means = read_result("asym", conf)
    both_forks_means = read_result("both_forks", conf)
    conductor_means = read_result("conductor", conf)

    x = np.arange(int(values[0]))
    width = 0.75

    fig, ax = plt.subplots()
    ax.bar(x - width / 3, asym_means, width / 3, label='asym')
    ax.bar(x, both_forks_means, width / 3, label='both_forks')
    ax.bar(x + width / 3, conductor_means, width / 3, label='conductor')

    ax.set_ylabel('average time [ms]')
    ax.set_title(f'philosophers: {values[0]}   iterations: {values[1]}')
    ax.set_xticks(x, labels)
    ax.legend()

    fig.tight_layout()

    plt.savefig(f'plots/{conf}.png')


if __name__ == '__main__':
    versions = ["asym", "both_forks", "conductor"]
    configurations = ["5_50", "5_200", "10_10", "20_5"]

    for conf in configurations:
        generate_plots(conf)
