package org.osmsurround.ra;

import java.util.List;

import org.osm.schema.OsmRoot;
import org.osmsurround.ra.converter.OsmSchemaConverterService;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

@Service
public class RelationLoaderService {

	private static final String GET_RELATION_URL = "http://api.openstreetmap.org/api/0.6/relation/{relation}/full";

	@Autowired
	private RestOperations restOperations;
	@Autowired
	private OsmSchemaConverterService converterService;

	@Cacheable("relation")
	public Relation loadRelation(long relationId) {
		OsmRoot osmRoot = restOperations.getForObject(GET_RELATION_URL, OsmRoot.class, String.valueOf(relationId));

		List<Relation> list = converterService.convert(osmRoot);
		for (Relation relation : list) {
			if (relationId == relation.getRelationId())
				return relation;
		}

		throw new RuntimeException("Relation ID " + relationId + " not found");
	}

	@CacheEvict("relation")
	public Relation reloadRelation(long relationId) {
		return loadRelation(relationId);
	}
}
