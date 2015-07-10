package com.rick.ui;


import com.rick.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author Anthrax
 *此类负责检测系统的默认浏览器等程序，并负责启动它们
 * @netSite 指定要显示的网址
 */
public class DesktopRuner {
    private Desktop desktop;
    private URI uri;
    private String netSite;
    private Cursor hander;
    /** Creates a new instance of DesktopRuner */
    public DesktopRuner() {
        this.desktop = Desktop.getDesktop();
    }
    /*
    *检测系统是否支持浏览器
    */
    public boolean checkBroswer(){
        if(desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE)){
            return true;
        }
        else{
            return false;
        }
    }
    /*
    *运行默认浏览器，并在其中显示指定网址
    */
    public void runBroswer(){
        netSite = Constants.ANS_URL;
        try {
            uri = new URI(netSite);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        try {
            desktop.browse(uri);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /*
    *改变鼠标形状
    */
    public void changeMouse(JLabel label){
        hander = new Cursor(Cursor.HAND_CURSOR);
        label.setCursor(hander);
    }
}