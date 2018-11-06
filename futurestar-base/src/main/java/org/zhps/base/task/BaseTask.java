package org.zhps.base.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 * <p/>
 * Created on 2016/12/28.
 */
public class BaseTask {

    public static Timer startTask(TimerTask timerTask, Date date, Long interval){
        if(date.before(new Date())){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
        }
        Timer timer = new Timer();
        timer.schedule(timerTask, date, interval);
        return timer;
    }

    public static Timer startTask(TimerTask timerTask, Long delay, Long interval){
        Timer timer = new Timer();
        timer.schedule(timerTask, delay, interval);
        return timer;
    }

    public static Timer startTask(TimerTask timerTask, Long delay){
        Timer timer = new Timer();
        timer.schedule(timerTask, delay);
        return timer;
    }

    public static int stopTask(TimerTask timer){
        try{
            timer.cancel();
            return 0;
        }catch (Exception e){
            return -1;
            //todo add log
        }
    }
}
