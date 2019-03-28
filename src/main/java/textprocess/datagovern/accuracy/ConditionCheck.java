package textprocess.datagovern.accuracy;

import java.util.Map;

public class ConditionCheck {
    public static boolean check(String checkColumn, Map<String, Integer> map, Map<Integer, String> conditions){
        if(conditions.keySet().contains(map.get(checkColumn))){
            String condition = conditions.get(map.get(checkColumn));
            String[] ors = condition.split("or");
            boolean bool_ors = false;
            for(int i = 0;i < ors.length;i++){
                String[] ands = ors[i].trim().split("and");
                boolean bool_ands = true;

                for(int j = 0;j < ands.length;j++){
                    if(ands[j].contains(">")){
                        String[] value = ands[j].trim().split(">");
                        if(map.get(value[0].trim()) > Integer.parseInt(value[1])) bool_ands = true;
                        else bool_ands = false;
                    }
                    else if(ands[j].contains("<")){
                        String[] value = ands[j].trim().split("<");
                        if(map.get(value[0].trim()) < Integer.parseInt(value[1])) bool_ands = true;
                        else bool_ands = false;
                    }
                    else if(ands[j].contains("<=")){
                        String[] value = ands[j].trim().split("<=");
                        if(map.get(value[0].trim()) <= Integer.parseInt(value[1])) bool_ands = true;
                        else bool_ands = false;
                    }
                    else if(ands[j].contains(">=")){
                        String[] value = ands[j].trim().split(">=");
                        if(map.get(value[0].trim()) >= Integer.parseInt(value[1])) bool_ands = true;
                        else bool_ands = false;
                    }
                    else if(ands[j].contains("=")){
                        String[] value = ands[j].trim().split("=");
                        if(map.get(value[0].trim()) == Integer.parseInt(value[1])) bool_ands = true;
                        else bool_ands = false;
                    }
                }
                bool_ors = bool_ors || bool_ands;
            }
            return bool_ors;
        }
        else{
            return true;
        }
    }
}
