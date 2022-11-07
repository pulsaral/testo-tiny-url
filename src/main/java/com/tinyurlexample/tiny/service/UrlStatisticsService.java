package com.tinyurlexample.tiny.service;

import com.tinyurlexample.tiny.model.UrlAction;
import com.tinyurlexample.tiny.model.entity.TinyUrl;
import com.tinyurlexample.tiny.model.response.statistics.UrlStatistics;
import org.springframework.stereotype.Service;

/*
 * This service would be a service class to communicate with another API
 * Because keeping statistics requires time, it is better to have these actions
 * executed asynchronously. We can use Spring WebFlux to make ASYNC REST calls
 * to this API which I'm calling Statistics-API.
 */
@Service
public class UrlStatisticsService {

    /*
        In case of SHORTENING action, we need to return the statistics alongside the short url.
        For this reason we cannot use an aysnc call, also shorting a URL is not that time sensitive as
        clicking one.
     */
    public UrlStatistics updateTinyUrlStatistics(TinyUrl tinyUrl, UrlAction urlAction) {
        return retrieveUrlStatistics(tinyUrl);
    }

    // TODO Async call to statistics-api
    public UrlStatistics updateTinyUrlStatisticsAsync(TinyUrl tinyUrl, UrlAction urlAction) {
        return retrieveUrlStatistics(tinyUrl);
    }

    // TODO Async call to statistics-api
    public UrlStatistics retrieveUrlStatistics(TinyUrl tinyUrl) {
        UrlStatistics urlStatistics = new UrlStatistics();
        urlStatistics.setNumberOfClicks(1232);
        urlStatistics.setNumberOfShorts(64);

        return urlStatistics;
    }

}
