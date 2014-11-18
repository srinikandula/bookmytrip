package com.tutorialspoint;

import java.util.Scanner;

import org.junit.Assert;

public class PigLatin {
	char vowels[] = new char[]{'a', 'e', 'i', 'o', 'u', 'y'};
	
	/*
	 * Stop   Opstay
	 * 
	 * Opstay
	 * 
	 * --> No littering
	 * 
	 * Onay itteringlay
	 * 
	 * --> No shirts, no shoes, no service
	 * 
	 * Onay irtsshay, onay eosshay, onay ervicesay
	 * 
	 * -->No persons under 14 admitted
	 * 
	 * Onay ersonspay .underay 14 admitteday
	 * 
	 * -->Hey buddy, get away from my car!
	 * 
	 * Eyhay uddybah, etgay awayay omfray ymay arcay!
	 */
	
	public String convertString(String input){
		String words[] = input.split(" ");
		StringBuilder result = new StringBuilder();
		for(String word:words){
			result.append(convertWord(word)+" ");
		}
		return result.toString().trim();
	}
	public String convertWord(String input){
		String prefix,stem;
		char suffix = '\u0000';
		
		for(int i=0;i<input.length();i++){
			if(isVowel(input.charAt(i))){
				prefix = input.substring(0,i);
				stem = input.substring(i);
				if(stem.length() > 0){
					if(prefix.length() > 0 && Character.isUpperCase(prefix.charAt(0))){
						prefix = prefix.toLowerCase();
						stem = Character.toUpperCase(stem.charAt(0))+stem.substring(1);
					}
					if(stem.endsWith(".")||stem.endsWith(",")||stem.endsWith("!")){
						suffix = stem.charAt(stem.length()-1);
						stem = stem.substring(0,stem.length()-1);
						return new StringBuilder().append(stem).append(prefix).append("ay").append(suffix).toString();
					}					
				}
				return new StringBuilder().append(stem).append(prefix).append("ay").toString();
			}
		}
		try{
			Integer.parseInt(input);
			return input;
		}catch(NumberFormatException e){
			return input+"yay";
		}
		
	}
	
	
	private boolean isVowel(char c){
		switch(c){
			case 'a':
			case 'e':
			case 'i':
			case 'o':
			case 'u':
			case 'y':
				return true;	
		}
		return false;
	}
	
	public static void main(String[] args) {
		PigLatin latin = new PigLatin();
		Assert.assertEquals(latin.convertWord("I"), "Iyay");
		Assert.assertEquals("ellohay",latin.convertWord("hello"));
		Assert.assertEquals(latin.convertWord("Stop"), "Opstay");
		Assert.assertEquals(latin.convertWord("No"), "Onay");
		Assert.assertEquals(latin.convertWord("littering"), "itteringlay");
		Assert.assertEquals("arcay!",latin.convertWord("car!"));
		Assert.assertEquals("Onay itteringlay",latin.convertString("No littering")); 
		Assert.assertEquals("Onay irtsshay, onay oesshay, onay ervicesay",latin.convertString("No shirts, no shoes, no service")); 
		Assert.assertEquals("Onay ersonspay underay 14 admitteday",latin.convertString("No persons under 14 admitted"));
		Assert.assertEquals("Eyhay uddybay, etgay awayay omfray ymay arcay!",latin.convertString("Hey buddy, get away from my car!"));
		System.out.println("Tests passed");
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		while(!input.equals("exit")){
			System.out.println(latin.convertString(input));
			input = scanner.nextLine();
		}
		scanner.close();
	}
	
}
