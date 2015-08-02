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

	private List<Category> knownHighwayCategories = new ArrayList<Category>();
	private List<Category> knownSurfaceCategories = new ArrayList<Category>();

	public StatisticsService() {

		knownHighwayCategories.add(new Category("motorway", "motorway", "motorway_link", "trunk", "trunk_link",
				"primary", "primary_link"));
		knownHighwayCategories
				.add(new Category("secondary", "secondary", "secondary_link", "tertiary", "tertiary_link"));

		knownHighwayCategories.add(new Category("calm", "pedestrian", "residential", "unclassified", "service",
				"living_street"));

		knownHighwayCategories.add(new Category("track", "track", "cycleway"));
		knownHighwayCategories.add(new Category("path", "path", "footway", "steps", "bridleway"));
		knownHighwayCategories.add(new Category("unknown"));

		knownSurfaceCategories.add(new Category("paved", "asphalt", "cobblestone", "sett", "concrete",
				"concrete:lanes", "paving_stones", "metal", "wood"));
		knownSurfaceCategories.add(new Category("unpaved", "compacted", "dirt", "earth", "fine_gravel", "grass",
				"grass_paver", "gravel", "ground", "ice", "mud", "pebblestone", "salt", "sand", "snow", "woodchips"));
		knownSurfaceCategories.add(new Category("unknown"));
	}

	private Hierarchy createHighwayHierarchy() {

		Hierarchy hierarchy = new Hierarchy("total");

		Hierarchy faststreets = new Hierarchy("fast");
		faststreets.addSubHierarchy(new Hierarchy("motorway", "highway", "motorway"));
		faststreets.addSubHierarchy(new Hierarchy("motorway", "highway", "motorway_link"));
		faststreets.addSubHierarchy(new Hierarchy("trunk", "highway", "trunk"));
		faststreets.addSubHierarchy(new Hierarchy("trunk", "highway", "trunk_link"));
		faststreets.addSubHierarchy(new Hierarchy("primary", "highway", "primary"));
		faststreets.addSubHierarchy(new Hierarchy("primary", "highway", "primary_link"));
		faststreets.addSubHierarchy(new Hierarchy("secondary", "highway", "secondary"));
		faststreets.addSubHierarchy(new Hierarchy("secondary", "highway", "secondary_link"));
		faststreets.addSubHierarchy(new Hierarchy("tertiary", "highway", "tertiary"));
		faststreets.addSubHierarchy(new Hierarchy("tertiary", "highway", "tertiary_link"));

		hierarchy.addSubHierarchy(faststreets);

		Hierarchy rural = new Hierarchy("rural");
		rural.addSubHierarchy(new Hierarchy("residential", "highway", "residential"));
		rural.addSubHierarchy(new Hierarchy("living_street", "highway", "living_street"));
		rural.addSubHierarchy(new Hierarchy("service", "highway", "service"));
		rural.addSubHierarchy(new Hierarchy("unclassified", "highway", "unclassified"));
		rural.addSubHierarchy(new Hierarchy("bus_guideway", "highway", "bus_guideway"));

		hierarchy.addSubHierarchy(rural);

		Hierarchy tracks = new Hierarchy("tracks");
		tracks.addSubHierarchy(new Hierarchy("track-g1", "highway", "track", "tracktype", "grade1"));
		tracks.addSubHierarchy(new Hierarchy("track-g2", "highway", "track", "tracktype", "grade2"));
		tracks.addSubHierarchy(new Hierarchy("track-g3", "highway", "track", "tracktype", "grade3"));
		tracks.addSubHierarchy(new Hierarchy("track-g4", "highway", "track", "tracktype", "grade4"));
		tracks.addSubHierarchy(new Hierarchy("track-g5", "highway", "track", "tracktype", "grade5"));
		tracks.addSubHierarchy(new Hierarchy("track-unknown", "highway", "track"));

		hierarchy.addSubHierarchy(tracks);

		Hierarchy humans = new Hierarchy("humans");
		humans.addSubHierarchy(new Hierarchy("pedestrian", "highway", "pedestrian"));
		humans.addSubHierarchy(new Hierarchy("path", "highway", "path"));
		humans.addSubHierarchy(new Hierarchy("footway", "highway", "footway"));
		humans.addSubHierarchy(new Hierarchy("steps", "highway", "steps"));
		humans.addSubHierarchy(new Hierarchy("bridleway", "highway", "bridleway"));
		humans.addSubHierarchy(new Hierarchy("cycleway", "highway", "cycleway"));

		hierarchy.addSubHierarchy(humans);

		Hierarchy unknown = new Hierarchy("unknown");
		unknown.addSubHierarchy(new Hierarchy("unknown", "highway", "*"));

		hierarchy.addSubHierarchy(unknown);

		return hierarchy;
	}

	private Hierarchy createSurfaceHierarchy() {

		Hierarchy hierarchy = new Hierarchy("total");

		Hierarchy paved = new Hierarchy("paved");
		paved.addSubHierarchy(new Hierarchy("paved", "surface", "paved"));
		paved.addSubHierarchy(new Hierarchy("asphalt", "surface", "asphalt"));
		paved.addSubHierarchy(new Hierarchy("cobblestone", "surface", "cobblestone"));
		paved.addSubHierarchy(new Hierarchy("sett", "surface", "sett"));
		paved.addSubHierarchy(new Hierarchy("concrete", "surface", "concrete"));
		paved.addSubHierarchy(new Hierarchy("concrete", "surface", "concrete:lanes"));
		paved.addSubHierarchy(new Hierarchy("paving_stones", "surface", "paving_stones"));
		paved.addSubHierarchy(new Hierarchy("paving_stones", "surface", "paving_stones:30"));
		paved.addSubHierarchy(new Hierarchy("paving_stones", "surface", "paving_stones:20"));
		paved.addSubHierarchy(new Hierarchy("metal", "surface", "metal"));
		paved.addSubHierarchy(new Hierarchy("wood", "surface", "wood"));
		hierarchy.addSubHierarchy(paved);

		Hierarchy unpaved = new Hierarchy("unpaved");
		unpaved.addSubHierarchy(new Hierarchy("unpaved", "surface", "unpaved"));
		unpaved.addSubHierarchy(new Hierarchy("compacted", "surface", "compacted"));
		unpaved.addSubHierarchy(new Hierarchy("dirt", "surface", "dirt"));
		unpaved.addSubHierarchy(new Hierarchy("earth", "surface", "earth"));
		unpaved.addSubHierarchy(new Hierarchy("gravel", "surface", "fine_gravel"));
		unpaved.addSubHierarchy(new Hierarchy("gravel", "surface", "gravel"));
		unpaved.addSubHierarchy(new Hierarchy("grass", "surface", "grass"));
		unpaved.addSubHierarchy(new Hierarchy("grass_paver", "surface", "grass_paver"));
		unpaved.addSubHierarchy(new Hierarchy("ground", "surface", "ground"));
		unpaved.addSubHierarchy(new Hierarchy("ice", "surface", "ice"));
		unpaved.addSubHierarchy(new Hierarchy("mud", "surface", "mud"));
		unpaved.addSubHierarchy(new Hierarchy("pebblestone", "surface", "pebblestone"));
		unpaved.addSubHierarchy(new Hierarchy("salt", "surface", "salt"));
		unpaved.addSubHierarchy(new Hierarchy("sand", "surface", "sand"));
		unpaved.addSubHierarchy(new Hierarchy("snow", "surface", "snow"));
		unpaved.addSubHierarchy(new Hierarchy("woodchips", "surface", "woodchips"));
		hierarchy.addSubHierarchy(unpaved);

		Hierarchy untagged = new Hierarchy("untagged");
		untagged.addSubHierarchy(new Hierarchy("untagged", "surface", "untagged"));
		hierarchy.addSubHierarchy(untagged);

		Hierarchy unknown = new Hierarchy("unknown");
		unknown.addSubHierarchy(new Hierarchy("unknown", "surface", "*"));
		hierarchy.addSubHierarchy(unknown);

		return hierarchy;
	}

	public RelationStatistics createRelationStatisticsHighway(Relation relation) {

		Hierarchy highwayHierarchy = createHighwayHierarchy();

		for (Member member : relation.getMembers()) {
			Way way = member.getWay();
			List<Node> nodes = way.getNodes();
			double length = LonLatMath.distance(nodes.get(0), nodes.get(nodes.size() - 1));
			highwayHierarchy.addWay(way, length);
		}

		List<Distribution> distributions = new ArrayList<Distribution>();
		highwayHierarchy.appendDistributions(distributions, 1);

		RelationStatistics relationStatistics = new RelationStatistics(distributions);
		relationStatistics.finish();

		// for (HighwayCategory category : knownCategories)
		// distributions.add(new Distribution(category.getName()));
		//
		// RelationStatistics relationStatistics = new
		// RelationStatistics(distributions);
		//
		// for (Member member : relation.getMembers()) {
		// identifyWay(member.getWay(), relationStatistics);
		// }
		//
		// relationStatistics.finish();

		return relationStatistics;

	}

	public RelationStatistics createRelationStatisticsSurface(Relation relation) {

		Hierarchy surfaceHierarchy = createSurfaceHierarchy();

		for (Member member : relation.getMembers()) {
			Way way = member.getWay();
			List<Node> nodes = way.getNodes();
			double length = LonLatMath.distance(nodes.get(0), nodes.get(nodes.size() - 1));
			if (!way.getTags().containsKey("surface")) {
				way.getTags().put("surface", "untagged");
			}
			surfaceHierarchy.addWay(way, length);
		}

		List<Distribution> distributions = new ArrayList<Distribution>();
		surfaceHierarchy.appendDistributions(distributions, 1);

		RelationStatistics relationStatistics = new RelationStatistics(distributions);
		relationStatistics.finish();

		return relationStatistics;

	}

	private void identifyWay(Way way, RelationStatistics relationStatistics) {

		String highway = way.getTags().get("highway");
		Category category = identifyCategory(highway);

		List<Node> nodes = way.getNodes();
		double length = LonLatMath.distance(nodes.get(0), nodes.get(nodes.size() - 1));

		relationStatistics.addWay(category.getName(), length);
	}

	private Category identifyCategory(String highway) {

		for (Category highwayCategory : knownHighwayCategories) {
			if (highwayCategory.isCategory(highway)) {
				return highwayCategory;
			}
		}
		throw new RuntimeException("Unknown highway: " + highway);
	}
}
