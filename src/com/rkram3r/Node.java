package com.rkram3r;

import java.util.concurrent.atomic.AtomicReference;

public class Node<T> {
	private final T item;
	private final AtomicReference<Node<T>> next;

	public Node(T item) {
		this.item = item;
		this.next = new AtomicReference<>();
	}

	public AtomicReference<Node<T>> getNext(){
		return next;
	}

	public T getItem(){
		return item;
	}
}