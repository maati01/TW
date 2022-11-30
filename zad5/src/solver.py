import re

from graphviz import Digraph

from parser import Parser


class Solver:
    def __init__(self):
        pass

    #funkcja tworząca graf zależności
    @staticmethod
    def prepare_graph(dependency_relation, word, path_to_file, case_name):
        graph = Digraph('dependency graph', filename=f'output/{case_name}/dependency_graph.dot')
        size = len(word)

        #macierz która odpowiada krawędziom w grafie
        matrix_graph = [[0 for _ in range(size)] for _ in range(size)]

        #wyznaczenie krawędzi
        for i in range(size):
            for j in range(i + 1, size):
                if i != j and (word[i], word[j]) in dependency_relation:
                    matrix_graph[i][j] = 1

        #usunięcie zbędnych krawędzi
        for i in range(size):
            for j in range(size):
                if matrix_graph[i][j]:
                    for k in range(size):
                        if matrix_graph[j][k]:
                            matrix_graph[i][k] = 0

        #dodanie krawędzi do grafu
        for i in range(size):
            for j in range(size):
                if matrix_graph[i][j]:
                    graph.edge(str(i), str(j))

        #nadanie wierzchołkom etykiet
        for i, ch in enumerate(word):
            graph.node(name=str(i), label=ch)

        graph.view()

    def run(self, path_to_file):
        case_name = re.split('[/.]', path_to_file)[1]
        file_to_save = open(f'output/{case_name}/result.txt', 'w')

        parser = Parser(path_to_file)
        dependency_relation, independency_relation = parser.get_relations()
        print("D: ", dependency_relation, file=file_to_save)
        print("I: ", independency_relation, file=file_to_save)
        alphabet = parser.alphabet
        word = parser.word
        print("Alphabet: ", alphabet, file=file_to_save)
        print("Word: ", word, file=file_to_save)

        #zostają stworzone stosy dla każdej ltiery alfabetu
        #słowo czytane jest od prawej do lewej
        #litera zostaje dodana na opowiadający jej stos
        #dla każdej litery będącej w relacji z obecnie przetwarzaną literą zostaje dodany pusty znacznik '*'
        stacks = {ch: [] for ch in alphabet}
        for ch1 in word[::-1]:
            stacks[ch1].append(ch1)
            for ch2 in alphabet:
                if ch1 != ch2 and (ch1, ch2) in dependency_relation:
                    stacks[ch2].append('*')

        #tworzenie postaci normalnej Foaty
        #zostają zdjęte wszystkie górne liter ze stosów
        #jeśli wszystkie stosy są puste pętla zostaje przerwana
        #ze zdjętych liter zostaje utworzona klasa Foaty
        #dla przetwarzanej litery zostają zdjęte ze stosów odpowiadające jej puste znaczniki
        foata_normal_form = []
        while True:
            top_values = []
            is_running = False
            for s in stacks.values():
                if len(s) > 0:
                    is_running = True
                    if s[-1] != '*':
                        top_values.append(s[-1])

            if not is_running:
                break

            for ch1 in top_values:
                stacks[ch1].pop()
                for ch2 in alphabet:
                    if ch1 != ch2 and (ch1, ch2) in dependency_relation:
                        stacks[ch2].pop()

            foata_normal_form.append(''.join(top_values))

        print("FNF: ", '(' + ')('.join(foata_normal_form) + ')', file=file_to_save)

        self.prepare_graph(dependency_relation, word, path_to_file,case_name)
