package edu.elon.mapapp;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView textView;
	private RelativeLayout relativeLayout;
	private LocationManager locManager;
	
	private int[] colors = {Color.RED, Color.BLUE};
	private int whichColorIsShowing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textView = (TextView) findViewById(R.id.textviewlayout);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
		whichColorIsShowing = -1;
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(/*LocationManager.NETWORK_PROVIDER*/LocationManager.GPS_PROVIDER,0,0,locationListener);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		locManager.removeUpdates(locationListener);
	}
	
	private LocationListener locationListener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			//grab the location
			double currentLatitude = location.getLatitude();
			double currentLongitude = location.getLongitude();
			
			textView.setText("Latitude: " + currentLatitude + "\n" +
					"Longitude:" + currentLongitude);
			whichColorIsShowing = (whichColorIsShowing+1) % colors.length;
			relativeLayout.setBackgroundColor(colors[whichColorIsShowing]);
			
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	};


}
