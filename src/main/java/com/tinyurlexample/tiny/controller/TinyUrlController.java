package com.tinyurlexample.tiny.controller;

import com.tinyurlexample.tiny.service.TinyUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class TinyUrlController {

    private final TinyUrlService urlService;

    @RequestMapping(value = "{shortUrl}", method = RequestMethod.GET)
    public ModelAndView method(@PathVariable String shortUrl) {
        String longUrl = urlService.retrieveLongUrl(shortUrl);
        return new ModelAndView("redirect:" + longUrl);
    }

}
