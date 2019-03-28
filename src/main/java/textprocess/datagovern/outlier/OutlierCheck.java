package textprocess.datagovern.outlier;

import java.util.*;

/**
 * @author zengc
 * @date 2018/10/10 20:33
 */
public class OutlierCheck {

    public static ArrayList<Boolean> number3Sigma(ArrayList<Double> source, double sigmaPara) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        double sum = 0;
        double sigma = 0;
        for (double d : source) {
            sum += d;
        }
        double mean = sum / source.size();
        for (double d : source) {
            sigma += (d - mean) * (d - mean);
        }
        sigma = Math.sqrt(sigma / source.size());
        for (double d : source) {
            if (Math.abs(d - mean) > sigmaPara * sigma)
                res.add(false);
            else
                res.add(true);
        }
        return res;
    }

    public static ArrayList<Boolean> number4Slot(ArrayList<Double> source) {
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        int length = source.size();
        double Q1Index = (length + 1) / 4.0;
        double Q2Index = (length + 1) / 2.0;
        double Q3Index = 3 * (length + 1) / 4.0;
        ArrayList<Double> copy = new ArrayList<>();
        copy = (ArrayList<Double>) source.clone();
        copy.sort(new Comparator<Double>() {
            @Override
            public int compare(Double o1, Double o2) {
                int i = o1 > o2 ? 1 : -1;
                return i;
            }
        });
        double Q1 = (1-(Q1Index - (int)Q1Index)) * copy.get((int)Q1Index-1) + (Q1Index - (int)Q1Index) * ((int)Q1Index < copy.size() ? copy.get((int)Q1Index) : 0);

        double Q2 = (1-(Q2Index - (int)Q2Index)) * copy.get((int)Q2Index-1) + (Q2Index - (int)Q2Index) * ((int)Q2Index < copy.size() ? copy.get((int)Q2Index) : 0);

        double Q3 = (1-(Q3Index - (int)Q3Index)) * copy.get((int)Q3Index-1) + (Q3Index - (int)Q3Index) * ((int)Q3Index < copy.size() ? copy.get((int)Q3Index) : 0);

        double IQR = Q3 - Q1;
        double upper = Q3 + 1.5 * IQR;
        double lower = Q1 - 1.5 * IQR;

        for (double d : source) {
            if (d < lower || d > upper)
                res.add(false);
            else
                res.add(true);
        }
        return res;
    }

    public static ArrayList<Boolean> stringFrequency(ArrayList<String> source,int minFrequency){
        ArrayList<Boolean> res = new ArrayList<Boolean>();
        Map<String,Integer> freq = new HashMap<>();
        for (String d : source) {
            if (freq.containsKey(d))
                freq.put(d,freq.get(d)+1);
            else
                freq.put(d,1);
        }
        for (String d : source) {
            if (freq.get(d)<minFrequency)
                res.add(false);
            else
                res.add(true);
        }
        return res;

    }

    public static void main(String [] a) {
        ArrayList<Double> copy = new ArrayList<>();
        copy.add(1.0);
        copy.add(0.6);
        copy.add(1.5);
        copy.add(1.5);
        copy.add(1.5);
        copy.add(4.0);
        System.out.println(number3Sigma(copy,3));
    }
}
