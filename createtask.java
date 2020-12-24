package utils;

import java.util.Random;

public class createtask {


     static Random rand = new Random();
     public static task creatatask(int ori){
    	 int i=rand.nextInt(3);
    	 //task t=new task(FaceIdentify.offloadsize,FaceIdentify.revolution,FaceIdentify.receivesize, ori);
    	 if(i==0){
    		 return(new task(FaceIdentify.offloadsize,FaceIdentify.revolution,FaceIdentify.receivesize, ori,FaceIdentify.ke,FaceIdentify.kt));
    	 }
    	 if(i==1){
    		 return(new task(licenseIdentify.offloadsize,licenseIdentify.revolution,licenseIdentify.receivesize, ori,licenseIdentify.ke,licenseIdentify.kt));
    	 }
    	 else{
    		 return(new task(CollisionMonitoring.offloadsize,CollisionMonitoring.revolution,CollisionMonitoring.receivesize, ori,CollisionMonitoring.ke,CollisionMonitoring.kt));
    	 }
    	 
     }
     public static void main(String[] args){
    	 System.out.println(rand.nextInt(3));
     }
     
}
