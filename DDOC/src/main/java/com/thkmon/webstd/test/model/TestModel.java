package com.thkmon.webstd.test.model;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.thkmon.webstd.test.service.TestServiceImpl;

@Controller
public class TestModel {
    
	private static final Logger logger = LoggerFactory.getLogger(TestModel.class);
    
    @Resource(name="testService")
    private TestServiceImpl testService;
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        
        try {
            int result = testService.getTestCount();
            logger.info("result : " + result);
            
            model.addAttribute("serverTime", result);
        
        } catch (Exception e) {
        	logger.error(e.getMessage());
            e.printStackTrace();
        }
        
        return "home";
    }
}