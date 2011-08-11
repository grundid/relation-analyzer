package org.osmsurround.ra.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osm.schema.OsmMember;
import org.osm.schema.OsmNd;
import org.osm.schema.OsmNode;
import org.osm.schema.OsmRelation;
import org.osm.schema.OsmRoot;
import org.osm.schema.OsmType;
import org.osm.schema.OsmWay;
import org.osmsurround.ra.data.Member;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.data.Relation;
import org.osmsurround.ra.data.Way;
import org.springframework.stereotype.Service;

@Service
public class OsmSchemaConverterService {

	public List<Relation> convert(OsmRoot osmRoot) {
		Map<Long, Node> knownNodes = createNodes(osmRoot);
		Map<Long, Way> knownWays = createWays(osmRoot, knownNodes);
		return createRelations(osmRoot, knownWays);
	}

	private List<Relation> createRelations(OsmRoot osmRoot, Map<Long, Way> knownWays) {

		List<Relation> relations = new ArrayList<Relation>();

		for (OsmRelation osmRelation : osmRoot.getRelation()) {
			List<Member> members = createMembers(osmRelation, knownWays);
			relations.add(new Relation(osmRelation.getId().longValue(), members));
		}

		return relations;
	}

	private List<Member> createMembers(OsmRelation osmRelation, Map<Long, Way> knownWays) {
		List<Member> knownMembers = new ArrayList<Member>();
		for (OsmMember osmMember : osmRelation.getMember()) {
			if (OsmType.WAY.equals(osmMember.getType()))
				knownMembers.add(new Member(knownWays.get(osmMember.getRef().longValue()), osmMember.getRole()));
		}
		return knownMembers;
	}

	private Map<Long, Way> createWays(OsmRoot osmRoot, Map<Long, Node> knownNodes) {
		Map<Long, Way> ways = new HashMap<Long, Way>(osmRoot.getWay().size());
		for (OsmWay way : osmRoot.getWay()) {
			ways.put(way.getId().longValue(), createWay(way, knownNodes));
		}
		return ways;
	}

	private Way createWay(OsmWay way, Map<Long, Node> knownNodes) {
		List<Node> nodes = new ArrayList<Node>(way.getNd().size());
		for (OsmNd nd : way.getNd()) {
			nodes.add(knownNodes.get(nd.getRef().longValue()));
		}
		return new Way(way.getId().longValue(), nodes);
	}

	private Map<Long, Node> createNodes(OsmRoot osmRoot) {
		Map<Long, Node> nodes = new HashMap<Long, Node>(osmRoot.getNode().size());
		for (OsmNode node : osmRoot.getNode()) {
			nodes.put(node.getId().longValue(), createNode(node));
		}
		return nodes;
	}

	private Node createNode(OsmNode node) {
		return new Node(node.getId().longValue(), node.getLat(), node.getLon());
	}
}
