package JVIM.Commend;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import JVIM.todo;
import JVIM.*;


public class commend {
    Boolean isArgs = false;
     String[] args = new String[100];

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
            StringBuffer commendBuffer = new StringBuffer(commend);
            while ((line = bufferedReader.readLine()) != null) {
                theLine++;
                if (commend.indexOf(' ')!=-1){
                    for (int i=0;i<=commend.length();i++){
                        if (commendBuffer.substring(0,i).equals(line)){
                                isArgs = true;
                                setArgs(commendBuffer.substring(i,commend.length()));
                                return theLine;
                        }
                    }
                }else {
                    isArgs =false;
                  if (line.equals(commend)){
                      return theLine;
                  }
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
        return 99;
    }
    private void setArgs(String Targs){
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < Targs.length(); i++) {
            if (Targs.charAt(i) == ' ') {
                positions.add(i);
            }
        }

        // 将字符串按照空格位置分割为子字符串
        String[] args = new String[positions.size() + 1]; // 数组长度为分割后的子字符串个数加一（因为最后一个子字符串后面没有空格）
        int prev = 0; // 前一个空格位置
        for (int i = 0; i < positions.size(); i++) {
            int pos = positions.get(i); // 当前空格位置
            args[i] = Targs.substring(prev, pos); // 分割出子字符串
            prev = pos + 1; // 更新前一个空格位置
        }
        args[args.length - 1] = Targs.substring(prev); // 最后一个子字符串
        this.args = args;
    }


    private void Docommend(int Number){
            switch (Number){
                case 1:
                    System.out.println("write");
                    //TODO write
                    if (isArgs = true) {
                        new todo().fileWrite(JVIM.getTempStr(),args[1]);
                    }else {
                        new todo().fileWrite(JVIM.getTempStr(),"test");
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
                case 3:
                        new todo().fileWrite(JVIM.getTempStr(),"test");
                    System.exit(0);
                    break;
                case 99:
                    System.out.println("error");
            }
    }
}
