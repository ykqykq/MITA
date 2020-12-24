package utils;

public class channelgain {

	 static double p=2;//衰弱指数
	static double v=2;//衰减系数
	static double A=30;//信道增益常数
	
	public static double getchannelgain(double distance){
		double chann=0;
		chann=A*p*Math.pow(distance, -v);
		return(chann);
	}
	public static double getdistance(double x1,double y1,double z1,double x2,double y2,double z2){
		double distance;
		distance=Math.sqrt(Math.pow((x1-x2),2)+Math.pow((y1-y2),2)+Math.pow((z1-z2), 2));
		return(distance);
		
	}
	//public static double getdistancebyid(vehicle )
	public static void main(String[] args){
		channelgain.getdistance(0, 0, 0, 1, 1, 0);
		System.out.print(channelgain.getdistance(0, 0, 0, 1, 1, 0));
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}
	
}
