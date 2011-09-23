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
package org.osmsurround.ra.segment;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.osmsurround.ra.AnalyzerException;
import org.osmsurround.ra.analyzer.ConnectableNode;
import org.osmsurround.ra.data.Node;
import org.osmsurround.ra.utils.LonLatMath;

public class FlexibleWay implements ConnectableSegment {

	protected List<Node> wayNodes;
	private ConnectableNode endpointNodes;

	public FlexibleWay(List<Node> nodes) {
		this.wayNodes = nodes;
		this.endpointNodes = new ConnectableNode(nodes);
	}

	@Override
	public SegmentNodes getSegmentNodes() {
		return new SegmentNodes(wayNodes.get(0), wayNodes.get(wayNodes.size() - 1));
	}

	@Override
	public ConnectableNode getEndpointNodes() {
		return endpointNodes;
	}

	@Override
	public Set<Node> getCommonNode(ConnectableSegment otherSegment) {
		Set<Node> commonNodes = getCommonNodesInternal(otherSegment);
		if (commonNodes.isEmpty())
			throw new AnalyzerException("No common nodes");

		return commonNodes;
	}

	@Override
	public boolean canConnect(ConnectableSegment otherSegment) {
		Set<Node> commonNodes = getCommonNodesInternal(otherSegment);
		return !commonNodes.isEmpty();
	}

	private Set<Node> getCommonNodesInternal(ConnectableSegment otherSegment) {
		Set<Node> commonNodes = new HashSet<Node>();
		for (Iterator<Node> it = otherSegment.getEndpointNodes().getNodesIterator(); it.hasNext();) {
			Node externalNode = it.next();
			if (wayNodes.contains(externalNode))
				commonNodes.add(externalNode);
		}
		return commonNodes;
	}

	@Override
	public void appendNodesBetween(Collection<Node> nodes, Node startNode, Node endNode) {

		int startNodeIndex = wayNodes.indexOf(startNode);
		int endNodeIndex = wayNodes.indexOf(endNode);

		if (startNodeIndex >= 0 && endNodeIndex >= 0) {

			if (startNodeIndex < endNodeIndex) {
				for (int x = startNodeIndex + 1; x < endNodeIndex + 1; x++) {
					nodes.add(wayNodes.get(x));
				}
			}
			else {
				appendNodesBackwards(nodes, startNodeIndex, endNodeIndex);
			}
		}
		else {
			throw new AnalyzerException("Cannot find given node in current way nodes");
		}
	}

	protected void appendNodesBackwards(Collection<Node> nodes, int startNodeIndex, int endNodeIndex) {
		for (int x = startNodeIndex - 1; x >= endNodeIndex; x--) {
			nodes.add(wayNodes.get(x));
		}
	}

	@Override
	public boolean containsNodes(Node... nodes) {
		for (Node node : nodes) {
			if (!wayNodes.contains(node))
				return false;
		}
		return true;
	}

	@Override
	public boolean canConnectNodesInDirection(Node startNode, Node endNode) {
		return containsNodes(startNode, endNode);
	}

	@Override
	public double getLength() {
		double sum = 0;
		Node startNode = wayNodes.get(0);
		for (int x = 1; x < wayNodes.size(); x++) {
			Node endNode = wayNodes.get(x);
			sum += LonLatMath.distance(startNode.getLon(), startNode.getLat(), endNode.getLon(), endNode.getLat());
			startNode = endNode;
		}

		return sum;
	}

}
