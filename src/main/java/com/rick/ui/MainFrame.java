package com.rick.ui;

import com.rick.Constants;
import com.rick.DataFetch;
import com.rick.model.Param;
import com.rick.model.Result;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class MainFrame extends JFrame {
    private JTabbedPane tp;

    private DataPanel dataPanel;

    private ReportPanel reportPanel;

    private SettingPanel settingPanel;

    @Resource
    private DataFetch fetch;

    public MainFrame() {
        super(Constants.SYSTEM_NAME);
        init();
        //refresh();
    }

    public void refresh() {
        try {
            Param param = Param.getParam();
            List<Result> list = fetch.getSourceDate(param.getR());
            //更新面板

            fetch.fetch(true,param,list);

            dataPanel.refresh(param, list);
            reportPanel.refresh(param, list);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            initEnv();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tp = new JTabbedPane();
        Param param = null;
        try {
            param = Param.getParam();
            List<Result> list = new DataFetch().getSourceDate(param.getR());
            dataPanel = new DataPanel(param,list);
            reportPanel = new ReportPanel(param, list);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        settingPanel = new SettingPanel();
        settingPanel.setMainFrame(this);

        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tp.addTab("开奖信息", new ImageIcon(MainFrame.class.getClassLoader().getResource("images/db.png")), dataPanel);
        tp.addTab("数据分析",new ImageIcon(MainFrame.class.getClassLoader().getResource("images/report.png")), reportPanel);
        tp.addTab("系统设置",new ImageIcon(MainFrame.class.getClassLoader().getResource("images/settings.png")), settingPanel);

        this.setSize(600, 500);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().add(tp, BorderLayout.CENTER);
        this.setIconImage(new ImageIcon(MainFrame.class.getClassLoader().getResource("images/egg.png")).getImage());
        this.setVisible(true); //这个放在最后*/
    }

    private void initEnv() throws IOException {
        //获取参数
        File file = new File(Constants.INI_PATH);
        if (!file.exists()) {
            file.createNewFile();
            String defaultParam = "#单双大小\n" +
                    "a=5\n" +
                    "#大小单双一跳\n" +
                    "b=5\n" +
                    "#大小单双两跳\n" +
                    "c=5\n" +
                    "#前多少把出现多少次大小单双\n" +
                    "m=12\n" +
                    "n=10\n" +
                    "#邮件设置\n" +
                    "#是否发邮件，true/false\n" +
                    "e=true\n" +
                    "#收件人，多个用;隔开\n" +
                    "to=jkxyx205@163.com;154894898@qq.com\n" +
                    "#显示多少行开奖信息\n" +
                    "r=50\n";
            FileUtils.writeStringToFile(file, defaultParam, "utf-8");
        }
    }

    public static void main(String[] args) throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
