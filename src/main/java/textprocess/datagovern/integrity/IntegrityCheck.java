package textprocess.datagovern.integrity;

import textprocess.util.DistanceUtil;
import textprocess.word2vec.Doc2vec;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zengc
 * @date 2018/10/10 9:36
 */
public class IntegrityCheck {
	//特殊值填充，传入一列数据和特殊值，对这列数据中为空的项更改为特殊值返回
    public static ArrayList<String> stringSpecalPad(ArrayList<String> source,String specialWord){
        ArrayList<String> res = new ArrayList<String>();
        for(String s:source){
            if (s==null || s.trim().equals(""))
                res.add(specialWord);
            else
                res.add(s);
        }
        return res;
    }

	//特殊值填充，传入一列数据和特殊值，对这列数据中为空的项更改为特殊值返回
    public static ArrayList<Double> numberSpecalPad(ArrayList<Double> source,Double specialNumber,double emptyNumber){
        ArrayList<Double> res = new ArrayList<Double>();
        final double epsilon = 0.000001;
        for(Double s:source){
            if (Math.abs(s - emptyNumber) < epsilon)
                res.add(specialNumber);
            else
                res.add(s);
        }
        return res;
    }

	//均值填充，传入源数据，将等于emptyNumber的数据项更改为平均值
    public static ArrayList<Double> numberMeanPad(ArrayList<Double> source,double emptyNumber){
        ArrayList<Double> res = new ArrayList<Double>();
        final double epsilon = 0.000001;
        int num = 0;
        double TotalNumber = 0.0d;
        for(Double s:source){
            if (Math.abs(s - emptyNumber) >= epsilon) {
                TotalNumber += s;
                num += 1;
            }
        }
        if(num == 0)
            return source;
        double mean = TotalNumber/num;
        for(Double s:source){
            if (Math.abs(s - emptyNumber) >= epsilon) {
                res.add(s);
            }
            else
                res.add(mean);
        }
        return res;
    }

	//这个可先不测试
    public static ArrayList<String> hotDeckPad(Map<String,ArrayList<String>> stringSource, Map<String,ArrayList<Double>> doubleSource,ArrayList<String> padString,Map<String,Double> ratio,String vecPath)throws Exception{
        final double epsilon = 0.000001;
        ArrayList<String> resString = new ArrayList<String>();
        for(int index=0;index<padString.size();index++){
            String s = padString.get(index);
            if(s!=null && !s.trim().equals(""))
                resString.add(s);
            else {
                ArrayList<Double> score = new ArrayList<>();
                for (int i=0;i<padString.size();i++)
                    score.add(0.0);
                for(String item:ratio.keySet()){

                    if(doubleSource !=null && doubleSource.containsKey(item)){
                        ArrayList<Double> itemArray = doubleSource.get(item);
                        double sample = itemArray.get(index);
                        for(int i=0;i<itemArray.size();i++)
                            if (Math.abs(itemArray.get(i) - sample) < epsilon) {
                                score.set(i,score.get(i)+ratio.get(item));
                            }
                    }else if(stringSource!=null && stringSource.containsKey(item)){
                        ArrayList<String> itemArray = stringSource.get(item);
                        String sample = itemArray.get(index);
                        Pattern pattern = Pattern.compile("[0-9]*");
                        Matcher isNum = pattern.matcher(sample);
                        if( !isNum.matches() ) {

                            float[] vec = Doc2vec.doc2vec(sample, vecPath);
                            for (int i = 0; i < itemArray.size(); i++) {
                                if (padString.get(i) == null || padString.get(i).trim().equals(""))
                                    continue;
                                float[] vec1 = Doc2vec.doc2vec(itemArray.get(i), vecPath);
                                double dis = DistanceUtil.calCosDistance(vec, vec1);
                                score.set(i, score.get(i) + ratio.get(item) * dis);
                            }
                        }else {
                            for(int i=0;i<itemArray.size();i++)
                                if (itemArray.get(i).trim().equals(sample.trim())) {
                                    score.set(i,score.get(i)+ratio.get(item));
                                }
                        }
                    }else {
                        throw new Exception("配比表错误");
                    }
                }
                double MaxScore = 0;
                int MaxIndex = 0;
                for(int i=0;i<score.size();i++){
                    System.out.println(score.get(i));
                    if(score.get(i)> MaxScore && padString.get(i)!=null && !padString.get(i).trim().equals("")){
                        MaxScore = score.get(i);
                        MaxIndex = i;
                    }
                }
                resString.add(padString.get(MaxIndex));
            }
        }
        return resString;
    }







    public static void main(String [] a)throws Exception {
//        String documents = "1.0";
//        double s = Double.parseDouble(documents);
//        Map<String,ArrayList<String>> stringSource = new HashMap<String,ArrayList<String>>();
//        stringSource.put("place",new ArrayList<>());
//        stringSource.get("place").add("江西赣州");
//        stringSource.get("place").add("湖南省长沙市");
//        stringSource.get("place").add("湖南长沙");
//        Map<String,Double> ratio = new HashMap<>();
//        ratio.put("place",1.0);
//        ArrayList<String> padString = new ArrayList<>();
//        padString.add("001");
//        padString.add("");
//        padString.add("002");
//
//        System.out.println(hotDeckPad(stringSource,null,padString,ratio,"src/main/resources/zhwiki_2017_03.sg_50d.word2vec"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        ParsePosition pos = new ParsePosition(0);
//        Date strtodate = formatter.parse("20181011102030", pos);
//        ParsePosition pos1 = new ParsePosition(0);
//        Date endtodate = formatter.parse("20181011142030", pos1);
//        long between=(strtodate.getTime()-endtodate.getTime())/1000;//除以1000是为了转换成秒
//        long min=between/60;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        ParsePosition pos = new ParsePosition(0);
        String online = "20171108100800";
        String offline = "20171108121800";
        Date strtodate = formatter.parse(online, pos);
        ParsePosition pos1 = new ParsePosition(0);
        Date endtodate = formatter.parse(offline, pos1);
        long between=(strtodate.getTime()-endtodate.getTime())/1000;//除以1000是为了转换成秒
        long min=between/60;
        System.out.println(min);

    }

}
