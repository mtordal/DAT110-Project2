package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from user to set of topics subscribed to by user
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	protected ConcurrentHashMap<String, ClientSession> clients;

	// task E, data structure for managing messages for disconnected clients
	// maps from user to set of messages
	protected ConcurrentHashMap<String, Set<Message>> buffer;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();

		// task E, buffer constructor
		buffer = new ConcurrentHashMap<String, Set<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	// task E, get set of messages from buffer
	public Set<Message> getMessages(String user) {
		return (buffer.get(user));
	}

	// task E, add message to buffer
	public void addBufferMessage(String user, Message msg) {
		if (!buffer.containsKey(user)) {
			Set<Message> set = new HashSet<Message>();
			set.add(msg);
			buffer.put(user, set);
		} else {
			buffer.get(user).add(msg);
		}
	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		
		if (!clients.containsKey(user)) {
			ClientSession session = new ClientSession(user, connection);
			clients.put(user, session);
		}
	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage

		if (clients.containsKey(user)) {
			clients.remove(user);
		}
	}

	public void disconnectUser(String user) {
		removeClientSession(user);
	}

	public void reconnectUser(String user, Connection connection) {
		clients.put(user, new ClientSession(user, connection));
	}

	public void createTopic(String topic) {

		// TODO: create topic in the storage

		if (!subscriptions.containsKey(topic)) {
			Set<String> subscribers = ConcurrentHashMap.newKeySet();
			subscriptions.put(topic, subscribers);
		}
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage

		if (subscriptions.containsKey(topic)) {
			subscriptions.remove(topic);
		}
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic

		if (subscriptions.containsKey(topic)) {
			Set<String> hashset = subscriptions.get(topic);
			if (!hashset.contains(user)) {
					hashset.add(user);
			}
		}
	}

	public void removeSubscriber(String user, String topic) {

		// TODO: remove the user as subscriber to the topic

		if (subscriptions.containsKey(topic)) {
			Set<String> hashset = subscriptions.get(topic);
			if (hashset.contains(user)) {
					hashset.remove(user);
			}
		}
	}
}
