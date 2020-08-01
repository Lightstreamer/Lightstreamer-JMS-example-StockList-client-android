/*
 * Copyright (c) Lightstreamer Srl
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lightstreamer.jms.demo.stocklist_client;

import com.lightstreamer.jms.LSConnectionFactory;
import com.lightstreamer.jms.demo.stocklist_service.message.FeedMessage;
import java.util.concurrent.CompletableFuture;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

/**
 * This class wraps the real Lightstreamer Client object, exposing start/stop methods for general
 * consumption. This class can be accessed concurrently.
 */
public class JMSExtenderClient {

  private final ConnectionFactory connectionFactory;

  private final String topicName;

  private CompletableFuture<Connection> connection;

  private Session session;

  public JMSExtenderClient(String serverAddress, String connector, String topicName) {
    connectionFactory = new LSConnectionFactory(serverAddress, connector);
    this.topicName = topicName;
  }

  public void connect(FeedMessageListener liveMessage) {
    connection =
        CompletableFuture.supplyAsync(
            () -> {
              try {
                Connection connection = connectionFactory.createConnection();
                connection.start();
                session = connection.createSession();

                Topic stocksList = session.createTopic(topicName);
                MessageConsumer consumer = session.createConsumer(stocksList);
                consumer.setMessageListener(
                    message -> {
                      ObjectMessage objectMessage = (ObjectMessage) message;
                      try {
                        FeedMessage feedMessage = (FeedMessage) objectMessage.getObject();
                        liveMessage.onMessage(feedMessage);
                      } catch (Exception e) {
                        throw new RuntimeException(e);
                      }
                    });
                return connection;
              } catch (JMSException e) {
                throw new RuntimeException(e);
              }
            });
  }

  public void disconnect() {
    if (connection != null) {
      connection.thenAcceptAsync(
          con -> {
            try {
              con.close();
            } catch (JMSException e) {
              throw new RuntimeException(e);
            }
          });
    }
  }
}
