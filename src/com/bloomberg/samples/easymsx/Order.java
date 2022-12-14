package com.bloomberg.samples.easymsx;

import java.util.ArrayList;

public class Order extends FieldsOwner {
	
	Orders parent;
	int sequence;
	private Broker broker;
	public Object metaData;
	
	ArrayList<NotificationHandler> notificationHandlers = new ArrayList<NotificationHandler>();

	Order(Orders parent) {
		this.parent = parent;
		this.fields = new Fields(this);
		this.sequence = 0;
	}
	
	public Broker getBroker(){
		return this.broker;
	}
	
	public Field field(String fieldname) {
		return this.fields.field(fieldname);
	}

	public void addNotificationHandler(NotificationHandler notificationHandler) {
		notificationHandlers.add(notificationHandler);
	}

	void notify(Notification notification) {
		
		for(NotificationHandler nh: notificationHandlers) {
			if(!notification.consume) nh.processNotification(notification);
		}
		if(!notification.consume) parent.processNotification(notification);

	}
}
