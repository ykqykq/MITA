package utils;

public class device {

	double finishtime=0;//������ȫ����������˻�����ģ�����ͼ���ʱ��/s
	double waittime=0;//���豸��Ҫ�ȴ���ʱ��/s
	int deviceid;
	public device(double finishtime,double waittime){
		this.finishtime=finishtime;
		this.waittime=waittime;

	}
	public device(int id){
		this.deviceid=id;
	}
}
