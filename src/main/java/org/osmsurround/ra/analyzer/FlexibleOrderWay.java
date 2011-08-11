package org.osmsurround.ra.analyzer;

import org.osmsurround.ra.data.Way;

public class FlexibleOrderWay extends FixedOrderWay {

	public FlexibleOrderWay(Way way, boolean reverse) {
		super(way, reverse);
	}

	@Override
	public boolean isReversible() {
		return true;
	}

	@Override
	public void reverse() {
		reverse = !reverse;
	}

}
