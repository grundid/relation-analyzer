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
