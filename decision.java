package utils;

public class decision {

	double offloadpersent;//������Ҫж�صĴ�С�İٷֱ�
	double vmake;
	double[] uavpersent=new double[main.uavnum];//ж�ص����˻��ͻ�վ�İٷֱȾ���
	double basepersent;
	//�˾��ߵ���Ч��
	double totalutility=9999999;
	//��ʱ�䡢�ܼ۸����ܺ�
	double totalprice=99999,totaltime=99999,totale=99999;
	int de;//�����������ߣ����ڳ������ߣ�0�������Լ���  1��ж�ص����˻���   2 ��ж�ص���վ��
	double time;//�������չ��ص����˻������Լ�������ʱ����
	double sendtime;//����ʱ��
	public decision(int de,double time,double sendtime){
		this.de=de;
		this.time=time;
		this.sendtime=sendtime;
	}
	public decision(){
		
	}
	public int getDe() {
		return de;
	}
	public void setDe(int de) {
		this.de = de;
	}
	public double getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public double getSendtime() {
		return sendtime;
	}
	public void setSendtime() {
		this.sendtime=sendtime;
	}
	
}
