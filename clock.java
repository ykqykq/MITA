package utils;

public class clock {

	public static long clocktime=0;//系统时间，毫秒
	public static long timeslot=10;//时间步长，单位毫秒
	
	public static void runtimeslot(){//时间向前执行一个时间步长
		clocktime=clocktime+timeslot;
	}
}
