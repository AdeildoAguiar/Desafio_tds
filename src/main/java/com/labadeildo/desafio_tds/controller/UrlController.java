package com.labadeildo.desafio_tds.controller;
import com.labadeildo.desafio_tds.dto.UrlRequest;
import  com.labadeildo.desafio_tds.dto.UrlResponse;
import com.labadeildo.desafio_tds.model.Url;
import com.labadeildo.desafio_tds.services.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @Operation(description = "Cadastra uma Url")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma Url encurtada"),
            @ApiResponse(responseCode = "404", description = "URL original não encontrada"),
            @ApiResponse(responseCode = "400", description = "URL inválida"),
            @ApiResponse(responseCode = "500", description = "Erro inesperado")
    })

    @PostMapping("/urls")
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody UrlRequest urlRequest) {
        Url url = urlService.createShortUrl(urlRequest.getOriginalUrl());
        UrlResponse urlResponse = new UrlResponse(url.getOriginalUrl(), url.getShortUrl());
        return ResponseEntity.ok(urlResponse);
    }


    @Operation(description = "Redireciona para a URL original")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redireciona para a URL original"),
            @ApiResponse(responseCode = "404", description = "URL encurtada não encontrada")
    })
    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(302).header("Location", originalUrl).build();
    }


    @Operation(description = "Obtém estatísticas de uma URL encurtada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as estatísticas da URL encurtada"),
            @ApiResponse(responseCode = "404", description = "URL encurtada não encontrada")
    })
    @GetMapping("/stats/{shortUrl}")
    public ResponseEntity<Url> getUrlStats(@PathVariable String shortUrl) {
        Url url = urlService.getUrlStatistics(shortUrl);
        return ResponseEntity.ok(url);
    }
}

