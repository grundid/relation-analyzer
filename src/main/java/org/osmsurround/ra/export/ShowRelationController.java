package org.osmsurround.ra.export;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.analyzer.AnalyzerService;
import org.osmsurround.ra.context.AnalyzerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/showRelation")
public class ShowRelationController {

	@Autowired
	private AnalyzerService analyzerService;
	@Autowired
	private ExportService exportService;

	@RequestMapping(method = RequestMethod.GET)
	public void get(AnalyzeRelationModel analyzeRelationModel, HttpServletResponse response, OutputStream out) {
		AnalyzerContext analyzerContext = analyzerService.analyzeRelation(analyzeRelationModel.getRelationId(),
				analyzeRelationModel.isNoCache());

		response.setContentType("application/xml");
		response.setHeader("Content-Disposition", "attachment; filename=\"relation_"
				+ analyzerContext.getRelation().getRelationId() + ".gpx\"");

		exportService.exportForDisplay(analyzerContext, out);
	}

}
