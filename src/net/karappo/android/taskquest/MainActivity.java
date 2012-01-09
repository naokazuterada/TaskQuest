package net.karappo.android.taskquest;

import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends MapActivity {
    
	private static final String TAG = "MapActivity";
	
	private Button command_btn;
	private Button tatakau_btn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        command_btn	= (Button) findViewById(R.id.command_btn);
        tatakau_btn	= (Button) findViewById(R.id.tatakau_btn);
        
        command_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) 
            {
            	Log.i(TAG, "onClick");
            	Intent intent = new Intent(getBaseContext(), StartUpActivity.class);
            	startActivity(intent);
            }
        });
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}