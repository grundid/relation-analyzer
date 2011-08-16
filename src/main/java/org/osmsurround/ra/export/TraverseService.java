package org.osmsurround.ra.export;

import java.util.List;

import org.osmsurround.ra.analyzer.IntersectionNode;
import org.osmsurround.ra.data.Node;
import org.springframework.stereotype.Service;

@Service
public class TraverseService {

	public List<Node> traverse(IntersectionNode startNode, int allNodes) {
		Traverser traverser = new Traverser(startNode, allNodes);
		return traverser.getNodes();
	}
}
