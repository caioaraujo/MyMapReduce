import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;

public class Main {

    public static class CompromiseMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        private Text word = new Text();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());

            final String[] input = value.toString().split(";");

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            final Compromise compromise = new Compromise(
                    input[0], input[1], LocalDate.parse(input[2], formatter),
                    new BigDecimal(input[3]), input[4], input[5]
            );

            //context.write();
        }
    }

    public static void main(String[] args) throws Exception {

        // Dado um arquivo como entrada, interpreta o mesmo
        // para processar suas linhas e gerar as entradas

        // Test mongo connection
        Main.connectToMongoDB();

    }

    private static void connectToMongoDB(Text compromiseJson) {
        MongoClient mongoClient = new MongoClient();

        MongoDatabase database = mongoClient.getDatabase("compromise");

        database.createCollection("bankCompromises", new CreateCollectionOptions());

        System.out.print("AAA");
    }
}
