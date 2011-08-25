package org.osmsurround.ra.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.osmsurround.ra.TestBase;
import org.osmsurround.ra.search.SearchModel;
import org.springframework.beans.factory.annotation.Autowired;

public class RelationSearchTest extends TestBase {

	@Autowired
	private RelationSearch relationSearch;

	@Test
	public void testSearch() throws Exception {
		SearchModel searchModel = new SearchModel();
		List<Relation> list = relationSearch.search(searchModel);
		assertFalse(list.isEmpty());
	}
}
