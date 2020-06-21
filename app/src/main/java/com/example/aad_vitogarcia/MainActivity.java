package com.example.aad_vitogarcia;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView stepsCounterView;
    TextView[] plates;
    TextView point25;
    TextView point20;
    TextView point15;
    TextView point10;
    TextView point5;
    TextView point2_5;
    TextView point1_25;
    TextView point0_5;
    TextView point0_25;
    CheckBox liftCollars;
    CheckBox plates25kg;
    int discCount[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    private TextView infoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        point25 = (TextView)findViewById(R.id.textView2);
        point20 = (TextView)findViewById(R.id.textView4);
        point15 = (TextView)findViewById(R.id.textView6);
        point10 = (TextView)findViewById(R.id.textView8);
        point5 = (TextView)findViewById(R.id.textView10);
        point2_5 = (TextView)findViewById(R.id.textView12);
        point1_25 = (TextView)findViewById(R.id.textView14);
        point0_5 = (TextView)findViewById(R.id.textView16);
        point0_25 = (TextView)findViewById(R.id.textView18);
        plates = new TextView[] {point25, point20, point15, point10, point5, point2_5, point1_25, point0_5, point0_25};
        liftCollars = findViewById(R.id.checkBox);
        plates25kg = findViewById(R.id._25kg);



        final EditText inputWeight = (EditText) findViewById(R.id.weight);
        inputWeight.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String rawWT = inputWeight.getText().toString();
                if (rawWT.matches("")){
                    return;
                }
                else
                    countReset();
                Arrays.fill(discCount, 0);
                calculate(Double.parseDouble(inputWeight.getText().toString()));
            }
        });

        plates25kg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                String rawWT = inputWeight.getText().toString();
                if (rawWT.matches("")){
                    return;
                }
                else
                    countReset();
                Arrays.fill(discCount, 0);
                calculate(Double.parseDouble(inputWeight.getText().toString()));
            }
        });

        liftCollars.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                String rawWT = inputWeight.getText().toString();
                if (rawWT.matches("")){
                    return;
                }
                else
                    countReset();
                Arrays.fill(discCount, 0);
                calculate(Double.parseDouble(inputWeight.getText().toString()));
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notes:
                Intent intentNotes =  new Intent(getApplicationContext(), NotesMain.class);
                startActivity(intentNotes);

            case R.id.info: {
                //Perform your click operation
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Powerlifting Weights Info");
                builder.setMessage("The standard weight of a barbell is 20 kilograms. \nThe weights being used are called plates.\nDepending on the gym, they won't have 25kg plates available. \nThe standard collar weight for USAPL is 2.5kg a piece, adding 5kg to the total. \nThe plates counted are for each side.\nFor example, 20kg x 4, means that there 8 total plates.");

                builder.show();
                break;
            }
            case R.id.stepsCounter:
                Intent intent =  new Intent(getApplicationContext(), StepsActivity.class);
                startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    public void calculate(double wt){
        if (wt % 0.5 != 0){ return; }
        if (wt <= 20 && liftCollars.isChecked() == false){ return; }

        double netWT = wt - 20;
        if (liftCollars.isChecked()){ netWT -= 5; }

        while (netWT > 0){
            if (netWT - 25*2 >= 0 && plates25kg.isChecked()){
                discCount[0]++;
                netWT = netWT - 25*2;
            }
            else if (netWT - 20*2 >= 0){
                discCount[1]++;
                netWT = netWT - 20*2;
            }
            else if (netWT - 15*2 >= 0){
                discCount[2]++;
                netWT = netWT - 15*2;
            }
            else if (netWT - 10*2 >= 0){
                discCount[3]++;
                netWT = netWT - 10*2;
            }
            else if (netWT - 5*2 >= 0){
                discCount[4]++;
                netWT = netWT - 5*2;
            }
            else if (netWT - 2.5*2 >= 0){
                discCount[5]++;
                netWT = netWT - 2.5*2;
            }
            else if (netWT - 1.25*2 >= 0){
                discCount[6]++;
                netWT = netWT - 1.25*2;
            }
            else if (netWT - 0.5*2 >= 0){
                discCount[7]++;
                netWT = netWT - 0.5*2;
            }
            else if (netWT - 0.25*2 >= 0){
                discCount[8]++;
                netWT = netWT - 0.25*2;
            }
        }
        for (int i =0; i < plates.length; i++){
            plates[i].setText("x" + Integer.toString(discCount[i]));
            String count = plates[i].getText().toString();
            System.out.println(count);
            if (count.equals("x0") == false){
                plates[i].setTextColor(Integer.parseInt("f44336", 16)+0xFF000000);
            }
        }
    }

    public void countReset(){
        for (int i =0; i < plates.length; i++) {
            plates[i].setText("x0");
            plates[i].setTextColor(Integer.parseInt("808080", 16)+0xFF000000);
        }
    }


}