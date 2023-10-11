package JVIM.code;

import JVIM.JVIM;

public class CheckCode {
    public static String[] getfunctionName(){
        int num=0;
       String[] TempStr;
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
    public static String[] getvariableName(){
        int num=0;
        String[] TempStr;
        String[] done = new String[600];
        for (int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
            TempStr = JVIM.getTempStr()[i].toString().split(" ");
            for (int a=0;a<TempStr.length;a++){
               // if (new CheckCode().checkAllstr());
            }
        }
        return done;
    }
    private boolean checkAllstr(){
        return false;
    }
    private String[] TheVarNameReader(){
        return new String[12];
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
