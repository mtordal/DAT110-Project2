package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class DisplayDevice {
	
	private static final int COUNT = 10;
		
	public static void main (String[] args) {
		
		System.out.println("Display starting ...");
		
		// TODO - START
				
		// create a client object and use it to
		Client client = new Client("displayclient", Common.BROKERHOST, Common.BROKERPORT);
		
		// - connect to the broker
		client.connect();

		// - create the temperature topic on the broker
		client.createTopic("temperature");

		// - subscribe to the topic
		client.subscribe("temperature");

		// - receive messages on the topic
		for (int i = 0; i < COUNT; i++) {
			client.receive();
		}

		// - unsubscribe from the topic
		client.unsubscribe("temperature");

		// - disconnect from the broker
		client.disconnect();
		
		// TODO - END
		
		System.out.println("Display stopping ... ");
	}
}
