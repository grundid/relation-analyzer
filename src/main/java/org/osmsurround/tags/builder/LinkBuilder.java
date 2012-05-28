package org.osmsurround.tags.builder;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.osmsurround.ra.data.Node;

public abstract class LinkBuilder {

	protected DecimalFormat createFormatter() {
		return new DecimalFormat("#.########", new DecimalFormatSymbols(Locale.US));
	}

	public abstract String buildLinkForNodes(Node node1, Node node2);
}
