package utils;

import java.io.IOException;
import java.text.DecimalFormat;


public class vehicletaskdecision {//主要是车辆的卸载决策
    //创建建筑物
	static building b1=new building(14,24,16,324,227,327,227,27);
	static building b2=new building(513,22,516,308,723,308,726,27);
	static building b3=new building(1013,24,1013,303,1228,308,1228,24);
	static building b4=new building(1515,24,1515,650,1728,650,1728,24);
	static building b5=new building(263,363,266,621,476,618,476,413);
	static building b6=new building(763,350,763,650,973,652,976,348);
	static building b7=new building(14,718,16,1315,224,1315,227,716);
	static building b8=new building(511,692,516,1312,723,1315,723,692);
	static building b9=new building(1015,689,1013,1315,1226,1315,1228,692);
	static building b10=new building(1515,687,1515,1315,1725,1315,1725,692);
	static building b11=new building(263,1352,266,1644,476,1649,476,1357);
	static building b12=new building(263,1686,266,1980,476,1980,476,1691);
	static building b13=new building(763,1352,766,1977,976,1977,976,1357);
	static building b14=new building(1265,1354,1265,1980,1478,1980,1475,1357);
	static building b15=new building(1765,1354,1767,1980,1975,1977,1977,1357);
	
	public static task taskdecide(task ta,vehicle v)throws Exception{//返回true代表车辆自己算，false代表卸载
		
		double ecvcpu=0.001;//所有设备都是，计算1单位的数据消耗的能量----车辆  
		double ecucpu=0.001;//无人机
		double ecbcpu=0;//基站
		double esv=20;//发送单位数据耗能----车辆
		double esu=20;//无人机
		double esb=0;//基站
		double uavprice=0.00003;//单位，元/M转
		double baseprice=0.00001;
		//传输速率、等待时间和计算能力的权重
		double speedweight=3,waitweight=3,calweight=4;
		//时间、能耗、价格的权重
		double at=0.7,ae=0.2,ap=0.1;
		//接下来算所有的传输速率
		//接下来是带宽传输功率背景噪声等不变的量
		ta.backgroundnoise=transmissionspeed.n0;
		ta.band=transmissionspeed.w;
		ta.transmissionpower=transmissionspeed.pn;
		
		//下面是等待时间
		double basewait;
		double[] uavwait=new double[main.uavnum];
		double waitsize;

		//基站的百分比
		double baseresult=0;
		//每个无人机的百分比
		double[] uavresult=new double[main.uavnum];
		//下面是计算各种距离的代码用于生成日志！！！
		//车辆与基站的距离
		double v2bdistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		//车辆与所有无人机的距离
		double[] v2udistance=new double[main.uavnum];
		boolean[] v2ucan=new boolean[main.uavnum];//标记每个无人机是否和车辆可以通信
		for(int i=0;i<main.uavnum;i++){//循环计算所有无人机的距离
//			System.out.println((int)clock.clocktime/1000+"   -*/");
			v2udistance[i]=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0,
					readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).y, 300);
			System.out.print("车辆"+v.id+"x:"+main.vehiclestation[v.id][0]+" y: "+main.vehiclestation[v.id][0]);
			System.out.println("无人机"+i+"x:"+readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).x+" y:"+readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).y);
		}
		for(int i=0;i<main.uavnum;i++){
			if(v2udistance[i]>uav.communicationradius){
				v2ucan[i]=false;
				System.out.print("无人机"+i+"距离："+v2udistance[i]+"不连  ");
			}
			else{
				v2ucan[i]=true;
				System.out.print("无人机"+i+"距离："+v2udistance[i]+"连   ");
			}
		}
		System.out.println("");
		//是否车辆与基站之前有障碍物   0代表可以通信
				double havebuilding;
				if(vehicletaskdecision.basedecide(v)){
					havebuilding=0;
				}
				else{
					havebuilding=1;
				}

		//下面是信道增益
		double basechann=channelgain.getchannelgain(v2bdistance);//与基站通信的信道增益
		double[] uavchann=new double[main.uavnum];//所有无人机的信道增益
		for(int i=0;i<main.uavnum;i++){
			uavchann[i]=channelgain.getchannelgain(v2udistance[i]);
		}
		// 下面是车辆传输对每个设备的传输速率
		//车辆对基站的传输速率
		double v2btransspeed=transmissionspeed.gettransmissionspeed(ta.origin,0,0);
		//车辆对无人机的传输速率
		double[] v2utransspeed=new double[main.uavnum];
		for(int i=0;i<main.uavnum;i++){
			v2utransspeed[i]=transmissionspeed.gettransmissionspeed(ta.origin,1,i);
		}

		//计算基站的等待时间
		basewait=basestationthread.getwait();
		//计算无人机的等待时间
		for(int i=0;i<main.uavnum;i++){
			uavwait[i]=uavthread.getwait(i);
		}

		//基站的特征值
//		double bscharacter=speedweight*v2btransspeed+waitweight*basewait+calweight*basestation.calculation;
//		//每个无人机的特征值
//		double[] uavcharacter=new double[main.uavnum];
//		for(int i=0;i<main.uavnum;i++){
////			System.out.print(i+"传输速度"+v2utransspeed[i]+" 等待时间"+uavwait[i]+" 计算能力"+uav.calculation);
////			System.out.println("");
//			uavcharacter[i]=speedweight*v2utransspeed[i]+waitweight*uavwait[i]+calweight*uav.calculation;
//		}
//		//有效特征值相加
//		double totalcharacter=0;
//		if(havebuilding==0){
//			totalcharacter=totalcharacter+bscharacter;
//		}
//		for(int i=0;i<main.uavnum;i++){
//			if(v2ucan[i]){
//				totalcharacter=totalcharacter+uavcharacter[i];
//			}
//		}
		//车辆本地计算的比例，和卸载的比例
		double vmake=0;//本地计算
		double v2xmake=1;
//		System.out.print("每个设备被卸载的百分比：基站："+baseresult);
		//每个无人机卸载的百分比
//		for(int i=0;i<main.uavnum;i++){
//			if(v2ucan[i]){
//				uavresult[i]=uavcharacter[i]/totalcharacter;
////				System.out.print("  无人机："+i+":"+uavresult[i]);
//			}
//			else{
//				uavresult[i]=0;
////				System.out.print("  无人机："+i+":"+uavresult[i]);
//			}
//		}
		
		
		//接下来计算效用
		//先算每个设备的总时间
		//车辆本地计算的总时间
		double totalutility=999999;
		decision dec=new decision();
        String data="";
        //新的分配方案
        newdevice[] dd=new newdevice[30]; 
        //设备0始终代表车辆自己
        dd[0]=new newdevice();
        dd[0].waittime=0;
        dd[0].waitu=0;
        dd[0].totaltime=ta.revolution/(v.calculation*1000);
        dd[0].totalu=calcu.calcu(dd[0].totaltime, ta.revolution*ecvcpu, 0);
        dd[0].excepttimeu=dd[0].totalu-calcu.calcu(dd[0].totaltime, 0, 0);
        newdevice.time=dd[0].totaltime;
        newdevice.timeu=dd[0].totalu-dd[0].excepttimeu;
        int connect=1;
//        havebuilding=1;
//        for (int i = 0; i < main.uavnum; i++) {
//			v2ucan[i]=false;
//		}
		if(havebuilding==0){
			dd[connect]=new newdevice();
			dd[connect].waittime=basewait;
			dd[connect].waitu=calcu.calcu(dd[connect].waittime, 0.0, 0.0);
			dd[connect].totaltime=(ta.revolution)/(basestation.calculation*1000)+(ta.offloadsize/1024)/v2btransspeed;
			dd[connect].totalu=calcu.calcu(dd[connect].waittime, ta.offloadsize*esv/1024, ta.revolution*baseprice);
			dd[connect].excepttimeu=dd[connect].totalu-calcu.calcu(dd[connect].totaltime, 0, 0);
			connect++;
		}
		for(int i=0;i<main.uavnum;i++){
			if(v2ucan[i]){
			dd[connect]=new newdevice();
			dd[connect].waittime=uavwait[i];
			dd[connect].waitu=calcu.calcu(dd[connect].waittime, 0.0, 0.0);
			dd[connect].totaltime=uavwait[i]+(ta.revolution)/(uav.calculation*1000)+(ta.offloadsize/1024)/v2utransspeed[i];
			dd[connect].totalu=calcu.calcu(dd[connect].totaltime, ta.offloadsize*esv/1024+ta.revolution*ecucpu, ta.revolution*uavprice);
			dd[connect].excepttimeu=dd[connect].totalu-calcu.calcu(dd[connect].totaltime, 0, 0);
			connect++;
		}
		}
		for(int j=0;j<connect;j++){
			String s="等待时间："+dd[j].waittime+"  等待效用："+dd[j].waitu+"  总时间："+dd[j].totaltime+"  总效用："+dd[j].totalu+"  除时间效用："+dd[j].excepttimeu;
			printdata.method("D:\\xiaoyong.txt", s);
		}
		printdata.method("D:\\xiaoyong.txt", "-------------------------------------");
        double[] judge=distributeforu.distribute(connect, dd);
        int getnum=0;//记录获取到结果的设备数
        vmake=judge[getnum];//车辆的结果
        System.out.println("车辆自己的结果："+vmake);
        getnum++;
        if(havebuilding==0){
        	baseresult=judge[getnum];//基站的结果
        	System.out.println("基站结果："+baseresult);
        	getnum++;
        }
        for(int i=0;i<main.uavnum;i++){
        	if(v2ucan[i]){
        		uavresult[i]=judge[getnum];//无人机的结果
        		System.out.println("  无人机"+i+"结果："+uavresult[i]);
        		getnum++;
        	}
        }
        
		double vtime=(ta.revolution*vmake)/(v.calculation*1000);
		//基站接收和计算的总时间  等待时间+计算时间+车辆发送任务时间
		double basetime=basewait+(ta.revolution*baseresult)/(basestation.calculation*1000)+(ta.offloadsize*baseresult/1024)/v2btransspeed;
       if(baseresult==0){
    	   basetime=0;
       }
       System.out.println("给基站发送大小："+ta.offloadsize*baseresult/1024+"  给基站发送速度："+v2btransspeed);
       System.out.println("基站计算时间："+basetime+"  基站等待时间："+basewait+"基站计算时间："+(ta.revolution*baseresult)/(basestation.calculation*1000)+"  车辆发送时间："+(ta.offloadsize*baseresult/1000)/v2btransspeed);
		//每个无人机的接收和计算的总时间 等待时间+计算时间+车辆发送任务时间
		double[] uavtime=new double[main.uavnum];
		
		for(int i=0;i<main.uavnum;i++){
			uavtime[i]=uavwait[i]+(ta.revolution*uavresult[i])/(uav.calculation*1000)+(ta.offloadsize*uavresult[i]/1024)/v2utransspeed[i];
			if(uavresult[i]==0){
				uavtime[i]=0;
			}
		}
		double maxtime=0;
		maxtime=vtime;
		
		for(int i=0;i<main.uavnum;i++){//选取等待时间最大的无人机时间
			if(v2ucan[i]){
				if(uavtime[i]>maxtime){
					maxtime=uavtime[i];
				}
			}
		}
		if(havebuilding==0){//代表基站可以通信
			if(basetime>maxtime){
				maxtime=basetime;
			}
		}//选取本地和被卸载设备里最大的计算时间，作为有效时间
		//本地计算时间
		if(vtime>maxtime){
			maxtime=vtime;
		}
//		System.out.println("本地计算时间："+localtime+"  基站计算时间："+basetime);
		//下面计算能耗
        //车辆计算能耗和发送能耗
		double vcale=ta.revolution*vmake*ecvcpu;//车辆本地计算能耗
		double vsende=ta.offloadsize*(1-vmake)*esv/1024;//车辆发送能耗
		double ucale=(ta.revolution*(1-vmake-baseresult))*ecucpu;//无人机计算能耗
//		System.out.println("车辆本地计算能耗："+vcale+"  车辆发送能耗："+vsende+"   无人机计算能耗："+ucale);
		//总能耗
		double totale=vcale+vsende+ucale;
		//下面计算总价格
		//基站计算的价格
		double totalbaseprice=ta.revolution*baseresult*baseprice;
		//无人机计算的价格
		double totaluavprice=ta.revolution*(1-vmake-baseresult)*uavprice;
		//总价格
		double totalprice=totalbaseprice+totaluavprice;
//		System.out.println("总价格为："+baseprice);
		//总效用
		totalutility=calcu.calcu(maxtime, totale, totalprice);
		
		dec.totalprice=totalprice;
		dec.totaltime=maxtime;
		dec.vmake=vmake;
		dec.totale=totale;
		dec.totalutility=totalutility;
//		dec.offloadpersent=v2xmake;
		dec.uavpersent=uavresult;
		dec.basepersent=baseresult;
//		while(vmake<1){
////			vmake=vmake+0.01;
////			
////			v2xmake=1-vmake;
////			if(v2xmake<0){
////				v2xmake=0;
////			}
////			System.out.println("出来了"+v2xmake);
//			device[] d=new device[20];
//			int connection=0;//可以和无人机相连的设备数量
//			for(int i=0;i<20;i++){//初始化这个设备数组
//				d[i]=new device(i);
//			}
//			
//	        //基站卸载的百分比
//			if(havebuilding==0){
//				d[connection].waittime=basewait;
//				d[connection].finishtime=((ta.revolution/1000)*v2xmake)/basestation.calculation;
//				connection++;
//			}
//	        for(int i=0;i<main.uavnum;i++){
//	        	if(v2ucan[i]){
//	        		d[connection].waittime=uavwait[i];
//	        		d[connection].finishtime=((ta.revolution/1000)*v2xmake)/uav.calculation;
//	        		d[connection].deviceid=i;
//	        		connection++;
//	        	}
//	        }
//	       
//	        if(connection>0){
//	        System.out.println("刘羽霏"+connection+" "+d[0].finishtime+" "+d[0].waittime);
//	        System.out.println("总卸载大小为："+v2xmake);
//	       
//	        double[] judge= taskdistribution.distributionmin(connection, d);//返回的任务分配结果
//	        
////	        System.out.println("出来了"+175);
//	        //获取任务分配结果
//	        int getnum=0;//记录获取到结果的设备数
//	        if(havebuilding==0){
//	        	baseresult=judge[getnum];
//	        }
//	        
//	        for(int i=0;i<main.uavnum;i++){
//	        	if(v2ucan[i]){
//	        		uavresult[i]=judge[getnum];
//	        		getnum++;
//	        	}
//	        }
//	        }
//	        else{
//		        if(havebuilding==0){
//		        	baseresult=0;
//		        }
//		        
//		        for(int i=0;i<main.uavnum;i++){
//		        	if(v2ucan[i]){
//		        		uavresult[i]=0;
//		        	}
//		        }
////		        vmake=1;
////		        v2xmake=0;
//	        }
//			double vtime=(ta.revolution*vmake)/(v.calculation*1000);
//			//基站接收和计算的总时间  等待时间+计算时间+车辆发送任务时间
//			double basetime=basewait+(ta.revolution*baseresult*v2xmake)/(basestation.calculation*1000)+(ta.offloadsize*v2xmake*baseresult/1024)/v2btransspeed;
//	       if(baseresult==0){
//	    	   basetime=0;
//	       }
//	       System.out.println("给基站发送大小："+ta.offloadsize*v2xmake*baseresult/1024+"  给基站发送速度："+v2btransspeed);
//	       System.out.println("基站计算时间："+basetime+"  基站等待时间："+basewait+"基站计算时间："+(ta.revolution*baseresult*v2xmake)/(basestation.calculation*1000)+"  车辆发送时间："+(ta.offloadsize*v2xmake*baseresult/1000)/v2btransspeed);
//			//每个无人机的接收和计算的总时间 等待时间+计算时间+车辆发送任务时间
//			double[] uavtime=new double[main.uavnum];
//			
//			for(int i=0;i<main.uavnum;i++){
//				uavtime[i]=uavwait[i]+(ta.revolution*uavresult[i]*v2xmake)/(uav.calculation*1000)+(ta.offloadsize*v2xmake*uavresult[i]/1024)/v2utransspeed[i];
//				if(uavresult[i]==0){
//					uavtime[i]=0;
//				}
//			}
//			double maxtime=0;
//			maxtime=vtime;
//			
//			for(int i=0;i<main.uavnum;i++){//选取等待时间最大的无人机时间
//				if(v2ucan[i]){
//					if(uavtime[i]>maxtime){
//						maxtime=uavtime[i];
//					}
//				}
//			}
//			if(havebuilding==0){//代表基站可以通信
//				if(basetime>maxtime){
//					maxtime=basetime;
//				}
//			}//选取本地和被卸载设备里最大的计算时间，作为有效时间
//			//本地计算时间
//			double localtime=(ta.revolution*vmake/1000)/v.calculation;
//			if(localtime>maxtime){
//				maxtime=localtime;
//			}
////			System.out.println("本地计算时间："+localtime+"  基站计算时间："+basetime);
//			//下面计算能耗
//	        //车辆计算能耗和发送能耗
//			double vcale=ta.revolution*vmake*ecvcpu;//车辆本地计算能耗
//			double vsende=ta.offloadsize*v2xmake*esv/1024;//车辆发送能耗
//			double ucale=(ta.revolution*v2xmake-ta.revolution*v2xmake*baseresult)*ecucpu;//无人机计算能耗
////			System.out.println("车辆本地计算能耗："+vcale+"  车辆发送能耗："+vsende+"   无人机计算能耗："+ucale);
//			//总能耗
//			double totale=vcale+vsende+ucale;
//			//下面计算总价格
//			//基站计算的价格
//			double totalbaseprice=ta.revolution*v2xmake*baseresult*baseprice;
//			//无人机计算的价格
//			double totaluavprice=ta.revolution*v2xmake*(1-baseresult)*uavprice;
//			//总价格
//			double totalprice=totalbaseprice+totaluavprice;
////			System.out.println("总价格为："+baseprice);
//			//总效用
//			totalutility=at*((maxtime-0.2652)/(37.14-0.2652))+ae*((totale-18.57)/(227.56-18.57))+ap*((totalprice-0)/(0.5571));
//			if(totalutility<dec.totalutility){
//				dec.totalprice=totalprice;
//				dec.totaltime=maxtime;
//				dec.vmake=vmake;
//				dec.totale=totale;
//				dec.totalutility=totalutility;
//				dec.offloadpersent=v2xmake;
//				dec.uavpersent=uavresult;
//				dec.basepersent=baseresult;
//			}
//			System.out.println("总效用为："+totalutility+"   总价格："+totalprice+"   总时间："+maxtime+"   总能耗："+totale);
//			
////		printdata.method("D:\\kaiqidata.txt", Double.parseDouble(String.format("%.3f", v2xmake))+" "+Double.parseDouble(String.format("%.3f", totalutility)));
////		
////		System.out.println("出来了*****************"+vmake);
//		}
//		System.out.println("出来了*****************");
		//做出了最优决策。然后分别给各个设备分发任务
		task t1=(task)ta.clone();//一个任务的副本
		//给基站分发任务，如果能够相连的话
		data="";
		
		data=data+ta.tasknum+" "+ta.origin+" "+String.format("%.3f", dec.vmake)+" "+String.format("%.3f", dec.basepersent)+" "+String.format("%.3f", dec.totale)+" "+String.format("%.3f", dec.totalutility)+" "+clock.clocktime+" "+(String.format("%.3f", clock.clocktime+dec.totaltime*1000)+" ");
		for(int i=0;i<main.uavnum; i++) {
			data=data+dec.uavpersent[i]+" ";
		}
		printdata.method("D:\\kaiqidata.txt", data);
		if(havebuilding==0&&baseresult!=0){
			t1=(task)ta.clone();
//			t1.offloadpercent=dec.offloadpersent;
			System.out.println("基站"+"添加任务:"+t1.tasknum);
			t1.offloaddevice=dec.basepersent;
			basestationthread.tasklist.put(ta.tasknum, t1);
			vehiclethread.havetask[v.id][0]=true;
		}
		for(int i=0;i<main.uavnum;i++){
			if(v2ucan[i]&&uavresult[i]!=0){
				System.out.println("无人机"+i+"添加任务:"+t1.tasknum);
				t1=(task)ta.clone();
//				t1.offloadpercent=dec.offloadpersent;
				t1.offloaddevice=dec.uavpersent[i];
				uavthread.tasktreeMapArray.get(i).put(t1.tasknum, t1);
				vehiclethread.havetask[v.id][main.basenum+i]=true;
			}
		}
		t1=(task)ta.clone();
		t1.offloaddevice=dec.vmake;
//		System.out.println("决策为：卸载比例为"+dec.offloadpersent+"  总效用为："+dec.totalutility);
		System.out.println("基站卸载比例："+dec.basepersent);
		return(t1);
		//无人机和基站的距离
//		double u2bdistance=channelgain.getdistance(main.u.getX(), main.u.getY(), main.u.getZ(), basestation.x, basestation.y, basestation.z);
//		
//		ta.v2bdistance=v2bdistance;
//		ta.u2bdistance=u2bdistance;
//		ta.havebuilding=havebuilding;
		
		
        //格式控制
//		DecimalFormat df=new DecimalFormat("#0.000000");
//		DecimalFormat df1=new DecimalFormat("#0.0000000000000");
//		//下面是信道增益
//		double distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
//		double chann=channelgain.getchannelgain(distance);
//		ta.v2bchannelgain=chann;//车辆对基站
////		distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, uav.x, uav.y, uav.z);
//		chann=channelgain.getchannelgain(distance);
//		ta.v2uchannelgain=chann;//车辆对无人机
//		//接下来是单位发送耗能和单位计算耗能
//		ta.vsende=esv;//车辆发送单位耗能
//		ta.usende=esu;
//		ta.vcalcue=ecvcpu;
//		ta.ucalcue=ecucpu;
//		
//
//		//各种时间时间时间时间时间时间时间时间时间时间时间时间
//		double sendtimetobase=(ta.offloadsize)/(transmissionspeed.gettransmissionspeed(ta.origin,0,0)*1024);
//		double calculation=(ta.revolution)/(50*1024)+800*basestationthread.tasklist.size()/(main.b.calculation*1024);//计算自己的时间和计算列表里的任务的时间
//		double receivetimebase=(ta.receivesize)/(transmissionspeed.gettransmissionspeed(ta.origin, 0,0)*1024);
//		double receivetimebtou=ta.receivesize/(20*1024);
//		double totaltimebase=sendtimetobase+calculation+receivetimebase;
//		double self=ta.revolution/(v.calculation*1024);
//		
//		//在无人机通信范围内但是不和基站直接相连：1 在无人机上算的总时间，2 在基站上算的总时间
//		//1 先算在无人机上算的,不管是给无人机还是基站都不考虑距离对传输时间的影响
//		double vtouspeed=transmissionspeed.gettransmissionspeed(ta.origin,1,1);
//		double sendtimetouav=(ta.offloadsize)/(vtouspeed*1024);
//		double receivetimeuav=(ta.receivesize)/(vtouspeed*1024);
//		double calculationtouav=(ta.revolution)/(main.u.calculation*1024)+(((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*(uavthread.tasktreeMapArray.size()+1))/(main.u.calculation*1024);
//		double totaltouav=sendtimetouav+calculationtouav+receivetimeuav;
//		//2在算卸载去基站的无人机和基站的传输速率是20Mb
//		double uavtobase=(ta.offloadsize)/(20*1024);
//		double sendtobase=uavtobase+sendtimetouav;
//		double calculationtobase=(ta.revolution)/(main.b.calculation*1024)+((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*basestationthread.tasklist.size()/(main.b.calculation*1024);
//		double receivetobase=receivetimebtou+receivetimeuav;
//		double totaltobase=sendtobase+calculationtobase+receivetobase;
//		//System.out.println("自己算"+self);
//		//System.out.println("无人机算"+totaltouav);
//		//System.out.println("基站算"+totaltobase);
//		
//		//各种能耗能耗能耗能耗能耗能耗能耗能耗能耗能耗能耗
//		double energycv=(ta.revolution*ecvcpu)/1000;//车辆自己计算任务耗费的能量
//		double energysv=(ta.offloadsize/1024)*esv;//车辆发送该任务需要的能量
//		double energycu=(ta.revolution*ecucpu)/1000;//无人机计算该任务需要的能量
//		double energyrsu=(ta.receivesize/1024)*esu;//无人机返回给车辆结果的能耗
//		double energysu=(ta.offloadsize/1024)*esu;//无人机发送该任务需要的能耗
//		double energycb=ta.revolution*ecbcpu;//基站计算该任务需要的能耗========不需要
//		
//		//车辆自己计算的能耗
//		double totalv=energycv;
//		//无人机计算的能耗
//		double totalvtou=energysv+energycu+energyrsu;
//		//基站计算的能耗
//		double totaltobs=energysv;
//		//无人机中继基站算的能耗
//		double totaltoutobs=energysv+energysu+energyrsu;
//		
//		//总的效用计算
//		//车辆自己计算的总效用
//		double totalutilityv =main.ke*((totalv-main.mine)/(main.maxe-main.mine))+main.kt*((self-main.mint)/(main.maxt-main.mint));
//        //无人机算的总效用
//		double totalutilitytou =main.ke*((totalvtou-main.mine)/(main.maxe-main.mine))+main.kt*((totaltouav-main.mint)/(main.maxt-main.mint));
//        //基站计算的总效用
//		double totalutilitytobs =main.ke*((totaltobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltimebase-main.mint)/(main.maxt-main.mint));
//        //无人机中继的基站计算的总效用
//		double totalutilitytoutobs =main.ke*((totaltoutobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltobase-main.mint)/(main.maxt-main.mint));
//       
//		boolean touav=true;
//		boolean tobs=true;
//		if(vehicletaskdecision.basedecide(v)){
//			tobs=true;
//			System.out.println("可以与基站直接通信，交付基站总时间为："+totaltimebase);
//			System.out.println("sendtimetobase:"+df.format(sendtimetobase)+"   calculation:"+df.format(calculation)+"   receivetimebase:"+df.format(receivetimebase));
//			//System.out.println("车辆给基站的传输速度："+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 0)));
//		}
//		else{
//			tobs=false;
//			System.out.println("不可以与基站直接通信");
//		}//此处无人机是三维的而车辆坐标是二维的，车辆的Z坐标默认为0
//		if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){
//			touav=false;
//			System.out.println("不在无人机通信范围");
//		}
//		else{
//			touav=true;
//			System.out.println("在无人机通信范围，卸载到无人机时间："+df.format(totaltouav)+"   通过无人机卸载到基站时间"+df.format(totaltobase));
//		}
//		
		
//		System.out.println("任务大小："+ta.offloadsize+"  需要转速："+ta.revolution);
//		System.out.println("车辆发无人机的传输速度："+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 1)));
//		System.out.println("无人机算：  车辆发送给无人机时间："+df.format(sendtimetouav)+"  无人机返回给车辆时间："+df.format(receivetimeuav));
//		System.out.println("无人机中继基站算：  发送给无人机的时间："+df.format(sendtimetouav)+"  无人机给基站的时间："+df.format(uavtobase)+"   发送总时间："+df.format(sendtobase)+"   基站返回给无人机："+df.format(receivetimebtou)+"   无人机返回给基站"+df.format(receivetimeuav));
//		System.out.println("自己算时间："+df.format(self)+"  无人机算时间："+df.format(totaltouav)+"   通过无人机给基站的时间："+df.format(totaltobase));
//		System.out.println("车辆自己算的能耗："+totalv);
//		System.out.println("车辆卸载到无人机的能耗："+totalvtou);
//		System.out.println("车辆直接卸载到基站的能耗："+totaltobs);
//		System.out.println("车辆以无人机为中继卸载到基站的能耗："+totaltoutobs);
//		System.out.println("车辆自己算的总效用："+totalutilityv);
//		System.out.println("车辆卸载到无人机的总效用："+totalutilitytou);
//		System.out.println("车辆卸载到基站的总效用："+totalutilitytobs);
//		System.out.println("车辆以无人机为中继卸载到基站算的总效用："+totalutilitytoutobs);
//		System.out.println("当前无人机列表大小为："+uavthread.tasktreeMapArray.size());
//		System.out.println("无人机计算总时间："+calculationtouav);
//		System.out.println("任务编号："+ta.taskid);
		
//		double[] total=new double[4];
//		total[0]=totalutilityv;
//		total[1]=totalutilitytou;
//		total[2]=totalutilitytobs;
//		total[3]=totalutilitytoutobs;
//		int[] totalsign=new int[4];
//		totalsign[0]=0;
//		totalsign[1]=1;
//		totalsign[2]=2;
//		totalsign[3]=3;
//		double[] totalsendtime=new double[4];
//		totalsendtime[0]=0;
//		totalsendtime[1]=sendtimetouav;
//		totalsendtime[2]=sendtimetobase;
//		totalsendtime[3]=sendtobase;
//		double n;
//		int n1;
//		for(int i =0;i<3;i++){
//			for(int j=0;j<3;j++){
//				if(total[j]>total[j+1]){
//					
//					n=total[j];
//					total[j]=total[j+1];
//					total[j+1]=n;
//					n1=totalsign[j];
//					totalsign[j]=totalsign[j+1];
//					totalsign[j+1]=n1;
//					
//				}
//				
//			}
//		}
//	    tobs=false;//使车辆和基站与无人机都无法相连@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
////		touav=false;//
//		ta.cost0=totalutilityv;
//		ta.cost1=totalutilitytou;
//		ta.cost2=totalutilitytobs;
//		ta.cost3=totalutilitytoutobs;
//		ta.energyconsumption0=totalv;
//		ta.energyconsumption1=totalvtou;
//		ta.energyconsumption2=totaltobs;
//		ta.energyconsumption3=totaltoutobs;
//		
//		String restring=df.format(ta.getOffloadsize())+"   "+df.format(ta.getRevolution())+"   "+df.format(ta.getKt())+"   "+df.format(ta.getKe())+"   "+df.format(ta.getVehicleability())+"   "+df.format(ta.getUavability())+"   "+df.format(ta.getBsability())+"   "+df.format(ta.getV2bdistance())+"   "+df.format(ta.getHavebuilding())+"   "+df.format(ta.getV2udistance())+"   "+df.format(ta.getV2bdistance())+"   "+df.format(ta.getBand())+"   "+df.format(ta.getTransmissionpower())+"   "+df.format(ta.getBackgroundnoise())+"   "+df.format(ta.getV2bchannelgain())+"   "+df.format(ta.getV2uchannelgain())+"   "+df.format(ta.getUsende())+"   "+df.format(ta.getVsende())+"   "+df.format(ta.getUcalcue())+"   "+df.format(ta.getVcalcue());
//		System.out.print(restring);
//		String judge=usepython.print(restring);//用深度学习得出来的决策结果
//		int ajudge=0;
//		System.out.print(judge);
//		ajudge=Integer.parseInt(judge);
//        if(ajudge==2){
//		if(tobs==false){
//			ajudge=0;	
//		}
//}
//        else if(ajudge==1||ajudge==3){
//        	if(touav==false){
//        		ajudge=0;
//        	}
//        }
//        for(int i=0;i<4;i++){
//        	if(totalsign[i]==ajudge){
//        		ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));	
//        		return(ta);
//        	}
//        }
//        ta.setDec(new decision(totalsign[ajudge],total[ajudge],totalsendtime[ajudge]));	
//		return(ta);
		
//		if(tobs){//与基站可以直接通信
//			if(touav){//可以与无人机通信
//				//for(int i=0;i<4;i++) {//修改为无人机不中继
//					//if(totalsign[i]!=3) {
//						ta.setDec(new decision(totalsign[0],total[0],totalsendtime[0]));	
//						return(ta);
//					}
//				
//			
//				//}
//				
//				
//		//	}
//			else{//不可以与无人机通信
//				for(int i=0;i<4;i++){
//					if(totalsign[i]!=1&&totalsign[i]!=3){
//						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
//						return(ta);
//					}
//				}
//			}
//				}
//			
//		else{//不可以与基站直接通信
//			if(touav){//可以与无人机通信
//				for(int i=0; i<4;i++) {
//					if(totalsign[i]!=2) {//修改为无人机不能中继
//						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
//						return(ta);
//					}
//				}
//}
//			else{//不可以与无人机通信
//				for(int i=0;i<4;i++){
//					if(totalsign[i]!=2&&totalsign[i]!=3&&totalsign[i]!=1){
//						//System.out.println(totalsign[i]+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
//						return(ta);
//					}
//
//				}
//			}
//		}
		
//		System.out.println("********************************决策出错*****************************************************************");
//		return(ta);
//		if(vehicletaskdecision.basedecide(v)){//可以与基站通信                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
//			//
//			if(self>totaltimebase){
//				ta.setDec(new decision(2,totaltimebase,sendtimetobase));
//				return(ta);//返回假，把任务卸载到基站，并把任务挂载到基站的处理队列中
//			}
//			else{
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);//返回真，车辆自己计算
//		}
//		}
//		else{//不可以与基站通信，继续判断卸载到哪里
//			//首先判断与无人机是否能通信
//			if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){//不在无人机通信范围内，自己算
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);
//			}
//			else{//在无人机通信范围内，判断决策
//				if(self<=totaltouav&&self<=totaltobase){//自己计算
//					ta.setDec(new decision(0,self,0.0));
//					return(ta);
//				}
//				else if(totaltouav<=self&&totaltouav<=totaltobase){//卸载到无人机
//                    ta.setDec(new decision(1,totaltouav,sendtimetouav));
//					return(ta);
//				}
//				else{//卸载到基站
//                    ta.setDec(new decision(3,totaltobase,sendtobase));
//					return(ta);
//				}
//			}
//			
//		}
		
	}
	public static boolean basedecide(vehicle v){//判断车辆与建筑物之间是否有障碍物
		if(b1.judge(basestation.x, basestation.y, v.x, v.y)||
				b2.judge(basestation.x, basestation.y, v.x, v.y)||
				b3.judge(basestation.x, basestation.y, v.x, v.y)||
				b4.judge(basestation.x, basestation.y, v.x, v.y)||
				b5.judge(basestation.x, basestation.y, v.x, v.y)||
				b6.judge(basestation.x, basestation.y, v.x, v.y)||
				b7.judge(basestation.x, basestation.y, v.x, v.y)||
				b8.judge(basestation.x, basestation.y, v.x, v.y)||
				b9.judge(basestation.x, basestation.y, v.x, v.y)||
				b10.judge(basestation.x, basestation.y, v.x, v.y)||
				b11.judge(basestation.x, basestation.y, v.x, v.y)||
				b12.judge(basestation.x, basestation.y, v.x, v.y)||
				b13.judge(basestation.x, basestation.y, v.x, v.y)||
				b14.judge(basestation.x, basestation.y, v.x, v.y)||
				b15.judge(basestation.x, basestation.y, v.x, v.y)
				)
			return(false);//只要有一个建筑物真就代表有障碍物，就返回假代表无法直接相连
		else
			return(true);//所欲建筑物都假代表都不相交，可以直接相连
	}
	
}
