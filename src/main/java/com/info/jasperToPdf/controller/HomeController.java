package com.info.jasperToPdf.controller;


import com.info.jasperToPdf.pdfService.PdfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;

@RestController
@RequestMapping(value = "/")
public class HomeController {

    public static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    PdfService pdfService;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/")
    public static ModelAndView viewHome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        logger.info("Welcome to home page");
        return modelAndView;
    }
}
