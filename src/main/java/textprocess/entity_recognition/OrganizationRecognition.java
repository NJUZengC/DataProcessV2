package textprocess.entity_recognition;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import textprocess.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Junnor.G
 * @Date 2018/8/28 下午6:57
 */
public class OrganizationRecognition {
//    public static void main(String[] args){
////        logger.info("Test github");
//        Map<String , List<String>> ret = getNameFromDir("ganjun_testdata");
//        for(String path : ret.keySet()){
//            System.out.println(path + "->");
//            for(String str : ret.get(path)){
//                System.out.println(str + " " );
//            }
//            System.out.println("");
//        }
////
//    }
//    // 将字符串中的所有机构或者地点实体提取出来(暂时先将整个文件所有的数据读到一起，作为一整个字符串)
//    public static List<String> getOrganization(String s){
//        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);
//        List<Term> termList = segment.seg(s);
//        List<String> nameList = new ArrayList<String>();
//
//        for (int i = 0 ; i < termList.size() ; i++){
//            String[] tmp = termList.get(i).toString().split("/");
////            System.out.println(tmp[tmp.length-1]);
////            if (tmp[tmp.length-1].contains("ni")
////                    || tmp[tmp.length-1].contains("nt")
////                    || tmp[tmp.length-1].contains("ns")
////                    || tmp[tmp.length-1].contains("nn")){
////                nameList.add(tmp[0]);
////            }
//            if (tmp[tmp.length-1].equals("nt")) {
//                nameList.add(tmp[0]);
//            }
//        }
//        return nameList;
//    }
//    // 从文件中目录中提取实体，每一个List<String>代表一个文件中的
//    public static Map<String , List<String>> getNameFromDir(String dirPath){
//        Map<String , List<String>> twoDimensionsEntities = new HashMap<String , List<String>>();
//        List<String> filePaths = FileUtil.getAllFilePath(dirPath);
//        for(String path : filePaths){
//            List<String> entities = new ArrayList<String>();
//            String doc = FileUtil.readFileAsString(path);
//            twoDimensionsEntities.put(path , getOrganization(doc));
//        }
//
//        return twoDimensionsEntities;
//    }
    public static List<String> getOrganization(String text) {
        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);

        //segment.config.nameRecognize=false;
        //segment.config.translatedNameRecognize=false;

        List<Term> termList = segment.seg(text);

        List<String> nt = new ArrayList<String>();//机构；

        for(Term term :termList){

            if(term.nature== Nature.nt){
                if(!term.word.endsWith("点"))
                    nt.add(term.word);
            }
        }

        return nt;
    }
}
