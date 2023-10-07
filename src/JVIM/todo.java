package JVIM;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class todo {
    public void fileWrite(StringBuffer[] theSTR,String where){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(where));
            System.out.println(theSTR.length);
            for (int i=0;i<JVIM.nowShowHow();i++) {
                writer.write(theSTR[i].toString());
                writer.newLine();
            }
            writer.close();
            System.out.println("done");
        } catch (IOException e) {
            System.err.println("filed" + e.getMessage());
        }
    }
}
