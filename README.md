# Lightstreamer JMS Extender - Basic Stock-List Demo - Android Client

<!-- START DESCRIPTION lightstreamer-jms-example-stocklist-client-android -->

This project contains a full example of an Android application that shows how the [Lightstreamer JMS Extender Java Client Library_](https://github.com/Lightstreamer/JMSExtender-lib-client-java) can be used to connect to Lightstreamer JMS Extender.

![Screenshot](screen_android_large.png)

## Details

This is a Java-for-Android version of the [Lightstreamer - Basic Stock-List Demo - HTML client](https://github.com/Lightstreamer/Lightstreamer-example-StockList-client-javascript#basic-stock-list-demo---html-client).<br>
This app uses the <b>Android Client API for Lightstreamer</b> to handle the communications with Lightstreamer Server. A simple user interface is implemented to display the real-time data received from Lightstreamer Server.<br>

This demo displays real-time market data for 20 stocks, generated by the feed simulator included in the [Lightstreamer JMS Extender - Stock-List Demo - Java (JMS) Service](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-service-java). For simplicity, only the `stock_name` and `last_price` fields of updated items are displayed, and they are just logged to the console. Moreover, a single JMS topic is used for all stocks, while in a real scenario you would probably use a different JMS topic for each stock.

Check out the sources for further explanations.
  
## Build

If you want to install a version of this demo pointing to your local Lightstreamer JMS Extender and running into 
an [Android Virtual Device](http://developer.android.com/tools/devices/emulator.html), follow these steps:

* Note that, as prerequisite, the [Lightstreamer JMS Extender - Stock-List Demo - Java (JMS) Service](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-service-java) has to be deployed on your local Lightstreamer JMS Extender instance. Please check out that project and follow the installation instructions provided with it.

* Launch Lightstreamer JMS Extender.

* Locate or install the [Android SDK](http://developer.android.com/sdk/index.html)
* Install the `Android_StockListDemo.apk` in your emulator:
  * Execute the emulator (Android SDK/SDK Manager->Tools->Manage AVDs...->New then Start)
  * Open the console and move to the platform-tools folder of SDK directory.
  * Then type the following command:
    ```
    adb install [.apk path]
    ```
* Look up the demo in your virtual device and launch it.

*Note that the demo targets the Lightstreamer server @ http://10.0.2.2:8080 because 10.0.2.2 is the special alias to your host loopback interface.*

## Build

### Setup the IDE

Note that you can skip this section and build the application without using any IDE. 

To open the project in [Android Studio](https://developer.android.com/sdk/installing/studio.html), import the provided Gradle project.

### Build

To build your own version of the demo you can launch the provided Gradle script from the command line or from the IDE itself.
As an example you can build and install a debug version of the application in an emulator (or device) by running
```
avdmanager create avd -n test -k "system-images;android-30;google_apis;x86"
```

```

emulator -avd test
```

```
$ gradlew installDebug
```

## See Also

### Lightstreamer JMS Extender Service Needed by This Demo Client

<!-- START RELATED_ENTRIES -->
* [Lightstreamer JMS Extender - Stock-List Demo - Java (JMS) Service](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-service-java)

### Related Projects

* [Lightstreamer JMS Extender - Basic Stock-List Demo - Java SE Client](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-client-java)
* [Lightstreamer JMS Extender - Basic Stock-List Demo - HTML Client](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-client-javascript)
* [Lightstreamer JMS Extender - Basic Stock-List Demo - Node.js Client](https://github.com/Lightstreamer/Lightstreamer-JMS-example-StockList-client-node)

## Lightstreamer Compatibility Notes

* Compatible with Lightstreamer JMS Extender Java Client Library since version 2.0.0 or newer.
* Compatible with Lightstreamer JMS Extender since version 2.0.0 or newer.
