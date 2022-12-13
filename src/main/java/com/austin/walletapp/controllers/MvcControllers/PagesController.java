package com.austin.walletapp.controllers.MvcControllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/views")
public class PagesController {

    @GetMapping("/")
    public String homePage() {
        return "index";
    }
}
