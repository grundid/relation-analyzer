package org.osmsurround.ra;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.analyzer.FixedOrderWay;
import org.osmsurround.ra.analyzer.FixedRoundaboutWay;
import org.osmsurround.ra.analyzer.FlexibleOrderWay;
import org.osmsurround.ra.analyzer.FlexibleRoundaboutWay;
import org.osmsurround.ra.analyzer.ISegment;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.social.test.client.MockRestServiceServer;
import org.springframework.social.test.client.RequestMatchers;
import org.springframework.social.test.client.ResponseCreators;
import org.springframework.web.client.RestTemplate;

public abstract class TestUtils {

	public static final long RELATION_12320 = 12320;
	public static final long RELATION_37415 = 37415;
	public static final long RELATION_959757 = 959757;

	private static final Map<Long, Node> NODES = new HashMap<Long, Node>();

	static {
		putNode(1, 0, 0);
		putNode(2, 1, 0);
		putNode(3, 1, 1);
		putNode(4, 2, 2);
		putNode(5, 3, 0);
		putNode(6, 4, 0);

		// Roundabout 1
		putNode(10, 3, 3);
		putNode(11, 2, 2);
		putNode(12, 3, 1);
		putNode(13, 4, 2);

		// Roundabout 2
		putNode(15, 8, 8);
		putNode(16, 7, 7);
		putNode(17, 8, 6);
		putNode(18, 9, 7);
	}

	private static void putNode(long id, float lon, float lat) {
		NODES.put(id, new Node(id, lat, lon));
	}

	public static MockRestServiceServer prepareRestTemplateForRelation(RestTemplate restTemplate, long relationId) {
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_XML);
		mockServer
				.expect(RequestMatchers.requestTo("http://api.openstreetmap.org/api/0.6/relation/" + relationId
						+ "/full"))
				.andExpect(RequestMatchers.method(HttpMethod.GET))
				.andRespond(
						ResponseCreators.withResponse(new ClassPathResource("/relations/" + relationId + ".xml",
								ClassLoader.getSystemClassLoader()), responseHeaders));
		return mockServer;
	}

	public static List<ISegment> asSegments(List<Node>... lists) {
		List<ISegment> segments = new ArrayList<ISegment>();
		for (List<Node> nodes : lists) {
			segments.add(new FixedOrderWay(new Way(0, nodes), false));
		}
		return segments;
	}

	public static Way asWay(long... nodeIds) {
		return new Way(0, asNodes(nodeIds));
	}

	public static ISegment asFixedOrderWay(long... nodeIds) {
		return new FixedOrderWay(new Way(0, asNodes(nodeIds)), false);
	}

	public static ISegment asFlexibleOrderWay(long... nodeIds) {
		return new FlexibleOrderWay(new Way(0, asNodes(nodeIds)));
	}

	public static FlexibleRoundaboutWay asFlexibleRoundaboutWay(long... nodeIds) {
		return new FlexibleRoundaboutWay(new Way(0, asNodes(nodeIds)));
	}

	public static FixedRoundaboutWay asFixedRoundaboutWay(long... nodeIds) {
		return new FixedRoundaboutWay(new Way(0, asNodes(nodeIds)), false);
	}

	public static List<Node> asNodes(long... nodeIds) {
		List<Node> nodes = new ArrayList<Node>();
		for (long id : nodeIds)
			nodes.add(getNode(id));
		return nodes;
	}

	public static Node getNode(long id) {
		return NODES.get(Long.valueOf(id));
	}

	public static void assertContainsNode(Node expected, Collection<ConnectableNode> connectableNodes) {
		boolean contains = false;
		for (ConnectableNode connectableNode : connectableNodes) {
			contains |= connectableNode.contains(expected);
		}
		assertTrue(contains);
	}

}
