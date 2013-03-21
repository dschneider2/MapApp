package edu.elon.mapapp;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
	private GoogleMap mMap;
	private int[] colors = {Color.RED, Color.BLUE};
	private int whichColorIsShowing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.googlemapfragment)).getMap();
		mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

		setUpMapIfNeeded();

		textView = (TextView) findViewById(R.id.textviewlayout);
		relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
		whichColorIsShowing = -1;
	}

	@Override
	protected void onResume(){
		super.onResume();

		setUpMapIfNeeded();
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER/*LocationManager.GPS_PROVIDER*/,0,0,locationListener);
	}

	@Override
	protected void onPause(){
		super.onPause();

		locManager.removeUpdates(locationListener);
	}

	private void setUpMapIfNeeded() {
	    // Do a null check to confirm that we have not already instantiated the map.
	    if (mMap == null) {
	        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.googlemapfragment))
	                            .getMap();
	        // Check if we were successful in obtaining the map.
	        if (mMap != null) {
	            // The Map is verified. It is now safe to manipulate the map.

	        }
	    }
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
			mMap.addMarker(new MarkerOptions()
	        .position(new LatLng(currentLatitude, currentLongitude))
	        .title("Hello world"));
			CameraPosition cp = new CameraPosition.Builder()
            .target(new LatLng(currentLatitude,currentLongitude))
            .zoom(17)
            .build();     
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));

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