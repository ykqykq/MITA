package utils;
import java.util.TreeMap;
public class practice {
	public static void mm(task t){
		t.taskid++;
	}
	static double w=20;//带宽单位MHz
	static double pn=100;//传输功率单位mWatts兆瓦特
	static double n0=Math.pow(10, -10);//背景噪声单位dBm
	public static void main(String[] args)throws Exception {
		task ta=new task();
		ta.taskid=1;
		TreeMap<Integer, task> t1=new TreeMap<Integer, task>();
		TreeMap<Integer, task> t2=new TreeMap<Integer, task>();
		t1.put(1, ta);
		ta.taskid=2;
		task ta2=(task)ta.clone();
		ta2.taskid=3;
		int id =5;
		for(int i=0;i<2;i++){
			task tt=(task)ta.clone();
			tt.taskid=id;
			id++;
			t1.put(tt.taskid, tt);
		}
		System.out.println(t1.get(5).taskid);
		System.out.println(t1.get(6).taskid);

		long i=4321;
		System.out.print(i/10);
		double chann=0;
		double totalchannel=0;
		double transspeed=w*(Math.log(1+((pn*chann)/(n0+pn*totalchannel)))/Math.log(2));
		System.out.println("速度为："+transspeed);
	}

	
}
