package com.samples;

public class subclass extends Super {
	public subclass(int x,int y) {
		this(x);
		System.out.println("4"+x);
	}
	public subclass(int x) {
		System.out.println("3"+x);
	}
	public static void main(String[] args) {
		new subclass(2,3);
	}
	

}
class Super{
	public Super() {
		this(0);
		System.out.println(1);
	}
	Super(int x){
		System.out.println("2"+x);
	}
}