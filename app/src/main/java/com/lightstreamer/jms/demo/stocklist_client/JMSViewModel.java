package com.lightstreamer.jms.demo.stocklist_client;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

public class JMSViewModel extends AndroidViewModel {

  private final JMSExtenderClient client;

  private final FeedMessageLiveData message;

  public JMSViewModel(Application app) {
    super(app);

    String host = app.getResources().getString(R.string.host);
    String connector = app.getResources().getString(R.string.connector);
    client =
        new JMSExtenderClient(host, connector, app.getResources().getString(R.string.topicName));

    message = new FeedMessageLiveData(client);
  }

  public FeedMessageLiveData getMessage() {
    return message;
  }
}
