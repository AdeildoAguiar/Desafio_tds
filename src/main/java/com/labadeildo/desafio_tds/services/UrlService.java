package com.labadeildo.desafio_tds.services;

import com.labadeildo.desafio_tds.exception.InvalidUrlException;
import com.labadeildo.desafio_tds.exception.UrlNotFoundException;
import com.labadeildo.desafio_tds.model.Url;
import com.labadeildo.desafio_tds.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlService {
    @Autowired
    private UrlRepository urlRepository;

    public Url createShortUrl(String originalUrl) {
        validateUrl(originalUrl);
        Url existingUrl = urlRepository.findByOriginalUrl(originalUrl);
        if (existingUrl != null) {
            return existingUrl;
        }

        String shortUrl = generateShortUrl();
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        return urlRepository.save(url);
    }

    public String getOriginalUrl(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            throw new UrlNotFoundException("URL not found");
        }
        url.setAccessCount(url.getAccessCount() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    public Url getUrlStatistics(String shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url == null) {
            throw new UrlNotFoundException("URL not found");
        }
        return url;
    }

    private void validateUrl(String url) {
        if (!url.matches("^(http|https)://.*$")) {
            throw new InvalidUrlException("Invalid URL format");
        }
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
