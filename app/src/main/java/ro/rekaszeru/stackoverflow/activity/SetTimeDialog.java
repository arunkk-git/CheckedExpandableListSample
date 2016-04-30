package ro.rekaszeru.stackoverflow.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import ro.rekaszeru.stackoverflow.R;

public class SetTimeDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_dialog);
    }
    public void onButtonClick(View V){
        finish();
    }
}
