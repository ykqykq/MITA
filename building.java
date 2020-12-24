package utils;

public class building {

	double x1,y1,x2,y2,x3,y3,x4,y4;
	public building(double x1,double y1,double x2,double y2,double x3,double y3,double x4,double y4){
		this.x1=x1;
		this.x2=x2;
		this.x3=x3;
		this.x4=x4;
		this.y1=y1;
		this.y2=y2;
		this.y3=y3;
		this.y4=y4;
	}
	public boolean judge(double x1,double y1,double x2,double y2){//有交点返回true
		
		if(building.intersection(this.x1, this.y1, this.x2, this.y2, x1, y1, x2, y2))
			return(true);
		if(building.intersection(this.x3, this.y3, this.x2, this.y2, x1, y1, x2, y2))
			return(true);
		
		if(building.intersection(this.x3, this.y3, this.x4, this.y4, x1, y1, x2, y2))
			return(true);
		if(building.intersection(this.x4, this.y4, this.x1, this.y1, x1, y1, x2, y2))
			return(true);
		
		return(false);
	}
	public static void main(String[] args){
		System.out.println(building.intersection(0, 0, 1, 1, 1, 0, 0, 1));
	}
	public static boolean intersection(double l1x1, double l1y1, double l1x2, double l1y2, 
			double l2x1, double l2y1, double l2x2, double l2y2)
			    {
			        // 快速排斥实验 首先判断两条线段在 x 以及 y 坐标的投影是否有重合。 有一个为真，则代表两线段必不可交。
			        if (Math.max(l1x1,l1x2) < Math.min(l2x1 ,l2x2)
			            || Math.max(l1y1,l1y2) < Math.min(l2y1,l2y2)
			            || Math.max(l2x1,l2x2) < Math.min(l1x1,l1x2)
			            || Math.max(l2y1,l2y2) < Math.min(l1y1,l1y2))
			        {
			            return false;
			        }
			        // 跨立实验  如果相交则矢量叉积异号或为零，大于零则不相交
			        if ((((l1x1 - l2x1) * (l2y2 - l2y1) - (l1y1 - l2y1) * (l2x2 - l2x1)) 
			              * ((l1x2 - l2x1) * (l2y2 - l2y1) - (l1y2 - l2y1) * (l2x2 - l2x1))) > 0
			            || (((l2x1 - l1x1) * (l1y2 - l1y1) - (l2y1 - l1y1) * (l1x2 - l1x1)) 
			              * ((l2x2 - l1x1) * (l1y2 - l1y1) - (l2y2 - l1y1) * (l1x2 - l1x1))) > 0)
			        {
			            return false;
			        }
			        return true;
			    }

	public double getX1() {
		return x1;
	}
	public void setX1(double x1) {
		this.x1 = x1;
	}
	public double getX2() {
		return x2;
	}
	public void setX2(double x2) {
		this.x2 = x2;
	}
	public double getX3() {
		return x3;
	}
	public void setX3(double x3) {
		this.x3 = x3;
	}
	public double getX4() {
		return x4;
	}
	public void setX4(double x4) {
		this.x4 = x4;
	}
	
}
