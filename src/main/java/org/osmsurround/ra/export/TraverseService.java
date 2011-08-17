package org.osmsurround.ra.export;

import java.util.List;

import org.osmsurround.ra.analyzer.IntersectionNode;
import org.osmsurround.ra.data.Node;
import org.springframework.stereotype.Service;

@Service
public class TraverseService {

	public List<Node> traverse(IntersectionNode startNode, IntersectionNode endNode) {
		SingleRouteTraverser traverser = new SingleRouteTraverser(startNode, endNode);
		return traverser.getNodes();
	}
}
