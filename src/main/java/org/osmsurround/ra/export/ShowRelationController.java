package org.osmsurround.ra.export;

import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.geojson.Point;
import org.osmsurround.ra.AnalyzeRelationModel;
import org.osmsurround.ra.analyzer.AnalyzerService;
import org.osmsurround.ra.report.EndNodeDistances;
import org.osmsurround.ra.report.Report;
import org.osmsurround.ra.report.ReportItem;
import org.osmsurround.ra.report.ReportService;
import org.osmsurround.tags.builder.JosmRemoteControlLinkBuilder;
import org.osmsurround.tags.builder.LinkBuilder;
import org.osmsurround.tags.builder.PotlatchLinkBuilder;
import org.osmtools.api.Section;
import org.osmtools.ra.context.AnalyzerContext;
import org.osmtools.ra.data.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/showRelation")
public class ShowRelationController {

	@Autowired
	private AnalyzerService analyzerService;
	@Autowired
	private ReportService reportService;
	@Autowired
	private GeoJsonExport geoJsonExport;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	FeatureCollection get(AnalyzeRelationModel analyzeRelationModel) {
		AnalyzerContext analyzerContext = analyzerService.analyzeRelation(analyzeRelationModel.getRelationId(),
				analyzeRelationModel.isNoCache());
		Report report = reportService.generateReport(analyzerContext);
		SimpleSegmentConverter converter = new SimpleSegmentConverter();
		List<Section> containers = converter.convert(analyzerContext);
		FeatureCollection featureCollection = geoJsonExport.export(containers, analyzerContext.getRelation());
		Locale locale = LocaleContextHolder.getLocale();
		for (int itemIndex = 0; itemIndex < report.getReportItems().size(); itemIndex++) {
			ReportItem reportItem = report.getReportItems().get(itemIndex);
			for (EndNodeDistances node : reportItem.getEndNodeDistances()) {
				Point point = new Point(node.getNode().getLon(), node.getNode().getLat());
				Feature feature = new Feature();
				feature.setGeometry(point);
				String popupContent = "<p>" + messageSource.getMessage("label.graph", null, locale) + "&nbsp;"
						+ itemIndex + "</p>" + messageSource.getMessage("editor.links", null, locale) + "<br/>"
						+ createLinks(node.getNode(), node.getNode());
				feature.setProperty("popup", popupContent);
				featureCollection.add(feature);
			}
		}
		return featureCollection;
	}

	private static class LinkBuilderDefinition {

		String cssClass;
		String titleMessageCode;
		String labelMessageCode;
		LinkBuilder linkBuilder;

		public LinkBuilderDefinition(String cssClass, String titleMessageCode, String labelMessageCode,
				LinkBuilder linkBuilder) {
			this.cssClass = cssClass;
			this.titleMessageCode = titleMessageCode;
			this.labelMessageCode = labelMessageCode;
			this.linkBuilder = linkBuilder;
		}
	}

	private static final LinkBuilderDefinition[] LINK_BUILDERS = {
			new LinkBuilderDefinition("nel josmrc", "link.title.josmrc", "link.label.josmrc",
					new JosmRemoteControlLinkBuilder()),
			new LinkBuilderDefinition("nel potlatch", "link.title.potlatch", "link.label.potlatch",
					new PotlatchLinkBuilder()) };

	private String createLinks(Node prevNode, Node nextNode) {
		StringBuilder sb = new StringBuilder();
		for (int x = 0; x < LINK_BUILDERS.length; x++) {
			LinkBuilderDefinition linkBuilderDefinition = LINK_BUILDERS[x];
			sb.append("<a class=\"").append(linkBuilderDefinition.cssClass).append("\" ");
			sb.append("href=\"").append(linkBuilderDefinition.linkBuilder.buildLinkForNodes(prevNode, nextNode))
					.append("\" ");
			sb.append("title=\"")
					.append(messageSource.getMessage(linkBuilderDefinition.titleMessageCode, null,
							LocaleContextHolder.getLocale())).append("\" ");
			sb.append("target=\"").append(linkBuilderDefinition.cssClass).append("\">");
			sb.append(messageSource.getMessage(linkBuilderDefinition.labelMessageCode, null,
					LocaleContextHolder.getLocale()));
			sb.append("</a>");
			if (x + 1 < LINK_BUILDERS.length)
				sb.append("&nbsp;");
		}
		return sb.toString();
	}
}
