package com.mobilenepal.hackathon1.HelloSarkar;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class PickADate extends Activity {
    private EditText et;
    DatePicker dpTime;
    String date;
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dbox);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // title
        try {
        	String s = "Pick a date";
            if (s.length() > 0) {
                this.setTitle(s);
            }
          
        } catch (Exception e) {
        }
        // value
		dpTime = (DatePicker) findViewById(R.id.datepick);


        // button
        ((Button) findViewById(R.id.btnDone)).setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
        		date = dpTime.getYear()+ "-" + (dpTime.getMonth()+1) + "-"+ dpTime.getDayOfMonth();
            	executeDone();
            }
        });
    }


    @Override
    public void onBackPressed() {
        executeDone();
        super.onBackPressed();
    }

    /**
     *
     */
    private void executeDone() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("value", date);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    
} 


