package com.example.projectoclase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean fixedUpperCase = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(),"Ha pulsado settings", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set keyboard buttons listener
        setButtonListener(findViewById(R.id.tableUpperCase));
        setButtonListener(findViewById(R.id.tableLowerCase));

        findViewById(R.id.encrypt).setOnClickListener(v -> encrypt(true));
        findViewById(R.id.decrypt).setOnClickListener(v -> encrypt(false));

        findViewById(R.id.buttonMayus).setOnClickListener(v -> {
            if (fixedUpperCase) {
                findViewById(R.id.tableLowerCase).setVisibility(View.VISIBLE);
                findViewById(R.id.tableUpperCase).setVisibility(View.INVISIBLE);
                ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource(R.drawable.arrow);
            } else {
                findViewById(R.id.tableLowerCase).setVisibility(View.INVISIBLE);
                findViewById(R.id.tableUpperCase).setVisibility(View.VISIBLE);
                ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource(R.drawable.arrow_blue);
            }
            fixedUpperCase = false;
        });

        findViewById(R.id.buttonMayus).setOnLongClickListener(v -> {
            findViewById(R.id.tableLowerCase).setVisibility(View.INVISIBLE);
            findViewById(R.id.tableUpperCase).setVisibility(View.VISIBLE);
            ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource (R.drawable.arrow_blue);
            fixedUpperCase = true;
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

    private void encrypt(boolean crypt) {
        char[] lowerCase = getResources().getString(R.string.lowerCase).toCharArray();
        char[] upperCase = getResources().getString(R.string.upperCase).toCharArray();
        String input = (String) ((TextView) findViewById(R.id.input)).getText();
        String output = "";
        int cryptKey = Integer.parseInt(((Spinner) findViewById(R.id.encryptOption)).getSelectedItem().toString());
        for (char c: input.toCharArray()) {
            if (c <= 90 || c == 165) {
                output += getChar(upperCase, c ,cryptKey, crypt);
            } else {
                output += getChar(lowerCase, c ,cryptKey, crypt);
            }
        }
        ((TextView) findViewById(R.id.output)).setText(output);
    }

    private char getChar(char[] chars, char c, int cryptKey, boolean crypt) {
        for (int i = 0; i < chars.length; i++) {
            if (c == chars[i]) {
                int index = 0;
                if (crypt) {
                    index = i + cryptKey >= chars.length ? i + cryptKey - chars.length : i + cryptKey;
                } else {
                    index = i - cryptKey < 0 ? i - cryptKey + chars.length : i - cryptKey;
                }
                return chars[index];
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
                    if (!fixedUpperCase)
                        swapKeyboards();
                });
        }
    }

    private void swapKeyboards() {
        findViewById(R.id.tableLowerCase).setVisibility(View.VISIBLE);
        findViewById(R.id.tableUpperCase).setVisibility(View.INVISIBLE);
        ((ImageButton)findViewById(R.id.buttonMayus)).setImageResource(R.drawable.arrow);
    }
}