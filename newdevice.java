package utils;

public class newdevice {
	static double time;
	static double timeu;
	double percent=0;//��ж�صİٷֱ�
    static double allpercent=0.01;//ÿ�η������
	double pt=0.7,pe=0.1,pp=0.2;//�ֱ����ʱ�䡢�ܺġ��۸��Ȩ��
	//����ÿ�������Ա�ж�ص��豸��ߵ�����
	boolean b=false;//���ڼٴ�����豸��δ������
	double waittime=0;//�ȴ�ʱ��
	double waitu=0;//�ȴ���Ч��
	double totaltime=0;//���һ���������ʱ��
	double totalu=0;//���һ���������Ч��
	double excepttimeu=0;//�������һ���������ʱ�����Ч��
	double currentu=0;//��ǰ�ѷ����Ч��
	double increase=0;//ÿ�ε�����
	double currenttime=0;//��ǰ����������������Ҫ��ʱ��
	static double maxtime=0;//��¼Ŀǰ�����ʱ��
	
	public void setincrease(){//����������ֵ
		this.increase=0;
		if(newdevice.maxtime<(totaltime*newdevice.allpercent+currenttime+waittime)){
			double t=(totaltime*newdevice.allpercent+currenttime+waittime)-newdevice.maxtime;
			double timeu=newdevice.gettimeu(t);
			double nu=this.excepttimeu*newdevice.allpercent;
			this.increase=nu+timeu;

		}
		else {
			this.increase=this.excepttimeu*newdevice.allpercent;
		}


	}
	public void setpercent(){
		this.percent=this.percent+newdevice.allpercent;//����ж�ذٷֱ�
		this.currenttime=this.currenttime+totaltime*newdevice.allpercent;
		if((this.currenttime+this.waittime)>newdevice.maxtime){
			newdevice.maxtime=this.currenttime+this.waittime;
		}
		
	}
	public static double gettimeu(double t){
		double timeu=0;
		
		timeu=newdevice.timeu*t/newdevice.time;
		return(timeu);
	}
	public static void main(String[] args){
		newdevice.time=3;
		newdevice.timeu=7;
		System.out.println(newdevice.gettimeu(10));
	}
	public double getPt() {
		return pt;
	}
	public void setPt(double pt) {
		this.pt = pt;
	}
	public double getPe() {
		return pe;
	}
	public void setPe(double pe) {
		this.pe = pe;
	}
	public double getPp() {
		return pp;
	}
	public void setPp(double pp) {
		this.pp = pp;
	}
	public boolean isB() {
		return b;
	}
	public void setB(boolean b) {
		this.b = b;
	}
	public double getWaittime() {
		return waittime;
	}
	public void setWaittime(double waittime) {
		this.waittime = waittime;
	}
	public double getTotalu() {
		return totalu;
	}
	public void setTotalu(double totalu) {
		this.totalu = totalu;
	}
	public double getExcepttime() {
		return excepttimeu;
	}
	public void setExcepttime(double excepttime) {
		this.excepttimeu = excepttime;
	}
	public double getCurrentu() {
		return currentu;
	}
	public void setCurrentu(double currentu) {
		this.currentu = currentu;
	}
	public double getIncrease() {
		return increase;
	}
	public void setIncrease(double increase) {
		this.increase = increase;
	}
	public double getCurrenttime() {
		return currenttime;
	}
	public void setCurrenttime(double currenttime) {
		this.currenttime = currenttime;
	}
	public static double getMaxtime() {
		return maxtime;
	}
	public static void setMaxtime(double maxtime) {
		newdevice.maxtime = maxtime;
	}
	
	
}
