package com.labadeildo.desafio_tds.tests;

import com.labadeildo.desafio_tds.exception.InvalidUrlException;
import com.labadeildo.desafio_tds.exception.UrlNotFoundException;
import com.labadeildo.desafio_tds.model.Url;
import com.labadeildo.desafio_tds.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private com.labadeildo.desafio_tds.services.UrlService urlService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateShortUrl_ExistingUrl() {
        String originalUrl = "http://example.com";
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl("abcd1234");

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(url);

        Url createdUrl = urlService.createShortUrl(originalUrl);

        assertNotNull(createdUrl);
        assertEquals("http://example.com", createdUrl.getOriginalUrl());
        assertEquals("abcd1234", createdUrl.getShortUrl());
    }

    @Test
    void testCreateShortUrl_NewUrl() {
        String originalUrl = "http://example.com";
        Url url = new Url();
        url.setOriginalUrl(originalUrl);
        url.setShortUrl("abcd1234");

        when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(null);
        when(urlRepository.save(any(Url.class))).thenReturn(url);

        Url createdUrl = urlService.createShortUrl(originalUrl);

        assertNotNull(createdUrl);
        assertEquals("http://example.com", createdUrl.getOriginalUrl());
        assertEquals("abcd1234", createdUrl.getShortUrl());
    }

    @Test
    void testGetOriginalUrl_ExistingUrl() {
        String shortUrl = "abcd1234";
        Url url = new Url();
        url.setOriginalUrl("http://example.com");
        url.setShortUrl(shortUrl);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(url);

        String originalUrl = urlService.getOriginalUrl(shortUrl);

        assertEquals("http://example.com", originalUrl);
    }

    @Test
    void testGetOriginalUrl_NonExistingUrl() {
        String shortUrl = "nonexistingurl";
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(null);

        Exception exception = assertThrows(UrlNotFoundException.class, () -> {
            urlService.getOriginalUrl(shortUrl);
        });

        String expectedMessage = "URL not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testInvalidUrl() {
        String invalidUrl = "invalid-url";

        Exception exception = assertThrows(InvalidUrlException.class, () -> {
            urlService.createShortUrl(invalidUrl);
        });

        String expectedMessage = "Invalid URL format";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}