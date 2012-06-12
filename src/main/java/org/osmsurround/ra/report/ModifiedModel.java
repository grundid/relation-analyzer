package org.osmsurround.ra.report;

public class ModifiedModel {

	private int amount;
	private String messageCode;

	public ModifiedModel(int amount, String messageCode) {
		this.amount = amount;
		this.messageCode = messageCode;
	}

	public int getAmount() {
		return amount;
	}

	public String getMessageCode() {
		return messageCode;
	}

}
