package textprocess.entity_recognition;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;

public class NER {

    public static List<String> nameNER(String sentence){

        Segment segment = HanLP.newSegment().enableNameRecognize(true);

        List<Term> termList = segment.seg(sentence);

        List<String> nr = new ArrayList<String>();//人名；

        for(Term term :termList){

            if(term.nature==Nature.nr){//中国人名
                nr.add(term.word);
            }

            if(term.nature==Nature.nrf){//音译人名
                nr.add(term.word);
            }
        }

        return nr;
    }
    public static List<String> placeNER(String sentence){

        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);

        List<Term> termList = segment.seg(sentence);

        List<String> ns = new ArrayList<String>();//地点；

        for(Term term :termList){

            if(term.nature==Nature.ns){
                ns.add(term.word);
            }

        }

        return ns;
    }
    public static List<String> organizationNER(String sentence){

        Segment segment = HanLP.newSegment().enableOrganizationRecognize(true);

        //segment.config.nameRecognize=false;
        //segment.config.translatedNameRecognize=false;

        List<Term> termList = segment.seg(sentence);

        List<String> nt = new ArrayList<String>();//机构；

        for(Term term :termList){

            if(term.nature== Nature.nt){
                if(!term.word.endsWith("点"))
                    nt.add(term.word);
            }
        }

        return nt;
    }



    public static void main(String[] args)
    {

        String sentence = "王田生，男，现年83岁，家住中方县中方村，家庭人口3人，其妻子也是80多岁，王田生年老多病，医药费用开销巨大，无劳动力，家庭贫困无任何经济收入，家庭贫困，全家生活只能女儿王桃梅一人和社会救助。望社会人士给予帮助。\n";
        List<String> nr = new ArrayList<String>();//name
        nr=nameNER(sentence);

        System.out.println();
        System.out.println("人名：");
        for(String w :nr){
            System.out.print(w+" ");
        }
//
        List<String> ns = new ArrayList<String>();//place
        ns=placeNER(sentence);
        System.out.println();
        System.out.println("地点：");
        for(String w :ns){
            System.out.print(w+" ");
        }


        List<String> nt = new ArrayList<String>();//organization
        nt=organizationNER(sentence);
        System.out.println();
        System.out.println("机构：");
        for(String w :nt){
            System.out.print(w+" ");
        }
    }
}
