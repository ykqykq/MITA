package utils;

public class Cycliccontrol {

	public static int idsign; 
	public static boolean cycliccontrol(int id){
		if(idsign==id){
			return(true);
		}
		else{
			return(false);
		}
	}
	public void runtimeslot(){
		clock.runtimeslot();
	}
	public static void addsign(int id){//sign��������վ�������˻��ı�ǣ�0��վ��1���˻���2����
		//System.out.println("�豸IDΪ:"+id);
		for(int i=0;i<main.totalnum;i++){
			if(allthreadstate.threadstate[(id+i+1)%(main.totalnum)]==true){
				
				idsign=(id+i+1)%(main.totalnum);
//				System.out.println("��һ��ִ�е��豸IDΪ:"+idsign);
				break;
			}
	}
	}
	public static void main(String[] args){
		main.basenum=2;
		main.uavnum=3;
		main.num=3;
		main.totalnum=8;
		
		new allthreadstate(main.totalnum);
//		allthreadstate.threadstate[3]=false;
		allthreadstate.threadstate[4]=false;
		allthreadstate.threadstate[5]=false;
		allthreadstate.threadstate[6]=false;
//		System.out.println(allthreadstate.threadstate[3]);
		for(int i=0;i<200;i++){
			
			if(idsign==i%7){
				System.out.println(i%7);
			    Cycliccontrol.addsign(i%7);
			}
		}
	}
}
