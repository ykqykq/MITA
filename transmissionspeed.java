package utils;

import java.text.DecimalFormat;
import java.util.TreeMap;

import utils.basestation;
import utils.vehicle;


public class transmissionspeed {

	
	static double w=20;//����λMHz
	static double pn=100;//���书�ʵ�λmWatts������
	static double n0=Math.pow(10, -10);//����������λdBm
	public static double gettransmissionspeed(int vid,int type,int uavid){//type :0���������վ����Ϣ��1�����������˻�����Ϣ
		double transspeed=0;//�����ٶ�
		double distance=0;//����
		double chann=0;//�ŵ�����
		double totalchannel=0;
		DecimalFormat df=new DecimalFormat("#0.000000");
		DecimalFormat df1=new DecimalFormat("#0.00000000000");
		if(type==0){//�������վͨ��
		   distance=channelgain.getdistance(main.vehiclestation[vid][0], main.vehiclestation[vid][1], 0, basestation.x, basestation.y, basestation.z);
		   chann=channelgain.getchannelgain(distance);
		   //System.out.println("��վ�복���ľ���"+distance);
		   //System.out.println("��վ�복����chann��"+df1.format(chann));
		   totalchannel=transmissionspeed.allchannel(type,uavid);
		   transspeed=w*(Math.log(1+((pn*chann)/(n0+pn*totalchannel)))/Math.log(2));
		  
		   System.out.println("����"+vid+"�Ļ�վ�Ĵ����ٶȣ�"+transspeed+"����λ�ã�x:"+main.vehiclestation[vid][0]+" y:"+main.vehiclestation[vid][1]+" ��վx��"+basestation.x+" ��վy:"+basestation.y+" ʱ�䣺"+clock.clocktime+"  totalchannel:"+totalchannel);
		   
		   if(transspeed>main.maxtransspeed){
				transspeed=main.maxtransspeed;
			}
		   //System.out.println("��վ��chann:"+df.format(chann)+"   totalchannel:"+df1.format(totalchannel)+"   transspeed:"+df.format(transspeed));
		}
		else if(type==1){//���˻��복��
			distance=channelgain.getdistance(main.vehiclestation[vid][0], main.vehiclestation[vid][1], 0, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y, 300);
			//System.out.println("���˻��복���ľ���"+distance);
			chann=channelgain.getchannelgain(distance);
			//System.out.println("���˻��복����chann��"+df1.format(chann));
			totalchannel=transmissionspeed.allchannel(type,uavid);
			transspeed=w*(Math.log(1+((pn*chann)/(n0+pn*totalchannel)))/Math.log(2));
			 System.out.println("����"+vid+"�����˻�"+uavid+"�Ĵ����ٶȣ�"+transspeed+"����λ�ã�x:"+main.vehiclestation[vid][0]+" y:"+main.vehiclestation[vid][1]+" ���˻�x��"+readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x+" ���˻�y:"+readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y+" ʱ�䣺"+clock.clocktime+"  totalchannel:"+totalchannel);

			if(transspeed>main.maxtransspeed){
				transspeed=main.maxtransspeed;
			}
			//System.out.println("���˻���chann:"+df.format(chann)+"   totalchannel:"+df1.format(totalchannel)+"   transspeed:"+df.format(transspeed));
		}
		else{
			System.out.println("�����˴���");
		}
		
		return(transspeed);
	}
	public static double allchannel(int type,int uavid){//�������˻����߻�վ��������������0����վ     1�����˻�
		double totalchannel=0;
		int v;
		if(type==0){//��վ�ĸ���
			//System.out.println("������");
			for(Integer taid:basestationthread.tasklist.keySet()){
				v=basestationthread.tasklist.get(taid).origin;
				double distance=channelgain.getdistance(main.vehiclestation[v][0], main.vehiclestation[v][1], 0, basestation.x, basestation.y, basestation.z);           
				//System.out.println("���������ľ���"+distance);
				double ch=channelgain.getchannelgain(distance);
				totalchannel=totalchannel+ch;
			}
		}
		else if(type==1){
			//System.out.println("������");
			
			for(Integer taid:uavthread.tasktreeMapArray.get(uavid).keySet()){
				
				v=uavthread.tasktreeMapArray.get(uavid).get(taid).origin;
				double distance=channelgain.getdistance(main.vehiclestation[v][0], main.vehiclestation[v][1], 0, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(uavid).get((int)clock.clocktime/1000+1).y, 0);
				//System.out.println("���������ľ���"+distance);
				double ch=channelgain.getchannelgain(distance);
				totalchannel=totalchannel+ch;
			}
		}
		else{
			System.out.println("��������");
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
