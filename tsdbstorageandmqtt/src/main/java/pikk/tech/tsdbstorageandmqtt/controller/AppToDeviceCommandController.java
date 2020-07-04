package pikk.tech.tsdbstorageandmqtt.controller;

import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pikk.tech.tsdbstorageandmqtt.criteria.AppToDeviceCommandCriteria;

@RestController
public class AppToDeviceCommandController {


@PostMapping(value = "/AppToDeviceCommand")
public boolean messageToDevice(@RequestBody AppToDeviceCommandCriteria commandCriteria) {
	try {
	InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086");
	Pong response = influxDB.ping();
	if (!response.getVersion().equalsIgnoreCase("unknown")) {
		influxDB.setDatabase("AppToDeviceCommand");
	    System.out.println("Influx connected");
	    System.out.println("pushing data to Influx");
	    Point point = Point.measurement("AppToDeviceCommand")
	    		  .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
	    		  .addField("topic",commandCriteria.getTopic()) 
	    		  .addField("command", commandCriteria.getCommand())
	    		  .addField("device", commandCriteria.getDevice()) 
	    		  .build();
	    influxDB.write(point);
	} 
	MqttClient client = new MqttClient("tcp://test.mosquitto.org", MqttClient.generateClientId());
	client.connect();
	if(client.isConnected()) {
		System.out.println("Client Connected");
	}
	MqttMessage message = new MqttMessage();
	message.setPayload(commandCriteria.getCommand().getBytes());
	client.publish(commandCriteria.getTopic(), message);
	client.disconnect();
	}catch (MqttException e) {
		System.out.println(e.getLocalizedMessage());
		return false;
	}
	return true;
	
}

}
