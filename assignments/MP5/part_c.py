from pyspark import *
from pyspark.sql import SparkSession
from graphframes import *

sc = SparkContext()
spark = SparkSession.builder.appName('fun').getOrCreate()


def get_shortest_distances(graphframe, dst_id):
    # TODO
    # Find shortest distances in the given graphframe to the vertex which has id `dst_id`
    # The result is a dictionary where key is a vertex id and the corresponding value is
    # the distance of this node to vertex `dst_id`.

    results = g.shortestPaths(landmarks=[dst_id])
    #results.select("id", "distances").show()
    ret_dict = {}

    for i in results.select("id", "distances").collect():
        # print(i)
        vertice_id, distance = list(i.asDict().values())

        if dst_id in distance:
            # print(vertice_id, distance['1'])
            ret_dict[vertice_id] =  distance['1']
        else:
            # print(vertice_id, -1)
            ret_dict[vertice_id] =  -1

    return ret_dict


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
    sc.setCheckpointDir("/tmp/shortest-paths")

    # We want the shortest distance from every vertex to vertex 1
    for k, v in get_shortest_distances(g, '1').items():
        print(k, v)
