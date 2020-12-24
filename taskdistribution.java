package utils;

import java.text.DecimalFormat;

public class taskdistribution {
	DecimalFormat df = new DecimalFormat("#.00");
	static double timeslot=0.01;
	public double[] distribution(int n,device[] d){//每次给除了等待时间最长的那个外，平均分发任务
		double  total=1;
		
		int maxindex;
		double[] judge=new double[n];
		maxindex=taskdistribution.max(n, d);
		while(total>0){
			maxindex=taskdistribution.max(n, d);
			
			for(int i=0;i<n;i++){
				if(i!=maxindex){
					total=total-timeslot/d[i].finishtime;
//					System.out.println(total+"---"+i);
					if(total<0){
						judge[i]=judge[i]+timeslot/d[i].finishtime-Math.abs(total);
//						System.out.println(judge[i]+"***"+i);
						break;
					}
					else{
						judge[i]=judge[i]+timeslot/d[i].finishtime;
						d[i].waittime=d[i].waittime+timeslot;
//						System.out.println(judge[i]+"***"+i);
					}

				}
				
			}
			
		}
		return(judge);
	}
	public static double[] distributionmin(int n,device[] d){
		double  total=1;
		int minindex;
		
		double[] judge=new double[n];
		minindex=taskdistribution.min(n, d);
//		System.out.println("***"+d[0].finishtime);
		if(n>0){
		if(d[0].finishtime>0){
		while(total>0){
			minindex=taskdistribution.min(n, d);
			
			for(int i=0;i<n;i++){
				if(i==minindex){
					total=total-timeslot/d[i].finishtime;
//					System.out.println("total"+total+"---"+i);
					if(total<0){
						judge[i]=judge[i]+timeslot/d[i].finishtime-Math.abs(total);
						d[i].waittime=d[i].waittime+timeslot;
//						System.out.println(d[i].waittime+"设备号"+i);
						break;
					}
					else{
						judge[i]=judge[i]+timeslot/d[i].finishtime;
						d[i].waittime=d[i].waittime+timeslot;
//						System.out.println(d[i].waittime+"设备号"+i);
//						System.out.println(judge[i]+"***"+i);
					}

				}
				
			}
			
		}
		}
		}
		double t=1;
		for(int i=0;i<n;i++){
			judge[i]=Double.parseDouble(String.format("%.3f", judge[i]));
//			if(i==n-1){
//				judge[i]=Double.parseDouble(String.format("%.3f", t));
//			}
//			else{
//				t=t-judge[i];
//			}
		}
		return(judge);
	}
	public static int max(int n,device[] d){
		int maxindex=0;
		double maxnumber=0;
		for(int i=0;i<n;i++){
			if(maxnumber<d[i].waittime){
				maxnumber=d[i].waittime;
				maxindex=i;
				}
		}
		return(maxindex);
	}
	public static int min(int n,device[] d){
		int minindex=999;
		double minnumber=999;
		for(int i=0;i<n;i++){
			if(minnumber>d[i].waittime){
				minnumber=d[i].waittime;
				minindex=i;
			}
		}
		return(minindex);
	}
	public static double getdistance(double x1,double y1,double x2,double y2){
		double distance;
		distance=Math.sqrt((y1-y2)*(y1-y2)+(x1-x2)*(x1-x2));
		return(distance);
	}
	public static void main(String[] args) {

//		d[0]=new device(12,1);
//		d[1]=new device(10,2);
        taskdistribution ta=new taskdistribution();
//        int index=ta.max(n, d);
//        System.out.println(index);
//		double[] judge=ta.distribution(n, d);
//        System.out.println(judge[0]);
//        System.out.println(judge[1]);
//        System.out.println("******");
		int n=0;
		device[] d=new device[100];
        d[0]=new device(4,1/0.00101);
		d[1]=new device(3,4);
		d[2]=new device(8,3);
		d[3]=new device(9,2);
		int index=taskdistribution.min(n, d);
        System.out.println(index);

        double[] judge=ta.distributionmin(n, d);
//        System.out.println(judge[0]);
//        System.out.println(judge[1]);
//        System.out.println(judge[2]);
//        System.out.println(judge[3]);
        System.out.println("距离："+Math.ceil(taskdistribution.getdistance(490.74, 498.37, 500, 500)));
        System.out.println("距离："+taskdistribution.getdistance(490.83, 495.07, 500, 500));

	}

}
