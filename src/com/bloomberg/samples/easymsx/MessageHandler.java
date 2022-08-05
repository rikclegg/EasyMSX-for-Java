package com.bloomberg.samples.easymsx;

import com.bloomberglp.blpapi.Message;

interface MessageHandler {
	public void processMessage(Message message);
}
