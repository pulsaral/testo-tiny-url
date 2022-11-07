package com.tinyurlexample.tiny.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TinyUrlResponse {
	
	private String shortUrl;

	private long numberOfClicks;

	private long numberOfShorts;

}
