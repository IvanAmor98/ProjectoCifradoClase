package com.example.projectoclase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
            ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource(R.drawable.arrow_blue);
        });
        findViewById(R.id.buttonMayus).setOnLongClickListener(v -> {
            findViewById(R.id.tableMinus).setVisibility(View.VISIBLE);
            findViewById(R.id.tableMayus).setVisibility(View.INVISIBLE);
            ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource(R.drawable.arrow);
            return true;
        });
        findViewById(R.id.erase).setOnClickListener(v -> {
            TextView input = findViewById(R.id.input);
            CharSequence value = input.getText();
            value = value.subSequence(0, value.length() - 1);
            input.setText(value);
        });
        findViewById(R.id.erase).setOnLongClickListener(v -> {
            ((TextView)findViewById(R.id.input)).setText("");
            return true;
        });
    }

    private void encrypt() {
        char[] minus = "abcdefghijklmnñopqrstuvwxyz".toCharArray();
        char[] mayus = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".toCharArray();
        String input = (String) ((TextView) findViewById(R.id.input)).getText();
        String output = "";
        int cryptKey = Integer.parseInt(((Spinner) findViewById(R.id.encryptOption)).getSelectedItem().toString());
        for (char c: input.toCharArray()) {
            if (c <= 90 || c == 165) {
                output += getChar(mayus, c ,cryptKey);
            } else {
                output += getChar(minus, c ,cryptKey);
            }
        }
        ((TextView) findViewById(R.id.output)).setText(output);
    }

    private char getChar(char[] chars, char c, int cryptKey) {
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                int index = i + cryptKey >= chars.length ? i + cryptKey - chars.length : i + cryptKey;
                return (char) (chars[index]);
            }
        }
        return ' ';
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