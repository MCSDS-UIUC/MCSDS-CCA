# Fetch ubuntu 16.04 LTS docker image
FROM ubuntu:16.04

#Make a copy of ubuntu apt repository before modifying it. 
RUN mv /etc/apt/sources.list /sources.list
#Now change the default ubuntu apt repositry, which is VERY slow, to another mirror that is much faster. It assumes the host already has created a sources.list.
COPY sources.list /etc/apt/sources.list

#uncomment this line to find the fastest ubuntu repository at the time. Probably overkill, so disabling for now
#Note that this functionality is untested and might need debugging a bit.

# Update apt, and install Java + curl + wget on your ubuntu image.
RUN \
  apt-get update && \
  apt-get install -y curl vim wget maven expect git zip unzip libboost-dev libboost-test-dev libboost-program-options-dev libboost-filesystem-dev libboost-thread-dev libevent-dev automake libtool flex bison pkg-config g++ libssl-dev telnet net-tools && \
  apt-get install -y openjdk-8-jdk 

RUN \
  apt-get install -y python && \
  apt-get install -y python-pip

RUN pip install happybase

ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-amd64
RUN pwd
RUN curl -s "http://apache.cs.utah.edu/hadoop/common/hadoop-2.9.0/hadoop-2.9.0.tar.gz" | tar -xz -C /usr/local/
RUN cd /usr/local && ln -s ./hadoop-2.9.0 hadoop
#COPY hadoop-2.9.0.tar.gz /usr/local/
#RUN cd /usr/local/ && tar xzf hadoop-2.9.0.tar.gz && ln -s ./hadoop-2.9.0 hadoop

ENV HADOOP_PREFIX /usr/local/hadoop
ENV HADOOP_COMMON_HOME /usr/local/hadoop
ENV HADOOP_HDFS_HOME /usr/local/hadoop
ENV HADOOP_MAPRED_HOME /usr/local/hadoop
ENV HADOOP_YARN_HOME /usr/local/hadoop
ENV HADOOP_CONF_DIR /usr/local/hadoop/etc/hadoop
ENV YARN_CONF_DIR $HADOOP_PREFIX/etc/hadoop
ENV HADOOP_CLASSPATH $JAVA_HOME/lib/tools.jar
ENV PATH="/usr/local/hadoop/bin:${PATH}"

RUN sed -i "/^export JAVA_HOME/ s:.*:export JAVA_HOME=$JAVA_HOME\nexport HADOOP_PREFIX=/usr/local/hadoop\nexport HADOOP_HOME=/usr/local/hadoop\n:" $HADOOP_PREFIX/etc/hadoop/hadoop-env.sh
RUN sed -i '/^export HADOOP_CONF_DIR/ s:.*:export HADOOP_CONF_DIR=/usr/local/hadoop/etc/hadoop/:' $HADOOP_PREFIX/etc/hadoop/hadoop-env.sh


RUN chmod a+rwx -R /usr/local/hadoop/
COPY autosu /usr/local/bin
RUN chmod 777 /usr/local/bin/autosu
RUN adduser hadoopuser --disabled-password --gecos ""
RUN echo 'hadoopuser:hadooppass' | chpasswd

# Download and setup HBase
RUN curl -s "http://archive.apache.org/dist/hbase/1.4.2/hbase-1.4.2-bin.tar.gz" | tar -xz -C /usr/local/
RUN cd /usr/local && ln -s ./hbase-1.4.2 hbase
#COPY hbase-1.4.2-bin.tar.gz /usr/local/
#RUN cd /usr/local/ && tar xzf hbase-1.4.2-bin.tar.gz && ln -s ./hbase-1.4.2 hbase


ENV HBASE_HOME /usr/local/hbase
ENV PATH="/usr/local/hbase/bin:${PATH}"
RUN chmod a+rwx -R /usr/local/hbase/


# Make vim nice
RUN echo "set background=dark" >> ~/.vimrc

#COPY MP3/ /MP3/
