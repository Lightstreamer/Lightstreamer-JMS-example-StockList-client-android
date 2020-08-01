package com.lightstreamer.jms.demo.stocklist_client;

import androidx.lifecycle.LiveData;
import com.lightstreamer.jms.demo.stocklist_service.message.FeedMessage;

public class FeedMessageLiveData extends LiveData<FeedMessage> {

  private final JMSExtenderClient client;

  public FeedMessageLiveData(JMSExtenderClient extenderClient) {
    this.client = extenderClient;
  }

  @Override
  protected void onActive() {
    super.onActive();
    client.connect(message -> postValue(message));
  }

  @Override
  protected void onInactive() {
    super.onInactive();
    client.disconnect();
  }
}
