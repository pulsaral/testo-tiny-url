package com.tinyurlexample.tiny.service;

import com.tinyurlexample.tiny.exception.InvalidUrlException;
import com.tinyurlexample.tiny.exception.UrlNotFoundException;
import com.tinyurlexample.tiny.model.UrlAction;
import com.tinyurlexample.tiny.model.entity.TinyUrl;
import com.tinyurlexample.tiny.model.request.TinyUrlRequest;
import com.tinyurlexample.tiny.model.response.TinyUrlResponse;
import com.tinyurlexample.tiny.model.response.statistics.UrlStatistics;
import com.tinyurlexample.tiny.repository.TinyUrlRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TinyUrlService {

    private final UrlProcessor urlProcessor;

    private final TinyUrlRepository urlRepository;

    private final UrlStatisticsService urlStatisticsService;

    @Transactional
    public TinyUrlResponse retrieveShortUrl(TinyUrlRequest shortUrlRequest) {
        validateUrl(shortUrlRequest.getLongUrl());

        // Check for existing URLs
        Optional<TinyUrl> tinyUrlOptional = getTinyUrlByLongUrl(shortUrlRequest.getLongUrl());
        if (tinyUrlOptional.isPresent()) {
            UrlStatistics urlStatistics = urlStatisticsService
                    .updateTinyUrlStatistics(tinyUrlOptional.get(), UrlAction.SHORTENED);

            TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
                    .shortUrl(tinyUrlOptional.get().getShortUrl())
                    .numberOfShorts(urlStatistics.getNumberOfShorts())
                    .numberOfClicks(urlStatistics.getNumberOfClicks())
                    .build();
            return tinyUrlResponse;
        }

        TinyUrl url = new TinyUrl();
        url.setLongUrl(shortUrlRequest.getLongUrl());
        url = urlRepository.save(url);

        // Get ID and encode it
        String encodedId = urlProcessor.encode(url.getId());
        url.setShortUrl(encodedId);

        // Save encoded ID
        urlRepository.save(url);

        UrlStatistics urlStatistics = urlStatisticsService.updateTinyUrlStatistics(url, UrlAction.SHORTENED);
        TinyUrlResponse tinyUrlResponse = TinyUrlResponse.builder()
                .shortUrl(url.getShortUrl())
                .numberOfShorts(urlStatistics.getNumberOfShorts())
                .numberOfClicks(urlStatistics.getNumberOfClicks())
                .build();
        return tinyUrlResponse;
    }

    public String retrieveLongUrl(String shortUrl) {
        Long id = urlProcessor.decode(shortUrl);
        TinyUrl url = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException("Url not found."));

        // Calling async because time is important in CLICKED action
        urlStatisticsService.updateTinyUrlStatisticsAsync(url, UrlAction.CLICKED);
        return url.getLongUrl();
    }

    private void validateUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        if (! urlValidator.isValid(url)) {
            throw new InvalidUrlException("Requested URL is invalid.");
        }
    }

    private Optional<TinyUrl> getTinyUrlByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

}
