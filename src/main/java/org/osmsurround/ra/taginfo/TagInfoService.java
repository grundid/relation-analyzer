package org.osmsurround.ra.taginfo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.osmtools.taginfo.KeysValues;
import org.osmtools.taginfo.KeysValuesResponse;
import org.osmtools.taginfo.Pagination;
import org.osmtools.taginfo.TagInfoFilter;
import org.osmtools.taginfo.TagInfoSort;
import org.osmtools.taginfo.TagInfoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagInfoService {

	@Autowired
	private TagInfoTemplate tagInfoTemplate;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final long DAY = 24 * 60 * 60 * 1000;

	private Map<String, String> valuesPerKey = new HashMap<String, String>();
	private long lastUpdated;

	public TagInfos getTagInfos() {
		TagInfos tagInfos = new TagInfos();

		tagInfos.setNetwork(getValuesAsJsonArray("network"));
		tagInfos.setRoute(getValuesAsJsonArray("route"));
		tagInfos.setType(getValuesAsJsonArray("type"));
		tagInfos.setOperator(getValuesAsJsonArray("operator"));

		return tagInfos;
	}

	public String getValuesAsJsonArray(String key) {
		try {
			assertValuesUpToDate(key);
			return valuesPerKey.get(key);
		}
		catch (Exception e) {
			e.printStackTrace();
			return "[]";
		}
	}

	private void assertValuesUpToDate(String key) {
		if (valuesPerKey.get(key) == null) {
			valuesPerKey.put(key, updateKey(key));
		}
		else if (lastUpdated + DAY < System.currentTimeMillis()) {
			for (Entry<String, String> entry : valuesPerKey.entrySet()) {
				entry.setValue(updateKey(entry.getKey()));
			}
		}
	}

	private String updateKey(String key) {
		KeysValuesResponse keysValuesResponse = tagInfoTemplate.getValuesWithKey(key, TagInfoFilter.RELATIONS,
				TagInfoSort.COUNT_RELATIONS, new Pagination(1, 25));
		lastUpdated = System.currentTimeMillis();
		return processResponse(keysValuesResponse);
	}

	private String processResponse(KeysValuesResponse response) {
		try {
			Collection<String> result = new TreeSet<String>();
			for (KeysValues keysValues : response.getData()) {
				result.add(keysValues.getValue());
			}
			return objectMapper.writeValueAsString(result);
		}
		catch (Exception e) {
			return "[]";
		}
	}

}
