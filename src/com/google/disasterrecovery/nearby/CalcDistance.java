package com.google.disasterrecovery.nearby;

public class CalcDistance {
	public CalcDistance() {

	}

	public Double getDistance(double lat1, double long1, double latitude,
			double longitude) {
		// TODO Auto-generated method stub
		double AVERAGE_RADIUS_OF_EARTH = 6372.797;

		double latDistance = Math.toRadians(latitude - lat1);
		double lngDistance = Math.toRadians(longitude - long1);

		double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2))
				+ (Math.cos(Math.toRadians(latitude)))
				* (Math.cos(Math.toRadians(longitude)))
				* (Math.sin(lngDistance / 2)) * (Math.sin(lngDistance / 2));

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double result = AVERAGE_RADIUS_OF_EARTH * c;
		return result;
	}
}
