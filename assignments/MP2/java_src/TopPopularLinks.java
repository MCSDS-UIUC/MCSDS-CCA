import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.lang.Integer;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class TopPopularLinks extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new TopPopularLinks(), args);
        System.exit(res);
    }

    @Override
    public int run(String[] args) throws Exception {
        // TODO
        Configuration conf = this.getConf();
        FileSystem fs = FileSystem.get(conf);
        Path tmpPath = new Path("./tmp");
        fs.delete(tmpPath, true);

        Job jobA = Job.getInstance(conf, "Link Count");
        jobA.setOutputKeyClass(IntWritable.class);
        jobA.setOutputValueClass(IntWritable.class);

        jobA.setMapperClass(LinkCountMap.class);
        jobA.setReducerClass(LinkCountReduce.class);

        FileInputFormat.setInputPaths(jobA, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobA, tmpPath);

        jobA.setJarByClass(TopPopularLinks.class);
        jobA.waitForCompletion(true);

        Job jobB = Job.getInstance(conf, "Top Titles Statistics");
        jobB.setOutputKeyClass(IntWritable.class);
        jobB.setOutputValueClass(IntWritable.class);

        jobB.setMapOutputKeyClass(NullWritable.class);
        jobB.setMapOutputValueClass(IntArrayWritable.class);

        jobB.setMapperClass(TopLinksMap.class);
        jobB.setReducerClass(TopLinksReduce.class);
        jobB.setNumReduceTasks(1);

        FileInputFormat.setInputPaths(jobB, tmpPath);
        FileOutputFormat.setOutputPath(jobB, new Path(args[1]));

        jobB.setInputFormatClass(KeyValueTextInputFormat.class);
        jobB.setOutputFormatClass(TextOutputFormat.class);

        jobB.setJarByClass(TopPopularLinks.class);
        return jobB.waitForCompletion(true) ? 0 : 1;
    }

     public static class IntArrayWritable extends ArrayWritable {
        public IntArrayWritable() {
            super(IntWritable.class);
        }

        public IntArrayWritable(Integer[] numbers) {
            super(IntWritable.class);
            IntWritable[] ints = new IntWritable[numbers.length];
            for (int i = 0; i < numbers.length; i++) {
                ints[i] = new IntWritable(numbers[i]);
            }
            set(ints);
        }
    }

    public static class LinkCountMap extends Mapper<Object, Text, IntWritable, IntWritable> {
        // TODO
    	@Override
        protected void setup(Context context) throws IOException,InterruptedException {
    		
    	}

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {
        	String line = value.toString();
        	String lineArr[] = line.split(":");
        	String lineKey = lineArr[0];
        	String lineValue = lineArr[1];
        	
        	StringTokenizer tokenizer = new StringTokenizer(lineValue, " ");
        	
        	context.write(new IntWritable(Integer.parseInt(lineKey)), new IntWritable(0));
			while (tokenizer.hasMoreTokens()) {
				String nextToken = tokenizer.nextToken().trim();
				context.write(new IntWritable(Integer.parseInt(nextToken)), new IntWritable(1));
			}
		}
    }

    public static class LinkCountReduce extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
    	// TODO
		@Override
		protected void reduce(IntWritable key,Iterable<IntWritable> values,Context context)
				throws IOException, InterruptedException {
	       	int sum = 0;
        	for(IntWritable value : values){
        		sum += value.get();
        	}
        	context.write(key, new IntWritable(sum));
		}
    }

    public static class TopLinksMap extends Mapper<Text, Text, NullWritable, IntArrayWritable> {
        Integer N;
        private TreeSet <TopPopLinksPair< Integer, Integer >> countToWordMap = new TreeSet <TopPopLinksPair< Integer, Integer >> ();
        
        @Override
        protected void setup(Context context) throws IOException,InterruptedException {
            Configuration conf = context.getConfiguration();
            this.N = conf.getInt("N", 10);
        }
        // TODO

		@Override
		protected void map(Text key,Text value,Context context)
				throws IOException, InterruptedException {
			Integer sumValue = Integer.parseInt(value.toString());
			Integer keyValue = Integer.parseInt(key.toString());
			countToWordMap.add(new TopPopLinksPair< Integer, Integer >(sumValue, keyValue));
			if (countToWordMap.size() > this.N) {
				countToWordMap.remove(countToWordMap.first());
			}
		}

		@Override
		protected void cleanup(
				Context context)
				throws IOException, InterruptedException {
			for (TopPopLinksPair< Integer, Integer > item: countToWordMap) {
				Integer[] integers = {
					item.second,
					item.first
				};
				IntArrayWritable val = new IntArrayWritable(integers);
				context.write(NullWritable.get(), val);
			}
		}
    }

    public static class TopLinksReduce extends Reducer<NullWritable, IntArrayWritable, IntWritable, IntWritable> {
        Integer N;
        private TreeSet <TopPopLinksPair< Integer, Integer >> countToWordMap = new TreeSet <TopPopLinksPair< Integer, Integer >> ();
		
        @Override
        protected void setup(Context context) throws IOException,InterruptedException {
            Configuration conf = context.getConfiguration();
            this.N = conf.getInt("N", 10);
        }
        // TODO

		@Override
		protected void reduce(NullWritable key,Iterable<IntArrayWritable> values,
				Context context)
				throws IOException, InterruptedException {
			
			for (IntArrayWritable val: values) {
//				Integer[] pair = (Integer[]) val.toArray();
				String[] strings = val.toStrings();
				
				Integer word = Integer.parseInt(strings[0]);
				Integer count = Integer.parseInt(strings[1]);
				
				countToWordMap.add(new TopPopLinksPair< Integer, Integer >(count, word));
				if (countToWordMap.size() > N) {
					countToWordMap.remove(countToWordMap.first());
				}
			}
			for (TopPopLinksPair< Integer, Integer > item: countToWordMap) {
				IntWritable word = new IntWritable(item.second);
				IntWritable value = new IntWritable(item.first);
				context.write(word, value);
			}
		}
    }
}


class TopPopLinksPair<A extends Comparable<? super A>,
        B extends Comparable<? super B>>
        implements Comparable<TopPopLinksPair<A, B>> {

    public final A first;
    public final B second;

    public TopPopLinksPair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static <A extends Comparable<? super A>,
            B extends Comparable<? super B>>
    TopPopLinksPair<A, B> of(A first, B second) {
        return new TopPopLinksPair<A, B>(first, second);
    }

    @Override
    public int compareTo(TopPopLinksPair<A, B> o) {
        int cmp = o == null ? 1 : (this.first).compareTo(o.first);
        return cmp == 0 ? (this.second).compareTo(o.second) : cmp;
    }

    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second);
    }

    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TopPopLinksPair))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((TopPopLinksPair<?, ?>) obj).first)
                && equal(second, ((TopPopLinksPair<?, ?>) obj).second);
    }

    private boolean equal(Object o1, Object o2) {
        return o1 == o2 || (o1 != null && o1.equals(o2));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }
}