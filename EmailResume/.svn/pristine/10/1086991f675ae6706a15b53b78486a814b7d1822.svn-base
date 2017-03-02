package org.emailresume;

import org.emailresume.common.*;

public class Main {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            while (true){
                Thread t = new Thread(new ParseTask());
                t.start();
                t.join();

                Thread.sleep(60000);
                System.gc();
            }
        }catch(Exception e){
            logger.error(Utils.getExceptionString(e));
        }
    }
}
