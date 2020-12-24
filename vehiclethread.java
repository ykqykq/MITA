package utils;

import java.sql.Time;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;


public class vehiclethread implements Runnable{
    static boolean[][] havetask=new boolean[500][main.basenum+main.uavnum];//用来标志线程是否等待任务再执行false代表当前没有任务true代表当前有任务在执行
    
	private int id;
	vehicle v;
	String a1="";
	double vehicleperiod = 1000;//车辆产生任务周期
	
	 private ReentrantLock r = new ReentrantLock();
	 public static boolean judegoffload(boolean[] bs){
		 boolean b=false;
		 for(int i=0;i<main.basenum+main.uavnum;i++){
			 if(bs[i]==true)
				 return(true);
		 }
		 return(false);
	 }
	public vehiclethread(int id,vehicle v) {
	       this.v=v;
	       this.id=id;
	       for(int i=0;i<main.uavnum+main.basenum;i++){
	    	   havetask[id][i]=false;
	       }
	    }
	public void run(){
		//boolean havetask=false;//标志车辆目前是否有计算任务，包括自己计算的和卸载计算的
		TreeMap<Double, Location> treeMap = readtraffic.treeMapArray.get(v.id-main.basenum-main.uavnum);//存储该车辆的轨迹
		//时间变量，存储开始时间当前时间和上个位置到达的时间
		//System.out.println(treeMap);
		Date d=new Date();
		long starttime=d.getTime();
		long currenttime=starttime;
		long lasttime=currenttime;
		long lasttime1=clock.clocktime;
		Location location=treeMap.firstEntry().getValue();
		treeMap.remove(treeMap.firstKey());
		boolean issend=false;
		task ta=null;
		time t=new time();
		double taskwait=0;//车辆产生任务的时间间隔最少一秒
		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(Cycliccontrol.cycliccontrol(v.id)){ 
//				System.out.println("执行车辆"+v.id);
			d=new Date();//每次循环都判断其是否经过一秒，进过一秒的话就改变xy坐标信息
			currenttime=d.getTime();
			if(treeMap.isEmpty()){
				System.out.println("车辆ID为"+v.id+"的结束");
				allthreadstate.threadstate[v.id]=false;
				Cycliccontrol.addsign(v.id);
				break;
			}
			if((clock.clocktime-lasttime1)>1000){//每秒更新位置信息，判断线程是否结束
				lasttime1=clock.clocktime;
				location=treeMap.firstEntry().getValue();
				v.setX(location.x);
				v.setY(location.y);
				main.vehiclestation[v.id][0]=location.x;
				main.vehiclestation[v.id][1]=location.y;
				//System.out.println(v.id+"   时间"+treeMap.firstKey()+"x坐标"+v.x+"  y坐标"+v.y);
				treeMap.remove(treeMap.firstKey());//每使用一个位置就把该信息从treemap中删除
			}
			//System.out.println("车辆ID:"+id+"   状态:"+vehiclethread.havetask[id]);
			if(!vehiclethread.judegoffload(havetask[v.id])){//此时代表车辆上一个任务计算完成，生成下一个任务，并且决策卸载
				
				synchronized (a1) {
					if(t.nexttime>0){
						t.Cumulativetime=t.Cumulativetime+t.nexttime;
						if(t.Cumulativetime<clock.timeslot){
							System.out.println("车辆ID"+v.id+"计算任务："+ta.tasknum+"已解决");
							ta.endtime=clock.clocktime;
//							main.maintasklist.put((double) ta.tasknum, ta);
							//vehiclethread.havetask[id]=false;
							t.nexttime=0;
							
						}
						else{   
							t.nexttime=t.Cumulativetime-clock.timeslot;
							t.Cumulativetime=0;
							Cycliccontrol.addsign(v.id);
						}
						
					}
					else{
						if (taskwait<=0) {
							
						ta=createtask.creatatask(v.id);//生成任务
						taskwait=1000;
						ta.starttime=clock.clocktime;
						ta.origin=v.id;
						ta.vehicleability=v.calculation;
						ta.uavability=main.u.calculation;
						ta.bsability=main.b.calculation;
						
//						System.out.println("车辆X坐标："+v.x+"    车辆Y坐标："+v.y);
						try {
							System.out.println("车辆ID为:"+v.id);
							ta=vehicletaskdecision.taskdecide(ta, v);
							System.out.println("车辆"+v.id+"决策完成*********************************************************");
						} catch (Exception e) {
							e.printStackTrace();
						}//进过决策返回结果0:自己    1：无人机    2：基站   3:通过无人机卸载到基站
//						System.out.println("车辆计算能力："+v.calculation);
						
						
//						if(ta.dec.de==1){
//							ta.offloadway=ta.dec.de;
//							ta.processtime=(ta.revolution/(main.u.calculation*1024))*1000;
//					        t.Cumulativetime=t.Cumulativetime+ta.dec.sendtime*1000;
//					        if(t.Cumulativetime<clock.timeslot){//当前时间步长内可以发送完毕
//					        	System.out.println("无人机："+ta.processtime);
//								uavthread.tasklist.put(ta.tasknum, ta);
//								uavthread.taskcount++;
//								t.Cumulativetime=0;
//								t.nexttime=0;
//								vehiclethread.havetask[id]=true;//把标志位置真，直到返回信息，再次生成任务
//								Cycliccontrol.addsign(v.id);
//					        }
//					        else{//当前时间步长内发送不完
//					        	t.nexttime=t.Cumulativetime-clock.timeslot;	
//					        	issend=true;//把车辆的发送进程的标志位置真
//					        	t.Cumulativetime=0;
//					        	Cycliccontrol.addsign(v.id);
//					        	}
							//System.out.println("无人机："+ta.processtime);
							//uavthread.tasklist.put(uavthread.taskcount, ta);
							//uavthread.taskcount++;
							//vehiclethread.havetask[id]=true;//把标志位置真，直到返回信息，再次生成任务
							//Cycliccontrol.addsign(v.id);
//						}
//						else if(ta.dec.de==2||ta.dec.de==3){
//							ta.offloadway=ta.dec.de;
//							ta.processtime=(ta.revolution/(main.b.calculation*1024))*1000;
//							System.out.println("基站："+ta.processtime);
//							basestationthread.tasklist.put(basestationthread.taskcount, ta);
//							basestationthread.taskcount++;
//							vehiclethread.havetask[id]=true;//把标志位置真，直到返回信息，再次生成任务
//							Cycliccontrol.addsign(v.id);
//						}
							
//							ta.offloadway=0;
							t.Cumulativetime=t.Cumulativetime+(((ta.revolution*ta.offloaddevice)/1024)/v.calculation)*1000;
							//System.out.println("&&&&&&&&&&&&&&&"+t.Cumulativetime);
							ta.processtime=(((ta.revolution*ta.offloaddevice)/1024)/v.calculation)*1000;
//							System.out.println("车辆自己："+ta.processtime);
							if(t.Cumulativetime<clock.timeslot){
								ta.endtime=clock.clocktime;
								//System.out.println("&&&&&&&&&&&&&&&"+clock.clocktime);
								main.maintasklist.put( ta.tasknum, ta);
//								vehiclethread.havetask[id]=false;
								t.nexttime=0;
								
							}
							else{
								t.nexttime=t.Cumulativetime-clock.timeslot;
								t.Cumulativetime=0;
								Cycliccontrol.addsign(v.id);
							}	
						
					}
						else {
							taskwait=taskwait-clock.timeslot;
							Cycliccontrol.addsign(v.id);
						}
				}
				}
				//vehiclethread.havetask[id]=true;//把标志位置真，直到返回信息，再次生成任务
				}
			else{
				Cycliccontrol.addsign(v.id);
			}
			}
			
			//System.out.println(v.id+"   "+starttime);
			  
		}
		}
        
		}
	
	/*public static void main(String[] args)throws Exception{
		readtraffic a=new readtraffic();
        a.readcar("E:\\traffic.txt");
		new Thread(new vehiclethread(1,new vehicle(1,2,2,100))).start();
		new Thread(new vehiclethread(2,new vehicle(2,2,2,100))).start();
		
		}*/


