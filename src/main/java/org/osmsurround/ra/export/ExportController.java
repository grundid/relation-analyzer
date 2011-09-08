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
@RequestMapping("/exportRelation")
public class ExportController {

	@Autowired
	private ExportService exportService;
	@Autowired
	private AnalyzerService analyzerService;

	@RequestMapping(method = RequestMethod.GET)
	public void get(AnalyzeRelationModel analyzeRelationModel, HttpServletResponse response, OutputStream out) {
		response.setContentType("application/xml");

		AnalyzerContext analyzerContext = analyzerService.analyzeRelation(analyzeRelationModel.getRelationId(),
				analyzeRelationModel.isNoCache());
		exportService.export(analyzerContext, out);
	}
}
