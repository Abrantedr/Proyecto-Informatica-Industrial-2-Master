import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;

import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;


public class Api {
    protected final static String path = "G:\\Mi unidad\\Master en Ingenieria Industrial\\" +
            "Tercer Cuatrimestre\\Informática Industrial\\Practicas\\" +
            "REE\\data\\data.json";
    protected static TimeSeries demSeries = new TimeSeries("Demanda");
    protected static TimeSeries dieSeries = new TimeSeries("Diésel");
    protected static TimeSeries gasSeries = new TimeSeries("Turbina de gas");
    protected static TimeSeries eolSeries = new TimeSeries("Eólica");
    protected static TimeSeries ccSeries = new TimeSeries("Ciclo combinado");
    protected static TimeSeries vapSeries = new TimeSeries("Turbina de vapor");
    protected static TimeSeries fotSeries = new TimeSeries("Solar fotovoltaica");
    protected static TimeSeries hidSeries = new TimeSeries("Hidráulica");

    public Api() {
        // Do nothing
    }

    public static void main(String[] args) {
        // Do nothing
    }

    public static void listContent(final String filePath) throws Exception {
        java.util.Date ts = null;
        double
                dem = 0,
                die = 0,
                gas = 0,
                eol = 0,
                cc = 0,
                vap = 0,
                fot = 0,
                hid = 0;

        // Define the date format to use for parsing
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm"
        );

        try (InputStream is = new FileInputStream(filePath);
             JsonParser parser = Json.createParser(is)) {
            while (parser.hasNext()) {
                Event e = parser.next();
                if (e == Event.KEY_NAME) {
                    switch (parser.getString()) {
                        case "ts" -> {
                            parser.next();
                            // Get timestamp
                            String stringts = parser.getString();
                            ts = dateFormat.parse(stringts);
                        }
                        case "dem" -> {
                            parser.next();
                            // Get demand
                            String stringdem = parser.getString();
                            dem = Double.parseDouble(stringdem);
                        }
                        case "die" -> {
                            parser.next();
                            // Get die
                            String stringdie = parser.getString();
                            die = Double.parseDouble(stringdie);
                        }
                        case "gas" -> {
                            parser.next();
                            // Get gas
                            String stringgas = parser.getString();
                            gas = Double.parseDouble(stringgas);
                        }
                        case "eol" -> {
                            parser.next();
                            // Get eol
                            String stringeol = parser.getString();
                            eol = Double.parseDouble(stringeol);
                        }
                        case "cc" -> {
                            parser.next();
                            // Get cc
                            String stringcc = parser.getString();
                            cc = Double.parseDouble(stringcc);
                        }
                        case "vap" -> {
                            parser.next();
                            // Get vap
                            String stringvap = parser.getString();
                            vap = Double.parseDouble(stringvap);
                        }
                        case "fot" -> {
                            parser.next();
                            // Get fot
                            String stringfot = parser.getString();
                            fot = Double.parseDouble(stringfot);
                        }
                        case "hid" -> {
                            parser.next();
                            // Get hid
                            String stringhid = parser.getString();
                            hid = Double.parseDouble(stringhid);

                            // At this point, we've gathered all the data
                            // Now it's time to add to time series
                            Minute tsDate = new Minute(ts);
                            demSeries.addOrUpdate(tsDate, dem);
                            dieSeries.addOrUpdate(tsDate, die);
                            gasSeries.addOrUpdate(tsDate, gas);
                            eolSeries.addOrUpdate(tsDate, eol);
                            ccSeries.addOrUpdate(tsDate, cc);
                            vapSeries.addOrUpdate(tsDate, vap);
                            fotSeries.addOrUpdate(tsDate, fot);
                            hidSeries.addOrUpdate(tsDate, hid);
                        }
                    }
                }
            }
        }
    }

    public static final String getUrl(final String date) {
        return "https://demanda.ree.es/WSvisionaMovilesCanariasRest/" +
                "resources/demandaGeneracionCanarias?" +
                "callback=angular.callbacks._2&curva=CANARIAS&" +
                "fecha=" + date;
    }

    public static void download(final String url, final String filePath) throws
            Exception {
        URL urlFile = new URL(url);
        InputStream inputStream = urlFile.openStream();
        OutputStream outputStream = new FileOutputStream(filePath);

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        sb.delete(0, 21);
        int length = sb.length();
        sb.delete(length - 2, length);

        outputStream.write(sb.toString().getBytes());
        inputStream.close();
        outputStream.close();
    }
}