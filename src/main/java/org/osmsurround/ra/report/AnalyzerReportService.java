package org.osmsurround.ra.report;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.osmsurround.ra.segment.ISegment;
import org.springframework.stereotype.Service;

@Service
public class AnalyzerReportService {

	public AnalyzerReport createReport(Map<String, List<ISegment>> aggregatedRelation) {
		AnalyzerReport analyzerReport = new AnalyzerReport();

		for (Entry<String, List<ISegment>> entry : aggregatedRelation.entrySet()) {
			AnalyzerReportItem reportItem = new AnalyzerReportItem(entry.getKey());

			analyzerReport.addReportItem(reportItem);

		}

		return analyzerReport;
	}

}
