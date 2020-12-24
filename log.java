package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.TreeMap;

public class log implements Runnable{
    public static boolean ifprint=false;
	public static long sendpacket=0;//发送的总包数
	public static long receivepacket=0;//接受的总包数
	static TreeMap<Double, task> counttasklist=new TreeMap<Double, task>();//
	public static void printdata(String path){

		//  这是日志文件的目录***************
			System.out.println("打印训练数据********************************");
			//String path = "D:\\loooooooog.txt";
			File file = new File(path);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
				
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(path);
				String ss="任务大小   所需CPU转数   时间占比   能量占比   车计算能力   UAV计算能力   BS计算能力   车与BS距离   车与BS是否有障碍物   车与UAV距离   UAV与BS距离   带宽   传输功率   背景噪声   车到BS信道增益   车到UAV信道增益   UAV发送一比特能耗   车发送一比特能耗   UAV计算能耗   车计算能耗   决策"+"\r\n";
				try {
					fos.write(ss.getBytes());
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String restring="";
	            try {
	            	while(true){
	            	if(main.maintasklist.size()<1){
	            		break;
	            	}	
	            	else{//下面是把全部任务的信息输出到文件里边
	            		task tta=main.maintasklist.firstEntry().getValue();
	            		main.maintasklist.remove(main.maintasklist.firstKey());
	            		
	            		restring=tta.getTasknum()+"   "+tta.getRevolution()+"   "+tta.getKt()+"   "+tta.getKe()+"   "+tta.getVehicleability()+"   "+tta.getUavability()+"   "+tta.getBsability()+"   "+tta.getV2bdistance()+"   "+tta.getHavebuilding()+"   "+tta.getV2udistance()+"   "+tta.getV2bdistance()+"   "+tta.getBand()+"   "+tta.getTransmissionpower()+"   "+tta.getBackgroundnoise()+"   "+tta.getV2bchannelgain()+"   "+tta.getV2uchannelgain()+"   "+tta.getUsende()+"   "+tta.getVsende()+"   "+tta.getUcalcue()+"   "+tta.getVcalcue()+"   "+tta.getDec()+"\r\n";
	            		fos.write(restring.getBytes());
	            	}
					
	            	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		
	}
	public static void printtotxt(String path,String pathtrain){
		//  这是日志文件的目录***************
			System.out.println("********************************");
			DecimalFormat df=new DecimalFormat("#0.00");
			//String path = "D:\\loooooooog.txt";
			File file = new File(path);
			File file1 = new File(pathtrain);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
			if(!file1.exists()){
				try {
					file1.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
				
			FileOutputStream fos;
			FileOutputStream fos1;
			try {
				fos = new FileOutputStream(path);
				fos1 = new FileOutputStream(pathtrain);
				String ss="任务ID   车辆ID   执行方式   开始时间   结束时间   执行时间   包的大小  返回大小   cost0   cost1   cost2   cost3   能耗0   能耗1   能耗2   能耗3   任务优先级"+"\r\n";
				String ss1="任务大小   所需CPU转数   时间占比   能量占比   车计算能力   UAV计算能力   BS计算能力   车与BS距离   车与BS是否有障碍物   车与UAV距离   UAV与BS距离   带宽   传输功率   背景噪声   车到BS信道增益   车到UAV信道增益   UAV发送一比特能耗   车发送一比特能耗   UAV计算能耗   车计算能耗   决策"+"\r\n";

				try {
					fos.write(ss.getBytes());
					fos1.write(ss1.getBytes());
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String restring="";
	            try {
	            	while(true){
	            	if(main.maintasklist.size()<1){
	            		break;
	            	}	
	            	else{//下面是把全部任务的信息输出到文件里边
	            		task tta=main.maintasklist.firstEntry().getValue();
	            		main.maintasklist.remove(main.maintasklist.firstKey());
	            		
	            		restring=tta.getTasknum()+"   "+tta.origin+"   "+tta.getOffloadway()+"   "+tta.getStarttime()+"   "+tta.getEndtime()+"   "+tta.getProcesstime()+"   "+tta.getOffloadsize()+"   "+tta.getReceivesize()+"   "+tta.getCost0()+"   "+tta.getCost1()+"   "+tta.getCost2()+"   "+tta.getCost3()+"   "+tta.getEnergyconsumption0()+"   "+tta.getEnergyconsumption1()+"   "+tta.getEnergyconsumption2()+"   "+tta.getEnergyconsumption3()+"   "+tta.getKt()+"\r\n";
	            		fos.write(restring.getBytes());
	            		restring=df.format(tta.getOffloadsize())+"   "+df.format(tta.getRevolution())+"   "+df.format(tta.getKt())+"   "+df.format(tta.getKe())+"   "+df.format(tta.getVehicleability())+"   "+df.format(tta.getUavability())+"   "+df.format(tta.getBsability())+"   "+df.format(tta.getV2bdistance())+"   "+df.format(tta.getHavebuilding())+"   "+df.format(tta.getV2udistance())+"   "+df.format(tta.getV2bdistance())+"   "+df.format(tta.getBand())+"   "+df.format(tta.getTransmissionpower())+"   "+df.format(tta.getBackgroundnoise())+"   "+df.format(tta.getV2bchannelgain())+"   "+df.format(tta.getV2uchannelgain())+"   "+df.format(tta.getUsende())+"   "+df.format(tta.getVsende())+"   "+df.format(tta.getUcalcue())+"   "+df.format(tta.getVcalcue())+"   "+tta.getDec().de+"\r\n";
	            		fos1.write(restring.getBytes());
	            	}
					
	            	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
		}
	public void run() {
		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		if(log.ifprint){//  这是日志文件的目录***************
			System.out.println("********************************");
			String path = "D:\\loooooooog.txt";
			File file = new File(path);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				}
				
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(path);
				String ss="任务ID   车辆ID   执行方式   开始时间   结束时间   执行时间   包的大小  返回大小   cost0   cost1   cost2   cost3"+"\r\n";
				try {
					fos.write(ss.getBytes());
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				String restring="";
	            try {
	            	while(true){
	            	if(main.maintasklist.size()<1){
	            		break;
	            	}	
	            	else{//下面是把全部任务的信息输出到文件里边
	            		task tta=main.maintasklist.firstEntry().getValue();
	            		main.maintasklist.remove(main.maintasklist.firstKey());
	            		
	            		restring=tta.getTasknum()+"   "+tta.origin+"   "+tta.getOffloadway()+"   "+tta.getStarttime()+"   "+tta.getEndtime()+"   "+tta.getProcesstime()+"   "+tta.getOffloadsize()+"   "+tta.getReceivesize()+"   "+tta.getCost0()+"   "+tta.getCost1()+"   "+tta.getCost2()+"   "+tta.getCost3()+"\r\n";
	            		fos.write(restring.getBytes());
	            	}
					
	            	}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
		}
	}
	public static void main(String[] args){
		new Thread(new log()).start();
	}
	
}
