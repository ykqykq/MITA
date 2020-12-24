package utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class usepython {
    public static void main(String[] args)throws Exception{

    		usepython.print("580.00   1381.00   0.80   0.20   0.70   10.00   70.00   607.81   1.00   301.76   660.77   20.00   100.00   0.00   0.00   0.00   50.00   20.00   2.00   1.00");
    	
    }
	public static String print(String s)throws IOException,InterruptedException {
		String exe = "python";
		String command = "G:\\pythonFirst\\getresult.py";
		//String command = "G:\\pythonFirst\\print.py";
        String num1 = s;
        String num2 = "Ñî¿­ä¿";
        String[] cmdArr = new String[] {exe,command,num1,num2};
        Process process = Runtime.getRuntime().exec(cmdArr);
        InputStream is = process.getInputStream();
        DataInputStream dis = new DataInputStream(is);
        String str = dis.readLine();
        process.waitFor();
        System.out.println(str);
        return(str);

	}

}
