
package utils;

public class vehicle {
    int id;
	double x;
	double y;//������λ����Ϣ
	double z=0;
	double calculation;//����������λGHz
	double speed;
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
	public double getCalculation() {
		return calculation;
	}
	public void setCalculation(int calculation) {
		this.calculation = calculation;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public vehicle(int id,double x,double y,double calculation){
		this.x=x;
		this.y=y;
		this.calculation=calculation;
		this.id=id;
	}
}
