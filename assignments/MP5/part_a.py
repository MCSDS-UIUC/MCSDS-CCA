from pyspark import *
from pyspark.sql import SparkSession
from graphframes import *

sc = SparkContext()
spark = SparkSession.builder.appName('fun').getOrCreate()


def get_connected_components(graphframe):
    # TODO:
    # get_connected_components is given a graphframe that represents the given graph
    # It returns a list of lists of ids.
    # For example, [[a_1, a_2, ...], [b_1, b_2, ...], ...]
    # then a_1, a_2, ..., a_n lie in the same component,
    # b_1, b2, ..., b_m lie in the same component, etc
    # return [[]]
    connected_components = g.connectedComponents()
    
    comp_to_vertices_map = {}
    for row in connected_components.rdd.collect():
        #print(type(row), row, list(row.asDict().values()))
        vertice_id, comp = list(row.asDict().values())

        if comp in comp_to_vertices_map:
            comp_to_vertices_map.get(comp).append(vertice_id)
        else:
            comp_to_vertices_map[comp] = [vertice_id]

    comp_vertices_list = list(comp_to_vertices_map.values())
    return comp_vertices_list


if __name__ == "__main__":
    vertex_list = []
    edge_list = []
    with open('dataset/graph.data') as f:  # Do not modify
        for line in f:
            if line[-1]=='\n':
                line=line[:-1]
            line = line.split(' ')
            src = line[0]  # TODO: Parse src from line
            dst_list = line[1:]  # TODO: Parse dst_list from line
            vertex_list.append((src,))
            edge_list += [(src, dst) for dst in dst_list]

    vertices = spark.createDataFrame(vertex_list, ["id"])  # TODO: Create vertices dataframe
    edges = spark.createDataFrame(edge_list, ["src", "dst"])  # TODO: Create edges dataframe

    g = GraphFrame(vertices, edges)
    sc.setCheckpointDir("/tmp/connected-components")

    result = get_connected_components(g)
    for line in result:
        print(' '.join(line))
