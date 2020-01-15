package utilities;



import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

public class updateJson {


        public static String readFile(String filename) {
            String result = "";
            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                result = sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        public static JSONObject updateJsonData(JSONObject obj, String ID) {
// String fileName = System.getProperty("user.dir") +
// System.getProperty("file.separator") + "test.json";
            //String content = readFile(filePath);
            //JSONObject obj = new JSONObject(content);
            String today = getToday();
            obj.remove("startMonth");
            obj.put("startMonth", today);
            int durationInMonths = obj.getInt("durationInMonths");
            long duration = durationInMonths;
            JSONArray arr = obj.getJSONArray("billingLineBuyingAreaRevenues");
            for(int i=0;i<arr.length();i++) {
                JSONObject intObj = arr.getJSONObject(i);
                intObj.remove("id");
                intObj.put("id", ID);
                JSONArray monthArray = intObj.getJSONArray("monthBuyingAreaRevenues");
                monthArray.remove(0);
                for(long j=0;j<duration;j++) {
                    JSONObject template = new JSONObject();
                    template.put("value", 0.01);
                    int index= (int) j;
                    if(j>0) {
                        String month = addMonth(j);
                        template.put("date", month);
                    }
                    else {
                        template.put("date", getToday());
                    }

                    monthArray.put(index,template);
                    System.out.println(monthArray);
                }

            }
            double revenue = 0.01*duration;
            obj.remove("revenue");
            obj.put("revenue", revenue);
            System.out.println(obj);
            return obj;

        }

        public static String getToday() {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            return dtf.format(now);
        }

        public static String addMonth(long number) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate now = LocalDate.now();
            LocalDate latest = now.plusMonths(number);
            return dtf.format(latest);
        }

    }



