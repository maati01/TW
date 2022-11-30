#klasa pomocnicza, która posiada wartości dla przetwarzanego równania
class Equation:
    def __init__(self, id, first_value, values):
        self.id = id
        self.first_value = first_value
        self.values_set = values