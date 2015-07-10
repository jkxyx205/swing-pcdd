package com.rick.ui;

import com.rick.model.Param;
import org.apache.commons.configuration.ConfigurationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends JScrollPane implements ActionListener {
    private JTextField field1;
    private JTextField field2;
    private JTextField field3;
    private JTextField field4;
    private JTextField field5;
    private JTextField field6;
    private JTextField field7;

    private JRadioButton yesRadio;
    private JRadioButton noRadio;

    public MainFrame mainFrame;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public SettingPanel() {
        init();
    }

    private void init() {
        this.setLayout(null);

        this.setBorder(BorderFactory.createTitledBorder("修改配置信息:"));

        JLabel label1 = new JLabel("单双大小", JLabel.RIGHT);
        label1.setBounds(62, 20, 169, 15);

        field1 = new JTextField();
        field1.setBounds(240,20,30,20);

        JLabel label2 = new JLabel("单双大小一跳", JLabel.RIGHT);
        label2.setBounds(62, 40, 169, 15);
        field2 = new JTextField();
        field2.setBounds(240,40,30,20);

        JLabel label3 = new JLabel("单双大小两跳", JLabel.RIGHT);
        label3.setBounds(62, 60, 169, 15);
        field3 = new JTextField();
        field3.setBounds(240,60,30,20);

        JLabel label4 = new JLabel("前【多少把】出现【多少】次大小单双");
        label4.setBounds(10, 80, 269, 15);
        field4 = new JTextField();
        field4.setBounds(240,80,30,20);
        field5 = new JTextField();
        field5.setBounds(280,80,30,20);


        JLabel label5 = new JLabel("邮件提醒", JLabel.RIGHT);
        label5.setBounds(62, 100, 169, 15);

        JLabel message2 = new JLabel("*多个邮箱用;隔开");
        message2.setBounds(360, 100, 120, 20);
        message2.setForeground(Color.red);



        yesRadio = new JRadioButton("开启");
        yesRadio.setBounds(240, 100, 60, 20);
        noRadio = new JRadioButton("关闭");
        noRadio.setBounds(300, 100, 60, 20);
        ButtonGroup bg=new ButtonGroup();
        bg.add(yesRadio);
        bg.add(noRadio);

        field7 = new JTextField();
        field7.setBounds(240,120,300,20);

        JLabel label6 = new JLabel("显示开奖记录数", JLabel.RIGHT);
        label6.setBounds(62, 140, 169, 15);
        field6 = new JTextField();
        field6.setBounds(240,140,30,20);

        JLabel message = new JLabel("*最好<200");
        message.setBounds(280, 140, 169, 15);
        message.setForeground(Color.red);

        JButton okBtn = new JButton("确定");
        okBtn.setBounds(200,180,60,20);
        okBtn.addActionListener(this);

        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(label4);
        this.add(label5);
        this.add(label6);

        this.add(message);
        this.add(message2);

        this.add(field1);
        this.add(field2);
        this.add(field3);
        this.add(field4);
        this.add(field5);
        this.add(field6);
        this.add(field7);
        this.add(yesRadio);
        this.add(noRadio);

        this.add(okBtn);

        fillData();
    }

    private void fillData()  {
        try {
            Param param = Param.getParam();

            field1.setText(String.valueOf(param.getA()));
            field2.setText(String.valueOf(param.getB()));
            field3.setText(String.valueOf(param.getC()));
            field4.setText(String.valueOf(param.getM()));
            field5.setText(String.valueOf(param.getN()));
            field6.setText(String.valueOf(param.getR()));
            field7.setText(param.getTo());

            if ("true".equals(param.getE())) {
                yesRadio.setSelected(true);
            } else {
                noRadio.setSelected(true);
            }

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //CHECK

        Param param = new Param();
        param.setA(Integer.parseInt(field1.getText()));
        param.setB(Integer.parseInt(field2.getText()));
        param.setC(Integer.parseInt(field3.getText()));
        param.setM(Integer.parseInt(field4.getText()));
        param.setN(Integer.parseInt(field5.getText()));
        param.setR(Integer.parseInt(field6.getText()));
        param.setTo(field7.getText());

        if (yesRadio.isSelected()) {
            param.setE("true");
        } else {
            param.setE("false");
        }

        try {
            Param.saveParam(param);
            getMainFrame().refresh();
        } catch (ConfigurationException e1) {
            e1.printStackTrace();
        }
    }
}
