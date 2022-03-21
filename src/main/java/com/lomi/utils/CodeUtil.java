package com.lomi.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 推荐码 数字装换 工具
 * @author ZHANGYUKUNUP
 *
 */
public class CodeUtil {
	

	/**
	 * 数字 装换成推荐码
	 * @param num 数字
	 * @return
	 */
	public static String numToStr(Integer num) {
		String temp = num+"";
		String rt = "";
		for(int i = 0;i<temp.length();i++  ) {
			char at = temp.charAt(i);
			switch( at ) {
				case '0':
					rt += "A";
					break;
				case '1':
					rt += "B";
					break;
				case '2':
					rt += "C";
					break;
				case '3':
					rt += "D";
					break;
				case '4':
					rt += "E";
					break;
				case '5':
					rt += "F";
					break;
				case '6':
					rt += "G";
					break;
				case '7':
					rt += "H";
					break;
				case '8':
					rt += "I";
					break;
				case '9':
					rt += "J";
					break;
			}
			
		}
		return rt;
	}
	
	/**
	 * 推荐码装换成数字
	 * @param str
	 * @return
	 */
	public static Integer strToNum(String str) {
		String rt = "";
		for(int i = 0;i<str.length();i++  ) {
			char at = str.charAt(i);
			switch( at ) {
				case 'A':
					rt += "0";
					break;
				case 'B':
					rt += "1";
					break;
				case 'C':
					rt += "2";
					break;
				case 'D':
					rt += "3";
					break;
				case 'E':
					rt += "4";
				break;
				case 'F':
					rt += "5";
					break;
				case 'G':
					rt += "6";
					break;
				case 'H':
					rt += "7";
					break;
				case 'I':
					rt += "8";
					break;
				case 'J':
					rt += "9";
					break;
			}
			
			
		}
		
		return Integer.valueOf( rt );
	}
	
	
	
	static final List<String> codeBase;
	static {
		codeBase = new ArrayList<>();
		codeBase.add("A");
		codeBase.add("B");
		codeBase.add("C");
		codeBase.add("D");
		codeBase.add("E");
		codeBase.add("F");
		codeBase.add("G");
		codeBase.add("H");
		codeBase.add("I");
		codeBase.add("J");
		codeBase.add("L");
		codeBase.add("M");
		codeBase.add("N");
		codeBase.add("O");
		codeBase.add("P");
		codeBase.add("Q");
		codeBase.add("R");
		codeBase.add("S");
		codeBase.add("T");
		codeBase.add("U");
		codeBase.add("V");
		codeBase.add("W");
		codeBase.add("X");
		codeBase.add("Y");
		codeBase.add("Z");
		
		codeBase.add("a");
		codeBase.add("b");
		codeBase.add("c");
		codeBase.add("d");
		codeBase.add("e");
		codeBase.add("f");
		codeBase.add("g");
		codeBase.add("H");
		codeBase.add("i");
		codeBase.add("j");
		codeBase.add("l");
		codeBase.add("m");
		codeBase.add("n");
		codeBase.add("o");
		codeBase.add("p");
		codeBase.add("q");
		codeBase.add("r");
		codeBase.add("s");
		codeBase.add("t");
		codeBase.add("u");
		codeBase.add("v");
		codeBase.add("W");
		codeBase.add("x");
		codeBase.add("y");
		codeBase.add("z");
		
		codeBase.add("1");
		codeBase.add("2");
		codeBase.add("3");
		codeBase.add("4");
		codeBase.add("5");
		codeBase.add("6");
		codeBase.add("7");
		codeBase.add("8");
		codeBase.add("9");
		codeBase.add("0");
	}
	
	static final List<String> numBase;
	static {
		numBase = new ArrayList<>();
		numBase.add("1");
		numBase.add("2");
		numBase.add("3");
		numBase.add("4");
		numBase.add("5");
		numBase.add("6");
		numBase.add("7");
		numBase.add("8");
		numBase.add("9");
		numBase.add("0");
	}
	
	
	/**
	 * 获取一个随机数
	 * @return
	 */
	public static String getRandomNum18() {
		Random random = new Random();
		int num = random.nextInt(89999999)+10000000;
		return (System.currentTimeMillis()/1000)+""+num;
	}
	
	
		
	
	/**
	 * 获取一个随机的推荐码
	 * @param n 推荐码长度
	 * @return
	 */
	public static String randomCode(int n) {
		int size = codeBase.size();
		StringBuilder  code = new StringBuilder();
		
		for(int i = 0 ;i<n;i++  ) {
			int index = (int) (Math.random()*size);
			code.append( codeBase.get(index) );
		}
		
		return code.toString();
	}
	
	
	/**
	 * 获取一个随机的数字推荐码
	 * @param n 长度
	 * @return
	 */
	public static String randomNumCode(int n) {
		int size = numBase.size();
		StringBuilder  code = new StringBuilder();
		
		for(int i = 0 ;i<n;i++  ) {
			int index = (int) (Math.random()*size);
			code.append( numBase.get(index) );
		}
		
		return code.toString();
	}
	

	
}	
