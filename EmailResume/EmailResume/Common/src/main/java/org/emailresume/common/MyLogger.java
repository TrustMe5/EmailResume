package org.emailresume.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;


public class MyLogger {
    static MyLogger m_instance = new MyLogger();

    Logger m_log;
    FileHandler m_fileHandler;

    static public MyLogger getInstance() {
        return m_instance;
    }

    private MyLogger() {
        try {
            m_log = Logger.getLogger("EmailResume");
            m_fileHandler = new FileHandler("E:/test.log");
            m_fileHandler.setLevel(Level.ALL);
            m_log.addHandler(m_fileHandler);
            //m_fileHandler.setFormatter(new SimpleFormatter());
            m_fileHandler.setFormatter(new MyLogHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeInfo(String str) {
        try {
            m_log.log(Level.INFO, str);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeWarning(String str) {
        try {
            m_log.log(Level.WARNING, str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class MyLogHandler extends Formatter {
    @Override
    public String format(LogRecord record) {
        Date date = new Date();
        date.setTime(record.getMillis());
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sFormat.setLenient(false);
        return sFormat.format(date) + "  " + record.getLevel() + ":" + record.getMessage() + "\n";
    }
}