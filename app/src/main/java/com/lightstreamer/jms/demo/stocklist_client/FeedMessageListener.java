package com.lightstreamer.jms.demo.stocklist_client;

import com.lightstreamer.jms.demo.stocklist_service.message.FeedMessage;

@FunctionalInterface
public interface FeedMessageListener {

  public void onMessage(FeedMessage message);
}
