package com.google.disasterrecovery.nearby;

public class Evac {
	private int id;
	private double latitude;
	private double longitude;
	private String name;
	private double distance;
	private String disaster;
	
	public Evac() {
		
	}

	public Evac(double latitude, double longitude, String name) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setLat(double latitude) {
		this.latitude = latitude;
	}

	public double getLat() {
		return this.latitude;
	}

	public void setLng(double longitude) {
		this.longitude = longitude;
	}

	public double getLng() {
		return this.longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisaster(String disaster) {
		this.disaster = disaster;
	}

	public String getDisaster() {
		return this.disaster;
	}

	public String getName() {
		return this.name;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getDistance() {
		return this.distance;
	}

	@Override
	public String toString() {
		return "Name : " + name + "\nLatitude : " + latitude + "\nLongitude : "
				+ longitude;
	}
}
