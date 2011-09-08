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
package org.osmsurround.ra.analyzer;


public class AnalyzerServiceOld {

	//	private Logger log = Logger.getLogger(getClass());
	//
	//	private Map<String, List<Segment>> segmentsRoleMap = new HashMap<String, List<Segment>>();
	//
	//	private Map<String, List<Way>> relationRoleMap = new TreeMap<String, List<Way>>();
	//
	//	private boolean usingCachedData = false;
	//
	//	private byte[][] colors = { { (byte)0xFF, 0x00, 0x00, (byte)0xFF },
	//			{ (byte)0xFF, (byte)0xFF, (byte)0x00, (byte)0x00 }, { (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x00 },
	//			{ (byte)0xFF, (byte)0x80, (byte)0x80, (byte)0x80 } };
	//
	//	private Map<String, Segment> orderedSegmentMap = new TreeMap<String, Segment>();
	//	private Map<String, Map<Long, Integer>> wayCounterPerRole = new HashMap<String, Map<Long, Integer>>();
	//	private List<Integer> memberRelation = new ArrayList<Integer>();
	//	private boolean multipleWaysPerRole = false;
	//
	//	public class ClosestData {
	//
	//		public double[] distance;
	//		public IWay way;
	//
	//		public ClosestData(double[] distance, IWay way) {
	//			super();
	//			this.distance = distance;
	//			this.way = way;
	//		}
	//
	//	}
	//
	//	private Way findConnectedWay(IWay way, List<Way> relationWays) {
	//		for (Iterator<Way> it = relationWays.iterator(); it.hasNext();) {
	//			Way w = it.next();
	//			if (w.isConnected(way)) {
	//				it.remove();
	//				return w;
	//			}
	//			//            else if (w.isRoundabout())
	//			//            {
	//			//                if (w.contains(way.getFirst()) || w.contains(way.getLast()))
	//			//                {
	//			//                    it.remove();
	//			//                    return w;
	//			//                }
	//			//            }
	//			//            else if (way.isRoundabout())
	//			//            {
	//			//                if (way.contains(w.getFirst()) || way.contains(w.getLast()))
	//			//                {
	//			//                    it.remove();
	//			//                    return w;
	//			//                }
	//			//            }
	//		}
	//		return null;
	//	}
	//
	//	private void findSegments(List<Way> relationWays, List<Segment> segments) {
	//		while (relationWays.size() > 0) {
	//			Segment segment = new Segment(relationWays.remove(0));
	//			segments.add(segment);
	//
	//			boolean found = false;
	//			do {
	//				Way way = findConnectedWay(segment, relationWays);
	//				found = way != null;
	//				if (found) {
	//					segment.add(way);
	//				}
	//
	//			} while (found);
	//
	//		}
	//	}
	//
	//	private void orderSegments(String role) {
	//		if (!segmentsRoleMap.get(role).isEmpty()) {
	//			List<Segment> seg2Order = new ArrayList<Segment>(segmentsRoleMap.get(role));
	//
	//			Segment orderedSegment = new Segment(seg2Order.remove(0));
	//
	//			boolean found = false;
	//			do {
	//				ClosestData cd = findClosestWay(orderedSegment, seg2Order);
	//				found = cd != null;
	//				if (found) {
	//					seg2Order.remove(cd.way);
	//					orderedSegment.addClose(cd.way, cd.distance[0]);
	//				}
	//
	//			} while (found);
	//
	//			orderedSegmentMap.put(role, orderedSegment);
	//		}
	//	}
	//
	//	public static double distance(Node n1, Node n2) {
	//		return Math.sqrt(Math.pow(n1.getLat() - n2.getLat(), 2) + Math.pow(n2.getLon() - n2.getLon(), 2));
	//	}
	//
	//	public static double distanceInKm(Node n1, Node n2) {
	//		return MyMath.distance(n1.getLon(), n1.getLat(), n2.getLon(), n2.getLat());
	//	}
	//
	//	private double[] smallestDistance(IWay way1, IWay way2) {
	//		double[] d = new double[4];
	//		d[0] = distance(way1.getFirst(), way2.getFirst());
	//		d[1] = distance(way1.getFirst(), way2.getLast());
	//		d[2] = distance(way1.getLast(), way2.getFirst());
	//		d[3] = distance(way1.getLast(), way2.getLast());
	//		Arrays.sort(d);
	//		return d;
	//	}
	//
	//	/**
	//	 * @param order
	//	 * @param seg2Order
	//	 * @return
	//	 */
	//	private ClosestData findClosestWay(Segment order, List<Segment> seg2Order) {
	//		ClosestData closest = null;
	//		if (order.hasNodes()) {
	//			double closestDistance = Double.POSITIVE_INFINITY;
	//
	//			for (Segment s : seg2Order) {
	//				if (s.hasNodes()) {
	//					double d[] = smallestDistance(order, s);
	//					if (d[0] < closestDistance) {
	//						closest = new ClosestData(d, s);
	//						closestDistance = d[0];
	//					}
	//				}
	//			}
	//		}
	//		return closest;
	//	}
	//
	//	public void run() {
	//
	//		for (String role : relationRoleMap.keySet()) {
	//			List<Segment> segments = new ArrayList<Segment>();
	//			segmentsRoleMap.put(role, segments);
	//			List<Way> relationWays = relationRoleMap.get(role);
	//			wayCounterPerRole.put(role, countWays(relationWays));
	//
	//			findSegments(relationWays, segments);
	//			orderSegments(role);
	//		}
	//	}
	//
	//	private Map<Long, Integer> countWays(List<Way> relationWays) {
	//		Map<Long, Integer> wayCountMap = new HashMap<Long, Integer>();
	//
	//		for (Way way : relationWays) {
	//			if (wayCountMap.containsKey(way.getId())) {
	//				wayCountMap.put(way.getId(), Integer.valueOf(wayCountMap.get(way.getId()).intValue() + 1));
	//				multipleWaysPerRole = true;
	//			}
	//			else {
	//				wayCountMap.put(way.getId(), Integer.valueOf(1));
	//			}
	//		}
	//
	//		return wayCountMap;
	//	}
	//
	//	public List<Segment> getSegments(String role) {
	//		return segmentsRoleMap.get(role);
	//	}
	//
	//	public boolean isUsingCachedData() {
	//		return usingCachedData;
	//	}
	//
	//	public boolean hasData() {
	//		return orderedSegmentMap != null && !orderedSegmentMap.isEmpty();
	//	}
	//
	//	public String rotateColors(int id) {
	//		HexBinaryAdapter hba = new HexBinaryAdapter();
	//		byte[] b = colors[id % colors.length];
	//		byte[] b2 = { b[1], b[2], b[3] };
	//		return "#" + hba.marshal(b2);
	//	}
	//
	//	public boolean hasMemberRelations() {
	//		return !memberRelation.isEmpty();
	//	}
	//
	//	public Map<String, Segment> getOrderedSegmentMap() {
	//		return orderedSegmentMap;
	//	}
	//
	//	public String createWayCounterInfo(String role, long wayId) {
	//		if (wayCounterPerRole.get(role).get(wayId).intValue() > 1) {
	//			return "&nbsp;(" + wayCounterPerRole.get(role).get(wayId).intValue() + "x)";
	//		}
	//		else
	//			return "";
	//	}
	//
	//	public boolean isMultipleWaysPerRole() {
	//		return multipleWaysPerRole;
	//	}

}
