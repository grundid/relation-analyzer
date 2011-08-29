/**
 * This file is part of Relation Analyzer for OSM.
 * Copyright (c) 2001 by Adrian Stabiszewski, as@grundid.de
 *
 * Relation Analyzer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Relation Analyzer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Relation Analyzer.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.osmsurround.ra.utils;

public class LonLatMath {

	private static int[] sinTable = new int[201];
	private static int[] cosTable = new int[201];

	static {
		for (int x = -157; x <= 157; x++) {
			int pos = round(Math.sin((double)x / 100.0) * 100);
			sinTable[pos + 100] = x;

			pos = round(Math.cos((double)x / 100.0) * 100);
			cosTable[pos + 100] = x;
		}
	}

	private static final double LAT_DEGEE_DISTANCE = 40009.153 / 362; // Erdumfang ueber die Pole
	private static final double LONG_DEGEE_DISTANCE = 40076.592 / 360; // Erdumfang am Aequator

	public static int round(double d) {
		return (int)new Double(Math.floor(d + 0.5d)).longValue();
	}

	public static double rotateX(double x, double y, double sin, double cos) {
		return cos * x + sin * y;
	}

	public static double rotateY(double x, double y, double sin, double cos) {
		return -sin * x + cos * y;
	}

	public static double distance(double long1, double lat1, double long2, double lat2) {
		double latDistance = Math.abs(lat1 - lat2) * LAT_DEGEE_DISTANCE;
		double longDistance = Math.abs(long1 - long2)
				* Math.cos(Math.toRadians(Math.max(lat1, lat2) - Math.abs(lat1 - lat2))) * LONG_DEGEE_DISTANCE;
		return Math.sqrt(latDistance * latDistance + longDistance * longDistance);
	}

	public static double asin(double d) {
		return (double)sinTable[round(d * 100) + 100] / 100.0;
	}

	public static double acos(double d) {
		return (double)cosTable[round(d * 100) + 100] / 100.0;
	}

	public static double checkpointDirection(double cpLong, double cpLat, double posLong, double posLat) {
		double latDistance = Math.abs(cpLat - posLat) * LAT_DEGEE_DISTANCE;
		double longDistance = Math.abs(cpLong - posLong)
				* Math.cos(Math.toRadians(Math.max(cpLat, posLat) - Math.abs(cpLat - posLat))) * LONG_DEGEE_DISTANCE;

		double hypo = Math.sqrt(latDistance * latDistance + longDistance * longDistance);

		double sin = longDistance / hypo;

		double raddeg = asin(sin);

		if (cpLat < posLat) {
			if (cpLong > posLong) {
				return Math.PI - raddeg; // SUED OST
			}
			else {
				return raddeg + Math.PI; // SUED WEST
			}
		}
		else {
			if (cpLong > posLong) {
				return raddeg; // NORD OST
			}
			else {
				return 2 * Math.PI - raddeg; // NORD WEST
			}
		}
	}
}
