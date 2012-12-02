package org.osmsurround.ra.stats;

import java.util.ArrayList;
import java.util.List;

import org.osmtools.ra.data.Member;
import org.osmtools.ra.data.Node;
import org.osmtools.ra.data.Relation;
import org.osmtools.ra.data.Way;
import org.osmtools.utils.LonLatMath;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

	private List<HighwayCategory> knownCategories = new ArrayList<HighwayCategory>();

	public StatisticsService() {

		createHierarchy();

		knownCategories.add(new HighwayCategory("motorway", "motorway", "motorway_link", "trunk", "trunk_link",
				"primary", "primary_link"));
		knownCategories
				.add(new HighwayCategory("secondary", "secondary", "secondary_link", "tertiary", "tertiary_link"));

		knownCategories.add(new HighwayCategory("calm", "pedestrian", "residential", "unclassified", "service",
				"living_street"));

		knownCategories.add(new HighwayCategory("track", "track", "cycleway"));
		knownCategories.add(new HighwayCategory("path", "path", "footway", "steps", "bridleway"));
		knownCategories.add(new HighwayCategory("unknown"));
	}

	private HighwayHierarchy createHierarchy() {
		HighwayHierarchy hierarchy = new HighwayHierarchy("total");

		HighwayHierarchy faststreets = new HighwayHierarchy("fast");
		faststreets.addSubHierarchy(new HighwayHierarchy("motorway", "highway", "motorway"));
		faststreets.addSubHierarchy(new HighwayHierarchy("motorway", "highway", "motorway_link"));
		faststreets.addSubHierarchy(new HighwayHierarchy("trunk", "highway", "trunk"));
		faststreets.addSubHierarchy(new HighwayHierarchy("trunk", "highway", "trunk_link"));
		faststreets.addSubHierarchy(new HighwayHierarchy("primary", "highway", "primary"));
		faststreets.addSubHierarchy(new HighwayHierarchy("primary", "highway", "primary_link"));
		faststreets.addSubHierarchy(new HighwayHierarchy("secondary", "highway", "secondary"));
		faststreets.addSubHierarchy(new HighwayHierarchy("secondary", "highway", "secondary_link"));
		faststreets.addSubHierarchy(new HighwayHierarchy("tertiary", "highway", "tertiary"));
		faststreets.addSubHierarchy(new HighwayHierarchy("tertiary", "highway", "tertiary_link"));

		hierarchy.addSubHierarchy(faststreets);

		HighwayHierarchy rural = new HighwayHierarchy("rural");
		rural.addSubHierarchy(new HighwayHierarchy("residential", "highway", "residential"));
		rural.addSubHierarchy(new HighwayHierarchy("living_street", "highway", "living_street"));
		rural.addSubHierarchy(new HighwayHierarchy("service", "highway", "service"));
		rural.addSubHierarchy(new HighwayHierarchy("unclassified", "highway", "unclassified"));
		rural.addSubHierarchy(new HighwayHierarchy("bus_guideway", "highway", "bus_guideway"));

		hierarchy.addSubHierarchy(rural);

		HighwayHierarchy tracks = new HighwayHierarchy("tracks");
		tracks.addSubHierarchy(new HighwayHierarchy("track-g1", "highway", "track", "tracktype", "grade1"));
		tracks.addSubHierarchy(new HighwayHierarchy("track-g2", "highway", "track", "tracktype", "grade2"));
		tracks.addSubHierarchy(new HighwayHierarchy("track-g3", "highway", "track", "tracktype", "grade3"));
		tracks.addSubHierarchy(new HighwayHierarchy("track-g4", "highway", "track", "tracktype", "grade4"));
		tracks.addSubHierarchy(new HighwayHierarchy("track-g5", "highway", "track", "tracktype", "grade5"));
		tracks.addSubHierarchy(new HighwayHierarchy("track-unknown", "highway", "track"));

		hierarchy.addSubHierarchy(tracks);

		HighwayHierarchy humans = new HighwayHierarchy("humans");
		humans.addSubHierarchy(new HighwayHierarchy("pedestrian", "highway", "pedestrian"));
		humans.addSubHierarchy(new HighwayHierarchy("path", "highway", "path"));
		humans.addSubHierarchy(new HighwayHierarchy("footway", "highway", "footway"));
		humans.addSubHierarchy(new HighwayHierarchy("steps", "highway", "steps"));
		humans.addSubHierarchy(new HighwayHierarchy("bridleway", "highway", "bridleway"));
		humans.addSubHierarchy(new HighwayHierarchy("cycleway", "highway", "cycleway"));

		hierarchy.addSubHierarchy(humans);

		HighwayHierarchy unknown = new HighwayHierarchy("unknown");
		humans.addSubHierarchy(new HighwayHierarchy("unknown", "highway", "*"));

		hierarchy.addSubHierarchy(unknown);

		return hierarchy;
	}

	public RelationStatistics createRelationStatistics(Relation relation) {

		HighwayHierarchy hierarchy = createHierarchy();

		for (Member member : relation.getMembers()) {
			Way way = member.getWay();
			List<Node> nodes = way.getNodes();
			double length = LonLatMath.distance(nodes.get(0), nodes.get(nodes.size() - 1));
			hierarchy.addWay(way, length);
		}

		List<Distribution> distributions = new ArrayList<Distribution>();
		hierarchy.appendDistributions(distributions, 1);
		RelationStatistics relationStatistics = new RelationStatistics(distributions);
		relationStatistics.finish();

		//		for (HighwayCategory category : knownCategories)
		//			distributions.add(new Distribution(category.getName()));
		//
		//		RelationStatistics relationStatistics = new RelationStatistics(distributions);
		//
		//		for (Member member : relation.getMembers()) {
		//			identifyWay(member.getWay(), relationStatistics);
		//		}
		//
		//		relationStatistics.finish();

		return relationStatistics;

	}

	private void identifyWay(Way way, RelationStatistics relationStatistics) {
		String highway = way.getTags().get("highway");
		HighwayCategory category = identifyCategory(highway);

		List<Node> nodes = way.getNodes();
		double length = LonLatMath.distance(nodes.get(0), nodes.get(nodes.size() - 1));

		relationStatistics.addWay(category.getName(), length);
	}

	private HighwayCategory identifyCategory(String highway) {
		for (HighwayCategory highwayCategory : knownCategories) {
			if (highwayCategory.isCategory(highway)) {
				return highwayCategory;
			}
		}
		throw new RuntimeException("Unknown highway: " + highway);
	}
}
