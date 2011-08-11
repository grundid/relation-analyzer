/*
 * apps.midp
 * (c) Copyright by M.IT 2002-2005
 * www.emaitie.de 
 */
package org.osmsurround.ra.analyzer;

/**
 * @author adrian
 * @since 22.06.2005
 * @version $Id: $
 */
public class MyMath {

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

		//		System.out.println("latD: "+latDistance+" longD: "+longDistance+" hypo: "+hypo);
		//		System.out.println("sin: "+sin+" cos: "+cos+" Grad sin: "+raddeg+ " Grad cos: "+raddegcos);

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
