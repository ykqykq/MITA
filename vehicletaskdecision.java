package utils;

import java.io.IOException;
import java.text.DecimalFormat;


public class vehicletaskdecision {//��Ҫ�ǳ�����ж�ؾ���
    //����������
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
	
	public static task taskdecide(task ta,vehicle v)throws Exception{//����true�������Լ��㣬false����ж��
		
		double ecvcpu=0.001;//�����豸���ǣ�����1��λ���������ĵ�����----����  
		double ecucpu=0.001;//���˻�
		double ecbcpu=0;//��վ
		double esv=20;//���͵�λ���ݺ���----����
		double esu=20;//���˻�
		double esb=0;//��վ
		double uavprice=0.00003;//��λ��Ԫ/Mת
		double baseprice=0.00001;
		//�������ʡ��ȴ�ʱ��ͼ���������Ȩ��
		double speedweight=3,waitweight=3,calweight=4;
		//ʱ�䡢�ܺġ��۸��Ȩ��
		double at=0.7,ae=0.2,ap=0.1;
		//�����������еĴ�������
		//�������Ǵ����书�ʱ��������Ȳ������
		ta.backgroundnoise=transmissionspeed.n0;
		ta.band=transmissionspeed.w;
		ta.transmissionpower=transmissionspeed.pn;
		
		//�����ǵȴ�ʱ��
		double basewait;
		double[] uavwait=new double[main.uavnum];
		double waitsize;

		//��վ�İٷֱ�
		double baseresult=0;
		//ÿ�����˻��İٷֱ�
		double[] uavresult=new double[main.uavnum];
		//�����Ǽ�����־���Ĵ�������������־������
		//�������վ�ľ���
		double v2bdistance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
		//�������������˻��ľ���
		double[] v2udistance=new double[main.uavnum];
		boolean[] v2ucan=new boolean[main.uavnum];//���ÿ�����˻��Ƿ�ͳ�������ͨ��
		for(int i=0;i<main.uavnum;i++){//ѭ�������������˻��ľ���
//			System.out.println((int)clock.clocktime/1000+"   -*/");
			v2udistance[i]=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0,
					readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).x, readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).y, 300);
			System.out.print("����"+v.id+"x:"+main.vehiclestation[v.id][0]+" y: "+main.vehiclestation[v.id][0]);
			System.out.println("���˻�"+i+"x:"+readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).x+" y:"+readtraffic.uavtreeMapArray.get(i).get((int)clock.clocktime/1000+1).y);
		}
		for(int i=0;i<main.uavnum;i++){
			if(v2udistance[i]>uav.communicationradius){
				v2ucan[i]=false;
				System.out.print("���˻�"+i+"���룺"+v2udistance[i]+"����  ");
			}
			else{
				v2ucan[i]=true;
				System.out.print("���˻�"+i+"���룺"+v2udistance[i]+"��   ");
			}
		}
		System.out.println("");
		//�Ƿ������վ֮ǰ���ϰ���   0�������ͨ��
				double havebuilding;
				if(vehicletaskdecision.basedecide(v)){
					havebuilding=0;
				}
				else{
					havebuilding=1;
				}

		//�������ŵ�����
		double basechann=channelgain.getchannelgain(v2bdistance);//���վͨ�ŵ��ŵ�����
		double[] uavchann=new double[main.uavnum];//�������˻����ŵ�����
		for(int i=0;i<main.uavnum;i++){
			uavchann[i]=channelgain.getchannelgain(v2udistance[i]);
		}
		// �����ǳ��������ÿ���豸�Ĵ�������
		//�����Ի�վ�Ĵ�������
		double v2btransspeed=transmissionspeed.gettransmissionspeed(ta.origin,0,0);
		//���������˻��Ĵ�������
		double[] v2utransspeed=new double[main.uavnum];
		for(int i=0;i<main.uavnum;i++){
			v2utransspeed[i]=transmissionspeed.gettransmissionspeed(ta.origin,1,i);
		}

		//�����վ�ĵȴ�ʱ��
		basewait=basestationthread.getwait();
		//�������˻��ĵȴ�ʱ��
		for(int i=0;i<main.uavnum;i++){
			uavwait[i]=uavthread.getwait(i);
		}

		//��վ������ֵ
//		double bscharacter=speedweight*v2btransspeed+waitweight*basewait+calweight*basestation.calculation;
//		//ÿ�����˻�������ֵ
//		double[] uavcharacter=new double[main.uavnum];
//		for(int i=0;i<main.uavnum;i++){
////			System.out.print(i+"�����ٶ�"+v2utransspeed[i]+" �ȴ�ʱ��"+uavwait[i]+" ��������"+uav.calculation);
////			System.out.println("");
//			uavcharacter[i]=speedweight*v2utransspeed[i]+waitweight*uavwait[i]+calweight*uav.calculation;
//		}
//		//��Ч����ֵ���
//		double totalcharacter=0;
//		if(havebuilding==0){
//			totalcharacter=totalcharacter+bscharacter;
//		}
//		for(int i=0;i<main.uavnum;i++){
//			if(v2ucan[i]){
//				totalcharacter=totalcharacter+uavcharacter[i];
//			}
//		}
		//�������ؼ���ı�������ж�صı���
		double vmake=0;//���ؼ���
		double v2xmake=1;
//		System.out.print("ÿ���豸��ж�صİٷֱȣ���վ��"+baseresult);
		//ÿ�����˻�ж�صİٷֱ�
//		for(int i=0;i<main.uavnum;i++){
//			if(v2ucan[i]){
//				uavresult[i]=uavcharacter[i]/totalcharacter;
////				System.out.print("  ���˻���"+i+":"+uavresult[i]);
//			}
//			else{
//				uavresult[i]=0;
////				System.out.print("  ���˻���"+i+":"+uavresult[i]);
//			}
//		}
		
		
		//����������Ч��
		//����ÿ���豸����ʱ��
		//�������ؼ������ʱ��
		double totalutility=999999;
		decision dec=new decision();
        String data="";
        //�µķ��䷽��
        newdevice[] dd=new newdevice[30]; 
        //�豸0ʼ�մ������Լ�
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
			String s="�ȴ�ʱ�䣺"+dd[j].waittime+"  �ȴ�Ч�ã�"+dd[j].waitu+"  ��ʱ�䣺"+dd[j].totaltime+"  ��Ч�ã�"+dd[j].totalu+"  ��ʱ��Ч�ã�"+dd[j].excepttimeu;
			printdata.method("D:\\xiaoyong.txt", s);
		}
		printdata.method("D:\\xiaoyong.txt", "-------------------------------------");
        double[] judge=distributeforu.distribute(connect, dd);
        int getnum=0;//��¼��ȡ��������豸��
        vmake=judge[getnum];//�����Ľ��
        System.out.println("�����Լ��Ľ����"+vmake);
        getnum++;
        if(havebuilding==0){
        	baseresult=judge[getnum];//��վ�Ľ��
        	System.out.println("��վ�����"+baseresult);
        	getnum++;
        }
        for(int i=0;i<main.uavnum;i++){
        	if(v2ucan[i]){
        		uavresult[i]=judge[getnum];//���˻��Ľ��
        		System.out.println("  ���˻�"+i+"�����"+uavresult[i]);
        		getnum++;
        	}
        }
        
		double vtime=(ta.revolution*vmake)/(v.calculation*1000);
		//��վ���պͼ������ʱ��  �ȴ�ʱ��+����ʱ��+������������ʱ��
		double basetime=basewait+(ta.revolution*baseresult)/(basestation.calculation*1000)+(ta.offloadsize*baseresult/1024)/v2btransspeed;
       if(baseresult==0){
    	   basetime=0;
       }
       System.out.println("����վ���ʹ�С��"+ta.offloadsize*baseresult/1024+"  ����վ�����ٶȣ�"+v2btransspeed);
       System.out.println("��վ����ʱ�䣺"+basetime+"  ��վ�ȴ�ʱ�䣺"+basewait+"��վ����ʱ�䣺"+(ta.revolution*baseresult)/(basestation.calculation*1000)+"  ��������ʱ�䣺"+(ta.offloadsize*baseresult/1000)/v2btransspeed);
		//ÿ�����˻��Ľ��պͼ������ʱ�� �ȴ�ʱ��+����ʱ��+������������ʱ��
		double[] uavtime=new double[main.uavnum];
		
		for(int i=0;i<main.uavnum;i++){
			uavtime[i]=uavwait[i]+(ta.revolution*uavresult[i])/(uav.calculation*1000)+(ta.offloadsize*uavresult[i]/1024)/v2utransspeed[i];
			if(uavresult[i]==0){
				uavtime[i]=0;
			}
		}
		double maxtime=0;
		maxtime=vtime;
		
		for(int i=0;i<main.uavnum;i++){//ѡȡ�ȴ�ʱ���������˻�ʱ��
			if(v2ucan[i]){
				if(uavtime[i]>maxtime){
					maxtime=uavtime[i];
				}
			}
		}
		if(havebuilding==0){//�����վ����ͨ��
			if(basetime>maxtime){
				maxtime=basetime;
			}
		}//ѡȡ���غͱ�ж���豸�����ļ���ʱ�䣬��Ϊ��Чʱ��
		//���ؼ���ʱ��
		if(vtime>maxtime){
			maxtime=vtime;
		}
//		System.out.println("���ؼ���ʱ�䣺"+localtime+"  ��վ����ʱ�䣺"+basetime);
		//��������ܺ�
        //���������ܺĺͷ����ܺ�
		double vcale=ta.revolution*vmake*ecvcpu;//�������ؼ����ܺ�
		double vsende=ta.offloadsize*(1-vmake)*esv/1024;//���������ܺ�
		double ucale=(ta.revolution*(1-vmake-baseresult))*ecucpu;//���˻������ܺ�
//		System.out.println("�������ؼ����ܺģ�"+vcale+"  ���������ܺģ�"+vsende+"   ���˻������ܺģ�"+ucale);
		//���ܺ�
		double totale=vcale+vsende+ucale;
		//��������ܼ۸�
		//��վ����ļ۸�
		double totalbaseprice=ta.revolution*baseresult*baseprice;
		//���˻�����ļ۸�
		double totaluavprice=ta.revolution*(1-vmake-baseresult)*uavprice;
		//�ܼ۸�
		double totalprice=totalbaseprice+totaluavprice;
//		System.out.println("�ܼ۸�Ϊ��"+baseprice);
		//��Ч��
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
////			System.out.println("������"+v2xmake);
//			device[] d=new device[20];
//			int connection=0;//���Ժ����˻��������豸����
//			for(int i=0;i<20;i++){//��ʼ������豸����
//				d[i]=new device(i);
//			}
//			
//	        //��վж�صİٷֱ�
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
//	        System.out.println("������"+connection+" "+d[0].finishtime+" "+d[0].waittime);
//	        System.out.println("��ж�ش�СΪ��"+v2xmake);
//	       
//	        double[] judge= taskdistribution.distributionmin(connection, d);//���ص����������
//	        
////	        System.out.println("������"+175);
//	        //��ȡ���������
//	        int getnum=0;//��¼��ȡ��������豸��
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
//			//��վ���պͼ������ʱ��  �ȴ�ʱ��+����ʱ��+������������ʱ��
//			double basetime=basewait+(ta.revolution*baseresult*v2xmake)/(basestation.calculation*1000)+(ta.offloadsize*v2xmake*baseresult/1024)/v2btransspeed;
//	       if(baseresult==0){
//	    	   basetime=0;
//	       }
//	       System.out.println("����վ���ʹ�С��"+ta.offloadsize*v2xmake*baseresult/1024+"  ����վ�����ٶȣ�"+v2btransspeed);
//	       System.out.println("��վ����ʱ�䣺"+basetime+"  ��վ�ȴ�ʱ�䣺"+basewait+"��վ����ʱ�䣺"+(ta.revolution*baseresult*v2xmake)/(basestation.calculation*1000)+"  ��������ʱ�䣺"+(ta.offloadsize*v2xmake*baseresult/1000)/v2btransspeed);
//			//ÿ�����˻��Ľ��պͼ������ʱ�� �ȴ�ʱ��+����ʱ��+������������ʱ��
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
//			for(int i=0;i<main.uavnum;i++){//ѡȡ�ȴ�ʱ���������˻�ʱ��
//				if(v2ucan[i]){
//					if(uavtime[i]>maxtime){
//						maxtime=uavtime[i];
//					}
//				}
//			}
//			if(havebuilding==0){//�����վ����ͨ��
//				if(basetime>maxtime){
//					maxtime=basetime;
//				}
//			}//ѡȡ���غͱ�ж���豸�����ļ���ʱ�䣬��Ϊ��Чʱ��
//			//���ؼ���ʱ��
//			double localtime=(ta.revolution*vmake/1000)/v.calculation;
//			if(localtime>maxtime){
//				maxtime=localtime;
//			}
////			System.out.println("���ؼ���ʱ�䣺"+localtime+"  ��վ����ʱ�䣺"+basetime);
//			//��������ܺ�
//	        //���������ܺĺͷ����ܺ�
//			double vcale=ta.revolution*vmake*ecvcpu;//�������ؼ����ܺ�
//			double vsende=ta.offloadsize*v2xmake*esv/1024;//���������ܺ�
//			double ucale=(ta.revolution*v2xmake-ta.revolution*v2xmake*baseresult)*ecucpu;//���˻������ܺ�
////			System.out.println("�������ؼ����ܺģ�"+vcale+"  ���������ܺģ�"+vsende+"   ���˻������ܺģ�"+ucale);
//			//���ܺ�
//			double totale=vcale+vsende+ucale;
//			//��������ܼ۸�
//			//��վ����ļ۸�
//			double totalbaseprice=ta.revolution*v2xmake*baseresult*baseprice;
//			//���˻�����ļ۸�
//			double totaluavprice=ta.revolution*v2xmake*(1-baseresult)*uavprice;
//			//�ܼ۸�
//			double totalprice=totalbaseprice+totaluavprice;
////			System.out.println("�ܼ۸�Ϊ��"+baseprice);
//			//��Ч��
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
//			System.out.println("��Ч��Ϊ��"+totalutility+"   �ܼ۸�"+totalprice+"   ��ʱ�䣺"+maxtime+"   ���ܺģ�"+totale);
//			
////		printdata.method("D:\\kaiqidata.txt", Double.parseDouble(String.format("%.3f", v2xmake))+" "+Double.parseDouble(String.format("%.3f", totalutility)));
////		
////		System.out.println("������*****************"+vmake);
//		}
//		System.out.println("������*****************");
		//���������ž��ߡ�Ȼ��ֱ�������豸�ַ�����
		task t1=(task)ta.clone();//һ������ĸ���
		//����վ�ַ���������ܹ������Ļ�
		data="";
		
		data=data+ta.tasknum+" "+ta.origin+" "+String.format("%.3f", dec.vmake)+" "+String.format("%.3f", dec.basepersent)+" "+String.format("%.3f", dec.totale)+" "+String.format("%.3f", dec.totalutility)+" "+clock.clocktime+" "+(String.format("%.3f", clock.clocktime+dec.totaltime*1000)+" ");
		for(int i=0;i<main.uavnum; i++) {
			data=data+dec.uavpersent[i]+" ";
		}
		printdata.method("D:\\kaiqidata.txt", data);
		if(havebuilding==0&&baseresult!=0){
			t1=(task)ta.clone();
//			t1.offloadpercent=dec.offloadpersent;
			System.out.println("��վ"+"�������:"+t1.tasknum);
			t1.offloaddevice=dec.basepersent;
			basestationthread.tasklist.put(ta.tasknum, t1);
			vehiclethread.havetask[v.id][0]=true;
		}
		for(int i=0;i<main.uavnum;i++){
			if(v2ucan[i]&&uavresult[i]!=0){
				System.out.println("���˻�"+i+"�������:"+t1.tasknum);
				t1=(task)ta.clone();
//				t1.offloadpercent=dec.offloadpersent;
				t1.offloaddevice=dec.uavpersent[i];
				uavthread.tasktreeMapArray.get(i).put(t1.tasknum, t1);
				vehiclethread.havetask[v.id][main.basenum+i]=true;
			}
		}
		t1=(task)ta.clone();
		t1.offloaddevice=dec.vmake;
//		System.out.println("����Ϊ��ж�ر���Ϊ"+dec.offloadpersent+"  ��Ч��Ϊ��"+dec.totalutility);
		System.out.println("��վж�ر�����"+dec.basepersent);
		return(t1);
		//���˻��ͻ�վ�ľ���
//		double u2bdistance=channelgain.getdistance(main.u.getX(), main.u.getY(), main.u.getZ(), basestation.x, basestation.y, basestation.z);
//		
//		ta.v2bdistance=v2bdistance;
//		ta.u2bdistance=u2bdistance;
//		ta.havebuilding=havebuilding;
		
		
        //��ʽ����
//		DecimalFormat df=new DecimalFormat("#0.000000");
//		DecimalFormat df1=new DecimalFormat("#0.0000000000000");
//		//�������ŵ�����
//		double distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, basestation.x, basestation.y, basestation.z);
//		double chann=channelgain.getchannelgain(distance);
//		ta.v2bchannelgain=chann;//�����Ի�վ
////		distance=channelgain.getdistance(main.vehiclestation[v.id][0], main.vehiclestation[v.id][1], 0, uav.x, uav.y, uav.z);
//		chann=channelgain.getchannelgain(distance);
//		ta.v2uchannelgain=chann;//���������˻�
//		//�������ǵ�λ���ͺ��ܺ͵�λ�������
//		ta.vsende=esv;//�������͵�λ����
//		ta.usende=esu;
//		ta.vcalcue=ecvcpu;
//		ta.ucalcue=ecucpu;
//		
//
//		//����ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��ʱ��
//		double sendtimetobase=(ta.offloadsize)/(transmissionspeed.gettransmissionspeed(ta.origin,0,0)*1024);
//		double calculation=(ta.revolution)/(50*1024)+800*basestationthread.tasklist.size()/(main.b.calculation*1024);//�����Լ���ʱ��ͼ����б���������ʱ��
//		double receivetimebase=(ta.receivesize)/(transmissionspeed.gettransmissionspeed(ta.origin, 0,0)*1024);
//		double receivetimebtou=ta.receivesize/(20*1024);
//		double totaltimebase=sendtimetobase+calculation+receivetimebase;
//		double self=ta.revolution/(v.calculation*1024);
//		
//		//�����˻�ͨ�ŷ�Χ�ڵ��ǲ��ͻ�վֱ��������1 �����˻��������ʱ�䣬2 �ڻ�վ�������ʱ��
//		//1 ���������˻������,�����Ǹ����˻����ǻ�վ�������Ǿ���Դ���ʱ���Ӱ��
//		double vtouspeed=transmissionspeed.gettransmissionspeed(ta.origin,1,1);
//		double sendtimetouav=(ta.offloadsize)/(vtouspeed*1024);
//		double receivetimeuav=(ta.receivesize)/(vtouspeed*1024);
//		double calculationtouav=(ta.revolution)/(main.u.calculation*1024)+(((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*(uavthread.tasktreeMapArray.size()+1))/(main.u.calculation*1024);
//		double totaltouav=sendtimetouav+calculationtouav+receivetimeuav;
//		//2����ж��ȥ��վ�����˻��ͻ�վ�Ĵ���������20Mb
//		double uavtobase=(ta.offloadsize)/(20*1024);
//		double sendtobase=uavtobase+sendtimetouav;
//		double calculationtobase=(ta.revolution)/(main.b.calculation*1024)+((FaceIdentify.revolution+CollisionMonitoring.revolution+licenseIdentify.revolution)/3)*basestationthread.tasklist.size()/(main.b.calculation*1024);
//		double receivetobase=receivetimebtou+receivetimeuav;
//		double totaltobase=sendtobase+calculationtobase+receivetobase;
//		//System.out.println("�Լ���"+self);
//		//System.out.println("���˻���"+totaltouav);
//		//System.out.println("��վ��"+totaltobase);
//		
//		//�����ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ��ܺ�
//		double energycv=(ta.revolution*ecvcpu)/1000;//�����Լ���������ķѵ�����
//		double energysv=(ta.offloadsize/1024)*esv;//�������͸�������Ҫ������
//		double energycu=(ta.revolution*ecucpu)/1000;//���˻������������Ҫ������
//		double energyrsu=(ta.receivesize/1024)*esu;//���˻����ظ�����������ܺ�
//		double energysu=(ta.offloadsize/1024)*esu;//���˻����͸�������Ҫ���ܺ�
//		double energycb=ta.revolution*ecbcpu;//��վ�����������Ҫ���ܺ�========����Ҫ
//		
//		//�����Լ�������ܺ�
//		double totalv=energycv;
//		//���˻�������ܺ�
//		double totalvtou=energysv+energycu+energyrsu;
//		//��վ������ܺ�
//		double totaltobs=energysv;
//		//���˻��м̻�վ����ܺ�
//		double totaltoutobs=energysv+energysu+energyrsu;
//		
//		//�ܵ�Ч�ü���
//		//�����Լ��������Ч��
//		double totalutilityv =main.ke*((totalv-main.mine)/(main.maxe-main.mine))+main.kt*((self-main.mint)/(main.maxt-main.mint));
//        //���˻������Ч��
//		double totalutilitytou =main.ke*((totalvtou-main.mine)/(main.maxe-main.mine))+main.kt*((totaltouav-main.mint)/(main.maxt-main.mint));
//        //��վ�������Ч��
//		double totalutilitytobs =main.ke*((totaltobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltimebase-main.mint)/(main.maxt-main.mint));
//        //���˻��м̵Ļ�վ�������Ч��
//		double totalutilitytoutobs =main.ke*((totaltoutobs-main.mine)/(main.maxe-main.mine))+main.kt*((totaltobase-main.mint)/(main.maxt-main.mint));
//       
//		boolean touav=true;
//		boolean tobs=true;
//		if(vehicletaskdecision.basedecide(v)){
//			tobs=true;
//			System.out.println("�������վֱ��ͨ�ţ�������վ��ʱ��Ϊ��"+totaltimebase);
//			System.out.println("sendtimetobase:"+df.format(sendtimetobase)+"   calculation:"+df.format(calculation)+"   receivetimebase:"+df.format(receivetimebase));
//			//System.out.println("��������վ�Ĵ����ٶȣ�"+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 0)));
//		}
//		else{
//			tobs=false;
//			System.out.println("���������վֱ��ͨ��");
//		}//�˴����˻�����ά�Ķ����������Ƕ�ά�ģ�������Z����Ĭ��Ϊ0
//		if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){
//			touav=false;
//			System.out.println("�������˻�ͨ�ŷ�Χ");
//		}
//		else{
//			touav=true;
//			System.out.println("�����˻�ͨ�ŷ�Χ��ж�ص����˻�ʱ�䣺"+df.format(totaltouav)+"   ͨ�����˻�ж�ص���վʱ��"+df.format(totaltobase));
//		}
//		
		
//		System.out.println("�����С��"+ta.offloadsize+"  ��Ҫת�٣�"+ta.revolution);
//		System.out.println("���������˻��Ĵ����ٶȣ�"+df1.format(transmissionspeed.gettransmissionspeed(ta.origin, 1)));
//		System.out.println("���˻��㣺  �������͸����˻�ʱ�䣺"+df.format(sendtimetouav)+"  ���˻����ظ�����ʱ�䣺"+df.format(receivetimeuav));
//		System.out.println("���˻��м̻�վ�㣺  ���͸����˻���ʱ�䣺"+df.format(sendtimetouav)+"  ���˻�����վ��ʱ�䣺"+df.format(uavtobase)+"   ������ʱ�䣺"+df.format(sendtobase)+"   ��վ���ظ����˻���"+df.format(receivetimebtou)+"   ���˻����ظ���վ"+df.format(receivetimeuav));
//		System.out.println("�Լ���ʱ�䣺"+df.format(self)+"  ���˻���ʱ�䣺"+df.format(totaltouav)+"   ͨ�����˻�����վ��ʱ�䣺"+df.format(totaltobase));
//		System.out.println("�����Լ�����ܺģ�"+totalv);
//		System.out.println("����ж�ص����˻����ܺģ�"+totalvtou);
//		System.out.println("����ֱ��ж�ص���վ���ܺģ�"+totaltobs);
//		System.out.println("���������˻�Ϊ�м�ж�ص���վ���ܺģ�"+totaltoutobs);
//		System.out.println("�����Լ������Ч�ã�"+totalutilityv);
//		System.out.println("����ж�ص����˻�����Ч�ã�"+totalutilitytou);
//		System.out.println("����ж�ص���վ����Ч�ã�"+totalutilitytobs);
//		System.out.println("���������˻�Ϊ�м�ж�ص���վ�����Ч�ã�"+totalutilitytoutobs);
//		System.out.println("��ǰ���˻��б��СΪ��"+uavthread.tasktreeMapArray.size());
//		System.out.println("���˻�������ʱ�䣺"+calculationtouav);
//		System.out.println("�����ţ�"+ta.taskid);
		
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
//	    tobs=false;//ʹ�����ͻ�վ�����˻����޷�����@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
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
//		String judge=usepython.print(restring);//�����ѧϰ�ó����ľ��߽��
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
		
//		if(tobs){//���վ����ֱ��ͨ��
//			if(touav){//���������˻�ͨ��
//				//for(int i=0;i<4;i++) {//�޸�Ϊ���˻����м�
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
//			else{//�����������˻�ͨ��
//				for(int i=0;i<4;i++){
//					if(totalsign[i]!=1&&totalsign[i]!=3){
//						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
//						return(ta);
//					}
//				}
//			}
//				}
//			
//		else{//���������վֱ��ͨ��
//			if(touav){//���������˻�ͨ��
//				for(int i=0; i<4;i++) {
//					if(totalsign[i]!=2) {//�޸�Ϊ���˻������м�
//						ta.setDec(new decision(totalsign[i],total[i],totalsendtime[i]));
//						return(ta);
//					}
//				}
//}
//			else{//�����������˻�ͨ��
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
		
//		System.out.println("********************************���߳���*****************************************************************");
//		return(ta);
//		if(vehicletaskdecision.basedecide(v)){//�������վͨ��                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
//			//
//			if(self>totaltimebase){
//				ta.setDec(new decision(2,totaltimebase,sendtimetobase));
//				return(ta);//���ؼ٣�������ж�ص���վ������������ص���վ�Ĵ��������
//			}
//			else{
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);//�����棬�����Լ�����
//		}
//		}
//		else{//���������վͨ�ţ������ж�ж�ص�����
//			//�����ж������˻��Ƿ���ͨ��
//			if(Math.sqrt((uav.x-v.x)*(uav.x-v.x)+(uav.y-v.y)*(uav.y-v.y)+uav.z*uav.z)>uav.communicationradius){//�������˻�ͨ�ŷ�Χ�ڣ��Լ���
//				ta.setDec(new decision(0,self,0.0));
//				return(ta);
//			}
//			else{//�����˻�ͨ�ŷ�Χ�ڣ��жϾ���
//				if(self<=totaltouav&&self<=totaltobase){//�Լ�����
//					ta.setDec(new decision(0,self,0.0));
//					return(ta);
//				}
//				else if(totaltouav<=self&&totaltouav<=totaltobase){//ж�ص����˻�
//                    ta.setDec(new decision(1,totaltouav,sendtimetouav));
//					return(ta);
//				}
//				else{//ж�ص���վ
//                    ta.setDec(new decision(3,totaltobase,sendtobase));
//					return(ta);
//				}
//			}
//			
//		}
		
	}
	public static boolean basedecide(vehicle v){//�жϳ����뽨����֮���Ƿ����ϰ���
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
			return(false);//ֻҪ��һ����������ʹ������ϰ���ͷ��ؼٴ����޷�ֱ������
		else
			return(true);//���������ﶼ�ٴ������ཻ������ֱ������
	}
	
}
