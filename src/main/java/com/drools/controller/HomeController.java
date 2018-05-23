package com.drools.controller;

import com.drools.rules.HelloRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    @Autowired
    HelloRuleService helloRuleService;

    @ResponseBody
    @RequestMapping(method = GET, produces = "application/json",path = "/Helloworld1")
    public HelloRuleService.Message HelloWorld1() {

        return this.helloRuleService.HelloWorld1();
    }
    @ResponseBody
    @RequestMapping(method = GET, produces = "application/json",path = "/Helloworld2")
    public HelloRuleService.Message HelloWorld2() {

        return this.helloRuleService.HelloWorld2();
    }
}