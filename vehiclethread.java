package utils;

import java.sql.Time;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;


public class vehiclethread implements Runnable{
    static boolean[][] havetask=new boolean[500][main.basenum+main.uavnum];//������־�߳��Ƿ�ȴ�������ִ��false����ǰû������true����ǰ��������ִ��
    
	private int id;
	vehicle v;
	String a1="";
	double vehicleperiod = 1000;//����������������
	
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
		//boolean havetask=false;//��־����Ŀǰ�Ƿ��м������񣬰����Լ�����ĺ�ж�ؼ����
		TreeMap<Double, Location> treeMap = readtraffic.treeMapArray.get(v.id-main.basenum-main.uavnum);//�洢�ó����Ĺ켣
		//ʱ��������洢��ʼʱ�䵱ǰʱ����ϸ�λ�õ����ʱ��
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
		double taskwait=0;//�������������ʱ��������һ��
		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(Cycliccontrol.cycliccontrol(v.id)){ 
//				System.out.println("ִ�г���"+v.id);
			d=new Date();//ÿ��ѭ�����ж����Ƿ񾭹�һ�룬����һ��Ļ��͸ı�xy������Ϣ
			currenttime=d.getTime();
			if(treeMap.isEmpty()){
				System.out.println("����IDΪ"+v.id+"�Ľ���");
				allthreadstate.threadstate[v.id]=false;
				Cycliccontrol.addsign(v.id);
				break;
			}
			if((clock.clocktime-lasttime1)>1000){//ÿ�����λ����Ϣ���ж��߳��Ƿ����
				lasttime1=clock.clocktime;
				location=treeMap.firstEntry().getValue();
				v.setX(location.x);
				v.setY(location.y);
				main.vehiclestation[v.id][0]=location.x;
				main.vehiclestation[v.id][1]=location.y;
				//System.out.println(v.id+"   ʱ��"+treeMap.firstKey()+"x����"+v.x+"  y����"+v.y);
				treeMap.remove(treeMap.firstKey());//ÿʹ��һ��λ�þͰѸ���Ϣ��treemap��ɾ��
			}
			//System.out.println("����ID:"+id+"   ״̬:"+vehiclethread.havetask[id]);
			if(!vehiclethread.judegoffload(havetask[v.id])){//��ʱ��������һ�����������ɣ�������һ�����񣬲��Ҿ���ж��
				
				synchronized (a1) {
					if(t.nexttime>0){
						t.Cumulativetime=t.Cumulativetime+t.nexttime;
						if(t.Cumulativetime<clock.timeslot){
							System.out.println("����ID"+v.id+"��������"+ta.tasknum+"�ѽ��");
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
							
						ta=createtask.creatatask(v.id);//��������
						taskwait=1000;
						ta.starttime=clock.clocktime;
						ta.origin=v.id;
						ta.vehicleability=v.calculation;
						ta.uavability=main.u.calculation;
						ta.bsability=main.b.calculation;
						
//						System.out.println("����X���꣺"+v.x+"    ����Y���꣺"+v.y);
						try {
							System.out.println("����IDΪ:"+v.id);
							ta=vehicletaskdecision.taskdecide(ta, v);
							System.out.println("����"+v.id+"�������*********************************************************");
						} catch (Exception e) {
							e.printStackTrace();
						}//�������߷��ؽ��0:�Լ�    1�����˻�    2����վ   3:ͨ�����˻�ж�ص���վ
//						System.out.println("��������������"+v.calculation);
						
						
//						if(ta.dec.de==1){
//							ta.offloadway=ta.dec.de;
//							ta.processtime=(ta.revolution/(main.u.calculation*1024))*1000;
//					        t.Cumulativetime=t.Cumulativetime+ta.dec.sendtime*1000;
//					        if(t.Cumulativetime<clock.timeslot){//��ǰʱ�䲽���ڿ��Է������
//					        	System.out.println("���˻���"+ta.processtime);
//								uavthread.tasklist.put(ta.tasknum, ta);
//								uavthread.taskcount++;
//								t.Cumulativetime=0;
//								t.nexttime=0;
//								vehiclethread.havetask[id]=true;//�ѱ�־λ���棬ֱ��������Ϣ���ٴ���������
//								Cycliccontrol.addsign(v.id);
//					        }
//					        else{//��ǰʱ�䲽���ڷ��Ͳ���
//					        	t.nexttime=t.Cumulativetime-clock.timeslot;	
//					        	issend=true;//�ѳ����ķ��ͽ��̵ı�־λ����
//					        	t.Cumulativetime=0;
//					        	Cycliccontrol.addsign(v.id);
//					        	}
							//System.out.println("���˻���"+ta.processtime);
							//uavthread.tasklist.put(uavthread.taskcount, ta);
							//uavthread.taskcount++;
							//vehiclethread.havetask[id]=true;//�ѱ�־λ���棬ֱ��������Ϣ���ٴ���������
							//Cycliccontrol.addsign(v.id);
//						}
//						else if(ta.dec.de==2||ta.dec.de==3){
//							ta.offloadway=ta.dec.de;
//							ta.processtime=(ta.revolution/(main.b.calculation*1024))*1000;
//							System.out.println("��վ��"+ta.processtime);
//							basestationthread.tasklist.put(basestationthread.taskcount, ta);
//							basestationthread.taskcount++;
//							vehiclethread.havetask[id]=true;//�ѱ�־λ���棬ֱ��������Ϣ���ٴ���������
//							Cycliccontrol.addsign(v.id);
//						}
							
//							ta.offloadway=0;
							t.Cumulativetime=t.Cumulativetime+(((ta.revolution*ta.offloaddevice)/1024)/v.calculation)*1000;
							//System.out.println("&&&&&&&&&&&&&&&"+t.Cumulativetime);
							ta.processtime=(((ta.revolution*ta.offloaddevice)/1024)/v.calculation)*1000;
//							System.out.println("�����Լ���"+ta.processtime);
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
				//vehiclethread.havetask[id]=true;//�ѱ�־λ���棬ֱ��������Ϣ���ٴ���������
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


