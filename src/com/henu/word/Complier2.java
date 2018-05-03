package com.henu.word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Complier2 {	

	private static String filename = "test.txt";

	@SuppressWarnings("unused")
	private static final String[] keywords = {"begin", "end", "if", "then", "while", "do", "const", "var", "call",
			"procedure", "odd"};
	
	//将 := 单列出来
	@SuppressWarnings("unused")
	private static final char[] operators = {'+', '-', '*', '/', '=', '#', '<', '>', '(', ')', ',', '.', ';'};

	public static void compiler() {
		//String file = readFromFile();
	}
	
	public static String readFromFile() {
		File source = new File(filename);
		if (!source.exists()) {
			System.out.println("文件不存在！！");
		}
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1024];
		if (source.canRead()) {
			try {
				FileInputStream fis = new FileInputStream(source);
				while (fis.read(buffer) != -1) {
					sb.append(new String(buffer));
				}
				fis.close();
				System.out.println(sb);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static String write2File() {
		File source = new File(filename);
		if (!source.exists()) {
			System.out.println("文件不存在！！");
		}
		StringBuffer sb = new StringBuffer();
		byte[] buffer = new byte[1024];
		if (source.canRead()) {
			try {
				FileInputStream fis = new FileInputStream(source);
				while (fis.read(buffer) != -1) {
					sb.append(new String(buffer));
				}
				fis.close();
				System.out.println(sb);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Complier2.compiler();
	}

}
