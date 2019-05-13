//package mp3;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;

import org.apache.hadoop.hbase.TableName;

import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class TablePartF{

   public static void main(String[] args) throws IOException {

        // TODO
        // DON' CHANGE THE 'System.out.println(xxx)' OUTPUT PART
        // OR YOU WON'T RECEIVE POINTS FROM THE GRADER

      // Instantiating Configuration class
      Configuration config = HBaseConfiguration.create();

      // Instantiating HTable class
      HTable table = new HTable(config, "powers");

      // Instantiating the Scan class
      Scan scan = new Scan();

      // Scanning the required columns
      scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("hero"));
      scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("power"));
      scan.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("name"));
      scan.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("xp"));
      scan.addColumn(Bytes.toBytes("custom"), Bytes.toBytes("color"));

      ResultScanner scanner = table.getScanner(scan);

      for (Result result = scanner.next(); result != null; result = scanner.next()) {
         String name = new String(result.getValue(Bytes.toBytes("professional"), Bytes.toBytes("name")));
         String power = new String(result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("power")));
         String color = new String(result.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

//         System.out.println(new String(result.getRow()));
         ResultScanner scanner1 = table.getScanner(scan);
         for (Result result1 = scanner1.next(); result1 != null; result1 = scanner1.next()) {
//            System.out.println(new String(result1.getRow()));

            String name1 = new String(result1.getValue(Bytes.toBytes("professional"), Bytes.toBytes("name")));
            String power1 = new String(result1.getValue(Bytes.toBytes("personal"), Bytes.toBytes("power")));
            String color1 = new String(result1.getValue(Bytes.toBytes("custom"), Bytes.toBytes("color")));

            if (color.equals(color1) && !name.equals(name1))
               System.out.println(name + ", " + power + ", " + name1 + ", " + power1 + ", "+color);
         }
         scanner1.close();
      }

      //

      //closing the scanner
      scanner.close();


   }
}