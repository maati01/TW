import re

from equation import Equation

#klasa która patsuje input do odpowiedniej formy
class Parser:
    def __init__(self, path_to_input):
        self.input = self.read_input(path_to_input)
        self.alphabet = self.get_alphabet()
        self.word = self.get_word()

    def read_input(self, path_to_input):
        file = open(path_to_input, encoding="utf8")
        return file.readlines()

    #funckja zwraca przetwarzane słowo
    def get_word(self):
        return self.input[-1].replace(" ", "").strip().split("=")[-1]

    #funkcja zwraca alfabet
    def get_alphabet(self):
        alphabet_string = self.input[-2].split("=")[-1]
        alphabet = []
        for ch in alphabet_string:
            if ch.isalpha():
                alphabet.append(ch)
        return alphabet

    def get_relations(self):
        equations = []

        #pętla pozwala sparsować kolejne równania
        for j in range(len(self.input) - 2):
            equation = self.input[j].split()
            values = set()
            for i in range(2, len(equation)):
                value = re.sub('[0-9]', '', equation[i])
                if value.isalpha():
                    values.add(value)
            equations.append(Equation(equation[0][1], equation[1], values))

        dependency_relation = set()
        size = len(equations)
        all_relations = set()

        #pętla wyznacza relacje zależności
        for i in range(size):
            for j in range(size):
                if equations[j].first_value in equations[i].values_set \
                        or equations[i].first_value in equations[j].values_set or i == j:
                    dependency_relation.add((equations[i].id, equations[j].id))
                all_relations.add((equations[i].id, equations[j].id))

        #relacja niezności jest różnicą wszystkich relacji i relacji zależności
        independency_relation = all_relations - dependency_relation

        return dependency_relation, independency_relation
