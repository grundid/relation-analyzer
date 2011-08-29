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
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class NodeLinkTag extends BodyTagSupport {

	@Override
	public int doEndTag() throws JspException {

		try {
			StringWriter writer = new StringWriter();
			bodyContent.writeOut(writer);
			String nodeId = writer.toString();
			JspWriter out = pageContext.getOut();
			out.write(createContent(nodeId));
		}
		catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_PAGE;
	}

	private String createContent(String nodeId) {
		StringBuilder sb = new StringBuilder();

		String cssClass = "node-" + nodeId;

		sb.append("<a href=\"http://www.openstreetmap.org/browse/node/").append(nodeId).append("\" ");
		sb.append("class=\"").append(cssClass).append("\" ");
		sb.append("onmouseover=\"$('.").append(cssClass).append("').addClass('node-highlight');\" ");
		sb.append("onmouseout=\"$('.").append(cssClass).append("').removeClass('node-highlight');\" ");

		sb.append(">").append(nodeId).append("</a>");
		return sb.toString();
	}
}
