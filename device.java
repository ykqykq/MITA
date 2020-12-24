package utils;

public class device {

	double finishtime=0;//此任务全部传输给无人机计算的，传输和计算时间/s
	double waittime=0;//该设备需要等待的时间/s
	int deviceid;
	public device(double finishtime,double waittime){
		this.finishtime=finishtime;
		this.waittime=waittime;

	}
	public device(int id){
		this.deviceid=id;
	}
}
