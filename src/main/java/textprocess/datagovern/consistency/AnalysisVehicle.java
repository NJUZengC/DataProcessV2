package textprocess.datagovern.consistency;
import java.io.*;
import java.util.*;
public class AnalysisVehicle {
    public static String getVehicleAddress(String vehicleNumber){
//        String needNumber = vehicleNumber.substring(0,2);
        String[] splits = vehicleNumber.split("·");
        String needNumber = splits[0];
        System.out.println(needNumber);
        String[] strArray = toArrayByFileReader();
        String vehicleAttribution = "";
        for(int j=0;j<strArray.length;j++){
            String[] total = strArray[j].split(",");
            String code = total[0];
            String city = total[1];
            String province = total[2];
            String Pcode = total[3];
            if(needNumber.equals(code)){
                vehicleAttribution = province+city;
            }
        }
        return vehicleAttribution;
    }
    public static String[] toArrayByFileReader () {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            InputStream is=AnalysisVehicle.class.getClassLoader().getResourceAsStream("vehicleCode.txt");
            InputStreamReader fr = new InputStreamReader(is, "utf-8");
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        int length = arrayList.size();
        String[] array = new String[length];
        for (int i = 0; i < length; i++) {
            String s = arrayList.get(i);
            array[i] = s;
        }
        // 返回数组
        return array;
    }
    public static void main(String[] args){
        String test = "浙B·NIW25";
        String result = getVehicleAddress(test);
        if(result.equals("")){
            System.out.println("Wrong");
        }else{
            System.out.println(result);
        }

    }

}
