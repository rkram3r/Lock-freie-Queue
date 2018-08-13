package com.rkram3r;

import java.util.concurrent.atomic.AtomicReference;

public class LockFreeQueue<T> {
    private AtomicReference<Node<T>> head = new AtomicReference<>(new Node<>( null));
    private AtomicReference<Node<T>> tail = new AtomicReference<>(head.get());

    public void enqueue(T item) {
        Node<T> newTail = new Node<>(item);
        while (true) {
            Node<T> curTail = tail.get();
            Node<T> next =  curTail.getNext().get();

            if (curTail == tail.get()) {
                if (next == null) {
                    if (curTail.getNext().compareAndSet(null, newTail)) {
                        tail.compareAndSet(curTail, newTail);
                        return;
                    }
                } else {
                    tail.compareAndSet(curTail, next);
                }
            }
        }
    }

    public T dequeue() {
        while (true) {
            Node<T> oldHead = head.get();
            Node<T> oldTail = tail.get();
            Node<T> oldHeadNext =  oldHead == null ? oldTail : oldHead.getNext().get();

            if (oldHead == head.get()) {
                if (oldHead == oldTail) {
                    if (oldHeadNext == null) {
                        return null;
                    }
                    tail.compareAndSet(oldTail, oldHeadNext);
                } else {
                    if (head.compareAndSet(oldHead, oldHeadNext)) {
                        return oldHeadNext.getItem();
                    }
                }
            }
        }
    }
}