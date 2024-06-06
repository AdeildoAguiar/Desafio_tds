package com.labadeildo.desafio_tds.repository;

import com.labadeildo.desafio_tds.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Url findByShortUrl(String shortUrl);
    Url findByOriginalUrl(String originalUrl);
}
