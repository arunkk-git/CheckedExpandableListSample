package ro.rekaszeru.stackoverflow.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import ro.rekaszeru.stackoverflow.R;

public class SetTimeDialog extends Activity {
    TimePicker timePicker  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_dialog);
        timePicker =  (TimePicker) findViewById(R.id.timePicker);

    }
    public void replywithResult(){
        int hour = 0;
        int minute = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        }
        else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Intent intent=new Intent();
        String futureTime = hour+ " : " + minute;
        intent.putExtra("MESSAGE", futureTime);
        setResult(2,intent);

    }
    public void onButtonClick(View V){
        replywithResult();
        finish();//finishing activity

    }
}
