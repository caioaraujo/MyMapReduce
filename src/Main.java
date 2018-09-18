import com.google.gson.Gson;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;

public class Main {

    public static class InputDataMapper
            extends Mapper<Object, Text, Text, Text> {

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            final String[] input = value.toString().split(";");

            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            final Compromise compromise = new Compromise(
                    input[0], input[1], LocalDate.parse(input[2], formatter),
                    new BigDecimal(input[3]), input[4], input[5]
            );

            final Gson gson = new Gson();
            final Text jsonCompromise = new Text(gson.toJson(compromise));

            final Text uuid = new Text(UUID.randomUUID().toString());

            context.write(uuid, jsonCompromise);
        }
    }

    public static void main(String[] args) throws Exception {

        // Dado um arquivo como entrada, interpreta o mesmo
        // para processar suas linhas e gerar as entradas

       // TODO
    }
}
