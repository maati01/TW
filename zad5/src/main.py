import sys

from solver import Solver

if __name__ == '__main__':
    solver = Solver()
    solver.run(sys.argv[1])