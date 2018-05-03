package com.henu.word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compiler3 {
	// private static String filename = "test.txt";

	public static String buffer = null;// 存储读取出来的字符串
	public static int typenum = 0;

	public static String[] type = // 0表示无种类，1代表关键字，2代表算术符号，3代表关系运算符，4代表分界符，5代表数字，6代表标识符
			{ "无", "关键字", "算术符号", "关系运算符", "分界符", "数字", "标识符" };

	public static String[] reservedword = // 保留字
			{ "if", "begin", "end", "while", "then", "do", "else", "var" };

	public static String[] operateword = // 算术符号
			{ "+", "-", "*", "/" };

	public static String[] relationword = // 关系符号
			{ ">", "<", "<=", ">=", "<>", "=", ":" };

	public static String[] limiterword = // 分界符
			{ ":=", ",", ".", ";", "(", ")" };

	public void read() {// 读取文件
		String path = "test.txt";// 文件存储路径
		try// 异常处理
		{
			FileReader filepath = new FileReader(path);

			BufferedReader bufferedreader = new BufferedReader(filepath);
			String Str;
			while ((Str = bufferedreader.readLine()) != null)// 按行读取
			{
				if (buffer == null)// 将读取出来的信息存入buffer中，这里因为buffer原本为空，如果都用buffer += Str;则buffer中第一位为null
				{
					buffer = Str;
				} else {
					buffer += Str;
				}
				buffer += "\n";
			}
			bufferedreader.close();
		} catch (IOException e)// 异常处理
		{
			e.printStackTrace();
		}

	}

	public int isreservedword(String s) {// 判断是否为保留字,是，则返回种别码;不是，则返回0.
		String aa="asdasd";
		int flag = -1;
		for (int j = 0; j < reservedword.length; j++) {
			if (reservedword[j].equals(s)) {
				flag = j + 1;
				break;
			}
		}
		return flag;
	}

	public int isoperateword(String s)// 判断是否为算术符号,是，则返回种别码;不是，则返回0.
	{
		int flag = 0;
		for (int j = 0; j < operateword.length; j++) {
			if (operateword[j].equals(s)) {
				flag = 9 + j;
				break;
			}
		}
		return flag;
	}

	public int isrelationword(String s)// 判断是否为关系符号,是，则返回种别码;不是，则返回0.
	{
		int flag = 0;
		for (int j = 0; j < relationword.length; j++) {
			if (relationword[j].equals(s)) {
				flag = 13 + j;
				break;
			}
		}
		return flag;
	}

	public int islimiterword(String s)// 判断是否为分界符,是，则返回种别码;不是，则返回0.
	{
		int flag = 0;
		for (int j = 0; j < limiterword.length; j++) {
			if (limiterword[j].equals(s)) {
				flag = 19 + j;
				break;
			}
		}
		return flag;
	}

	public int analysis(int p, String s) {// 对字符串进行分析(排除了界符，关系符，算术符号)
		int sign = 0;
		boolean flag = false;// 标志变量，用来判断是否为数字
		if (isreservedword(s) >= 0)// 是否为保留字
		{
			sign = isreservedword(s);
			typenum = 1;
		} else {
			for (int k = 0; k < s.length(); k++)// 是否为数字串
			{
				if (s.substring(k, k + 1).compareTo("0") >= 0 && s.substring(k, k + 1).compareTo("9") <= 0) {
					flag = true;// 如果一直是数字，则为真
				} else {
					flag = false;
					break;// 当有一个不位数字，则为假
				}
			}
			if (flag) {
				sign = 27;
				typenum = 5;
			} else// 标识符
			{
				sign = 26;
				typenum = 6;
			}
		}
		return sign;
	}

	public void Test() {// 词法分析主程序
		read();// 读取文件
		String Str1 = null;
		int p = 0, typenum1 = 0;
		int sign = 0, num = 0;
		boolean flag1 = true, flag2 = false;
		do {
			flag2 = false;// 标识关系两个字节的关系运算符
			Str1 = null;// 存储提取的一段字符串
			num = 0;// 存储种别码
			while (!buffer.substring(p, p + 1).equals(" ") && !buffer.substring(p, p + 1).equals("\n"))
			// 读取buffer中的字符，直到遇到换行符或者空格
			{
				if (islimiterword(buffer.substring(p, p + 1)) > 0)// 遇到分界符
				{
					typenum1 = 4;
					num = islimiterword(buffer.substring(p, p + 1));
					break;
				}
				if (isoperateword(buffer.substring(p, p + 1)) > 0)// 遇到算术符号
				{
					typenum1 = 2;
					num = isoperateword(buffer.substring(p, p + 1));
					break;
				}
				if (isrelationword(buffer.substring(p, p + 1)) > 0)// 遇到关系运算符
				{
					if (buffer.substring(p, p + 1).equals(":")) {
						if (buffer.substring(p + 1, p + 2).equals("="))// 遇到:=分界符
						{
							typenum1 = 4;
							num = 25;
							break;
						}
					}
					typenum1 = 3;
					if (isrelationword(buffer.substring(p, p + 2)) > 0)// 遇到两个字节的关系运算符
					{
						flag2 = true;
						num = isrelationword(buffer.substring(p, p + 2));
					} else// 一个字节的关系运算符
					{
						num = isrelationword(buffer.substring(p, p + 1));
					}
					break;
				}
				if (buffer.substring(p, p + 1).equals("#"))// 遇到结束符号
				{
					flag1 = false;// flag1表示着是否遇到结束符号
					break;
				}
				if (Str1 == null)// 这是为了区别当Str1为null时，使用+=会造成字符串中多处一个null
				{
					Str1 = buffer.substring(p, p + 1);
				} else {
					Str1 += buffer.substring(p, p + 1);
				}
				p++;

			}

			if (Str1 != null)// 当提取的字符串不为空时
			{
				sign = analysis(p, Str1);// 分析字符串的种别码
				System.out.println("<" + sign + "," + Str1 + "," + type[typenum] + ">");
			}
			if (num > 0)// 当为特殊符号时(分界符，算术符号，关系运算符)
			{
				if (num == 25) {
					System.out.println("<25,:=,分界符>");
					p = p + 2;
					continue;
				}
				if (flag2)// 两个字节的关系运算符
				{
					System.out.println("<" + num + "," + buffer.substring(p, p + 2) + "," + type[typenum1] + ">");
					p++;
				} else {
					System.out.println("<" + num + "," + buffer.substring(p, p + 1) + "," + type[typenum1] + ">");
				}
			}
			p++;
		} while (flag1);
		System.out.println("<0,#>");
	}

	public static void main(String[] args) {
		new Compiler3().Test();
	}
}
