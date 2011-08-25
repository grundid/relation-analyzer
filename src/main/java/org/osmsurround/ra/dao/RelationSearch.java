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
		setSql("SELECT relation_id,relation_type,name,route,ref,network,operator FROM relation WHERE relation_type ILIKE ? AND "
				+ "(name ILIKE ?) AND (route ILIKE ?) AND (ref ILIKE ?) AND "
				+ "(network ILIKE ?) AND (operator ILIKE ?) " + "ORDER BY name, relation_type, route");
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
			return param + "%";
		else
			return "%";
	}
}
