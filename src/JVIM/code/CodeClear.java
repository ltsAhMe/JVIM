package JVIM.code;

import JVIM.JVIM;

public class CodeClear {
    public StringBuffer[] getClearCode(StringBuffer[] Code) {
        int PBnum=0;
        StringBuffer[] done = new StringBuffer[Code.length];
        //init done as Code
        for (int i=0;i<JVIM.nowShowHow();i++){
            done[i] = new StringBuffer(Code[i].toString());
        }
        if (checkisAllPood(Code)){
            //init
            for(int i=0;i<JVIM.nowShowHow();i++){
                done[i]=new StringBuffer(Code[i].toString());
            }
            //startPool
            for(int i=0;i<JVIM.nowShowHow();i++){
                for (int a=0;a<Code[i].length();a++){
                    switch (Code[i].charAt(a)){
                        case '{':
                            PBnum++;
                            break;
                        case '}':
                            PBnum--;
                            break;
                    }
                }
                done[i] = new StringBuffer(setTheBodebe(Code[i].toString(),PBnum));
            }
            for(int i=0;i<JVIM.nowShowHow();i++) {
            }
        }
        return done;
    }

private String setTheBodebe(String input,int how){
        StringBuffer temp = new StringBuffer(input);
        int theBode=0;
        for (int i=0;i<input.length();i++){
            if (input.charAt(i) == ' '){
                theBode++;
            }else {
                break;
            }
        }
      if (theBode > how){
              temp.delete(0,theBode-how);
      } else {
              temp.insert(0,makeBode(how-theBode));
      }
      return temp.toString();
}
private String makeBode(int num){
        String done ="";
        for(int i=0;i<num;i++){
            done+=" ";
        }
        return done;
}
private boolean checkisAllPood(StringBuffer[] input){
        int num=0;
        boolean ishave=false;
    for(int i=0;i<JVIM.nowShowHow();i++){
        for (int x=0;x<input[i].length();x++){
            switch (input[i].charAt(x)){
                case '{':
                    num++;
                    ishave = true;
                    break;
                case '}':
                    num--;
                    ishave = true;
                    break;
            }
        }
    }
    if (!ishave){
        return false;
    }
    if (num==0){
        return true;
    }else {
        return false;
    }
}
}