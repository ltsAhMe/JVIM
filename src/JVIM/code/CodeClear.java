package JVIM.code;

import JVIM.JVIM;

public class CodeClear {
    public StringBuffer[] getClearCode(StringBuffer[] Code) {
        boolean notPB = false;
        int startLine=0;
        int endLine=0;
        int PBnum=0;
        StringBuffer[] done = new StringBuffer[Code.length];
        //init done as Code
        for (int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
            done[i] = new StringBuffer(Code[i].toString());
        }
        if (checkisAllPood(Code)){
            //init
            for(int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
                done[i]=new StringBuffer(Code[i].toString());
            }
            //startPool
            for(int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
                for (int a=0;a<Code[i].length();a++){
                    switch (Code[i].charAt(a)){
                        case '{':
                            notPB=false;
                            if (PBnum==0){
                                startLine=i;
                            }
                            PBnum++;
                            break;
                        case '}':
                            notPB=false;
                            if (PBnum==0){
                                endLine=i;
                            }

                            PBnum--;
                            break;
                        default:
                            notPB=true;
                    }
                }

                    done[i] = new StringBuffer(setTheBodebe(Code[i].toString(), PBnum*2));
            }
        }
        return done;
    }
private int checkhowBodehave(String input){
        int i=0;
    for (int a=0;a<input.length();a++){
        if (input.charAt(a)!=' '){
            break;
        }else{
            i++;
        }
    }
    return i;
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
    for(int i=0;i<JVIM.nowShowHow(JVIM.getTempStr());i++){
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