package textprocess.datagovern.consistency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.String;
import java.lang.Object;
import java.lang.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.*;
public class DecomposeIdentity {
    public static String[] decompose(String s){
        String address_code = s.substring(0,6);
        String birthday_code = s.substring(6,14);
        String sequence_code = s.substring(14,17);
        int length = s.length();
        String check_code = s.substring(length-1,length);
        String [] strArray = new String [4];
        strArray[0] = address_code;
        strArray[1] = birthday_code;
        strArray[2] = sequence_code;
        strArray[3] = check_code;
        return strArray;
    }
    public static String[] analysis_identity(String s) {
        String[] strDecompose = decompose(s);
        String[] strArray = toArrayByFileReader();
        String[] strArray2 = new String[4];
        String address = "not found";
        String address_out = "not found";
        String gender_out = "男";
        for(int j=0;j<strArray.length;j++){
            String[] total = strArray[j].split(",");
            String code = total[0].substring(1,total[0].length()-1);
            String name = total[1].substring(1,total[1].length()-1);
            String level = total[2].substring(1,total[2].length()-1);
            String parent = total[3].substring(1,total[3].length()-1);
            if(strDecompose[0].equals(code)){
                address = name;
            }
        }
        if(address.equals("not found")){
            address_out = "not found";
        }
        if (!address.equals("not found")) {
            address_out = address;
        }
        String birthday_out = strDecompose[1];
        String age = birthday_out.substring(0, 4);
        int age_number = Integer.parseInt(age);
        int age_out = 2019 - age_number;
        String sequence = strDecompose[2];
        String ismale = sequence.substring(sequence.length() - 1, sequence.length());
        int isnumber = Integer.parseInt(ismale);
        if (isnumber % 2 == 0) {
            gender_out = "男";
        }
        if (isnumber % 2 != 0) {
            gender_out = "女";
        }

        strArray2[0] = address_out;
        strArray2[1] = birthday_out;
        strArray2[2] = Integer.toString(age_out);
        strArray2[3] = gender_out;
        return strArray2;
    }
    public static String[] toArrayByFileReader () {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            InputStream is=DecomposeIdentity.class.getClassLoader().getResourceAsStream("regionCode.txt");
            //FileReader fr = new FileReader("./test.txt");
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
    public static String check_identity(String s){
        String strDecompose1 = s.substring(0,s.length()-1);
        String strDecompose2 = s.substring(s.length()-1,s.length());
        int sum=0;
        String isTrueIdetity ="TrueIdentity";
        int[] nums = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            map.put(nums[i], i);
//        }
        for(int i=0;i<17;i++){
            sum = sum + Integer.parseInt(strDecompose1.substring(i,i+1))*nums[i];}
        String[] check={"1","0","X","9","8","7","6","5","4","3","2"};
        int sum_mode = sum%11;
        String check_out = check[sum_mode];
        if(check_out.equals(strDecompose2)){
            isTrueIdetity="TrueIdentity";
        }else{isTrueIdetity="FalseIdentity";}
        return isTrueIdetity;
    }
    public static void main(String[] args) {
        String test = "371202200611260064";
        String[] str1 = decompose(test);
        for(int i=0;i<str1.length;i++){
            System.out.println(str1[i]);}
        String database = "test";
        String[] str2 = analysis_identity(test);
        for(int i=0;i<str2.length;i++){
            System.out.println(str2[i]);}
        String isTrueIdentity=check_identity(test);
        System.out.println("isTrueIdentity:"+isTrueIdentity);
    }
}

