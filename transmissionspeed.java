package utils;

import java.text.DecimalFormat;
import java.util.TreeMap;

import utils.basestation;
import utils.vehicle;


public class transmissionspeed {

	
	static double w=20;//带宽单位MHz
	static double pn=100;//传输功率单位mWatts兆瓦特
	static double n0=Math.pow(10, -10);//背景噪声单位dBm
	public static double gettransmissionspeed(int vid,int type,int uavid){//type :0代表车辆与基站传信息，1代表车辆与无人机传信息
		double transspeed=0;//传输速度
		double distance=0;//距离
		double chann=0;//信道增益
		double totalchannel=0;
		DecimalFormat df=new DecimalFormat("#0.000000");
		DecimalFormat df1=new DecimalFormat("#0.00000000000");
		if(type==0){//车辆与基站通信
		   distance=channelgain.getdistance(main.vehiclestation[vid][0], main.vehiclestation[vid][1], 0, basestation.x, basestation.y, basestation.z);
		   chann=channelgain.getchannelgain(distance);
		   //System.out.println("基站与车辆的距离"+distance);
		   //System.out.println("基站与车辆的chann："+df1.format(chann));
		   totalchannel=transmissionspeed.allchannel(type,uavid);
		   transspeed=w*(Math.log(1+((pn*chann)/(n0+pn*totalchannel)))/Math.log(2));
		  
		   System.out.println("车辆"+vid+"的基站的传输速度："+transspeed+"车辆位置：x:"+main.vehiclestation[vid][0]+" y:"+main.vehiclestation[vid][1]+" 基站x："+basestation.x+" 基站y:"+basestation.y+" 时间："+clock.clocktime+"  totalchannel:"+totalchannel);
		   
		   if(transspeed>main.maxtransspeed){
				transspeed=main.maxtransspeed;
			}
		   //System.out.println("基站：chann:"+df.format(chann)+"   totalchannel:"+df1.format(totalchannel)+"   transspeed:"+df.format(transspeed));
		}
		else if(type==1){//无人机与车辆
			distance=channelgain.getdistance(main.vehiclestation[vid][0], main.vehiclestation[vid][1], 0, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y, 300);
			//System.out.println("无人机与车辆的距离"+distance);
			chann=channelgain.getchannelgain(distance);
			//System.out.println("无人机与车辆的chann："+df1.format(chann));
			totalchannel=transmissionspeed.allchannel(type,uavid);
			transspeed=w*(Math.log(1+((pn*chann)/(n0+pn*totalchannel)))/Math.log(2));
			 System.out.println("车辆"+vid+"的无人机"+uavid+"的传输速度："+transspeed+"车辆位置：x:"+main.vehiclestation[vid][0]+" y:"+main.vehiclestation[vid][1]+" 无人机x："+readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x+" 无人机y:"+readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y+" 时间："+clock.clocktime+"  totalchannel:"+totalchannel);

			if(transspeed>main.maxtransspeed){
				transspeed=main.maxtransspeed;
			}
			//System.out.println("无人机：chann:"+df.format(chann)+"   totalchannel:"+df1.format(totalchannel)+"   transspeed:"+df.format(transspeed));
		}
		else{
			System.out.println("发生了错误");
		}
		
		return(transspeed);
	}
	public static double allchannel(int type,int uavid){//计算无人机或者基站的其他车辆干扰0：基站     1：无人机
		double totalchannel=0;
		int v;
		if(type==0){//基站的干扰
			//System.out.println("进来了");
			for(Integer taid:basestationthread.tasklist.keySet()){
				v=basestationthread.tasklist.get(taid).origin;
				double distance=channelgain.getdistance(main.vehiclestation[v][0], main.vehiclestation[v][1], 0, basestation.x, basestation.y, basestation.z);           
				//System.out.println("其他车辆的距离"+distance);
				double ch=channelgain.getchannelgain(distance);
				totalchannel=totalchannel+ch;
			}
		}
		else if(type==1){
			//System.out.println("进来了");
			
			for(Integer taid:uavthread.tasktreeMapArray.get(uavid).keySet()){
				
				v=uavthread.tasktreeMapArray.get(uavid).get(taid).origin;
				double distance=channelgain.getdistance(main.vehiclestation[v][0], main.vehiclestation[v][1], 0, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y, 0);
				//System.out.println("其他车辆的距离"+distance);
				double ch=channelgain.getchannelgain(distance);
				totalchannel=totalchannel+ch;
			}
		}
		else{
			System.out.println("产生错误");
		}
		return(totalchannel);
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	public double getPn() {
		return pn;
	}
	public void setPv(double pn) {
		this.pn = pn;
	}
	public double getN0() {
		return n0;
	}
	public void setN0(double n0) {
		this.n0 = n0;
	}
	
	
}
