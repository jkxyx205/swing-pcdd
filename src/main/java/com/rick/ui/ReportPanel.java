package com.rick.ui;

import com.rick.model.Param;
import com.rick.model.Result;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2015/7/9.
 */
public class ReportPanel extends JPanel {
    private Param param;

    private List<Result> list;

    private  JPanel panel = new JPanel();

    public ReportPanel(Param param, List<Result> list) {
        this.param = param;
        this.list = list;

        //解决中文乱码问题
        StandardChartTheme theme = new StandardChartTheme("unicode") {
            public void apply(JFreeChart chart) {
                chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
                super.apply(chart);
            }
        };
        theme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 14));
        theme.setLargeFont(new Font("宋体", Font.PLAIN, 14));
        theme.setRegularFont(new Font("宋体", Font.PLAIN, 12));
        theme.setSmallFont(new Font("宋体", Font.PLAIN, 10));
        ChartFactory.setChartTheme(theme);

        this.setLayout(null);

        panel.setLayout(null);

        refresh(param,list);
        //主要是这句代码，设置panel的首选大小，同时保证宽高大于JScrollPane的宽高，这样下面的JScrollPane才会出现滚动条
        panel.setPreferredSize(new Dimension(600,850));

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBounds(0, 0, 590, 500);
        this.add(scrollPane);
    }

    public void refresh(Param param, List<Result> list) {
        this.param = param;
        this.list = list;
        panel.removeAll();
        panel.add(createChar1(param, list));
        panel.add(createChar2(param, list));
        panel.add(createChar3(param, list));
        panel.add(createChar4(param, list));
    }

    private ChartPanel createChar1(Param param, List<Result> list) {
        int b = 0;
        int s = 0;
        for (Result result : list) {
            if (result.getSingleOrDouble() == 1) {
                b++;
            } else {
                s++;
            }
        }

        DefaultPieDataset dpd=new DefaultPieDataset();
        dpd.setValue("单"+s+"", s);
        dpd.setValue("双"+b+"", b);

        JFreeChart chart= ChartFactory.createPieChart("前"+param.getR()+"单双", dpd, true, true, false);

        ChartPanel chartPanel=new ChartPanel(chart);
        chartPanel.setBounds(25,280,250,250);
        return chartPanel;
    }

    private ChartPanel createChar2(Param param, List<Result> list) {
        int b = 0;
        int s = 0;
        for (Result result : list) {
            if (result.getSmallOrBig() == 1) {
                b++;
            } else {
                s++;
            }
        }
        DefaultPieDataset dpd=new DefaultPieDataset();
        dpd.setValue("大"+b+"", b);
        dpd.setValue("小"+s+"", s);

        JFreeChart chart= ChartFactory.createPieChart("前"+param.getR()+"大小", dpd, true, true, false);

        ChartPanel chartPanel=new ChartPanel(chart);
        chartPanel.setBounds(310,280,250,250);
        return chartPanel;
    }

    private ChartPanel createChar3(Param param, List<Result> list) {
        int b1 = 0;
        int s1 = 0;
        int b2 = 0;
        int s2 = 0;

        for (Result result : list) {
            if (result.getSmallOrBig() == 1 && result.getSingleOrDouble() == 0) {
                b1++;
            } else  if (result.getSmallOrBig() == 0 && result.getSingleOrDouble() == 0) {
                s1++;
            } else  if (result.getSmallOrBig() == 1 && result.getSingleOrDouble() == 1) {
                b2++;
            } else if (result.getSmallOrBig() == 0 && result.getSingleOrDouble() == 1) {
                s2++;
            }
        }

        DefaultPieDataset dpd=new DefaultPieDataset();
        dpd.setValue("大单"+b1+"", b1);
        dpd.setValue("小单"+s1+"", s1);
        dpd.setValue("大双"+b2+"", b2);
        dpd.setValue("小双"+s2+"", s2);

        JFreeChart chart= ChartFactory.createPieChart("前"+param.getR()+"大小单双组合", dpd, true, true, false);
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL

        ChartPanel chartPanel=new ChartPanel(chart);
        chartPanel.setBounds(25,550,250,250);
        return chartPanel;
    }

    private ChartPanel createChar4(Param param, List<Result> list) {
        Map<String,Integer> map= new LinkedHashMap<String,Integer>();
        for (int i = 0; i <=27; i++) {
            map.put(String.valueOf(i), 0);
        }

        for (Result result : list) {
            String r = String.valueOf(result.getResult());
            int count = map.get(r);
            count++;
            map.put(r,count);
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String,Integer> en : map.entrySet()) {
            dataset.setValue(en.getValue(), en.getKey(), en.getKey());
        }

        JFreeChart jfreeChart = ChartFactory.createBarChart("",
                "数字分布", "出现次数", dataset, PlotOrientation.VERTICAL, true, false,
                false);

        jfreeChart.setTitle("前"+param.getR()+"次数字分布图");
//        jfreeChart.setTitle(new TextTitle("数字分布图", new Font("宋体", Font.BOLD
//                + Font.ITALIC, 20)));
//        CategoryPlot plot = (CategoryPlot) jfreeChart.getPlot();
//        CategoryAxis categoryAxis = plot.getDomainAxis();
//        categoryAxis.setLabelFont(new Font("仿宋", Font.ROMAN_BASELINE, 12));

        ChartPanel chartPanel=new ChartPanel(jfreeChart);
        chartPanel.setBounds(25,5,550,250);
        return chartPanel;

    }
}
