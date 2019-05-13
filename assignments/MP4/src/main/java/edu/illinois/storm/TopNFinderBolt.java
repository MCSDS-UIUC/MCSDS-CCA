package edu.illinois.storm;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * a bolt that finds the top n words.
 */
public class TopNFinderBolt extends BaseRichBolt {
    private OutputCollector collector;
    private int N;
    private Map<String, Integer> currentTopWords = new HashMap<String, Integer>();

    // Hint: Add necessary instance variables and inner classes if needed


    @Override
    public void prepare(Map conf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        this.N = Integer.valueOf((String)conf.get("N"));

        withNProperties(this.N);
        //System.out.println("TopNFinderBolt-N : " + this.N);
    }

  public TopNFinderBolt withNProperties(int N) {
    /* ----------------------TODO-----------------------
    Task: set N
    ------------------------------------------------- */
        this.N = N;
		// End
		return this;
  }

    @Override
    public void execute(Tuple tuple) {
    /* ----------------------TODO-----------------------
    Task: keep track of the top N words
		Hint: implement efficient algorithm so that it won't be shutdown before task finished
		      the algorithm we used when we developed the auto-grader is maintaining a N size min-heap
    ------------------------------------------------- */
        String word = tuple.getStringByField("word");
        Integer count = tuple.getIntegerByField("count");

        Integer updatedCountVal = 0;
        if (this.currentTopWords.containsKey(word)) {
            updatedCountVal = this.currentTopWords.get(word) + count;
        }else {
            updatedCountVal = count;
        }

        this.currentTopWords.put(word, updatedCountVal);
        this.currentTopWords = sortByValue(this.currentTopWords, false);
        //System.out.println("currentTopWords - " + this.currentTopWords);
        collector.emit(new Values("top-N", printMap()));
        // End
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    /* ----------------------TODO-----------------------
    Task: define output fields
		Hint: there's no requirement on sequence;
					For example, for top 3 words set ("hello", "word", "cs498"),
					"hello, world, cs498" and "world, cs498, hello" are all correct
    ------------------------------------------------- */
        declarer.declare(new Fields("top-N", "values"));
        // END
    }

    public String printMap() {
        StringBuilder stringBuilder = new StringBuilder();
        int counter = 0;

        int map_len = this.currentTopWords.size();
        //System.out.println(map_len);
        for (String word : this.currentTopWords.keySet()) {

            if (counter < this.N && counter < map_len)
                stringBuilder.append(word + ", ");
            else
                break;
            counter++;
        }
        int lastCommaIndex = stringBuilder.lastIndexOf(",");
        stringBuilder.deleteCharAt(lastCommaIndex + 1);
        stringBuilder.deleteCharAt(lastCommaIndex);

        String output = stringBuilder.toString();
        //System.out.println("PrintMap - " + output);
        return output;
    }

    private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

}
