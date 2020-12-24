package utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

public class task implements Cloneable{
	double offloaddevice=0;//卸载任务中卸载到该设备的百分比
    static int taskid;//任务ID标志位
	double offloadsize=0;//卸载大小单位KB
	double revolution=0;//需要的CPU转的次数，单位兆圈
	int origin=0;//定义该任务的起源车辆ID
	long starttime=0;//开始时间
	long endtime=0;//结束时间
	double receivesize=0;//返回数据结果的的大小
     int tasknum;//生成的任务的ID
    double processtime;//执行时间
    double ke=0;//能量占比
    double kt=0;//时间占比
    //新写的日志
    double vehicleability=0;//车的计算能力
    double v2bdistance=0;//车与基站的距离
    double havebuilding=0;//车与基站之间是否有障碍物 0  没有，1 有
    double v2udistance=0;//车与无人机距离
    double u2bdistance=0;//无人机与基站的距离
    double uavability=0;//无人机的计算能力
    double bsability=0;//基站的计算能力
    double band=0;//带宽
    double transmissionpower=0;//传输功率
    double backgroundnoise=0;//背景噪声
    double v2bchannelgain=0;//车辆对基站的信道增益
    double v2uchannelgain=0;//车辆对无人机的信道增益
    double vsende=0;//车辆发送单位耗能
    double usende=0;//无人机发送单位耗能
    double vcalcue=0;//车辆计算单位耗能
    double ucalcue=0;//无人机计算单位耗能
    decision dec;//决策部分
    double cost0;//本地成本
    double cost1;//卸载UAV成本
    double cost2;//卸载基站成本
    double cost3;//卸载到无人机再到基站
    double energyconsumption0;//本地能源消耗
    double energyconsumption1;
    double energyconsumption2;
    double energyconsumption3;
	@Override
	public  Object clone() throws CloneNotSupportedException {

		return super.clone() ;
	}
    public int getTaskid() {
		return taskid;
	}
	public double getEnergyconsumption0() {
		return energyconsumption0;
	}
	public void setEnergyconsumption0(double energyconsumption0) {
		this.energyconsumption0 = energyconsumption0;
	}

	public double getEnergyconsumption1() {
		return energyconsumption1;
	}
	public void setEnergyconsumption1(double energyconsumption1) {
		this.energyconsumption1 = energyconsumption1;
	}

	public double getEnergyconsumption2() {
		return energyconsumption2;
	}
	public void setEnergyconsumption2(double energyconsumption2) {
		this.energyconsumption2 = energyconsumption2;
	}

	public double getEnergyconsumption3() {
		return energyconsumption3;
	}

	public void setEnergyconsumption3(double energyconsumption3) {
		this.energyconsumption3 = energyconsumption3;
	}

	public double getCost0() {
		return cost0;
	}

	public void setCost0(double cost0) {
		this.cost0 = cost0;
	}

	public double getCost1() {
		return cost1;
	}

	public void setCost1(double cost1) {
		this.cost1 = cost1;
	}

	public double getCost2() {
		return cost2;
	}

	public void setCost2(double cost2) {
		this.cost2 = cost2;
	}

	public double getCost3() {
		return cost3;
	}

	public void setCost3(double cost3) {
		this.cost3 = cost3;
	}

	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}

	public long getStarttime() {
		return starttime;
	}

	public void setStarttime(long starttime) {
		this.starttime = starttime;
	}

	public long getEndtime() {
		return endtime;
	}

	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}

	public double getReceivesize() {
		return receivesize;
	}

	public void setReceivesize(double receivesize) {
		this.receivesize = receivesize;
	}

	public int getTasknum() {
		return tasknum;
	}

	public void setTasknum(int tasknum) {
		this.tasknum = tasknum;
	}

	public double getProcesstime() {
		return processtime;
	}

	public void setProcesstime(double processtime) {
		this.processtime = processtime;
	}

	public int getOffloadway() {
		return offloadway;
	}

	public void setOffloadway(int offloadway) {
		this.offloadway = offloadway;
	}

	public void setOffloadsize(double offloadsize) {
		this.offloadsize = offloadsize;
	}

	public void setRevolution(double revolution) {
		this.revolution = revolution;
	}
	int offloadway;//卸载方式 有三种方式 自己算为1，基站算为2，无人机算为3，通过无人机给基站算为4
	public task(double size,double revolution,double receivesize,int ori,double ke,double kt){
		this.offloadsize=size;
		this.revolution=revolution;
		this.origin=ori;
		this.receivesize=receivesize;
		starttime=new Date().getTime();
		tasknum=taskid++;
		this.ke=ke;
		this.kt=kt;
	}
	
	public int getOrigin() {
		return origin;
	}

	public void setOrigin(int origin) {
		this.origin = origin;
	}

	public double getOffloadsize() {
		return offloadsize;
	}
	public void setOffloadsize(int offloadsize) {
		this.offloadsize = offloadsize;
	}
	public double getRevolution() {
		return revolution;
	}
	public void setRevolution(int revolution) {
		this.revolution = revolution;
	}

	public double getKe() {
		return ke;
	}

	public void setKe(double ke) {
		this.ke = ke;
	}

	public double getKt() {
		return kt;
	}

	public void setKt(double kt) {
		this.kt = kt;
	}

	public double getVehicleability() {
		return vehicleability;
	}

	public void setVehicleability(double vehicleability) {
		this.vehicleability = vehicleability;
	}

	public double getV2bdistance() {
		return v2bdistance;
	}

	public void setV2bdistance(double v2bdistance) {
		this.v2bdistance = v2bdistance;
	}

	public double getHavebuilding() {
		return havebuilding;
	}

	public void setHavebuilding(int havebuilding) {
		this.havebuilding = havebuilding;
	}

	public double getV2udistance() {
		return v2udistance;
	}

	public void setV2udistance(double v2udistance) {
		this.v2udistance = v2udistance;
	}

	public double getU2bdistance() {
		return u2bdistance;
	}

	public void setU2bdistance(double u2bdistance) {
		this.u2bdistance = u2bdistance;
	}

	public double getUavability() {
		return uavability;
	}

	public void setUavability(double uavability) {
		this.uavability = uavability;
	}

	public double getBsability() {
		return bsability;
	}

	public void setBsability(double bsability) {
		this.bsability = bsability;
	}

	public double getBand() {
		return band;
	}

	public void setBand(double band) {
		this.band = band;
	}

	public double getTransmissionpower() {
		return transmissionpower;
	}

	public void setTransmissionpower(double transmissionpower) {
		this.transmissionpower = transmissionpower;
	}

	public double getBackgroundnoise() {
		return backgroundnoise;
	}

	public void setBackgroundnoise(double backgroundnoise) {
		this.backgroundnoise = backgroundnoise;
	}



	public double getV2bchannelgain() {
		return v2bchannelgain;
	}

	public void setV2bchannelgain(double v2bchannelgain) {
		this.v2bchannelgain = v2bchannelgain;
	}

	public double getV2uchannelgain() {
		return v2uchannelgain;
	}

	public void setV2uchannelgain(double v2uchannelgain) {
		this.v2uchannelgain = v2uchannelgain;
	}

	public double getVsende() {
		return vsende;
	}

	public void setVsende(double vsende) {
		this.vsende = vsende;
	}

	public double getUsende() {
		return usende;
	}

	public void setUsende(double usende) {
		this.usende = usende;
	}

	public double getVcalcue() {
		return vcalcue;
	}

	public void setVcalcue(double vcalcue) {
		this.vcalcue = vcalcue;
	}

	public double getUcalcue() {
		return ucalcue;
	}

	public void setUcalcue(double ucalcue) {
		this.ucalcue = ucalcue;
	}

	public void setHavebuilding(double havebuilding) {
		this.havebuilding = havebuilding;
	}

	public decision getDec() {
		return dec;
	}

	public void setDec(decision dec) {
		this.dec = dec;
	}
	public task(){
		
	}
}
