package org.osmsurround.tags;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.osmsurround.ra.data.Node;

public class RemoteControlTag extends SimpleTagSupport {

	private Node node1;
	private Node node2;

	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	@Override
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(createLink(node1, node2));
	}

	private static String createLink(Node prevNode, Node nextNode) {
		StringBuilder sb = new StringBuilder();

		sb.append("<a href=\"").append(createJOSMRemoteControlLink(prevNode, nextNode))
				.append("\" title=\"JOSM RemoteControl\" target=\"josmrc\">JOSM</a>");

		return sb.toString();
	}

	private static String createJOSMRemoteControlLink(Node prevNode, Node nextNode) {
		DecimalFormat df = new DecimalFormat("#.########", new DecimalFormatSymbols(Locale.US));

		float addSpace = 0.005f;

		String right, left, top, bottom = null;

		if (prevNode.getLat() > nextNode.getLat()) {
			top = df.format(prevNode.getLat() + addSpace);
			bottom = df.format(nextNode.getLat() - addSpace);
		}
		else {
			top = df.format(nextNode.getLat() + addSpace);
			bottom = df.format(prevNode.getLat() - addSpace);
		}

		if (prevNode.getLon() > nextNode.getLon()) {
			right = df.format(prevNode.getLon() + addSpace);
			left = df.format(nextNode.getLon() - addSpace);
		}
		else {
			right = df.format(nextNode.getLon() + addSpace);
			left = df.format(prevNode.getLon() - addSpace);
		}

		return "http://localhost:8111/load_and_zoom?left=" + left + "&right=" + right + "&top=" + top + "&bottom="
				+ bottom + "&select=node" + prevNode.getId() + ",node" + nextNode.getId();

	}

}
