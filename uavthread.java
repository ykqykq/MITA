package utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class uavthread implements Runnable{
    static double taskcount=0;
	
	static List<TreeMap<Integer, task>> tasktreeMapArray= new ArrayList<TreeMap<Integer, task>>();
	
	uav u;
	String a1="";
	public uavthread(uav u){
		this.u=u;
		tasktreeMapArray.add(u.id-main.basenum, new TreeMap<Integer, task>());
	}
	
	public void run(){
		TreeMap<Integer, task> tasklist=new TreeMap<Integer, task>();
		tasklist=tasktreeMapArray.get(u.id-main.basenum);
		//ʱ��������洢��ʼʱ�䵱ǰʱ����ϸ�λ�õ����ʱ��
		TreeMap<Integer, Location> uavtreeMap = readtraffic.uavtreeMapArray.get(u.id-main.basenum);//�洢���˻��Ĺ켣�Ĺ켣
		Location location;
		Date d=new Date();
		long starttime=d.getTime();
		long currenttime=starttime;
		long lasttime=currenttime;
		long lasttime1=clock.clocktime;
		task ta=null;
		time t=new time();
		while(true){//ÿ��ѭ��ִ��һ������
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
              e.printStackTrace();
			}
			d=new Date();//ÿ��ѭ������ȡ��ǰʱ��
			currenttime=d.getTime();
			if((clock.clocktime-lasttime1)>1000){//ÿ�����λ����Ϣ���ж��߳��Ƿ����
			    location=uavtreeMap.firstEntry().getValue();
                u.setX(location.x);//ÿ��������˻�λ����Ϣ������XY����Z������
                u.setY(location.y);
                lasttime1=clock.clocktime;
                uavtreeMap.remove(uavtreeMap.firstKey());
			}
			if(Cycliccontrol.cycliccontrol(u.id)){
			synchronized (a1) {
			if(t.nexttime>0){
				t.Cumulativetime=t.Cumulativetime+t.nexttime;
				if(t.Cumulativetime<clock.timeslot){
					ta.endtime=clock.clocktime;
//				    main.maintasklist.put((double) ta.tasknum, ta);//������������������
				    vehiclethread.havetask[ta.origin][u.id]=false;//�ѳ����ϱ�־����״̬�ı�����Ϊ�٣����������Ѿ����
				    System.out.println("���˻�������idΪ"+ta.origin+"�ĳ�������ID:"+ta.tasknum);
				    taskcount++;//���˻������������һ
				    t.nexttime=0;   
				}
				else{
					t.nexttime=t.Cumulativetime-clock.timeslot;
					t.Cumulativetime=0;
					Cycliccontrol.addsign(u.id);

				}
			}
			else{
			if(tasklist.size()>0){
				
			    ta=tasklist.firstEntry().getValue(); 
			    tasklist.remove(tasklist.firstKey());
			    double calculationtouav=(ta.revolution*ta.offloaddevice)/(u.calculation*1024);//��ǰ�����ִ��ʱ��
                t.currenttime=calculationtouav*1000;
                t.Cumulativetime=t.Cumulativetime+t.currenttime;
                if(t.Cumulativetime<clock.timeslot){//�ж��Ƿ�����ʱ�䲽����ִ����
                	d=new Date();
    			    currenttime=d.getTime();
    			    ta.endtime=clock.clocktime;
//    			    main.maintasklist.put((double) ta.tasknum, ta);//������������������
    			    vehiclethread.havetask[ta.origin][u.id]=false;//�ѳ����ϱ�־����״̬�ı�����Ϊ�٣����������Ѿ����
    			    System.out.println("���˻�������idΪ"+ta.origin+"�ĳ�������ID:"+ta.tasknum);
    			    taskcount++;//���˻������������һ
    			    t.nexttime=0;    			    
                }
                else{
                	t.nexttime=t.Cumulativetime-clock.timeslot;
                	t.Cumulativetime=0;
                	Cycliccontrol.addsign(u.id);

                }			    
				}
			else{
				Cycliccontrol.addsign(u.id);

			}
				}
			}
		}
			}
		}
       
	public static double getwait(int uid){
		double waittime=0;
		double waitsize=0;
		
		for(Integer m:tasktreeMapArray.get(uid).keySet()){
			System.out.println("�����С��"+tasktreeMapArray.get(uid).get(m).revolution+"  ж�ر���"+"  �ٷֱ�"+tasktreeMapArray.get(uid).get(m).offloaddevice);
			waitsize=waitsize+tasktreeMapArray.get(uid).get(m).revolution*tasktreeMapArray.get(uid).get(m).offloaddevice;
		}
		waittime=(waitsize/1000)/uav.calculation;
		System.out.println("���˻�"+uid+" "+tasktreeMapArray.get(uid).size()+" �ȴ�ʱ�䣺"+waittime+"�ȴ���С��"+waitsize);
		return(waittime);
	}
}
