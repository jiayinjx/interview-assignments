import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

public class DailyUrlHitCount {

    public static void main(String []args){
        if(args.length > 0) {
            File input = new File(args[0]);
            Map<String, Map<String, Integer>> dateMap = new TreeMap<>();

            try {
                BufferedReader b = new BufferedReader(new FileReader(input));

                String currLine = "";
                while ((currLine = b.readLine()) != null) {
                    String[] arrOfStr = currLine.split("\\|");
                    String formattedDate = generateUnixToData(arrOfStr[0]);

                    Map<String, Integer> urlMap = new HashMap<>();

                    if(dateMap.containsKey(formattedDate)) {
                        urlMap = dateMap.get(formattedDate);
                    }
                    urlMap.put(arrOfStr[1], urlMap.getOrDefault(arrOfStr[1], 0)+1);
                    dateMap.put(formattedDate, urlMap);

                }

                outputFile(dateMap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String generateUnixToData(String unix) {

        long unixSeconds = Long.valueOf(unix);
        Date date = new java.util.Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public static void outputFile(Map<String, Map<String, Integer>> dateMap) throws IOException {

        for(Map.Entry<String, Map<String, Integer>> date : dateMap.entrySet()) {
            System.out.println(date.getKey() + " GMT");
            Iterator<Map.Entry<String, Integer>> urlsHit = date.getValue().entrySet().iterator();
            while(urlsHit.hasNext()) {
                Map.Entry<String, Integer> entry = urlsHit.next();
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }
}
