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

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.lightstreamer.jms.demo.stocklist_service.message.FeedMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StockListDemoActivity extends AppCompatActivity {

  private static final List<Field> fields;

  static {
    List<Field> tempFields = new ArrayList<>();
    tempFields.add(new Field("Stock name", "stock_name"));
    tempFields.add(new Field("Time", "time"));
    tempFields.add(new Field("Last", "last_price"));
    tempFields.add(new Field("Min", "min"));
    tempFields.add(new Field("Max", "max"));
    tempFields.add(new Field("Bid", "bid"));
    tempFields.add(new Field("Ask", "ask"));
    fields = Collections.unmodifiableList(tempFields);
  }

  private HashMap<String, TableRow> rowsMap = new HashMap<>();

  private TableLayout tableLayout;

  private Handler handler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    ImageView logoImageView = findViewById(R.id.logoImageView);
    logoImageView.setBackgroundResource(R.drawable.lslogo);

    this.handler = new Handler(Looper.getMainLooper());

    tableLayout = findViewById(R.id.tableLayout);

    /* Add table headers */
    TableRow headerRow = new TableRow(this);
    for (int i = 0; i < fields.size(); i++) {
      TextView view = new TextView(this);
      Typeface tf = Typeface.create("", Typeface.BOLD);
      view.setText(fields.get(i).header);
      view.setTypeface(tf);
      this.tableLayout.setColumnStretchable(i, true);
      this.tableLayout.setColumnShrinkable(i, true);
      headerRow.addView(view);
    }
    tableLayout.addView(headerRow);

    JMSViewModel model = new ViewModelProvider(this).get(JMSViewModel.class);
    model.getMessage().observe(this, this::handleMessage);
  }

  private void handleMessage(FeedMessage feedMessage) {
    TableRow rowToUpdate =
        rowsMap.computeIfAbsent(
            feedMessage.itemName,
            itemName -> {
              TableRow newRow = new TableRow(this);
              fields.forEach(
                  f -> {
                    TextView view = new TextView(this);
                    newRow.addView(view);
                  });
              tableLayout.addView(newRow);
              return newRow;
            });

    for (int i = 0; i < rowToUpdate.getChildCount(); i++) {
      TextView view = (TextView) rowToUpdate.getChildAt(i);

      String previousValue = view.getText().toString();
      if (previousValue.isEmpty()) {
        previousValue = "0.0";
      }

      Field field = fields.get(i);
      String currentValue = feedMessage.currentValues.get(field.name);
      view.setText(currentValue);

      double upDown = 0.0;
      if (!field.name.equals("stock_name") && !field.name.equals("time")) {
        double valueInt = Double.parseDouble(currentValue);
        double oldValueInt = Double.parseDouble(previousValue);
        upDown = valueInt - oldValueInt;
      }
      updateBackgroundColor(view, upDown);
    }
  }

  private void updateBackgroundColor(View view, double upDown) {
    int color = getResources().getColor(R.color.RSS, null);
    if (upDown > 0) {
      color = getResources().getColor(R.color.TECHCCRUNC_GREEN, null);
    } else if (upDown < 0) {
      color = getResources().getColor(R.color.ROLLYO_RED, null);
    }

    view.setBackgroundColor(color);

    /* Reset color after 2 seconds */
    handler.postDelayed(() -> view.setBackgroundColor(Color.TRANSPARENT), 2000);
  }
}
