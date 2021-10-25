package com.example.projectoclase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtonListener(findViewById(R.id.tableMayus));
        setButtonListener(findViewById(R.id.tableMinus));

        findViewById(R.id.Submit).setOnClickListener(v -> encrypt());
        findViewById(R.id.buttonMayus).setOnClickListener(v -> {
            findViewById(R.id.tableMinus).setVisibility(View.INVISIBLE);
            findViewById(R.id.tableMayus).setVisibility(View.VISIBLE);
        });
        findViewById(R.id.buttonMayus).setOnLongClickListener(v -> {
            findViewById(R.id.tableMinus).setVisibility(View.VISIBLE);
            findViewById(R.id.tableMayus).setVisibility(View.INVISIBLE);
            return true;
        });
    }

    private void encrypt() {
        String input = (String) ((TextView) findViewById(R.id.input)).getText();
        String output = "";
        int cryptKey = Integer.parseInt(((Spinner) findViewById(R.id.encryptOption)).getSelectedItem().toString());
        for (char c: input.toCharArray()) {
            output += (char) (c + cryptKey);
        }
        ((TextView) findViewById(R.id.output)).setText(output);
    }

    private void setButtonListener(TableLayout table) {
        for (int i = 0; i < table.getChildCount(); i++) {
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++)
                row.getChildAt(j).setOnClickListener(v -> {
                    TextView textView = findViewById(R.id.input);
                    String output = textView.getText() + ((String) ((TextView) findViewById(v.getId())).getText());
                    textView.setText(output);
                });
        }
    }
}