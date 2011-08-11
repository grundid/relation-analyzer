package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.segment.ISegment;
import org.springframework.stereotype.Service;

@Service
public class AggregationService {

	public List<ISegment> aggregate(List<ISegment> segments) {

		List<ISegment> result = new ArrayList<ISegment>(segments);

		int lastRun = 0;
		do {
			List<AggregatedSegment> aggregatedSegments = new ArrayList<AggregatedSegment>();
			lastRun = result.size();
			for (Iterator<ISegment> it = result.iterator(); it.hasNext();) {
				ISegment segment = it.next();
				if (!canConnect(aggregatedSegments, segment))
					aggregatedSegments.add(new AggregatedSegment(segment));
			}

			result = new ArrayList<ISegment>(aggregatedSegments);

		} while (result.size() > 1 && result.size() != lastRun);

		return result;
	}

	private boolean canConnect(List<AggregatedSegment> connectableSegments, ISegment segment) {
		Collection<ConnectableNode> newSegmentStartNodes = segment.getStartNodes();
		Collection<ConnectableNode> newSegmentEndNodes = segment.getEndNodes();

		for (AggregatedSegment connectableSegment : connectableSegments) {
			Collection<ConnectableNode> startNodes = connectableSegment.getStartNodes();
			Collection<ConnectableNode> endNodes = connectableSegment.getEndNodes();

			if (isConnectable(startNodes, newSegmentEndNodes) || isConnectable(endNodes, newSegmentStartNodes)) {
				connectableSegment.addSegment(segment);
				return true;
			}
		}
		return false;
	}

	private boolean isConnectable(Collection<ConnectableNode> nodes1, Collection<ConnectableNode> nodes2) {
		for (ConnectableNode node1 : nodes1) {
			for (ConnectableNode node2 : nodes2) {
				if (node1.isConnectable(node2))
					return true;
			}
		}
		return false;
	}
}
