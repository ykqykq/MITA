package utils;

public class clockthread implements Runnable{
    int id;
    public clockthread(int id){
    	this.id=id;
    	}
	public void run() {
		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(Cycliccontrol.cycliccontrol(id)){
				clock.runtimeslot();
				Cycliccontrol.addsign(id);
			}
		}
		
	}

}
