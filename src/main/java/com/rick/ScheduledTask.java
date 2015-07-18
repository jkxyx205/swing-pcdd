package com.rick;


import com.rick.ui.MainFrame;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2015/7/6.
 */

@Component
public class ScheduledTask {
    @Resource
    private MainFrame mainFrame;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        try {
            Thread.sleep(10000);

            System.out.println("execute at " + sdf.format(new Date()));
            mainFrame.refresh();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
