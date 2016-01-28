package com.springapp.mvc;

import com.springapp.mvc.model.Classify;
import com.springapp.mvc.model.FormAttributes;
import com.springapp.mvc.model.KeyValueObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
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
        RestTemplate rest = new RestTemplate();


        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter stringHttpMessageConverternew = new StringHttpMessageConverter();
        List<HttpMessageConverter<?>> httpMessageConverterList = new ArrayList<HttpMessageConverter<?>>();
//        converters.add(new MappingJackson2HttpMessageConverter());
        httpMessageConverterList.add(stringHttpMessageConverternew);
        httpMessageConverterList.add(formHttpMessageConverter);
        httpMessageConverterList.add(new MappingJackson2HttpMessageConverter());

        rest.setMessageConverters(httpMessageConverterList);

//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.postForObject()

        String url = "http://robotdjango.herokuapp.com/classify/";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("text", message.getName());

        Classify result = rest.postForObject(url, map, Classify.class);
        logger.info("rest response "+result.getResult());

//        logger.info("data "+message.getName());
//        message.setName(message.getName()+" received");
        modelMap.addAttribute("classify",result.getResult());
        modelMap.addAttribute("formdata", message);
        List<KeyValueObject> keyValueObjectList = dataParseHelper.parseFinancialData(message.getName());
        modelMap.addAttribute("results", keyValueObjectList);
        return "hello";
    }
}