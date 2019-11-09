package com.info.jasperToPdf.util;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

@Service
public class LogUtil {

    public static void main(String[] args) {
        String d = "dfgdg";

//        logFile(d);

    }


    public static void logFile(String s) {
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        String d = "dfgdg";
        try {
            System.out.println("Present Project Directory : " + System.getProperty("user.dir"));

            String rootDir = System.getProperty("user.dir");
            rootDir = rootDir + File.separator + "logs";
            // Make sure the output directory exists.
            File outDir = new File(rootDir);
            outDir.mkdirs();

            // This block configure the logger with handler and formatter
            fh = new FileHandler(outDir + "/LogFile.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.info(s);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info(s);
    }


}
