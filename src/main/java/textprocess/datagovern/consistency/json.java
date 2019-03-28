package textprocess.datagovern.consistency;
import java.util.*;
import net.sf.json.*;
import java.io.*;
public class json {
    public static void main(String[] args){
        String json = "[{'first': 'one','next': 'two'},{'first': 'three','next': 'fore'},{'first': 'five','next': 'six'}]";

        try {

            JSONArray jsonObject = JSONArray.fromObject(json);
            for (Iterator<Object> iterator = jsonObject.iterator(); iterator.hasNext();) {
                JSONObject job = (JSONObject) iterator.next();
                Iterator<Object> it=job.keys();
                String json1;
                String totaljson = "";
                while (it.hasNext()){
                    json1 = job.get(it.next()).toString();
                    totaljson = totaljson + json1 + ",";
                }
                totaljson = totaljson.substring(0,totaljson.length()-1);
                System.out.println(totaljson);
                try{
                    FileOutputStream fos = new FileOutputStream("vehicleCode.txt",true);
                    //true表示在文件末尾追加
                    fos.write(totaljson.getBytes());
                    if(iterator.hasNext()){
                    fos.write('\n');}
                    fos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {

            e.printStackTrace();

        }
    }
}
