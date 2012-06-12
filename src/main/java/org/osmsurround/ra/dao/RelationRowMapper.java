package org.osmsurround.ra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class RelationRowMapper implements RowMapper<Relation> {

	@Override
	public Relation mapRow(ResultSet rs, int rowNum) throws SQLException {
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

}
