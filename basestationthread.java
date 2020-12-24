package utils;

import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class basestationthread implements Runnable{

    public static Lock lock = new ReentrantLock();

    static double taskcount=0;
	static TreeMap<Integer, task> tasklist=new TreeMap<Integer, task>();
	static basestation bs;
	int id;
	String a1="";
	public basestationthread(basestation b){
		this.bs=b;
	}
	public void run(){
		Date d=new Date();//初始化时间标志
		long starttime=d.getTime();
		long currenttime=starttime;
		long lasttime=currenttime;
		task ta = null;
        time t=new time();
        double lasttime1;
        System.out.println("时间为："+clock.clocktime);
        lasttime1=clock.clocktime;
		while(true){			 
		    try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   d=new Date();//每次循环都获取当前时间
	           currenttime=d.getTime();
	           if(clock.clocktime>1000000){//线程执行500s之后停止
	        	   log.printtotxt("D:\\loooooooog.txt","D:\\printdata.txt");
	        	  // log.printdata("D:\\printdata.txt");
		           break;
	           }
	           if((clock.clocktime-lasttime1)>1000){//每秒更新位置信息，判断线程是否结束
                 bs.setX(bs.getX());//每秒更新基站位置信息，
                 bs.setY(bs.getY());
                 lasttime1=clock.clocktime;
	           }
			if(Cycliccontrol.cycliccontrol(bs.id)){//每次循环执行一个任务
			   synchronized (a1) {
			   if(t.nexttime>0){//有未执行完的任务
                  t.Cumulativetime=t.Cumulativetime+t.nexttime;
                  if(t.Cumulativetime<clock.timeslot){//判断是否一个步长可以执行完
                	  ta.endtime= clock.clocktime;
//            	      main.maintasklist.put((double) ta.tasknum, ta);//把任务加入已完成链表
    			      vehiclethread.havetask[ta.origin][0]=false;//把车辆上标志任务状态的变量置为假，代表任务已经完成
    			      System.out.println("基站计算了id为"+ta.origin+"的车的    任务ID:"+ta.tasknum);
    			      taskcount++;//基站任务完成数加一
                      t.nexttime=0;
			        }
                  else{
                	  t.nexttime=t.Cumulativetime-clock.timeslot;
                	  t.Cumulativetime=0;
                	  Cycliccontrol.addsign(bs.id);
  					
                  }
			}
			else{//没有未执行完的任务
		         if(basestationthread.tasklist.size()>0){
			           ta=basestationthread.tasklist.firstEntry().getValue(); 
			           tasklist.remove(tasklist.firstKey());
			           double calculationtobase=(ta.revolution*ta.offloaddevice)/(bs.calculation*1024);//当前任务的执行时间
			           t.currenttime=calculationtobase*1000;			   
                       t.Cumulativetime=t.Cumulativetime+t.currenttime;
                     if(t.Cumulativetime<clock.timeslot){
			             d=new Date();
			             currenttime=d.getTime();
			             ta.endtime= clock.clocktime;
//			             main.maintasklist.put((double) ta.tasknum, ta);//把任务加入已完成链表
			             vehiclethread.havetask[ta.origin][0]=false;//把车辆上标志任务状态的变量置为假，代表任务已经完成
			             System.out.println("基站计算了id为"+ta.origin+"的车的    任务ID:"+ta.tasknum);
			             taskcount++;//基站任务完成数加一
			             //System.out.println(v.id+"   "+starttime);
			             t.nexttime=0;
			             
                       }
                     else{
                        	t.nexttime=t.Cumulativetime-clock.timeslot;
                        	t.Cumulativetime=0;
                        	Cycliccontrol.addsign(bs.id);

                     }
			           }
		         else{
		        	 Cycliccontrol.addsign(bs.id);

		         }
			}
			}
		}
		}
		
	}
	public static double getwait(){
		double waittime=0;
		double waitsize=0;
		for(Integer m:tasklist.keySet()){
			waitsize=waitsize+tasklist.get(m).offloadsize*tasklist.get(m).offloaddevice;
		}
		waittime=(waitsize/1000)/bs.calculation;
		return(waittime);
	}
}

