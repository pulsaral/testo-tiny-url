package com.tinyurlexample.tiny.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinyurlexample.tiny.model.request.TinyUrlRequest;
import com.tinyurlexample.tiny.model.response.TinyUrlResponse;
import com.tinyurlexample.tiny.service.TinyUrlService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/short-url")
@RequiredArgsConstructor
public class EncodeUrlController {
	
	private final TinyUrlService urlService;
	
	@PostMapping("")
	public TinyUrlResponse shortUrl(@RequestBody TinyUrlRequest shortUrlRequest) {
		TinyUrlResponse shortUrlResponse = urlService.retrieveShortUrl(shortUrlRequest);
		return shortUrlResponse;
	}

}
