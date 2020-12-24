package utils;

import java.util.TreeMap;

import javax.print.attribute.standard.OrientationRequested;

public class main {

	static int num=100;//*****************�����ķ�������
	static int uavnum=16;//���˻�������
	static int basenum=2;//��վ������
	static int  totalnum=num+uavnum+basenum;//�������˻��ͻ�վ������
	static TreeMap<Integer, task> maintasklist=new TreeMap<Integer, task>();//���д��������������
	static double[][] vehiclestation=new double[500][2];
	static uav u=new uav(1,500,500,300,10);//��ʼ�����˻��ͻ�վ
	static basestation b=new basestation(0,-64,1000,70);
	static double maxtransspeed=1000;//��λMb/s												
	static double maxe=145.5;//�����С���ܺ���ʱ��
	static double mine=0.024;
	static double maxt=5.6;
	static double mint=0.01953125;
	static double ke=0.3;//������Ȩ��
	static double kt=0.7;//ʱ���Ȩ��
	
	public static void main(String[] args)throws Exception{
		
		//basestation bs=new basestation(-144.0,627.0,50);//�����վ���˻���ȡ�ļ�
		//uav ua=new uav(541.0,639.0,50.0,3);
		String data="����ID ����ID ���ذٷֱ� ��վ�ٷֱ� ���ܺ� ��Ч�� ��ʼʱ�� ����ʱ�� ���˻�1�ٷֱ� ���˻�2�ٷֱ� ���˻�3�ٷֱ� ���˻�4�ٷֱ� ���˻�5�ٷֱ� ���˻�6�ٷֱ� ���˻�7�ٷֱ� ���˻�8�ٷֱ� ���˻�9�ٷֱ�";
		printdata.method("D:\\kaiqidata.txt", data);
		readtraffic mobilitymodel=new readtraffic();   
		mobilitymodel.readcar("D:\\traffic.txt");//
		mobilitymodel.readuav("D:\\uav.txt");//
		//System.out.println(mobilitymodel.treeMapArray);

		new Thread(new clockthread(1)).start();
		new allthreadstate(main.totalnum);//  �����߳��Ƿ�����ı�־��true��ʾ���ڣ�false��ʾ�Ѿ�����
		//��ʼ�����˻��ͻ�վ���߳�
		for(int i=0;i<1;i++){
			new Thread(new basestationthread(new basestation(i,0,20,70))).start();//��վidΪ0
		}
		for(int i=basenum;i<basenum+uavnum;i++){
			new Thread(new uavthread(new uav(i,500,500,0,10))).start();//���˻�idΪ1
			uav.uavlist.put(i, new uav(i,500,500,0,10));
		}
		for(int i=basenum+uavnum;i<main.totalnum;i++){
			new Thread(new vehiclethread(i,new vehicle(i,2,2,0.7))).start();
		}
		//new Thread(new vehiclethread(2,new vehicle(2,2,2,0.8))).start();
		//new Thread(new vehiclethread(3,new vehicle(3,2,2,0.5))).start();
		//new Thread(new vehiclethread(4,new vehicle(4,2,2,0.5))).start();
		
		
	}
	public static String six(double a){
		String s=String.format("%.6f", a);
		return(s);
	}
}
