package com.rick.model;

import com.rick.Constants;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import javax.swing.*;
import java.io.File;

/**
 * Created by Administrator on 2015/7/7.
 */
public class Param {
    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public static Param getParam() throws ConfigurationException {
        Configuration config = new PropertiesConfiguration(new File(Constants.INI_PATH));
        int a = Integer.parseInt(config.getString("a"));
        int b = Integer.parseInt(config.getString("b"));
        int c = Integer.parseInt(config.getString("c"));

        int m = Integer.parseInt(config.getString("m"));
        int n = Integer.parseInt(config.getString("n"));

        String e = config.getString("e");
        String to = config.getString("to");
        int r = Integer.parseInt(config.getString("r"));

        Param param = new Param();
        param.setA(a);
        param.setB(b);
        param.setC(c);
        param.setM(m);
        param.setN(n);
        param.setE(e);
        param.setTo(to);
        param.setR(r);
        return param;
    }

    public static void saveParam(Param param) throws ConfigurationException {
        PropertiesConfiguration  config = new PropertiesConfiguration(new File(Constants.INI_PATH));
        config.setProperty("a",param.getA());
        config.setProperty("b",param.getB());
        config.setProperty("c",param.getC());
        config.setProperty("e",param.getE());
        config.setProperty("m",param.getM());
        config.setProperty("n",param.getN());
        config.setProperty("r",param.getR());
        config.setProperty("to",param.getTo());
        config.save();
        JOptionPane.showMessageDialog(null, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private int a;
    private int b;
    private int c;

    private int m;
    private int n;

    private String e;
    private String to;

    private int r;

}
