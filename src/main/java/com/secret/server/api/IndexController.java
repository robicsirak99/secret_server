package com.secret.server.api;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

//controller for the index page
@Controller
public class IndexController {

    //load the index page
    @RequestMapping(value = "/", method = GET, produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView loadIndexPage() {
        return new ModelAndView("index");
    }

}
