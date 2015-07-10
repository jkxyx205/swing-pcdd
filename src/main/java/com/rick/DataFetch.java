package com.rick;

import com.rick.model.Email;
import com.rick.model.Param;
import com.rick.model.Result;
import com.rick.ui.RightCornerPopMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ArrayUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataFetch {

    @Resource
    private EmailSender sender;

    public void fetch(boolean inited,Param param, List<Result> list) throws URISyntaxException, IOException, ConfigurationException {
        analysisData(list,param,inited);
    }

    private void analysisData(List<Result> list,Param param,boolean inited) throws ConfigurationException {
        if (CollectionUtils.isEmpty(list))
            return;

        Result first = list.get(0);
        //small big
        StringBuilder smallOrBig = new StringBuilder();
        //single double
        StringBuilder singleOrDouble = new StringBuilder();

        for (Result r : list) {
            smallOrBig.append(r.getSmallOrBig());
            singleOrDouble.append(r.getSingleOrDouble());
        }

        StringBuilder template1 = new StringBuilder();
        StringBuilder template2 = new StringBuilder();
        StringBuilder template3 = new StringBuilder();
        StringBuilder template4 = new StringBuilder();
        StringBuilder template5 = new StringBuilder();
        StringBuilder template6 = new StringBuilder();

        for (int i = 0; i < param.getA(); i++) {
            template1.append("0");
            template2.append("1");
        }
        for (int i = 0; i < param.getB(); i++) {
            template3.append("01");
            template4.append("10");
        }
        for (int i = 0; i < param.getC(); i++) {
            template5.append("0011");
            template6.append("1100");
        }

        StringBuilder sb = new StringBuilder("<html>");

        if(smallOrBig.indexOf(template1.toString()) == 0 || (!inited && smallOrBig.indexOf(template1.toString()) > -1)) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"0")+"次买【小】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"0")+"次买【小】");
        }

        if(smallOrBig.indexOf(template2.toString()) == 0 || (!inited && smallOrBig.indexOf(template2.toString()) > -1)) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"1")+"次买【大】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"1")+"次买【大】");
        }

        if(singleOrDouble.indexOf(template1.toString()) == 0 || (!inited && singleOrDouble.indexOf(template1.toString()) > -1)) {
            sb.append("<p>" + first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"0")+"次买【单】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"0")+"次买【单】");
        }

        if(singleOrDouble.indexOf(template2.toString()) == 0 || (!inited && singleOrDouble.indexOf(template2.toString()) > -1)) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"1")+"次买【双】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"1")+"次买【双】");
        }

        if((smallOrBig.indexOf(template3.toString()) == 0 || (!inited && smallOrBig.indexOf(template3.toString()) > -1))
                || (smallOrBig.indexOf(template4.toString()) == 0 ||(!inited && smallOrBig.indexOf(template4.toString()) > -1))) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"01","10")+"次【大小一跳】</p>");
            //System.out.println(first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"01","10")+"次【大小一跳】");
        }

        if((singleOrDouble.indexOf(template3.toString()) == 0 || (!inited && singleOrDouble.indexOf(template3.toString()) > -1))
                || (singleOrDouble.indexOf(template4.toString()) == 0 || (!inited && singleOrDouble.indexOf(template4.toString()) > -1))) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"01","10")+"次【单双一跳】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+param.getB()+"次【单双一跳】");
        }

        if((smallOrBig.indexOf(template5.toString()) == 0 || (!inited && smallOrBig.indexOf(template5.toString()) > -1))
                 || (smallOrBig.indexOf(template6.toString()) == 0)|| (!inited && smallOrBig.indexOf(template6.toString())>-1)) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(smallOrBig.toString(),"0011","1100")+"次【大小两跳】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+param.getC()+"次【大小两跳】");
        }

        if((singleOrDouble.indexOf(template5.toString()) == 0 || (!inited && singleOrDouble.indexOf(template5.toString()) > -1))
                 || (singleOrDouble.indexOf(template6.toString()) == 0 || (!inited && singleOrDouble.indexOf(template6.toString()) > -1))) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 连续"+hitCount(singleOrDouble.toString(),"0011","1100")+"次【单双两跳】</p>");
//            System.out.println(first.getId() + "|" + first.getDate() + " 连续"+param.getC()+"次【单双两跳】");
        }

        //
        int sub = param.getM() > param.getR() ? param.getR() : param.getM();
        String lastsb = smallOrBig.substring(0, sub);
        String lastsd = singleOrDouble.substring(0, sub);

        int len1 = lastsb.replace("0","").length();
        int len2 = lastsd.replace("0","").length();

        if (len1 >= param.getN()) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+len1+"次【大】</p>");
            System.out.println(first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+len1+"次【大】");
        }
        if ( param.getM() - len1 >= param.getN()) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+(param.getM() - len1)+"次【小】</p>");
            System.out.println(first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+(param.getM() - len1)+"次【小】");
        }
        if (len2 >= param.getN()) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+len2+"次【双】</p>");
            System.out.println(first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+len2+"次【双】");
        }
        if (param.getM() - len2 >= param.getN()) {
            sb.append("<p>" +first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+(param.getM() - len2)+"次【单】</p>");
            System.out.println(first.getId() + "|" + first.getDate() + " 前"+param.getM()+"次有"+(param.getM() - len2)+"次【单】");
        }

        sb.append("</html>");
        if (sb.length() > 20) {
            RightCornerPopMessage rp = new RightCornerPopMessage(sb.toString());
            //send email
            if (Boolean.parseBoolean(param.getE())) {
                Email e = new Email();
                e.setFrom("jkxyx205@163.com");
                e.setTo(param.getTo().split(";"));
                e.setText(sb.toString() + "<br/>" + Constants.ANS_URL);
                e.setSubject("蛋爽，哈大！！！" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                sender.launch(e);
            }
        }
    }

    private int hitCount(String result, String ... templates) {
        int count = 0;
        for (String template : templates) {
            StringBuilder sb = new StringBuilder();
            sb.append(template);

            while(result.indexOf(sb.toString()) == 0) {
                sb.append(template);
                count++;
            }
            if (count > 0)
                break;
        }
        return count;
    }

    public List<Result> getSourceDate(int pageRow) throws IOException {
        pageRow = pageRow > 15 ? pageRow :15;
        int num = 1;
        if (pageRow > 15) {
            if ((pageRow - 15) % 20 > 0) {
                num = (pageRow - 15)/20 + 2;
            }
        }
        List<Result> list = new ArrayList<Result>(100);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        //post
        int sortIndex = 1;
        for (int i = 0; i < num; i++) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("CurrentPageIndex", String.valueOf(i+1)));

            HttpPost httpPost = new HttpPost(Constants.URL);

            HttpEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);

            httpPost.setEntity(entity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                InputStream instream = responseEntity.getContent();
                try {
                    Document doc = Jsoup.parse(instream, "utf-8", "");
                    Elements trElements = doc.select("#panel > tbody tr");
                    int size = trElements.size();
                    if (size > 0) {
                        for (int index = 1; index <= 20; index++) {
                            Result result = new Result();
                            Element tr = trElements.get(index);
                            Element lastTd = tr.child(6);
                            Element idTd = tr.child(0);
                            Element dateTd = tr.child(1);
                            Element resultTd = tr.child(2);

                            String calStr = (resultTd.select("script").get(0)).childNode(0).outerHtml().trim();
                            String checkStr = (lastTd.select("script").get(0)).childNode(0).outerHtml().trim();

                            if( "2".equals(testCheck(checkStr))) {
                                calculate(calStr,result);

                                result.setSortId(sortIndex++);
                                result.setId(idTd.text());
                                result.setDate(dateTd.text());
                            } else
                                continue;

                            list.add(result);
                        }
                    }
                } finally {
                    instream.close();
                }
            }
        }
        return list.subList(0,pageRow);
    }

    private void calculate(String str,Result result) {
        str = testCheck(str);

        String[] nums = str.trim().replaceAll("\\s+|=", "").split("[+]");

        int resultNum = 0;
        if (ArrayUtils.isNotEmpty(nums)) {
            for (String n : nums) {
                resultNum += Integer.valueOf(n);
            }
        }

        result.setQuestion(str + resultNum);

        result.setResult(resultNum);
        if (resultNum % 2 < 1)
            result.setSingleOrDouble(1);
        if (resultNum >= 14)
            result.setSmallOrBig(1);
    }

    private String testCheck(String s) {
        int start = s.indexOf("'") + 1;
        s = s.replaceFirst("'","*");
        int end = s.indexOf("'");
        String  backStr = s.substring(start, end);
        return  backStr;
    }
}