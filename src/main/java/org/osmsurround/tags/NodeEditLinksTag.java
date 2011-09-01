/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.osmsurround.ra.data.Node;
import org.osmsurround.tags.builder.JosmRemoteControlLinkBuilder;
import org.osmsurround.tags.builder.LinkBuilder;
import org.osmsurround.tags.builder.PotlatchLinkBuilder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class NodeEditLinksTag extends TagSupport {

	private WebApplicationContext applicationContext;
	private Node node1;
	private Node node2;

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

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(pageContext
				.getServletContext());
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().write(createLinks(node1, node2));
		}
		catch (IOException e) {
			throw new JspException(e);
		}

		return super.doEndTag();
	}

	private String createLinks(Node prevNode, Node nextNode) {
		StringBuilder sb = new StringBuilder();

		for (int x = 0; x < LINK_BUILDERS.length; x++) {

			LinkBuilderDefinition linkBuilderDefinition = LINK_BUILDERS[x];
			sb.append("<a class=\"").append(linkBuilderDefinition.cssClass).append("\" ");
			sb.append("href=\"").append(linkBuilderDefinition.linkBuilder.buildLinkForNodes(prevNode, nextNode))
					.append("\" ");
			sb.append("title=\"")
					.append(applicationContext.getMessage(linkBuilderDefinition.titleMessageCode, null,
							LocaleContextHolder.getLocale())).append("\" ");
			sb.append("target=\"").append(linkBuilderDefinition.cssClass).append("\">");
			sb.append(applicationContext.getMessage(linkBuilderDefinition.labelMessageCode, null,
					LocaleContextHolder.getLocale()));
			sb.append("</a>");

			if (x + 1 < LINK_BUILDERS.length)
				sb.append("&nbsp;");
		}
		return sb.toString();
	}
}
