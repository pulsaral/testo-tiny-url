package com.tinyurlexample.tiny.service.impl;

import com.tinyurlexample.tiny.exception.UrlNotFoundException;
import com.tinyurlexample.tiny.service.UrlProcessor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class UrlProcessorImpl implements UrlProcessor {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ123456789+";

    private static final String SEP_CHAR = "0";

    private static final int MAX_URL_LENGTH = 6;

    @Override
    public String encode(Long id) {
        String alphabet = getAlphabet();
        int base = alphabet.length();

        StringBuilder returnString = new StringBuilder();
        while (id > 0) {
            returnString.append(alphabet.charAt((int) (id % base)));
            id /= base;

        }

        returnString = returnString.reverse();

        if (returnString.length() < MAX_URL_LENGTH) {
            String hash = md5Hash(returnString.toString());
            String tail = hash.substring(0, MAX_URL_LENGTH - returnString.length() - 1);

            returnString.append(SEP_CHAR + tail);
        }

        return returnString.toString();
    }

    @Override
    public Long decode(String shortUrl) throws UrlNotFoundException {
        String encodedId = shortUrl;
        int base = getAlphabet().length();

        if (shortUrl.contains(SEP_CHAR)) {
            int sepIndex = shortUrl.indexOf(SEP_CHAR);

            encodedId = shortUrl.substring(0, sepIndex);
            String hash = md5Hash(encodedId); // Get HASH of ID

            // Get HASH head from URL
            String hashHead = shortUrl.substring(sepIndex + 1);

            if (! hash.startsWith(hashHead)) {
                throw new UrlNotFoundException("Url not found!");
            }
        }

        // Decode from base62 to base 10
        long id = 0;
        for (int i = 0; i < encodedId.length(); i++) {
            id = id * base + getAlphabet().indexOf(encodedId.charAt(i));
        }

        return id;
    }

    @Override
    public String getAlphabet() {
        return ALPHABET;
    }

    private String md5Hash(String s) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(StandardCharsets.UTF_8.encode(s));
            String hash = String.format("%032x", new BigInteger(1, md5.digest()));

            return hash.replaceAll(SEP_CHAR, "");
        } catch (Exception e) {
            // TODO Error should be logged and not printed.
            e.printStackTrace();
        }

        return null;
    }

}
