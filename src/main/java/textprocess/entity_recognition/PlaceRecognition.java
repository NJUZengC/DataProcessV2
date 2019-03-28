package textprocess.entity_recognition;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;

public class PlaceRecognition {

    public static List<String> getPlace(String text) {
        Segment segment = HanLP.newSegment().enablePlaceRecognize(true);

        List<Term> termList = segment.seg(text);

        List<String> ns = new ArrayList<String>();//地点；

        for(Term term :termList){

            if(term.nature== Nature.ns){
                ns.add(term.word);
            }

        }

        return ns;
    }

}
