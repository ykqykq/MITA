package utils;

import java.util.Random;

public class vehicleability {
	
		static Random rand = new Random();
		static double a=0;
	    public static double creatatask(int ori){
	   	 int i=rand.nextInt(ori);
	   	 //task t=new task(FaceIdentify.offloadsize,FaceIdentify.revolution,FaceIdentify.receivesize, ori);
	   	 if(i==0){
	   		 a=0.5;
	   		 
	   	 }
	   	 else if(i==1){
	   		 
	   		 a=0.7;
	   	 }
	   	 else{
	   		a=0.8;
	   	 }
	   	 return a;
	   //	System.out.println(i);
	   	//System.out.println(a);
	 
	    }
	    public static void main(String[] args){
	    	double i;
	    	i=creatatask(3);

	    }
	    
	}




