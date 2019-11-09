package com.info.jasperToPdf.controller;

import com.info.jasperToPdf.pdfService.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/pdf")
public class PdfController {
    public static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    ServletContext context;

    @Autowired
    PdfService pdfService;

    Map<String, Object> modelMap = new HashMap<>();

    @RequestMapping(value = "/")
    public ModelAndView viewHome(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/home")
    public static ModelAndView viewHom(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map generatePdf(HttpServletRequest request, HttpServletResponse response) {
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement().toString();
            modelMap.put(parameterName, request.getParameter(parameterName));
        }
        Map<String, Object> map = new HashMap<>();
        String fileName = "";

        try {
            map = printPdf(modelMap, request, response);
            return map;
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            map.put("e", e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            map.put("e", e.getMessage());
        }
        System.gc();
        return map;
    }


    /*@RequestMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String generatePdf(HttpServletRequest request, HttpServletResponse response) {
        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement().toString();
            modelMap.put(parameterName, request.getParameter(parameterName));
        }
        Map<String, Object> map = new HashMap<>();
        String fileName = "";

        try {
            map = printPdf(modelMap, request, response);
            fileName = map.get("fileName").toString();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        System.gc();
        return fileName;
    }
*/
    public Map printPdf(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> responseMap = new HashMap<>();
        responseMap = pdfService.generatePdfFile(map, request, response);
        return responseMap;
    }

}
