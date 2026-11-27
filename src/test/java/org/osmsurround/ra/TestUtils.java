package org.osmsurround.ra;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.test.client.MockRestServiceServer;
import org.springframework.social.test.client.RequestMatchers;
import org.springframework.social.test.client.ResponseCreators;
import org.springframework.web.client.RestTemplate;

public abstract class TestUtils {

	public static final long RELATION_12320_NECKARTAL_WEG = 12320;
	public static final long RELATION_37415 = 37415;
	public static final long RELATION_959757_LINE_10 = 959757;
	public static final long RELATION_954995_LINE_11 = 954995;
	public static final long RELATION_5504331_EP_EXPRESS = 5504331;
	public static final long RELATION_2807115_TRAM_15 = 2807115;
	public static final long RELATION_73714_BUS = 73714;
	public static final long RELATION_1903644_FERRY_F2 = 1903644;

	public static MockRestServiceServer prepareRestTemplateForRelation(RestTemplate restTemplate, long relationId) {
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_XML);
		mockServer.expect(RequestMatchers.requestTo("https://api.openstreetmap.org/api/0.6/relation/" + relationId
						+ "/full"))
				.andExpect(RequestMatchers.method(HttpMethod.GET))
				.andRespond(
						ResponseCreators.withResponse(new ClassPathResource("/relations/" + relationId + ".xml",
								ClassLoader.getSystemClassLoader()), responseHeaders));
		return mockServer;
	}

}
