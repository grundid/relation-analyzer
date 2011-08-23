package org.osmsurround.ra;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Way;
import org.osmsurround.ra.graph.Graph;
import org.osmsurround.ra.graph.GraphCreator;
import org.osmsurround.ra.graph.IntersectionNode;
import org.osmsurround.ra.segment.ConnectableSegment;
import org.osmsurround.ra.segment.FixedWay;
import org.osmsurround.ra.segment.FlexibleWay;
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
	public static final long RELATION_954995_LINE_11 = 954995;

	private static final Map<Long, Node> NODES = new HashMap<Long, Node>();

	static {
		for (int x = 0; x < 30; x++) {
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

	public static List<ConnectableSegment> asSegments(List<Node>... lists) {
		List<ConnectableSegment> segments = new ArrayList<ConnectableSegment>();
		for (List<Node> nodes : lists) {
			segments.add(new FlexibleWay(nodes));
		}
		return segments;
	}

	public static Way asWay(long... nodeIds) {
		return new Way(0, asNodes(nodeIds));
	}

	public static FlexibleWay asFixedOrderWay(long... nodeIds) {
		return new FixedWay(asNodes(nodeIds));
	}

	public static FlexibleWay asFlexibleOrderWay(long... nodeIds) {
		return new FlexibleWay(asNodes(nodeIds));
	}

	public static ConnectableSegment asRoundaboutWay(long... nodeIds) {
		return new RoundaboutWay(asNodes(nodeIds));
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

	public static void assertContainsNodeId(Collection<IntersectionNode> nodes, long nodeId) {
		for (IntersectionNode node : nodes) {
			if (node.getNode().getId() == nodeId)
				return;
		}
		fail("Node " + nodeId + " not found");
	}

	public static void assertContainsOnlyNodeIds(Collection<IntersectionNode> nodes, long... nodeIds) {
		assertEquals(nodeIds.length, nodes.size());
		for (IntersectionNode node : nodes) {

			boolean found = false;
			for (long nodeId : nodeIds) {
				if (node.getNode().getId() == nodeId)
					found = true;
			}
			if (!found) {
				fail("Node " + node.getNode() + " was not expected");
			}
		}
	}

	public static void assertContainsNodeIds(Collection<ConnectableNode> connectableNodes, long... nodeIds) {

		Set<Long> idSet = new HashSet<Long>();
		for (long id : nodeIds)
			idSet.add(id);
		for (ConnectableNode connectableNode : connectableNodes) {

			for (Iterator<Node> it = connectableNode.getNodesIterator(); it.hasNext();) {
				assertTrue(idSet.contains(it.next().getId()));
			}
		}
	}

	public static void assertNodesInOrder(long[] expected, Collection<Node> actual) {
		Iterator<Node> it = actual.iterator();
		for (long id : expected)
			assertEquals(TestUtils.getNode(id), it.next());
	}

	public static Collection<IntersectionNode> executeAndGetLeaves(SegmentsBuilder segmentsBuilder) {
		return executeAndGetLeaves(segmentsBuilder.getSegments());
	}

	public static Collection<IntersectionNode> executeAndGetLeaves(List<ConnectableSegment> segments) {
		GraphCreator intersectionNodeWebCreator = new GraphCreator(segments);
		Graph intersectionWeb = intersectionNodeWebCreator.createGraph();
		return intersectionWeb.getLeaves();
	}

	public static List<ConnectableSegment> asList(Object... objects) {
		List<ConnectableSegment> list = new ArrayList<ConnectableSegment>();
		for (Object o : objects)
			list.add((ConnectableSegment)o);
		return list;
	}

}
