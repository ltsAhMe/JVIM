package JVIM.code;

import JVIM.JVIM;
import JVIM.Commend.commend;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CheckCode {
    static File varFile = new commend().readFileFromJar("JVIM/code/LSP/TheVarName");

    public static String[] getfunctionName(){
        int num=0;
        String TempStr[];
        String[] done = new String[600];
       for (int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
       TempStr = JVIM.getTempStr()[i].toString().split(" ");
          for (int a=0;a<TempStr.length;a++) {
              if (TempStr[a].length() > 2) {
                  if (TempStr[a].lastIndexOf("()") != -1){
                      if (!new CheckCode().CheckReHave(done,TempStr[a].substring(0,TempStr[a].lastIndexOf("()")))){
                          done[num] = TempStr[a].substring(0,TempStr[a].lastIndexOf("()")+2);
                          num++;
                      }
                  }
              }
          }
       }
       return done;
    }
    public static String[] getvarName(){
        int num=0;
        String[] TempStr;
        String[] done = new String[600];
        for (int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
            TempStr = JVIM.getTempStr()[i].toString().split(" ");
            for (int a=0;a<TempStr.length;a++){
                if(new CheckCode().teedwrqpiejgoijfijkeofihj(TempStr[a])){
                  if (!new CheckCode().CheckReHave(done,TempStr[a+1])){
                      done[num] = TempStr[a+1];
                      num++;
                  }
                }
            }
        }
        return done;
    }
    private boolean checkAllstr(){
        return false;
    }
    private boolean teedwrqpiejgoijfijkeofihj(String input){
        String[] str = TheVarNameReader();
        for (int i=0;i<str.length;i++){
            if (str[i].equals(input)){
                return true;
            }
        }
        return false;
    }
    private String[] TheVarNameReader() {

        String[] name = new String[0];

        FileReader fileReader = null;
        try {
            fileReader = new FileReader(varFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while (true) {
                try {
                    if (!((line = bufferedReader.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                name = line.split(",");
            }
        return name;
    }

    private boolean CheckReHave(String[] input,String check){
        for (int i=0;i<JVIM.nowShowHowString(input);i++){
            if (input[i].equals(check)){
                return true;
            }
        }
        return false;
    }
}
