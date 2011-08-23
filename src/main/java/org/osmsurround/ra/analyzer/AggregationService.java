package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.context.AnalyzerContext;
import org.osmsurround.ra.segment.ConnectableSegment;
import org.springframework.stereotype.Service;

@Service
public class AggregationService {

	public void aggregate(AnalyzerContext analyzerContext) {
		List<AggregatedSegment> aggregatedSegments = aggregateSegments(analyzerContext.getSegments());
		analyzerContext.setAggregatedSegments(aggregatedSegments);
	}

	List<AggregatedSegment> aggregateSegments(List<? extends ConnectableSegment> segments) {
		List<AggregatedSegment> aggregatedSegments = new ArrayList<AggregatedSegment>();
		for (Iterator<? extends ConnectableSegment> it = segments.iterator(); it.hasNext();) {
			ConnectableSegment segment = it.next();

			AggregatedSegment newAggregatedSegment = new AggregatedSegment(segment);
			if (!canConnect(aggregatedSegments, newAggregatedSegment))
				aggregatedSegments.add(newAggregatedSegment);
		}
		return aggregateMore(aggregatedSegments);
	}

	private List<AggregatedSegment> aggregateMore(List<AggregatedSegment> segments) {

		List<AggregatedSegment> aggregatedSegments = new ArrayList<AggregatedSegment>();
		int lastRun = 0;
		do {

			lastRun = segments.size();
			aggregatedSegments = new ArrayList<AggregatedSegment>();
			for (Iterator<AggregatedSegment> it = segments.iterator(); it.hasNext();) {
				AggregatedSegment segment = it.next();

				if (!canConnect(aggregatedSegments, segment))
					aggregatedSegments.add(segment);
			}

			segments = aggregatedSegments;

		} while (aggregatedSegments.size() > 1 && lastRun != aggregatedSegments.size());

		return aggregatedSegments;
	}

	private boolean canConnect(List<AggregatedSegment> connectableSegments, AggregatedSegment segment) {
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
