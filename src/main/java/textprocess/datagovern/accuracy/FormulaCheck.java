package textprocess.datagovern.accuracy;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaCheck {
    public static String check(String formula, Map<String, Integer> map){
        LinkedList<String> sign = new LinkedList<>();
        LinkedList<Integer> num = new LinkedList<>();
        String[] fml = formula.split("=");
        String[] right = fml[1].trim().split(" ");
        for (int i = 0; i < right.length; i++) {
            if (right[i].equals("+") || right[i].equals("-")) {
                sign.add(right[i]);
            }
            else if (right[i].equals("*") || right[i].equals("/")) {
                int a = num.pollLast();
                int b;
                if (isNumeric(right[i + 1])) b = Integer.parseInt(right[i + 1]);
                else b = map.get(right[i + 1]);

                if (right[i].equals("*")) num.add(a * b);
                else num.add(a / b);
                i++;
            }
            else {
                if (isNumeric(right[i])) num.add(Integer.parseInt(right[i]));
                else num.add(map.get(right[i]));
            }
        }

        System.out.println(sign);
        System.out.println(num);
        while (!sign.isEmpty()) {
            String si = sign.pollFirst();
            int a = num.pollFirst();
            int b = num.pollFirst();
            if (si.equals("+")) num.addFirst(a + b);
            else num.addFirst(a - b);
        }

        String result = "";
        boolean bool_re = false;
        if (map.get(fml[0].trim()) == num.peek()) {
            bool_re = true;
        } else {
            result = String.valueOf(num.peek());
        }
        return result;
    }

    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
        String bigStr;
        try
        {
            bigStr = new BigDecimal(str).toString();
        }
        catch (Exception e)
        {
            return false;
        }
        Matcher isNum = pattern.matcher(bigStr);
        if (!isNum.matches()) return false;
        return true;
    }

}
