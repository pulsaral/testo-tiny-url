package com.tinyurlexample.tiny.service;

import com.tinyurlexample.tiny.exception.UrlNotFoundException;

public interface UrlProcessor {

    String encode(Long id);

    Long decode(String shortUrl) throws UrlNotFoundException;

    String getAlphabet();

}
