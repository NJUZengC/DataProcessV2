package textprocess.time;

import textprocess.time.nlp.TimeNormalizer;
import textprocess.time.nlp.TimeUnit;
import textprocess.time.util.DateUtil;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TimeRecog {

    public static List<String> timeRecog(String text) throws URISyntaxException {
//        String path = TimeNormalizer.class.getResource("").getPath();
//        String classPath = path.substring(0, path.indexOf("/com/time"));
//        System.out.println(classPath+"/TimeExp.m");
//        TimeNormalizer normalizer = new TimeNormalizer(classPath+"/TimeExp.m");
        URL url = TimeNormalizer.class.getResource("/TimeExp.m");
        System.out.println(url.toURI().toString());
        TimeNormalizer normalizer = new TimeNormalizer(url.toURI().toString());
        normalizer.setPreferFuture(true);

        normalizer.parse(text);// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        if(unit.length <=0 ) return new ArrayList<String>();
        List<String> out = new ArrayList<String>();
        for(TimeUnit tu:unit) out.add(DateUtil.formatDateDefault(tu.getTime()));
        System.out.println(text);
//        System.out.println(out.toString() + "-" + unit[0].getIsAllDayTime());
        return out;
//
//        normalizer.parse("早上六点起床");// 注意此处识别到6天在今天已经过去，自动识别为明早六点（未来倾向，可通过开关关闭：new TimeNormalizer(classPath+"/TimeExp.m", false)）
//        unit = normalizer.getTimeUnit();
//        System.out.println("早上六点起床");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("周一开会");// 如果本周已经是周二，识别为下周周一。同理处理各级时间。（未来倾向）
//        unit = normalizer.getTimeUnit();
//        System.out.println("周一开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("下下周一开会");//对于上/下的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("下下周一开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6:30 起床");// 严格时间格式的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("6:30 起床");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6-3 春游");// 严格时间格式的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("6-3 春游");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6月3  春游");// 残缺时间的识别 （打字输入时可便捷用户）
//        unit = normalizer.getTimeUnit();
//        System.out.println("6月3  春游");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("明天早上跑步");// 模糊时间范围识别（可在RangeTimeEnum中修改
//        unit = normalizer.getTimeUnit();
//        System.out.println("明天早上跑步");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("本周日到下周日出差");// 多时间识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("本周日到下周日出差");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
//
//        normalizer.parse("周四下午三点到五点开会");// 多时间识别，注意第二个时间点用了第一个时间的上文
//        unit = normalizer.getTimeUnit();
//        System.out.println("周四下午三点到五点开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
//
//        //新闻随机抽取长句识别（2016年6月7日新闻,均以当日0点为基准时间计算）
//        //例1
//        normalizer.parse("昨天上午，第八轮中美战略与经济对话气候变化问题特别联合会议召开。中国气候变化事务特别代表解振华表示，今年中美两国在应对气候变化多边进程中政策对话的重点任务，是推动《巴黎协定》尽早生效。", "2016-06-07-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("昨天上午，第八轮中美战略与经济对话气候变化问题特别联合会议召开。中国气候变化事务特别代表解振华表示，今年中美两国在应对气候变化多边进程中政策对话的重点任务，是推动《巴黎协定》尽早生效。");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }
//
//        //例2
//        normalizer.parse("《辽宁日报》今日报道，6月3日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。", "2016-06-07-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("《辽宁日报》今日报道，6月3日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }
//
//        //例3
//        normalizer.parse("去年11月起正式实施的刑法修正案（九）中明确，在法律规定的国家考试中，组织作弊的将入刑定罪，最高可处七年有期徒刑。另外，本月刚刚开始实施的新版《教育法》中也明确...", "2016-06-07-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("去年11月起正式实施的刑法修正案（九）中明确，在法律规定的国家考试中，组织作弊的将入刑定罪，最高可处七年有期徒刑。另外，本月刚刚开始实施的新版《教育法》中也明确...");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }
    }

    public static void main(String [] args) {
        try {
            List<String> results = TimeRecog.timeRecog("济南杨铭宇餐饮管理有限公司是由杨先生创办的餐饮企业米奇" +
                    "糕点店,晚上九点去吃饭七号，2008年5月3日北京今天很热");
            if(results.size()==0){
                System.out.println("没有识别出时间点");
            }else{
                System.out.println("识别出时间点"+results.size()+"个");
            }
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

}
