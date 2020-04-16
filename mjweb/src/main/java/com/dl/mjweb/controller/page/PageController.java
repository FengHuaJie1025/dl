package com.dl.mjweb.controller.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(value = "ppp")
public class PageController {

    public String index() {
        return "index";
    }

}
