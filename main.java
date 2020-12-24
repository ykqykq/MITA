package utils;

import java.util.TreeMap;

import javax.print.attribute.standard.OrientationRequested;

public class main {

	static int num=100;//*****************车辆的仿真数量
	static int uavnum=16;//无人机的数量
	static int basenum=2;//基站的数量
	static int  totalnum=num+uavnum+basenum;//车辆无人机和基站的总数
	static TreeMap<Integer, task> maintasklist=new TreeMap<Integer, task>();//所有处理完的任务链表
	static double[][] vehiclestation=new double[500][2];
	static uav u=new uav(1,500,500,300,10);//初始化无人机和基站
	static basestation b=new basestation(0,-64,1000,70);
	static double maxtransspeed=1000;//单位Mb/s												
	static double maxe=145.5;//最大最小的能耗与时间
	static double mine=0.024;
	static double maxt=5.6;
	static double mint=0.01953125;
	static double ke=0.3;//能量的权重
	static double kt=0.7;//时间的权重
	
	public static void main(String[] args)throws Exception{
		
		//basestation bs=new basestation(-144.0,627.0,50);//定义基站无人机读取文件
		//uav ua=new uav(541.0,639.0,50.0,3);
		String data="任务ID 车辆ID 本地百分比 基站百分比 总能耗 总效用 开始时间 结束时间 无人机1百分比 无人机2百分比 无人机3百分比 无人机4百分比 无人机5百分比 无人机6百分比 无人机7百分比 无人机8百分比 无人机9百分比";
		printdata.method("D:\\kaiqidata.txt", data);
		readtraffic mobilitymodel=new readtraffic();   
		mobilitymodel.readcar("D:\\traffic.txt");//
		mobilitymodel.readuav("D:\\uav.txt");//
		//System.out.println(mobilitymodel.treeMapArray);

		new Thread(new clockthread(1)).start();
		new allthreadstate(main.totalnum);//  车辆线程是否结束的标志，true表示还在，false表示已经死亡
		//初始化无人机和基站的线程
		for(int i=0;i<1;i++){
			new Thread(new basestationthread(new basestation(i,0,20,70))).start();//基站id为0
		}
		for(int i=basenum;i<basenum+uavnum;i++){
			new Thread(new uavthread(new uav(i,500,500,0,10))).start();//无人机id为1
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
