from pyspark import SparkContext
from numpy import array
from pyspark.mllib.clustering import KMeans, KMeansModel

############################################
#### PLEASE USE THE GIVEN PARAMETERS     ###
#### FOR TRAINING YOUR KMEANS CLUSTERING ###
#### MODEL                               ###
############################################

NUM_CLUSTERS = 4
SEED = 0
MAX_ITERATIONS = 100
INITIALIZATION_MODE = "random"

cars_rdd = None

sc = SparkContext()


def get_clusters(data_rdd, num_clusters=NUM_CLUSTERS, max_iterations=MAX_ITERATIONS,
                 initialization_mode=INITIALIZATION_MODE, seed=SEED):
    # TODO:
    # Use the given data and the cluster pparameters to train a K-Means model
    # Find the cluster id corresponding to data point (a car)
    # Return a list of lists of the titles which belong to the same cluster
    # For example, if the output is [["Mercedes", "Audi"], ["Honda", "Hyundai"]]
    # Then "Mercedes" and "Audi" should have the same cluster id, and "Honda" and
    # "Hyundai" should have the same cluster id

    clusters = KMeans.train(data_rdd, num_clusters, maxIterations=max_iterations, initializationMode=INITIALIZATION_MODE, seed=seed)

    cluster_carlist = {}
    for cluster_no, car in zip( data_rdd.map(clusters.predict).collect(), cars_rdd.collect()):

        if cluster_no in cluster_carlist:
            cluster_carlist[cluster_no].append(car[0])
        else:
            cluster_carlist[cluster_no] = [car[0]]
        
    return list(cluster_carlist.values())


if __name__ == "__main__":
    f = sc.textFile("dataset/cars.data")

    # TODO: Parse data from file into an RDD
    #data_rdd = f.map(lambda line: array([float(x) for x in line[1:].split(' ')]))
    data_rdd = f.map(lambda line: array([float(x) for x in line.split(',')[1:]]))
    cars_rdd = f.map(lambda line: line.split(',')[:1])
    
    clusters = get_clusters(data_rdd)

    for cluster in clusters:
        print(','.join(cluster))
