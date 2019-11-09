package com.info.jasperToPdf.pdfService;

import com.info.jasperToPdf.util.DBUtil;
import net.sf.jasperreports.engine.*;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.*;

@Service
public class PdfService {
    public static final Logger logger = LoggerFactory.getLogger(PdfService.class);

    public Map generatePdfFile(Map map, HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pMap = new HashMap<>();
        String[] strings = map.get("P_REPORT_NAME").toString().split("\\.");
        File file1 = new File(map.get("REPORT_DIR").toString());
        String reportDirectory = file1.toString() + File.separator;
        String reportFileName = map.get("P_REPORT_NAME").toString().replaceAll("jasper", "jrxml");
        File PDF_DIR = new File(map.get("PDF_DIR").toString() + File.separator); // Pdf Directory for knit_hcm
        OutputStream outputStream = null;
        String jrxmlFile = "";
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        int hour = date.getHours();

        File hourFile = new File(PDF_DIR + File.separator + hour + File.separator);
        try {
            jrxmlFile = reportDirectory + reportFileName;
            InputStream input = new FileInputStream(new File(jrxmlFile));
            JasperReport jasperReport = JasperCompileManager.compileReport(input); //Generating the report

            Connection conn = DBUtil.dbConnection();

            if (conn == null) {
                pMap.put("e", "Database connection error");
            }
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    map, conn);



            if (!PDF_DIR.exists()) {
                PDF_DIR.mkdirs();
            }
            for (File file : PDF_DIR.listFiles()) {
                if (!file.equals(new File(hourFile + File.separator))) {
                    FileUtils.deleteDirectory(file);
                }
            }

            if (!hourFile.exists()) {
                hourFile.mkdirs();
            }

            fileName = strings[0] + "_" + System.currentTimeMillis() + ".pdf"; // Generated pdf file name

            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    hourFile + File.separator + fileName); // Export PDF to the specific folder in server.

            pMap.put("status", response.getStatus());
            pMap.put("PDF_DIR", hourFile);
            pMap.put("FOLDER", hour);
            pMap.put("fileName", fileName);

            return pMap;
        } catch (IOException e) {
            System.out.println("Msg........" + e.getMessage());
            e.printStackTrace();
            pMap.put("e", e.getMessage());
        } catch (Exception e) {
            System.out.println("Msg........" + e.getMessage());
            logger.info(e.getMessage());
            e.printStackTrace();
            pMap.put("e", e.getMessage());
        } finally {
            if (outputStream != null)
                try {
                    outputStream.close();
                    outputStream.flush();
                } catch (java.lang.IllegalStateException e) {
                    pMap.put("e", e.getMessage());
                    e.printStackTrace();
                } catch (IOException e) {
                    pMap.put("e", e.getMessage());
                    e.printStackTrace();
                }
        }
        return pMap;
    }
}
