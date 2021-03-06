package mapreduce;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.bson.Document;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Main {

    public static class InputDataMapper
            extends Mapper<Object, Text, Text, Text> {

        public static final Log log = LogFactory.getLog(InputDataMapper.class);

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            final String[] input = value.toString().split(";");

            log.info("Input: "+ input);

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            final Compromise compromise = new Compromise(
                    input[0], input[1], LocalDate.parse(input[2], formatter),
                    new BigDecimal(input[3]), input[4], input[5]
            );

            final Gson gson = new Gson();
            final Text jsonCompromise = new Text(gson.toJson(compromise));

            final Text uuid = new Text(UUID.randomUUID().toString());

            log.info("Writing:" + jsonCompromise.toString());
            context.write(uuid, jsonCompromise);
        }
    }

    public static class InputDataReducer
            extends Reducer<Text,Text,Text,Text> {

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

            for (Text text: values) {
                final Document compromiseDocument = new Document();
                compromiseDocument.append(key.toString(), text.toString());
                Main.insertInMongoDB(compromiseDocument);
                context.write(key, text);
            }
        }

    }

    public static void main(String[] args) throws Exception {

        // Dado um arquivo como entrada, interpreta o mesmo
        // para processar suas linhas e gerar as entradas

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "bank compromise");
        job.setJarByClass(Main.class);
        job.setMapperClass(InputDataMapper.class);
        job.setReducerClass(InputDataReducer.class);
        job.setCombinerClass(InputDataReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    private static void insertInMongoDB(Document compromiseJson) {
        MongoClient mongoClient = new MongoClient();

        // Connect to mongo database
        MongoDatabase database = mongoClient.getDatabase("compromise");

        // Get (or create) the bankCompromises document
        MongoCollection<Document> bankCompromissesCollection = database.getCollection("bankCompromises");

        // Insert the compromise data in document
        bankCompromissesCollection.insertOne(compromiseJson);

        System.out.print("All data was saved successfully");
    }
}
