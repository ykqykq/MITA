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
		Date d=new Date();//��ʼ��ʱ���־
		long starttime=d.getTime();
		long currenttime=starttime;
		long lasttime=currenttime;
		task ta = null;
        time t=new time();
        double lasttime1;
        System.out.println("ʱ��Ϊ��"+clock.clocktime);
        lasttime1=clock.clocktime;
		while(true){			 
		    try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			   d=new Date();//ÿ��ѭ������ȡ��ǰʱ��
	           currenttime=d.getTime();
	           if(clock.clocktime>1000000){//�߳�ִ��500s֮��ֹͣ
	        	   log.printtotxt("D:\\loooooooog.txt","D:\\printdata.txt");
	        	  // log.printdata("D:\\printdata.txt");
		           break;
	           }
	           if((clock.clocktime-lasttime1)>1000){//ÿ�����λ����Ϣ���ж��߳��Ƿ����
                 bs.setX(bs.getX());//ÿ����»�վλ����Ϣ��
                 bs.setY(bs.getY());
                 lasttime1=clock.clocktime;
	           }
			if(Cycliccontrol.cycliccontrol(bs.id)){//ÿ��ѭ��ִ��һ������
			   synchronized (a1) {
			   if(t.nexttime>0){//��δִ���������
                  t.Cumulativetime=t.Cumulativetime+t.nexttime;
                  if(t.Cumulativetime<clock.timeslot){//�ж��Ƿ�һ����������ִ����
                	  ta.endtime= clock.clocktime;
//            	      main.maintasklist.put((double) ta.tasknum, ta);//������������������
    			      vehiclethread.havetask[ta.origin][0]=false;//�ѳ����ϱ�־����״̬�ı�����Ϊ�٣����������Ѿ����
    			      System.out.println("��վ������idΪ"+ta.origin+"�ĳ���    ����ID:"+ta.tasknum);
    			      taskcount++;//��վ�����������һ
                      t.nexttime=0;
			        }
                  else{
                	  t.nexttime=t.Cumulativetime-clock.timeslot;
                	  t.Cumulativetime=0;
                	  Cycliccontrol.addsign(bs.id);
  					
                  }
			}
			else{//û��δִ���������
		         if(basestationthread.tasklist.size()>0){
			           ta=basestationthread.tasklist.firstEntry().getValue(); 
			           tasklist.remove(tasklist.firstKey());
			           double calculationtobase=(ta.revolution*ta.offloaddevice)/(bs.calculation*1024);//��ǰ�����ִ��ʱ��
			           t.currenttime=calculationtobase*1000;			   
                       t.Cumulativetime=t.Cumulativetime+t.currenttime;
                     if(t.Cumulativetime<clock.timeslot){
			             d=new Date();
			             currenttime=d.getTime();
			             ta.endtime= clock.clocktime;
//			             main.maintasklist.put((double) ta.tasknum, ta);//������������������
			             vehiclethread.havetask[ta.origin][0]=false;//�ѳ����ϱ�־����״̬�ı�����Ϊ�٣����������Ѿ����
			             System.out.println("��վ������idΪ"+ta.origin+"�ĳ���    ����ID:"+ta.tasknum);
			             taskcount++;//��վ�����������һ
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

