package com.henu.word;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Compiler3 {
	// private static String filename = "test.txt";

	public static String buffer = null;// �洢��ȡ�������ַ���
	public static int typenum = 0;

	public static String[] type = // 0��ʾ�����࣬1����ؼ��֣�2�����������ţ�3�����ϵ�������4����ֽ����5�������֣�6�����ʶ��
			{ "��", "�ؼ���", "��������", "��ϵ�����", "�ֽ��", "����", "��ʶ��" };

	public static String[] reservedword = // ������
			{ "if", "begin", "end", "while", "then", "do", "else", "var" };

	public static String[] operateword = // ��������
			{ "+", "-", "*", "/" };

	public static String[] relationword = // ��ϵ����
			{ ">", "<", "<=", ">=", "<>", "=", ":" };

	public static String[] limiterword = // �ֽ��
			{ ":=", ",", ".", ";", "(", ")" };

	public void read() {// ��ȡ�ļ�
		String path = "test.txt";// �ļ��洢·��
		try// �쳣����
		{
			FileReader filepath = new FileReader(path);

			BufferedReader bufferedreader = new BufferedReader(filepath);
			String Str;
			while ((Str = bufferedreader.readLine()) != null)// ���ж�ȡ
			{
				if (buffer == null)// ����ȡ��������Ϣ����buffer�У�������Ϊbufferԭ��Ϊ�գ��������buffer += Str;��buffer�е�һλΪnull
				{
					buffer = Str;
				} else {
					buffer += Str;
				}
				buffer += "\n";
			}
			bufferedreader.close();
		} catch (IOException e)// �쳣����
		{
			e.printStackTrace();
		}

	}

	public int isreservedword(String s) {// �ж��Ƿ�Ϊ������,�ǣ��򷵻��ֱ���;���ǣ��򷵻�0.
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

	public int isoperateword(String s)// �ж��Ƿ�Ϊ��������,�ǣ��򷵻��ֱ���;���ǣ��򷵻�0.
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

	public int isrelationword(String s)// �ж��Ƿ�Ϊ��ϵ����,�ǣ��򷵻��ֱ���;���ǣ��򷵻�0.
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

	public int islimiterword(String s)// �ж��Ƿ�Ϊ�ֽ��,�ǣ��򷵻��ֱ���;���ǣ��򷵻�0.
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

	public int analysis(int p, String s) {// ���ַ������з���(�ų��˽������ϵ������������)
		int sign = 0;
		boolean flag = false;// ��־�����������ж��Ƿ�Ϊ����
		if (isreservedword(s) >= 0)// �Ƿ�Ϊ������
		{
			sign = isreservedword(s);
			typenum = 1;
		} else {
			for (int k = 0; k < s.length(); k++)// �Ƿ�Ϊ���ִ�
			{
				if (s.substring(k, k + 1).compareTo("0") >= 0 && s.substring(k, k + 1).compareTo("9") <= 0) {
					flag = true;// ���һֱ�����֣���Ϊ��
				} else {
					flag = false;
					break;// ����һ����λ���֣���Ϊ��
				}
			}
			if (flag) {
				sign = 27;
				typenum = 5;
			} else// ��ʶ��
			{
				sign = 26;
				typenum = 6;
			}
		}
		return sign;
	}

	public void Test() {// �ʷ�����������
		read();// ��ȡ�ļ�
		String Str1 = null;
		int p = 0, typenum1 = 0;
		int sign = 0, num = 0;
		boolean flag1 = true, flag2 = false;
		do {
			flag2 = false;// ��ʶ��ϵ�����ֽڵĹ�ϵ�����
			Str1 = null;// �洢��ȡ��һ���ַ���
			num = 0;// �洢�ֱ���
			while (!buffer.substring(p, p + 1).equals(" ") && !buffer.substring(p, p + 1).equals("\n"))
			// ��ȡbuffer�е��ַ���ֱ���������з����߿ո�
			{
				if (islimiterword(buffer.substring(p, p + 1)) > 0)// �����ֽ��
				{
					typenum1 = 4;
					num = islimiterword(buffer.substring(p, p + 1));
					break;
				}
				if (isoperateword(buffer.substring(p, p + 1)) > 0)// ������������
				{
					typenum1 = 2;
					num = isoperateword(buffer.substring(p, p + 1));
					break;
				}
				if (isrelationword(buffer.substring(p, p + 1)) > 0)// ������ϵ�����
				{
					if (buffer.substring(p, p + 1).equals(":")) {
						if (buffer.substring(p + 1, p + 2).equals("="))// ����:=�ֽ��
						{
							typenum1 = 4;
							num = 25;
							break;
						}
					}
					typenum1 = 3;
					if (isrelationword(buffer.substring(p, p + 2)) > 0)// ���������ֽڵĹ�ϵ�����
					{
						flag2 = true;
						num = isrelationword(buffer.substring(p, p + 2));
					} else// һ���ֽڵĹ�ϵ�����
					{
						num = isrelationword(buffer.substring(p, p + 1));
					}
					break;
				}
				if (buffer.substring(p, p + 1).equals("#"))// ������������
				{
					flag1 = false;// flag1��ʾ���Ƿ�������������
					break;
				}
				if (Str1 == null)// ����Ϊ������Str1Ϊnullʱ��ʹ��+=������ַ����жദһ��null
				{
					Str1 = buffer.substring(p, p + 1);
				} else {
					Str1 += buffer.substring(p, p + 1);
				}
				p++;

			}

			if (Str1 != null)// ����ȡ���ַ�����Ϊ��ʱ
			{
				sign = analysis(p, Str1);// �����ַ������ֱ���
				System.out.println("<" + sign + "," + Str1 + "," + type[typenum] + ">");
			}
			if (num > 0)// ��Ϊ�������ʱ(�ֽ�����������ţ���ϵ�����)
			{
				if (num == 25) {
					System.out.println("<25,:=,�ֽ��>");
					p = p + 2;
					continue;
				}
				if (flag2)// �����ֽڵĹ�ϵ�����
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
