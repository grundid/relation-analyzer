package org.osmsurround.ra.analyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.osmsurround.ra.data.Node;
import org.springframework.stereotype.Service;

@Service
public class AggregationService {

	private final AnalysisResult NOT_CONNECTABLE = new AnalysisResult();

	private class AnalysisResult {

		Connection connection;
		ISegment segment;
		ISegment newSegment;

		AnalysisResult() {
		}

		public AnalysisResult(Connection connection, ISegment segment, ISegment newSegment) {
			this.connection = connection;
			this.segment = segment;
			this.newSegment = newSegment;
		}
	}

	public List<ISegment> aggregate(List<ISegment> segments) {

		List<ISegment> aggregatedSegments = new ArrayList<ISegment>();
		int aggregatedCount = 0;
		do {
			aggregatedCount = aggregatedSegments.size();
			aggregatedSegments = new ArrayList<ISegment>();
			for (Iterator<ISegment> it = segments.iterator(); it.hasNext();) {
				ISegment segment = it.next();

				AnalysisResult analysisResult = analyzePossibleConnection(aggregatedSegments, segment);

				if (NOT_CONNECTABLE.equals(analysisResult)) {
					aggregatedSegments.add(new AggregatedSegment(segment));
				}
				else {
					connectSegment(analysisResult);
				}
			}
			segments = aggregatedSegments;
		} while (aggregatedCount != aggregatedSegments.size());

		return aggregatedSegments;
	}

	private void connectSegment(AnalysisResult analysisResult) {
		ISegment aggregatedSegment = analysisResult.segment;
		ISegment newSegment = analysisResult.newSegment;

		switch (analysisResult.connection) {
			case LAST_WITH_FIRST:
				addAtTheBottom(aggregatedSegment, newSegment);
				break;
			case FIRST_WITH_LAST:
				addAtTheTop(aggregatedSegment, newSegment);
				break;
			case LAST_WITH_LAST:
				if (aggregatedSegment.isReversible()) {
					aggregatedSegment.reverse();
					addAtTheTop(aggregatedSegment, newSegment);
				}
				else if (newSegment.isReversible()) {
					newSegment.reverse();
					addAtTheBottom(aggregatedSegment, newSegment);
				}
				break;
			case FIRST_WITH_FIRST:
				if (aggregatedSegment.isReversible()) {
					aggregatedSegment.reverse();
					addAtTheBottom(aggregatedSegment, newSegment);
				}
				else if (newSegment.isReversible()) {
					newSegment.reverse();
					addAtTheTop(aggregatedSegment, newSegment);
				}
				break;
			default:
				throw new UnsupportedOperationException("Unknown Connection " + analysisResult.connection);
		}
	}

	private void addAtTheTop(ISegment aggregatedSegment, ISegment newSegment) {
		aggregatedSegment.getSegments().add(0, newSegment);
	}

	private void addAtTheBottom(ISegment aggregatedSegment, ISegment newSegment) {
		aggregatedSegment.getSegments().add(newSegment);
	}

	private AnalysisResult analyzePossibleConnection(List<ISegment> aggregatedSegments, ISegment segmentToConnect) {
		for (ISegment segment : aggregatedSegments) {

			Connection connectable = Connection.NO_CONNECTION;

			if (lastNodeMatchesFirstNode(segment, segmentToConnect)) {
				connectable = Connection.LAST_WITH_FIRST;
			}
			else if (firstNodeMatchesLastNode(segment, segmentToConnect)) {
				connectable = Connection.FIRST_WITH_LAST;
			}
			else if (lastNodeMatchesLastNode(segment, segmentToConnect)) {
				if (segment.isReversible() || segmentToConnect.isReversible())
					connectable = Connection.LAST_WITH_LAST;
			}
			else if (firstNodeMatchesFirstNode(segment, segmentToConnect)) {
				if (segment.isReversible() || segmentToConnect.isReversible())
					connectable = Connection.FIRST_WITH_FIRST;
			}
			if (!Connection.NO_CONNECTION.equals(connectable))
				return new AnalysisResult(connectable, segment, segmentToConnect);

		}
		return NOT_CONNECTABLE;
	}

	private boolean firstNodeMatchesLastNode(ISegment segmentToTest, ISegment segmentToConnect) {
		return firstNodeMatchesNodes(segmentToTest, segmentToConnect.getLastNode());
	}

	private boolean firstNodeMatchesFirstNode(ISegment segmentToTest, ISegment segmentToConnect) {
		return firstNodeMatchesNodes(segmentToTest, segmentToConnect.getFirstNode());
	}

	private boolean firstNodeMatchesNodes(ISegment segmentToTest, Collection<Node> nodesToMatch) {
		for (Node nodeToConnect : nodesToMatch) {
			if (segmentToTest.getFirstNode().contains(nodeToConnect))
				return true;
		}
		return false;
	}

	private boolean lastNodeMatchesFirstNode(ISegment segmentToTest, ISegment segmentToConnect) {
		return lastNodeMatchesNodes(segmentToTest, segmentToConnect.getFirstNode());
	}

	private boolean lastNodeMatchesLastNode(ISegment segmentToTest, ISegment segmentToConnect) {
		return lastNodeMatchesNodes(segmentToTest, segmentToConnect.getLastNode());
	}

	private boolean lastNodeMatchesNodes(ISegment segmentToTest, Collection<Node> nodesToMatch) {
		for (Node nodeToConnect : nodesToMatch) {
			if (segmentToTest.getLastNode().contains(nodeToConnect))
				return true;
		}
		return false;
	}
}
