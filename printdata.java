package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class printdata {

	public static void main(String[] args) {
		

	}

	public static void method(String path,String printinfo) {
		FileWriter fw = null;
		try {
		//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
		File f=new File(path);
		fw = new FileWriter(f, true);
		} catch (IOException e) {
		e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(printinfo);
		pw.flush();
		try {
		fw.flush();
		pw.close();
		fw.close();
		} catch (IOException e) {
		e.printStackTrace();
		}
		}
}
