package edu.illinois.storm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/**
 * a spout that generate sentences from a file
 */
public class FileReaderSpout implements IRichSpout {
    private SpoutOutputCollector _collector;
    private TopologyContext _context;
    private String inputFile;
    private BufferedReader br = null;

    // Hint: Add necessary instance variables if needed

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this._context = context;
        this._collector = collector;

    /* ----------------------TODO-----------------------
    Task: initialize the file reader
    ------------------------------------------------- */
//        System.out.println("File Name - " + conf.get("inputFile").toString());
        try {
            this.br = new BufferedReader(new FileReader(new File(conf.get("inputFile").toString())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        this._context = context;
        this._collector = collector;

        // END

    }

    // Set input file path
    public FileReaderSpout withInputFileProperties(String inputFile) {
        this.inputFile = inputFile;
        return this;
    }

    @Override
    public void nextTuple() {

    /* ----------------------TODO-----------------------
    Task:
    1. read the next line and emit a tuple for it
    2. don't forget to add a small sleep when the file is entirely read to prevent a busy-loop
    ------------------------------------------------- */
        String sentence = null;

        try{
            sentence = br.readLine();
            if(sentence!=null){
                _collector.emit(new Values(sentence));
            }else{
                Utils.sleep(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        // END
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    /* ----------------------TODO-----------------------
    Task: define the declarer
    ------------------------------------------------- */
        declarer.declare(new Fields("word"));
        // END
    }

    @Override
    public void close() {
    /* ----------------------TODO-----------------------
    Task: close the file
    ------------------------------------------------- */
        try {
            this.br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // END

    }

    public void fail(Object msgId) {
    }

    public void ack(Object msgId) {
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
