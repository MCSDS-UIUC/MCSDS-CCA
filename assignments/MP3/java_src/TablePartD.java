//package mp3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Get;


import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class TablePartD{

    public static void main(String[] args) throws IOException {

        // TODO
        // DON' CHANGE THE 'System.out.println(xxx)' OUTPUT PART
        // OR YOU WON'T RECEIVE POINTS FROM THE GRADER

        // Instantiating Configuration class
        Configuration config = HBaseConfiguration.create();

        // Instantiating HTable class
        HTable table = new HTable(config, "powers");

        // Instantiating Get class
        Get g1 = new Get(Bytes.toBytes("row1"));
        // Reading the data
        Result result1 = table.get(g1);

        String hero = Bytes.toString(result1.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero")));
        String power = Bytes.toString(result1.getValue(Bytes.toBytes("personal"),Bytes.toBytes("power")));
        String name = Bytes.toString(result1.getValue(Bytes.toBytes("professional"),Bytes.toBytes("name")));
        String xp = Bytes.toString(result1.getValue(Bytes.toBytes("professional"),Bytes.toBytes("xp")));
        String color = Bytes.toString(result1.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color")));
        System.out.println("hero: "+hero+", power: "+power+", name: "+name+", xp: "+xp+", color: "+color);

        // Instantiating Get class
        Get g19 = new Get(Bytes.toBytes("row19"));
        // Reading the data
        Result result19 = table.get(g19);

        hero = Bytes.toString(result19.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero")));;
        color = Bytes.toString(result19.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color")));
        System.out.println("hero: "+hero+", color: "+color);

        hero = Bytes.toString(result1.getValue(Bytes.toBytes("personal"),Bytes.toBytes("hero")));;
        name = Bytes.toString(result1.getValue(Bytes.toBytes("professional"),Bytes.toBytes("name")));
        color = Bytes.toString(result1.getValue(Bytes.toBytes("custom"),Bytes.toBytes("color")));
        System.out.println("hero: "+hero+", name: "+name+", color: "+color);
    }
}
