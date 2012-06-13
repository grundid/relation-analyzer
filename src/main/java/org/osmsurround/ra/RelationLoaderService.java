/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.ra;

import java.util.List;

import org.openstreetmap.api._0._6.OsmRoot;
import org.osmsurround.ra.converter.OsmSchemaConverterService;
import org.osmsurround.ra.data.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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
		try {
			OsmRoot osmRoot = restOperations.getForObject(GET_RELATION_URL, OsmRoot.class, String.valueOf(relationId));
			List<Relation> list = converterService.convert(osmRoot);
			for (Relation relation : list) {
				if (relationId == relation.getRelationId())
					return relation;
			}

			throw new RuntimeException("Relation ID " + relationId + " not found");

		}
		catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.GONE) {
				throw new RelationGoneException();
			}
			else
				throw e;
		}

	}

	@CacheEvict("relation")
	public Relation reloadRelation(long relationId) {
		return loadRelation(relationId);
	}
}
