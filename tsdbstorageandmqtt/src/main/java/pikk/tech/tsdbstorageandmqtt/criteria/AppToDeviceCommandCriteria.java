package pikk.tech.tsdbstorageandmqtt.criteria;

import java.io.Serializable;


public class AppToDeviceCommandCriteria implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String topic;
	private String command;
	private String device;
	
	public AppToDeviceCommandCriteria() {
		
	}
	
	public AppToDeviceCommandCriteria(String topic, String command, String device) {
		super();
		this.topic = topic;
		this.command = command;
		this.device = device;
	}
	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	
}
