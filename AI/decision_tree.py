from sklearn.tree import export_graphviz
import numpy as np
import pandas as pd
import re
import xgboost as xgb
import seaborn as sns
import matplotlib.pyplot as plt

from sklearn import tree
import json
from functools import reduce
import math
import copy


def cal_avg_entropy(dataset, index, classes):
    feature_values = list(set(row[index] for row in dataset))
    n_set = len(dataset)
    avg_entropy = 0
    branchs = {}
    for value in feature_values:
        subset = list(filter(lambda x: x[index] == value, dataset))
        n_subset = len(subset)
        quantities = [0]*len(classes)
        for row in subset:
            label = classes.index(row[-1])
            quantities[label] += 1
        branchs[value] = quantities
        entropy = -reduce(lambda x, y: x + (0 if y == 0 else y
                          * np.log2(y/n_subset)), quantities, 0)
        avg_entropy += entropy / n_set
    return avg_entropy, branchs


def get_split(dataset, classes, features):
    if (len(dataset[0]) == 1):
        return {}
    best_value = 999
    result = {}
    best_index = 0
    branchs = {}
    for index in range(len(dataset[0]) - 1):
        avg_entropy, result_branchs = cal_avg_entropy(dataset, index, classes)
        if (avg_entropy < best_value):
            best_value = avg_entropy
            branchs = result_branchs
            best_index = index
    result["question"] = features[best_index]
    for key, value in branchs.items():
        subset = []
        for row in dataset:
            if (row[best_index] == key):
                subset.append(np.delete(row, best_index))

        result[key] = get_split(subset, classes,
                                np.delete(features, best_index))
        temp = copy.deepcopy(classes)

        def sortBy(x):
            return value[classes.index(x)]
        temp.sort(key=sortBy, reverse=True)
        len_sol = 0
        for x in value:
            if x != 0:
                len_sol += 1
        result[key]["values"] = temp[:len_sol]
    return result


def build_tree(dataset, features):
    classes = list(set(row[-1] for row in dataset))
    return get_split(dataset, classes, features)


def predict(tree, data, features):
    if "question" not in tree:
        return tree["values"][0]
    question = tree["question"]
    answer = data[features.index(question)]
    if answer not in tree:
        return tree["values"][0]
    return predict(tree[answer], data, features)


def cal_accuracy(tree, test, features):
    sum = 0
    for row in test:
        result = predict(tree, row, features)
        if result == row[-1]:
            sum += 1
    return sum / len(test)


dataset = pd.read_excel("dataset.xlsx")
data = list(dataset.to_numpy())
split = math.floor(len(data)*0.2)
train = data[split:]
test = data[:split]

features = list(dataset.columns.values)
tree = build_tree(train, features)
with open('../frontend/src/utils/model/decisionTree.json', 'w', encoding='utf8') as json_file:
    json.dump(tree, json_file, ensure_ascii=False)

print("Accuracy:", cal_accuracy(tree, test, features))
