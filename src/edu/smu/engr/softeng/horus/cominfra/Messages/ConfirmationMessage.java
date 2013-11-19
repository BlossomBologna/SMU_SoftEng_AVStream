package edu.smu.engr.softeng.horus.cominfra.Messages;

public class ConfirmationMessage extends Message {

	public int confirmedMessageType;
	
	public ConfirmationMessage(int client, int conf) {
		super(5, client);
		confirmedMessageType = conf;
	}
	
}