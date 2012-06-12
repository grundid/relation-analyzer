package org.osmsurround.ra.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

public class SearchQueryBuilder {

	private static final String SELECT = "SELECT relation_id,relation_type,name,route,ref,network,operator FROM relation";
	private static final String ORDER_BY = " ORDER BY name,relation_type,route";
	private StringBuilder where = new StringBuilder();
	private List<Integer> types = new ArrayList<Integer>();
	private List<Object> values = new ArrayList<Object>();

	public SearchQueryBuilder() {
	}

	public SearchQueryBuilder append(String rowName, String value) {
		return append(rowName, value, false);
	}

	public SearchQueryBuilder append(String rowName, String value, boolean wildcard) {
		if (StringUtils.hasText(value)) {
			if (where.length() > 0)
				where.append(" AND ");
			where.append("(LOWER(").append(rowName).append(") LIKE ?)");
			if (wildcard)
				values.add(("%" + value + "%").toLowerCase());
			else
				values.add((value + "%").toLowerCase());
			types.add(Types.VARCHAR);
		}
		return this;
	}

	public String getSql() {
		if (where.length() > 0)
			return SELECT + " WHERE " + where.toString() + ORDER_BY;
		else
			return SELECT + ORDER_BY;
	}

	public List<Integer> getTypes() {
		return types;
	}

	public Object[] getValues() {
		return values.toArray();
	}
}
