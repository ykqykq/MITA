package utils;

public class basestation {

	static int id;
	static double calculation;//计算能力单位GHz
	static double x,y,z;
	public basestation(int id,double x,double y,int c){
		this.x=x;
		this.y=y;
		this.calculation=c;
		this.id=id;
	}
	public double getCalculation() {
		return calculation;
	}
	public void setCalculation(int calculation) {
		this.calculation = calculation;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	
}
