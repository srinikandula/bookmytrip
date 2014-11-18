package com.samples;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedList {
	private Node root;
	private int size;
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public void add(int value){
		Node add = new Node(value);
		if(this.root == null){
			this.root = add;
		}else{
			Node current = root;
			while(current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(add);
		}
		size++;
	}
	public void scan(){
		Node current = this.root;
		while(current != null){
			System.out.println(current.getValue());
			current = current.getNext();
		}
	}
	public void reverse(){
		Node current = this.root;
		Node prev = null,next;
		while(current != null){
			next = current.getNext();
			current.setNext(prev);
			prev = current;
			current = next;
		}
		this.root = prev;
	}
	public void delete(int value){
		Node current = root,prev = null;
		while(current != null){
			if(current.getValue() == value){
				if(current == root){
					this.root = current.getNext();
				}else{
					prev.setNext(current.getNext());	
				}
			}else{
				prev = current;
			}
			current = current.getNext();
		}
	}
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.scan();
		/*for(int i =0;i<10;i++){
			list.add(i);
		}*/
		
		list.add(1);
		list.add(2);
		list.add(2);
		list.add(2);
		list.add(2);
		list.add(3);
		
		list.scan();
		/*System.out.println("reversing..");
		list.reverse();		
		list.scan();
		System.out.println("Deleting 5");
		list.delete(5);
		System.out.println("Deleting 10");
*/	
		System.out.println("Deleting 2");
		
	//	list.scan();
		Set<String> s = new LinkedHashSet<String>();
		s.add("1");
		s.add("2");
		s.add("3");
		s.add("2");
		s.add("3");
		for(String sr:s){
			System.out.println(sr);
		}
		
	}
	
	
}

class Node{
	private int value;	
	public Node(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public Node getNext() {
		return next;
	}
	public void setNext(Node next) {
		this.next = next;
	}
	private Node next;
	
}