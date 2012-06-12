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
package org.osmsurround.ra.dao;

import java.util.List;

import org.osmsurround.ra.search.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RelationSearch {

	@Autowired
	private RelationRowMapper relationRowMapper;
	@Autowired
	private JdbcOperations jdbcOperations;

	public List<Relation> search(SearchModel searchModel) {
		SearchQueryBuilder queryBuilder = new SearchQueryBuilder();
		queryBuilder.append("relation_type", searchModel.getRelationType());
		queryBuilder.append("name", searchModel.getName(), true);
		queryBuilder.append("route", searchModel.getRoute());
		queryBuilder.append("ref", searchModel.getRef());
		queryBuilder.append("network", searchModel.getNetwork());
		queryBuilder.append("operator", searchModel.getOperator());

		Object[] args = queryBuilder.getValues();
		String sql = queryBuilder.getSql();

		List<Relation> list = jdbcOperations.query(sql, args, relationRowMapper);
		return list;
	}
}
