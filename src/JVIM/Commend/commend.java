package JVIM.Commend;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import JVIM.todo;
import JVIM.*;
import jdk.jfr.internal.tool.Main;

public class commend {
    public void runCommend(String commend) {
        Docommend(whatcommend(commend));
    }

    private int whatcommend(String commend) {
        File commends = new File("src/JVIM/Commend/Commends");
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;

        try {

            fileReader = new FileReader(commends);

            bufferedReader = new BufferedReader(fileReader);

            int theLine = 0;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                theLine++;
                if (commend.equals(line)){
                    return theLine;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return 1;
    }

    private void Docommend(int Number){
            switch (Number){
                case 1:
                    System.out.println("write");
                    //TODO write

                    new todo().fileWrite(JVIM.getTempStr(),"");
                    break;
                case 2:
                    System.exit(0);
            }
    }
}
