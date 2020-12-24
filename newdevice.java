package utils;

public class newdevice {
	static double time;
	static double timeu;
	double percent=0;//被卸载的百分比
    static double allpercent=0.01;//每次分配多少
	double pt=0.7,pe=0.1,pp=0.2;//分别代表时间、能耗、价格的权重
	//代表每个个可以被卸载的设备里边的属性
	boolean b=false;//等于假代表此设备从未被分配
	double waittime=0;//等待时间
	double waitu=0;//等待的效用
	double totaltime=0;//完成一个任务的总时间
	double totalu=0;//完成一个任务的总效用
	double excepttimeu=0;//代表完成一个任务除了时间的总效用
	double currentu=0;//当前已分配的效用
	double increase=0;//每次的增量
	double currenttime=0;//当前计算所分配任务需要的时间
	static double maxtime=0;//记录目前的最大时间
	
	public void setincrease(){//更新增量的值
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
		this.percent=this.percent+newdevice.allpercent;//增加卸载百分比
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
