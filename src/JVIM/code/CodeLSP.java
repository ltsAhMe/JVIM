package JVIM.code;
import JVIM.Commend.commend;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CodeLSP {
    static File theLSPfile = new File("");
    static String thePaths = "";
    public static boolean checkisCode(String theCode,int where){
        String[] theLSP = new CodeLSP().getFromFile();
        String[] words = theCode.split(" ");
        String TempStr = "";
        int theTrue = 0;
        if (theCode.equals("")){
            return false;
        }
        for (int i = 0;i < words.length; i++) {
            if (i < 1) {
                TempStr += words[i];
            } else {
                TempStr += words[i] + " ";
            }
            if (where <= TempStr.length()) {
                theTrue = i;
                break;
            }
        }
        if (theTrue >= 0 && theTrue < words.length) {
            String doneCode = words[theTrue];
            for (int i = 0; i < theLSP.length; i++) {
                if (doneCode.length() < theLSP[i].length()) {
                    if (theLSP[i].substring(0, doneCode.length()).equals(doneCode)) {
                        return true;
                    }
                }
            }
        }
      return false;
    }
    public static String[] getLSP(String theCode,int where){

       String[] theLSP = new CodeLSP().getFromFile();
       String[] checkDone = new String[50];
        String[] words = theCode.split(" ");
        String TempStr = "";
        int theTrue = 0;
//        for (int i=0;i<words.length;i++){
//            System.out.println(words[i]);
//        }
        for (int i = 0;i < words.length; i++) {
                if (i < 1) {
                    TempStr += words[i];
                } else {
                    TempStr += words[i] + " ";
                }
            if (where <= TempStr.length()) {
                theTrue = i;
                break;
            }
        }
        if (theTrue >= 0 && theTrue < words.length) {
            String doneCode = words[theTrue];
            int num = 0;
            for (int i = 0; i < theLSP.length; i++) {
                if (doneCode.length() < theLSP[i].length()) {
                    if (theLSP[i].substring(0, doneCode.length()).equals(doneCode)) {
                        checkDone[num] = theLSP[i];
                        num++;
                    }
                }
            }
            return checkDone;
        }
            return new String[]{"","","","",""};
        }
    private String[] getFromFile(){
        String text="";
        try {
            FileReader reader = new FileReader(theLSPfile);
            int date;
            while ((date = reader.read()) != -1){
                text+=(char)date;
            }
                    reader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text.split(",");
    }
    public static void setLSPfile(String thepath){
        switch (thepath){
            case "java":
                thePaths = "JVIM/code/LSP/java_LSP";
                break;
            case "C":
                thePaths = "JVIM/code/LSP/C_LSP";
                break;
        }
        theLSPfile = new commend().readFileFromJar(thePaths);
    }
}
