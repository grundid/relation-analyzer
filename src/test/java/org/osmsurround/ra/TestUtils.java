package org.osmsurround.ra;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.osmsurround.ra.segment.FixedOrderWay;
import org.osmsurround.ra.segment.FlexibleOrderWay;
import org.osmsurround.ra.segment.ISegment;
import org.osmsurround.ra.segment.RoundaboutWay;
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

	private static final Map<Long, Node> NODES = new HashMap<Long, Node>();

	static {
		for (int x = 0; x < 20; x++) {
			putNode(x, x, x);
		}
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

	public static RoundaboutWay asFlexibleRoundaboutWay(long... nodeIds) {
		return new RoundaboutWay(new Way(0, asNodes(nodeIds)));
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

	public static void assertContainsNode(Node expected, ConnectableNode connectableNode) {
		assertTrue(connectableNode.contains(expected));
	}

	public static void assertContainsNode(Node expected, Collection<ConnectableNode> connectableNodes) {
		boolean contains = false;
		for (ConnectableNode connectableNode : connectableNodes) {
			contains |= connectableNode.contains(expected);
		}
		assertTrue(contains);
	}

	public static void assertNodesInOrder(long[] expected, Collection<Node> actual) {
		Iterator<Node> it = actual.iterator();
		for (long id : expected)
			assertEquals(TestUtils.getNode(id), it.next());
	}

	public static void assertOppositeNode(ISegment segment, long startNodeId, long... expectedOppositeNodeIds) {
		ConnectableNode oppositeNode = segment.getOppositeNode(new ConnectableNode(getNode(startNodeId)));
		for (Node node : asNodes(expectedOppositeNodeIds))
			assertTrue(oppositeNode.contains(node));
	}
}
