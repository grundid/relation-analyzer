package org.osmsurround.ra.analyzer;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.osmsurround.ra.data.Node;

public class SingleNodeSet implements Set<Node> {

	private Node node;

	public SingleNodeSet(Node node) {
		this.node = node;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return node.equals(o);
	}

	@Override
	public Iterator<Node> iterator() {

		return new Iterator<Node>() {

			private boolean hasNext = true;

			@Override
			public boolean hasNext() {
				return hasNext;
			}

			@Override
			public Node next() {
				hasNext = false;
				return node;
			}

			@Override
			public void remove() {
			}
		};
	}

	@Override
	public Object[] toArray() {
		return new Object[] { node };
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean add(Node e) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends Node> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}

}
