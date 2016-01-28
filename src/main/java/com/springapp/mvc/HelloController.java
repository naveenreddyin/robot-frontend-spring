package com.springapp.mvc;

import com.springapp.mvc.model.FormAttributes;
import com.springapp.mvc.model.KeyValueObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    DataParseHelper dataParseHelper;

    private static final Log logger = LogFactory.getLog(HelloController.class);
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

        FormAttributes formAttributes = new FormAttributes();
        formAttributes.setName("name is here");
		model.addAttribute("message", "Hello world!");
        model.addAttribute("formdata", formAttributes);
		return "hello";
	}

    @RequestMapping(method =  RequestMethod.POST)
    public String formPosted(@ModelAttribute("formdata") FormAttributes message, ModelMap modelMap){
//        modelMap.addAttribute("message",message+" received");
        logger.info("data "+message.getName());
//        message.setName(message.getName()+" received");
        modelMap.addAttribute("formdata", message);
        List<KeyValueObject> keyValueObjectList = dataParseHelper.parseFinancialData(message.getName());
        modelMap.addAttribute("results", keyValueObjectList);
        return "hello";
    }
}