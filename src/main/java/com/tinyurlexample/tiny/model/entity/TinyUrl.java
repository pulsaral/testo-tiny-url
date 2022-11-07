package com.tinyurlexample.tiny.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "SHORT_URLS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TinyUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LONG_URL")
    private String longUrl;

    @Column(name = "SHORT_URL")
    private String shortUrl;

}
