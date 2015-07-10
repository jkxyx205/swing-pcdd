package com.rick;


import com.rick.ui.MainFrame;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * Created by Administrator on 2015/7/6.
 */

@Component
public class ScheduledTask {
    @Resource
    private MainFrame mainFrame;

    @Scheduled(cron = "30 */5 * * * ?")
//    @Scheduled(cron = "*/10 * * * * ?")
    public void execute() {
        mainFrame.refresh();
    }
}
