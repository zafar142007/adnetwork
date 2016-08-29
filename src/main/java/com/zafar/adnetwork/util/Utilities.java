package com.zafar.adnetwork.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import com.zafar.adnetwork.domain.PageMetrics;
import com.zafar.adnetwork.domain.Response;
import com.zafar.adnetwork.model.CrawledInfo;

@Component
public class Utilities {
	
	@Value("${timeout.request.ms:15000}")
	private long requestTimeout;
	
	private static Logger logger=LoggerFactory.getLogger(Utilities.class);
	

	
	/**
	 * factory method
	 * @param <T>
	 * @return an empty result with error scenarios prefilled with timeout
	 */
	public DeferredResult<Response<String>> emptyResponseWithTimeout(){
		DeferredResult<Response<String>> result=
				new DeferredResult<Response<String>>(requestTimeout,
						new Response<String>(Constants.STATUS_TIMEOUT));
		result.onCompletion(() -> {
			//put something to do here in case of completion event
			//such as kafka lognew Response<PageMetrics>
			logger.debug("result received.");
		});
		result.onTimeout(() -> {
			//put something to do here in case of timeout
		});
		return result;
	}
	public DeferredResult<String> emptyStringResponseWithTimeout(){
		DeferredResult<String> result=
				new DeferredResult<String>(requestTimeout,"");
		result.onCompletion(() -> {
			//put something to do here in case of completion event
			//such as kafka lognew Response<PageMetrics>
			logger.debug("result received.");
		});
		result.onTimeout(() -> {
			//put something to do here in case of timeout
		});
		return result;
	}
	public DeferredResult<Response<CrawledInfo>> emptyCrawlResultWithTimeout(){
		DeferredResult<Response<CrawledInfo>> result=
				new DeferredResult<Response<CrawledInfo>>(requestTimeout,
						new Response<CrawledInfo>(Constants.STATUS_TIMEOUT));
		result.onCompletion(() -> {
			//put something to do here in case of completion event
			//such as kafka lognew Response<PageMetrics>
			logger.debug("result received.");
		});
		result.onTimeout(() -> {
			//put something to do here in case of timeout
		});
		return result;
	}
	/**
	 * An empty response factory method
	 * @return an empty result without timeout
	 */
	public DeferredResult<Response<PageMetrics>> emptyResponse(){
		DeferredResult<Response<PageMetrics>> result=new DeferredResult<Response<PageMetrics>>();
		result.onCompletion(() -> {
			//put something to do here in case of completion event
			//such as kafka log
			logger.debug("result received.");
		}
		);
		return result;
	}

}

