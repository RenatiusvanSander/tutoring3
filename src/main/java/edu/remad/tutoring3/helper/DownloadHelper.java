package edu.remad.tutoring3.helper;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;

/**
 * Helper for files to be downloaded
 * 
 * @author edu.remad
 * @since 2026
 */
public final class DownloadHelper {

	/**
	 * private constructor to not instantiate an instance of {@link DownloadHelper}
	 */
	private DownloadHelper() {
		throw new IllegalAccessError(getClass().getSimpleName() + " shall not access private constructor.");
	}
	
	/**
	 * Creates HTTP Headers for a string-encoded filename
	 * 
	 * @param fileName file name
	 * @return {@link HttpHeaders}
	 */
	public static HttpHeaders createHttpHeaders(String fileName) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders
		.setContentDisposition(
				ContentDisposition
				.builder("attachment")
				.filename(fileName)
				.build());
		
		return httpHeaders;
	}
	
}
