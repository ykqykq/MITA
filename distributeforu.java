package utils;

public class distributeforu {

	public static double[] distribute(int n,newdevice[] d){
		double[] judge=new double[n];
		double doing=0;
		newdevice.maxtime=0;
		while(doing<1){
			distributeforu.initialized(n, d);//��ʼ��ÿ���豸������
//			System.out.println("�豸0����"+d[0].increase);
//			System.out.println("�豸1����"+d[1].increase);
			d[distributeforu.getmin(n, d)].setpercent();
			doing=doing+newdevice.allpercent;
			for(int i=0;i<n;i++){
				System.out.println("�豸"+i+" �ѷ��䣺"+String.format("%.6f", d[i].percent)+" ����Ϊ��"+String.format("%.6f", d[i].increase)+" ��ǰʱ�䣺"+String.format("%.6f", d[i].currenttime)+" �ȴ�ʱ�䣺"+String.format("%.6f", d[i].waittime));
				judge[i]=d[i].percent;
			}
			System.out.println("���ʱ�䣺"+String.format("%.6f", newdevice.maxtime));
			System.out.println("******************");
		}
		return(judge);
	}
	public static int getmin(int n,newdevice[] d){
		int index=999;
		double minincrease=9999;
		for(int i=0;i<n;i++){
			if(minincrease>d[i].increase){
				index=i;
				minincrease=d[i].increase;
			}
		}
		return(index);
	}
	public static void initialized(int n,newdevice[] d){
		for(int i=0;i<n;i++){
			d[i].setincrease();
		}
	}
	public static void main(String[] args) {
		newdevice[] d=new newdevice[100];
		d[0]=new newdevice();
		d[1]=new newdevice();
		d[2]=new newdevice();
		d[0].waittime=0;//�±�0�������Լ�
		d[0].waitu=0;
		d[0].totaltime=26.52;
		d[0].totalu=0.5163;
		d[0].excepttimeu=0.0163;
		d[1].waittime=0;//�±�1�������˻�
		d[1].waitu=0;
		d[1].totaltime=0.276;
		d[1].totalu=0.167;
		d[1].excepttimeu=0.1620;
//		d[0].waittime=0;//�±�0�������Լ�
//		d[0].waitu=0;
//		d[0].totaltime=12;
//		d[0].totalu=0.72;
//		d[0].excepttimeu=0.195;
//		d[1].waittime=2;//�±�1�������˻�
//		d[1].waitu=0.04375;
//		d[1].totaltime=8;
//		d[1].totalu=0.67;
//		d[1].excepttimeu=0.495;
////		d[1].currenttime=8;
		int n=2;
		newdevice.time=26.52;
		newdevice.timeu=0.5163-0.01632;
		distributeforu.distribute(n, d);

	}

}
