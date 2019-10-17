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

            Map<String, Integer> urlsHit = sortUrlsHit(date.getValue());
            Iterator<Map.Entry<String, Integer>> set = urlsHit.entrySet().iterator();

            while(set.hasNext()) {
                Map.Entry<String, Integer> entry = set.next();
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
    }

    public static Map<String, Integer> sortUrlsHit(Map<String, Integer> urlsHit) {

        List<Map.Entry<String, Integer> > hitsList =
                new LinkedList<Map.Entry<String, Integer>>(urlsHit.entrySet());

        Collections.sort(hitsList, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<String, Integer> map = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> h : hitsList) {
            map.put(h.getKey(), h.getValue());
        }
        return map;
    }
}
