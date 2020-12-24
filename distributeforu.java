package utils;

public class distributeforu {

	public static double[] distribute(int n,newdevice[] d){
		double[] judge=new double[n];
		double doing=0;
		newdevice.maxtime=0;
		while(doing<1){
			distributeforu.initialized(n, d);//初始化每个设备的增量
//			System.out.println("设备0增量"+d[0].increase);
//			System.out.println("设备1增量"+d[1].increase);
			d[distributeforu.getmin(n, d)].setpercent();
			doing=doing+newdevice.allpercent;
			for(int i=0;i<n;i++){
				System.out.println("设备"+i+" 已分配："+String.format("%.6f", d[i].percent)+" 增量为："+String.format("%.6f", d[i].increase)+" 当前时间："+String.format("%.6f", d[i].currenttime)+" 等待时间："+String.format("%.6f", d[i].waittime));
				judge[i]=d[i].percent;
			}
			System.out.println("最大时间："+String.format("%.6f", newdevice.maxtime));
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
		d[0].waittime=0;//下标0代表车辆自己
		d[0].waitu=0;
		d[0].totaltime=26.52;
		d[0].totalu=0.5163;
		d[0].excepttimeu=0.0163;
		d[1].waittime=0;//下标1代表无人机
		d[1].waitu=0;
		d[1].totaltime=0.276;
		d[1].totalu=0.167;
		d[1].excepttimeu=0.1620;
//		d[0].waittime=0;//下标0代表车辆自己
//		d[0].waitu=0;
//		d[0].totaltime=12;
//		d[0].totalu=0.72;
//		d[0].excepttimeu=0.195;
//		d[1].waittime=2;//下标1代表无人机
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
