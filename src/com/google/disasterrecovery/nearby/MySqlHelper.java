package com.google.disasterrecovery.nearby;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "LocationDB.sqlite";
	private static String DATABASE_PATH = "/data/data/com.zmachmobile.gps/databases/";
	private final Context myContext;
	private SQLiteDatabase myDataBase;

	public MySqlHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// String CREATE_LOCATION_TABLE="CREATE TABLE location("+
		// "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
		// "latitude TEXT, "+
		// "longitude TEXT, "+
		// "name TEXT)";
		//
		// db.execSQL(CREATE_LOCATION_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// db.execSQL("DROP TABLE IF EXISTS location");
		// this.onCreate(db);
	}

	// Locations table name
	private static final String TABLE_LOCATION = "location";

	// Locations Table Columns names
	private static final String KEY_ID = "_id";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGITUDE = "longitude";
	private static final String KEY_NAME = "name";

	private static final String[] COLUMNS = { KEY_ID, KEY_LATITUDE,
			KEY_LONGITUDE, KEY_NAME };

	public Evac getLocation(int id) {

		// 1. get reference to readable DB
		SQLiteDatabase db = this.getReadableDatabase();

		// 2. build query
		Cursor cursor = db.query(TABLE_LOCATION, // a. table
				COLUMNS, // b. column names
				" _id = ?", // c. selections
				new String[] { String.valueOf(id) }, // d. selections args
				null, // e. group by
				null, // f. having
				null, // g. order by
				null); // h. limit

		// 3. if we got results get the first one
		if (cursor != null)
			cursor.moveToFirst();

		Evac location = new Evac();
		location.setId(Integer.parseInt(cursor.getString(0)));
		location.setLat(Double.parseDouble(cursor.getString(1)));
		location.setLng(Double.parseDouble(cursor.getString(2)));
		location.setName(cursor.getString(3));

		return location;
	}

	public List<Evac> getAllLocations() {
		List<Evac> locations = null;

		// 1. build the query
		String query = "SELECT  * FROM " + TABLE_LOCATION;

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		if (cursor.moveToFirst()) {
			do {
				Evac location = new Evac();
				location.setId(Integer.parseInt(cursor.getString(0)));
				location.setLat(Double.parseDouble(cursor.getString(1)));
				location.setLng(Double.parseDouble(cursor.getString(2)));
				location.setName(cursor.getString(3));

				locations.add(location);
			} while (cursor.moveToNext());
		}

		// Log.d("getAllBooks()", books.toString());

		// return books
		return locations;
	}

	public Evac getEvac(double latitude, double longitude) {

		// 1. build the query
		String query = "SELECT * FROM evac";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		// Location evac = null;
		CalcDistance dist = new CalcDistance();
		Double minDist = 0.0;
		int counter = 0;
		Evac evac = new Evac();
		if (cursor.moveToFirst()) {
			do {
				Double distance = dist.getDistance(
						Double.parseDouble(cursor.getString(1)),
						Double.parseDouble(cursor.getString(2)), latitude,
						longitude);
				if (counter == 0) {
					counter = 1;
					minDist = distance;
					evac.setId(Integer.parseInt(cursor.getString(0)));
					evac.setLat(Double.parseDouble(cursor.getString(1)));
					evac.setLng(Double.parseDouble(cursor.getString(2)));
					evac.setName(cursor.getString(4));
					evac.setDistance(minDist);
				} else {
					if (distance < minDist) {
						minDist = distance;
						evac.setId(Integer.parseInt(cursor.getString(0)));
						evac.setLat(Double.parseDouble(cursor.getString(1)));
						evac.setLng(Double.parseDouble(cursor.getString(2)));
						evac.setName(cursor.getString(4));
						evac.setDistance(minDist);
					}
				}
				// Add book to books
			} while (cursor.moveToNext());
		}

		return evac;
	}

	public List<Evac> getDisasters(double latitude, double longitude) {
		List<Evac> disasters = new ArrayList<Evac>();

		String query = "SELECT  * FROM location";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		CalcDistance dist = new CalcDistance();
		Double minDist = 0.0;
		int counter = 0;
		int loc_id = 0;
		if (cursor.moveToFirst()) {
			do {
				Double distance = dist.getDistance(
						Double.parseDouble(cursor.getString(1)),
						Double.parseDouble(cursor.getString(2)), latitude,
						longitude);
				if (counter == 0) {
					counter = 1;
					minDist = distance;
					loc_id = Integer.parseInt(cursor.getString(0));
				} else {
					if (distance < minDist) {
						minDist = distance;
						loc_id = Integer.parseInt(cursor.getString(0));
					}
				}
			} while (cursor.moveToNext());
		}
		String query2 = "SELECT * FROM disaster JOIN location ON location._id=disaster.loc_id WHERE disaster.loc_id="
				+ Integer.toString(loc_id);
		Cursor cursor2 = db.rawQuery(query2, null);
		if (cursor2.moveToFirst()) {
			do {
				Evac evac = new Evac();
				evac.setDisaster(cursor2.getString(2));
				evac.setName(cursor2.getString(6));
				disasters.add(evac);
			} while (cursor2.moveToNext());
		}
		return disasters;
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			// do nothing - database already exist
		} else {

			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DATABASE_PATH + DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			// database doesn't exist yet.
			e.printStackTrace();
		}

		if (checkDB != null) {
			checkDB.close();
			return true;
		}
		
		return false;
	}

	private void copyDataBase() throws IOException {

		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DATABASE_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		// Open the database
		String myPath = DATABASE_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READONLY);

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}

}
