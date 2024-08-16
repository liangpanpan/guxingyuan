package com.guxingyuan.logback;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/5/14       create this file
 * </pre>
 */
public class FastJsonTest {

    public static void main(String[] args) throws IOException {
        FastJsonTest.parseObject();
    }


    public static void parseObject() {
        String content = "{\"serviceType\":18,\"note\":\"\",\"riskLevel\":\"2\",\"evidence\":[\"https://testdrp.tj-un.com/api/V3/interface//riskresource/2403/00ff1fddc90146f9b3b9cfc4005604b0/evidence/588742cd6dd64c67b7c25d0d343c9234.jpg\"],\"riskVersion\":\"d038c6a96eb142f2a68a7f076b2aa211\",\"description\":\"此次 Morgan Stanley开放的2024金融实习，非常值得广大留学生参与~ 岗位有投行、量化、资管、行研... 岗位要求:本科以上学历金融相关专业~(远程实习，适合国外留学生或者无法到实地的学生) . 岗位职责 1、Gain industry and organizational knowledge through daily business interac - tions and job assignments ; 2、Develop business , financial and analytical skills needed for career in global wealth management and financial services ; 3、Provide marketing and sales support to branches and their teams . Perform market research ; 4、Gain exposure to wealth management business, products, services and clients ' financial needs. 想参加的同学留下【jp]暑假/近期均可提前安排~ #行研#北美求职#留学生求职#职业规划#香港实习#留学英国#摩根大通#大学生#九大投行#内推#投行#intern#留学生实习#实习内推#量化#摩根#实习#jpmorgan#商科实习#留学美国#金融\",\"title\":\"JPMorgan<span class=\\\"marke-red\\\">远程</span>!\",\"appPath\":\"\",\"platform\":\"小红书\",\"hitKeywords\":\"\\\"wealth\\\" \\\"远程实习\\\" \\\"Morgan Stanley\\\" \\\"远程\\\"\",\"likeNum\":\"\",\"leakageContent\":[],\"accountID\":\"NA\",\"fraudAccount\":\"Fay-实习咨询\",\"artContent\":\"此次 Morgan Stanley开放的2024金融实习，非常值得广大留学生参与~ 岗位有投行、量化、资管、行研... 岗位要求:本科以上学历金融相关专业~(远程实习，适合国外留学生或者无法到实地的学生) . 岗位职责 1、Gain industry and organizational knowledge through daily business interac - tions and job assignments ; 2、Develop business , financial and analytical skills needed for career in global wealth management and financial services ; 3、Provide marketing and sales support to branches and their teams . Perform market research ; 4、Gain exposure to wealth management business, products, services and clients ' financial needs. 想参加的同学留下【jp]暑假/近期均可提前安排~ #行研#北美求职#留学生求职#职业规划#香港实习#留学英国#摩根大通#大学生#九大投行#内推#投行#intern#留学生实习#实习内推#量化#摩根#实习#jpmorgan#商科实习#留学美国#金融\",\"detectTime\":\"20240329110457\",\"tag\":[\"招聘\",\"摩根史丹利\"],\"appSha256\":\"\",\"engineVersion\":\"\",\"updatedTime\":\"20240712172605\",\"brandName\":\"麦肯锡\",\"ip\":\"81.69.116.102\",\"falseReportCompany\":\"\",\"hitPosition\":\"全文\",\"socialAccount\":\"小红书:NA\",\"url\":\"https://www.xiaohongshu.com/explore/6604d0c4000000001203cd8b\",\"riskID\":\"00ff1fddc90146f9b3b9cfc4005604b0\",\"commentNum\":\"0\",\"carrier\":[\"Tencent\"],\"readNum\":\"0\",\"pubtime\":\"20240328100700\",\"brandId\":\"205\",\"lastDetectTime\":\"20240329110457\",\"assetName\":\"\",\"location\":\"中国 上海市\",\"asn\":[\"45090\"],\"leakage\":\"\",\"status\":12}";
        DigitalRisk digitalRisk = JSONObject.parseObject(content, DigitalRisk.class);
        System.out.println(digitalRisk.toString());
    }


    public static void readFileToJson() throws IOException {
        File file = new File("D://tmp/test/test.json");
        System.out.println(file.exists());
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String content;
        while ((content = bufferedReader.readLine()) != null) {
            stringBuilder.append(content.trim());
        }
        bufferedReader.close();
        JSONObject jsonObject = JSONObject.parseObject(stringBuilder.toString());
        System.out.println(jsonObject.toJSONString());
        System.out.println("finish");
    }



}
