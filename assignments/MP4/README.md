# Machine Problem 4: Apache Storm
## Java Implementation
## 1. Overview

Welcome to the Storm machine practice.
The final goal of this assignment is to build a topology that finds the top N words in a given corpus.
We will build the assignment step by step on top of the topology in the tutorial.

## 2. General Requirements

All these assignments are designed to work on the **Docker image** that we provide.

# Java submission

## Overview and Requirements

This assignment is going to build on **Tutorial 4: Introduction to Storm (Java)**.
It is highly recommended that you practice that tutorial before starting this assignment.
We are going to use `Storm V1.2.2`, `Redis 5.0.3` and `Open JDK 8`.

## Set up the environment

**Step 1**: Start the "default" Docker machine that you created when following the "Tutorial: Docker installation" in week 4, run:

    docker-machine start default
    docker-machine env
    # follow the instruction to configure your shell: eval $(...)

**Step 2**: Download the Dockerfile and related files for this MP, change the current folder, build, and run the docker image, run:

    git clone https://github.com/UIUC-CS498-Cloud/MP4_docker
    cd MP4_docker
    docker build -t mp4_docker .

## Procedures

**Step 3**: Download the Java templates and change the current folder, run:

    git clone https://github.com/UIUC-CS498-Cloud/MP4_java_template MP4
    cd MP4
    docker run -it -v "$(pwd)":/mp4/solution mp4_docker

Now, the MP4 template folder has been mapped to `/mp4/solution` in the container.

**Step 4**: Finish the exercises by editing the provided template files. You need to complete the parts marked with **TODO**.
The template is just our suggestions about how to implement the application.
You can modify the template at will as long as your solution can be compiled as ran by the command we provide in this instruction.
Our auto-grade compiles and runs your submission in the same way.

**Step 5**: After finishing the assignment, submit the zip file containing the `MP4` folder.
The zip file structure should look like:

    Archive:  MP4.zip
      Length      Date    Time    Name
    ---------  ---------- -----   ----
            0  03-13-2019 17:18   MP4/
        75314  03-13-2019 17:18   MP4/dump.rdb
         4572  03-13-2019 17:18   MP4/pom.xml
           19  03-13-2019 17:18   MP4/README.md
         4415  03-13-2019 17:18   MP4/dependency-reduced-pom.xml
            0  03-13-2019 17:18   MP4/src/
            0  03-13-2019 17:18   MP4/src/main/
            0  03-13-2019 17:18   MP4/src/main/resources/
         2946  03-13-2019 17:18   MP4/src/main/resources/part_d_topology.yaml
         2698  03-13-2019 17:18   MP4/src/main/resources/part_c_topology.yaml
         2494  03-13-2019 17:18   MP4/src/main/resources/part_b_topology.yaml
         3215  03-13-2019 17:18   MP4/src/main/resources/part_a_topology.yaml
            0  03-13-2019 17:18   MP4/src/main/java/
            0  03-13-2019 17:18   MP4/src/main/java/edu/
            0  03-13-2019 17:18   MP4/src/main/java/edu/illinois/
            0  03-16-2019 21:31   MP4/src/main/java/edu/illinois/storm/
         1533  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/RandomSentenceSpout.java
         4198  03-16-2019 21:17   MP4/src/main/java/edu/illinois/storm/TopNFinderBolt.java
         1199  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/TopNStoreMapper.java
         1210  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/WordCountStoreMapper.java
         1255  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/WordCountBolt.java
         1718  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/NormalizerBolt.java
         1109  03-13-2019 17:18   MP4/src/main/java/edu/illinois/storm/SplitSentenceBolt.java
         3021  03-16-2019 21:31   MP4/src/main/java/edu/illinois/storm/FileReaderSpout.java

# Exercise A: Simple Word Count Topology

In this exercise, you are going to build a simple word counter that counts the words a random sentence spout generates.
This first exercise is similar to **Tutorial 4: Introduction to Storm**.

In this exercise, we are going to use the `RandomSentenceSpout` class as the spout, the `SplitSentenceBolt` class to split sentences into words, `WordCountBolt` class to count the words and RedisStoreBolt to save the output in Redis.
The necessary knowledge has already been covered in the Tutorial.

For the splitter, we split the sentences at any characters other than numbers or letters (`[^a-zA-Z0-9-]`).

To save the output to redis, you should save field-value pairs `({word}, {count})` in hashes `partAWordCount`.
We've provided you the template in `src/main/java/edu/illinois/storm/WordCountStoreMapper.java`.
To make it clear, in the auto-grader, we retrieve your answer from Redis by executing script equivalent to:

    # for example, we want to check your count for word "apple"
    redis-cli -a uiuc_cs498_mp4 HGET partAWordCount apple

You may find the hashes name for the other parts in their template topology yaml files we provide.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.
Keep in mind that the most important task for this part is to master how to set up a storm application.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_a_topology.yaml -s 15000

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently.

# Exercise B: Input Data from a File

As can be seen, the spout used in the topology of Exercise A is generating random sentences from a predefined set in the spout’s class.
However, we want to count words for a given corpus.
Thus, in this exercise, you are going to create a new spout that reads data from an input file and emits each line as a tuple.
Remember to put a 1-second sleep after reading the whole file to avoid a busy loop.

To make the implementation easier, we have provided a boilerplate for the spout needed in the following file: `src/main/java/edu/illinois/storm/FileReaderSpout.java`.

After finishing the implementation of `FileReaderSpout `class, you have to wire up the topology with this new spout.

To make the implementation easier, we have provided a boilerplate for the topology needed in the following file: `src/main/resources/part_b_topology.yaml`.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

**NOTE**: You probably want to set the number of executors of the spout to “1” so that you don’t read the input file more than once.
However, you have the freedom to have a different implementation as long as the result is correct.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_b_topology.yaml -s 15000

Note that you'll need to set the path of the input file in `part_b_topology.yaml` and then put the input file in the right place.
We've covered how to pass configuration to spout. You can think about how you can set the input file path in `part_b_topology.yaml`.
We will put the input data at `/tmp/data.txt` in the auto-grader. You should modify your path accordingly before pack your solution.

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently and maybe develop some simple tools to help you build some tests.

# Exercise C: Normalizer Bolt

The application we developed in Exercise B counts the words “Apple” and “apple” as two different words.
However, if we want to find the top N words, we have to count these words the same.
Additionally, we don’t want to take common English words into consideration.

Therefore, in this part, we are going to normalize the words by adding a normalizer bolt that gets the words from the splitter, normalizes them, and then sends them to the counter bolt.
The responsibility of the normalizer is to:

1. Make all input words lowercase.
2. Remove common English words.

To make the implementation easier, we have provided a boilerplate for the normalizer bolt in the following file: `src/main/java/edu/illinois/storm/NormalizerBolt.java`.

There is a list of common words to filter in this class, so please make sure you use this exact list to in order to receive the maximum points for this part.
After finishing the implementation of this class, you have to wire up the topology with this bolt added to the topology.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_c_topology.yaml -s 15000

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently and maybe develop some simple tools to help you build some tests.

# Exercise D: Top N Words

In this exercise, we are going to add a new bolt which uses the output of the count bolt to keep track of and report the top N words.
Upon receipt of a new count from the count bolt, it updates the top N words and emits top N words set anytime it changes.

To output the top N words set, you should use ", " connect all top words.
You don't need to worry about the sequence.
The result should contain and only contain the top N words.
For example, if 3-top words are "blue", "red" and "green", "blue, red, green", "red, blue, green" are all correct answer.

To save the output to Redis, you should save field-value pairs ("top-N", {top N words string}") in hashes `partDTopN`.
It's not the best way to save a set in Redis, but Redis is not the key point for this assignment.
So we decided to make it easier for you to implement by keeping using hashes in part d.
We've provided you the template in `src/main/java/edu/illinois/storm/TopNStoreMapper.java`.
To make it clear, in the auto-grader, we retrieve your answer from Redis by executing script equivalent to:

    redis-cli -a uiuc_cs498_mp4 HGET partDTopN top-N
    # the output for example above should be:
    # "blue, red, green"

After finishing the implementation of this class, you have to wire up the topology with this bolt added to the topology.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.
For this part, different algorithm will have huge different performance.
15s is not a strict restriction, but your solution may failed to finish within 15s on Coursera even though it works on your local environment.
We suggest you to refine your algorithm to make it more efficient if you failed in this part while you get correct answer locally.
Algorithm with time complexity of `O(nN)` seems to be save to pass where n is the number of total words and N is the amount of top words you need track.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_d_topology.yaml -s 15000

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently and maybe develop some simple tools to help you build some tests.

## Notes
p://storm.apache.org/releases/2.0.0-SNAPSHOT/flux.html

1. Git clone https://github.com/UIUC-CS498-Cloud/MP4_docker.
2. Cd MP4_docker
3. docker build -t mp4_docker_image.v1 .
4. docker run -it -v "$(pwd)":/mp4/solution mp4_docker_image.v1
    


https://storm.apache.org/releases/2.0.0-SNAPSHOT/storm-redis.html
https://piazza.com/class/jqz0r68mx9863m?cid=746#


Qt- https://piazza.com/class/jqz0r68mx9863m?cid=783#

{the=4, seven=2, and=2, years=1, with=1, white=1, two=1, snow=1, score=1, over=1, nature=1, moon=1, keeps=1, jumped=1, i=1, four=1, dwarfs=1, doctor=1, day=1, cow=1, away=1, at=1, apple=1, an=1, am=1, ago=1, a=1}

{the=4, seven=2, and=2, years=1, with=1, white=1, two=1, snow=1, score=1, over=1, nature=1, moon=1, keeps=1, jumped=1, i=1, four=1, dwarfs=1, doctor=1, day=1, cow=1, away=1, at=1, apple=1, an=1, am=1, ago=1, a=1}

