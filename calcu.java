package utils;

public class calcu {

	static double maxt=94.3,mint=0,maxe=431.15,mine=0,maxp=1.4145,minp=0;
	static double pt=0.7,pe=0.2,ppp=0.1;
	public static double calcu(double t,double e,double p){
		double u;
		double tt,ee,pp;
		tt=t/calcu.maxt;
		ee=e/calcu.maxe;
		pp=p/calcu.maxp;
		u=tt*calcu.pt+ee*calcu.pe+pp*calcu.ppp;
		return(u);
	}
	public static void main(String[] args) {
		double d=calcu.calcu(0, 0, 0.5571);
       System.out.print(d);
	}

}
