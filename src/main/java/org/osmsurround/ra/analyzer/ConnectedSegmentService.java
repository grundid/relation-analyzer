package org.osmsurround.ra.analyzer;

import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.stereotype.Service;

@Service
public class ConnectedSegmentService {

	public IntersectionNode connect(List<ISegment> segments) {

		if (segments.isEmpty())
			throw new AnalyzerException("Cannot connect empty segment list");

		return null;
	}

}
