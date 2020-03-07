package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		// TODO - start

		// create a client object and use it to
		Client client = new Client("temperatureclient", Common.BROKERHOST, Common.BROKERPORT);

		// - connect to the broker
		client.connect();

		// - publish the temperature(s)
		for (int i = 0; i < COUNT; i++) {
			client.publish("temperature", Integer.toString(sn.read()));
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// - disconnect from the broker
		client.disconnect();

		// TODO - end

		System.out.println("Temperature device stopping ... ");
	}
}
