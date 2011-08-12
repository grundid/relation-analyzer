package org.osmsurround.ra.analyzer;

import java.util.Collection;
import java.util.List;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.segment.ISegment;
import org.springframework.stereotype.Service;

@Service
public class ConnectedSegmentService {

	public IntersectionNode connect(List<ISegment> segments) {
		if (segments.isEmpty())
			throw new AnalyzerException("Cannot connect empty segment list");

		return null;
	}

	private Node determineFirstNode(List<ISegment> segments) {
		for (ISegment segment : segments) {
			Collection<ConnectableNode> startNodes = segment.getStartNodes();
			if (startNodes.size() <= 2)
				return null;
		}
		throw new AnalyzerException("Cannot determine a start node");
	}

	private Edge connectWithSegments(Edge connectedSegment, List<ISegment> segments) {

		return null;
	}
}
