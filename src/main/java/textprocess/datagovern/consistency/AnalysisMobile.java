package textprocess.datagovern.consistency;
//import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.alibaba.fastjson.*;
import java.util.*;
public class AnalysisMobile {
    public static String getMobileCity(String mobileNumber) {
        //百度的API地址
        String urlString = "http://mobsec-dianhua.baidu.com/dianhua_api/open/location?tel=" + mobileNumber;
        StringBuilder sb = new StringBuilder();
        BufferedReader buffer;
        String provinces = "";
        String city = "";
        String operators = "";
        try {
            URL url = new URL(urlString);
            //获取URL地址中的页面内容
            InputStream in = url.openStream();
            // 解决乱码问题
            buffer = new BufferedReader(new InputStreamReader(in, "utf8"));
            String line = null;
            //一行一行的读取数据
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            buffer.close();
            //定义字符串
//            String objectStr = "{"response":{"***********":{"detail":{"area":[{"city":"开封"}],"province":"河南","type":"domestic","operator":"移动"},"location":"河南开封移动"}},"responseHeader":{"status":200,"time":1532934563306,"version":"1.1.0"}}";
            //1、使用JSONObject
            System.out.println(sb);
            JSONObject jsonObject2 = JSONObject.parseObject(sb.toString());
            String result = jsonObject2.getString("response");
            System.out.println(result);
//          使用fastjson的parseObject方法将json字符串转换成Map
            LinkedHashMap<String, String> jsonMap = JSON.parseObject(result, new TypeReference<LinkedHashMap<String, String>>() {
            });
            System.out.println(jsonMap);
            for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
                String value = entry.getValue();
                LinkedHashMap<String, String> jsonMap1 = JSON.parseObject(value, new TypeReference<LinkedHashMap<String, String>>() {
                });
                for (Map.Entry<String, String> entry1 : jsonMap1.entrySet()) {
                    if (entry1.getKey().equals("detail")) {
                        String value1 = entry1.getValue();
                        LinkedHashMap<String, String> jsonMap2 = JSON.parseObject(value1, new TypeReference<LinkedHashMap<String, String>>() {
                        });
                        for (Map.Entry<String, String> entry2 : jsonMap2.entrySet()) {
                            if (entry2.getKey().equals("province")) {
                                provinces = entry2.getValue();
                            }
                            if (entry2.getKey().equals("operator")) {
                                operators = entry2.getValue();
                            }
                            if (entry2.getKey().equals("area")) {
                                String value2 = entry2.getValue();
                                String value3 = value2.substring(1, value2.length() - 1);
                                LinkedHashMap<String, String> jsonMap3 = JSON.parseObject(value3, new TypeReference<LinkedHashMap<String, String>>() {
                                });
                                for (Map.Entry<String, String> entry3 : jsonMap3.entrySet()) {
                                    city = entry3.getValue();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //从JSONObject对象中读取城市名称
        return provinces+" "+city+" "+operators;
    }
    public static void main(String[] args){
        String test = "18705153386";
        String result = getMobileCity(test);
        String attribution;
        String operator;
        System.out.println(result);
        if(result.equals("  ")){
            attribution ="WrongNumber";
            operator ="WrongNumber";
        }
        else{
        String[] aa = result.split("\\s+");
        attribution = aa[0]+aa[1];
        operator = aa[2];}
        System.out.println(attribution);
        System.out.println(operator);
    }
}
