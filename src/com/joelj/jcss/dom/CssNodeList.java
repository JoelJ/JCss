package com.joelj.jcss.dom;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * User: joeljohnson
 * Date: 5/17/12
 * Time: 11:02 PM
 */
class CssNodeList implements List<CssNode> {
	private final List<CssNode> nodeList;

	CssNodeList(NodeList nodes) {
		List<CssNode> nodeList = new ArrayList<CssNode>(nodes.getLength());
		if(nodes != null) {
			for(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				nodeList.add(new CssNodeImpl(node));
			}
		}
		this.nodeList = Collections.unmodifiableList(nodeList);
	}

	@Override
	public int size() {
		return nodeList.size();
	}

	@Override
	public boolean isEmpty() {
		return nodeList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return nodeList.contains(o);
	}

	@Override
	public Iterator<CssNode> iterator() {
		return nodeList.iterator();
	}

	@Override
	public Object[] toArray() {
		return nodeList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return nodeList.toArray(a);
	}

	@Override
	public boolean add(CssNode cssNode) {
		return nodeList.add(cssNode);
	}

	@Override
	public boolean remove(Object o) {
		return nodeList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return nodeList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends CssNode> c) {
		return nodeList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends CssNode> c) {
		return nodeList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return nodeList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return nodeList.retainAll(c);
	}

	@Override
	public void clear() {
		nodeList.clear();
	}

	@Override
	public boolean equals(Object o) {
		return nodeList.equals(o);
	}

	@Override
	public int hashCode() {
		return nodeList.hashCode();
	}

	@Override
	public CssNode get(int index) {
		return nodeList.get(index);
	}

	@Override
	public CssNode set(int index, CssNode element) {
		return nodeList.set(index, element);
	}

	@Override
	public void add(int index, CssNode element) {
		nodeList.add(index, element);
	}

	@Override
	public CssNode remove(int index) {
		return nodeList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return nodeList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return nodeList.lastIndexOf(o);
	}

	@Override
	public ListIterator<CssNode> listIterator() {
		return nodeList.listIterator();
	}

	@Override
	public ListIterator<CssNode> listIterator(int index) {
		return nodeList.listIterator(index);
	}

	@Override
	public List<CssNode> subList(int fromIndex, int toIndex) {
		return nodeList.subList(fromIndex, toIndex);
	}
}
