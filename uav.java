package utils;

import java.util.TreeMap;

public class uav {

	int id;
    double x,y,z;
	static double calculation;//����������λGHz
	double speed;
	static double communicationradius=500;
	//���˻�����
	static TreeMap<Integer, uav> uavlist=new TreeMap<Integer, uav>();//���������е����˻��߳�
	
	public uav(int id,double x,double y,double z,int c){
		this.calculation=c;
		this.y=y;
		this.x=x;
		this.z=z;
		this.id=id;
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
	
}
