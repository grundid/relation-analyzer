package org.osmsurround.ra.report;

public class ModifiedRange {

	private long from;
	private long to;

	private long factor;

	private String messageCode;

	public ModifiedRange(long from, long to, long factor, String messageCode) {
		this.from = from;
		this.to = to;
		this.factor = factor;
		this.messageCode = messageCode;
	}

	public long getFrom() {
		return from;
	}

	public long getTo() {
		return to;
	}

	public long getFactor() {
		return factor;
	}

	public String getMessageCode() {
		return messageCode;
	}
}
