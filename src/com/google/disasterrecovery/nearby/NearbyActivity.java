package com.google.disasterrecovery.nearby;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class NearbyActivity extends Activity {
	double latitude;
	double longitude;
	double lat1 = -7.540811;
	double long1 = 110.445600;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
		// buka database, databasenya ada di assets
		MySqlHelper myDbHelper = new MySqlHelper(this);
		// jika database g ada, maka buat database, jika ada, open aja
		try {
			myDbHelper.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create database");
		}

		try {
			myDbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		// inisiasi GPS
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); 

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);

		List<String> providers = locationManager
				.getProviders(criteria, true /* enabledOnly */);
		// nge-list semua provider yg tersedia, jika ketemu, maka panggil locationlistener
		for (String provider : providers) {
			long minTime = 2000;
			float minDistance = 20;
			try {
				Toast.makeText(getApplicationContext(), provider,
						Toast.LENGTH_LONG).show();
				locationManager.requestLocationUpdates(provider, minTime,
						minDistance, locationListener);
			} catch (Exception e) {
				// TODO: handle exception
				CharArrayWriter cw = new CharArrayWriter();
				PrintWriter w = new PrintWriter(cw);
				e.printStackTrace(w);
				w.close();
				String trace = cw.toString();
				Log.w("Error", trace);
				Toast.makeText(getApplicationContext(), trace,
						Toast.LENGTH_LONG).show();
			}
			break;
		}
	}

	private final LocationListener locationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			longitude = location.getLongitude();
			latitude = location.getLatitude();
			String lat = Double.toString(latitude);
			String lng = Double.toString(longitude);
			TextView err = (TextView) findViewById(R.id.title);
			
			MySqlHelper myDbHelper = new MySqlHelper(null);
			myDbHelper = new MySqlHelper(getApplicationContext());
			myDbHelper.openDataBase();
			List<Evac> currentloc = myDbHelper.getDisasters(latitude, longitude);
			err.setText("You are in "+currentloc.get(0).getName()+"\n\n");
			for (Evac disaster : currentloc) {
				err.append("You are in the middle of : "+disaster.getDisaster().toString()+"\n");
			}
			Evac nearest = myDbHelper.getEvac(latitude, longitude); // objek evac untuk hitung lokasi dgn jarak terdekat

			double dist = nearest.getDistance();
			DecimalFormat df = new DecimalFormat("#.##");
			dist = Double.valueOf(df.format(dist));

			Toast.makeText(getApplicationContext(),
					"Lat : " + Double.toString(latitude) + ", Long : " + Double.toString(longitude),
					Toast.LENGTH_LONG).show();
			err.append("\nCurrent Latitude : " + Double.toString(latitude) + "\nCurrent Longitude : "
					+ Double.toString(longitude)
					+ "\n\nNearest Evac : " + nearest.getName().toString()
					+ " in about " + Double.toString(dist) + " km");
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			TextView err = (TextView) findViewById(R.id.title);
			err.append("\n OK");
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
