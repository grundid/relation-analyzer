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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.osmsurround.ra.search.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class RelationSearch extends MappingSqlQuery<Relation> {

	@Autowired
	public RelationSearch(DataSource dataSource) {
		setDataSource(dataSource);
		setSql("SELECT relation_id,relation_type,name,route,ref,network,operator FROM relation WHERE "
				+ "(LOWER(relation_type) LIKE ?) AND "
				+ "(LOWER(name) LIKE ?) AND (LOWER(route) LIKE ?) AND (LOWER(ref) LIKE ?) AND "
				+ "(LOWER(network) LIKE ?) AND (LOWER(operator) LIKE ?) " + "ORDER BY name, relation_type, route");
		setTypes(new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR });
	}

	@Override
	protected Relation mapRow(ResultSet rs, int rowNum) throws SQLException {
		int c = 1;
		Relation relation = new Relation();
		relation.setRelationId(rs.getLong(c++));
		relation.setRelationType(rs.getString(c++));
		relation.setName(rs.getString(c++));
		relation.setRoute(rs.getString(c++));
		relation.setRef(rs.getString(c++));
		relation.setNetwork(rs.getString(c++));
		relation.setOperator(rs.getString(c++));

		return relation;
	}

	public List<Relation> search(SearchModel searchModel) {

		return execute(prepareParam(searchModel.getRelationType()), prepareParam(searchModel.getName()),
				prepareParam(searchModel.getRoute()), prepareParam(searchModel.getRef()),
				prepareParam(searchModel.getNetwork()), prepareParam(searchModel.getOperator()));
	}

	private String prepareParam(String param) {
		if (StringUtils.hasText(param))
			return (param + "%").toLowerCase();
		else
			return "%";
	}
}
