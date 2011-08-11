package org.osmsurround.ra.report;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osmsurround.ra.analyzer.ISegment;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerReportService {

	public AnalyzerReport createReport(Map<String, List<ISegment>> aggregatedRelation) {
		AnalyzerReport analyzerReport = new AnalyzerReport();

		for (Entry<String, List<ISegment>> entry : aggregatedRelation.entrySet()) {
			AnalyzerReportItem reportItem = new AnalyzerReportItem(entry.getKey());

			for (Iterator<ISegment> it = entry.getValue().iterator(); it.hasNext();) {
				ISegment segment = it.next();
				double length = 0;
				Segment reportSegment = new Segment(segment.getFirstNode(), segment.getLastNode(), length);
				reportItem.addRelationItem(reportSegment);

				//				if (it.hasNext())
				//					reportItem.addRelationItem(new Gap());
			}

			analyzerReport.addReportItem(reportItem);

		}

		return analyzerReport;
	}

}
