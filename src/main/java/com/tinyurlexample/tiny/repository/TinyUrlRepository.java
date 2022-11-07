package com.tinyurlexample.tiny.repository;

import com.tinyurlexample.tiny.model.entity.TinyUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TinyUrlRepository extends JpaRepository<TinyUrl, Long> {

    Optional<TinyUrl> findByLongUrl(String longUrl);

}
