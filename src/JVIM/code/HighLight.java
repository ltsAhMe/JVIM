package JVIM.code;

import JVIM.JVIM;
import JVIM.Commend.commend;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HighLight {
    static String thepath;
    static Map<String, String> keywordMap;

    public Color colorReader(String tempString, int where) throws IOException {
        char chars = tempString.charAt(where);
        if (checkSM(tempString) && where >= checkSMwhere(tempString)){
            return Color.darkGray;
        }else {
            if (chars == ';' || chars == '{' || chars == '}' || chars == '(' || chars == ')' || chars == '"' || chars == ':' || chars == ',' || chars == '[' || chars == ']') {
                switch (chars) {
                    case ';':
                        return new Color(64, 199, 110);
                    case '{':
                        return new Color(128, 49, 237);
                    case '}':
                        return new Color(128, 49, 237);
                    case '(':
                        return new Color(255, 187, 0);
                    case ')':
                        return new Color(255, 187, 0);
                    case '"':
                        return new Color(246, 57, 213);
                    case ':':
                        return new Color(154, 255, 0);
                    case ',':
                        return new Color(0, 116, 23);
                    case '[':
                        return new Color(0, 255, 51);
                    case ']':
                        return new Color(0, 255, 51);
                }
            } else {
                String[] words = tempString.split(" ");
                int theTrue = 0;
                String TempStr = "";
                for (int i = 0; i < tempString.length(); i++) {
                    if (words[i] != null) {
                        if (i < 1) {
                            TempStr += words[i];
                        } else {
                            TempStr += words[i] + " ";
                        }
                    }
                    if (where < TempStr.length()) {
                        theTrue = i;
                        break;
                    }
                }
                if (keywordMap.get(words[theTrue]) != null) {
                    return colorrecode(keywordMap.get(words[theTrue]));
                } else {
                    return Color.white;
                }
            }
        }
        return Color.white;
    }
    private boolean checkSM(String str){
        for (int i=0;i<str.length()-2;i++){
            if (str.substring(i,i+2).equals("//")){
                return true;
            }
        }
        return false;
    }
    private int checkSMwhere(String str){
        for (int i=0;i<str.length()-2;i++){
            if (str.substring(i,i+2).equals("//")){
                return i;
            }
        }
        return -1;
    }
    public Color colorrecode(String str) {
        String[] rgb = str.split(",");
        int r, g, b;
        try {
            r = Integer.parseInt(rgb[0].trim());
            g = Integer.parseInt(rgb[1].trim());
            b = Integer.parseInt(rgb[2].trim());
        } catch (NumberFormatException e) {
            return null; // 处理解析错误的情况，返回 null 或者其他适当的值
        }
        return new Color(r, g, b);
    }
    public void setCHLfile(String string){
        switch (string){
            case "java":
                thepath = "JVIM/code/java_CHL";
                break;
            case "C":
                thepath = "JVIM/code/C_CHL";
                break;
        }
        try {
            keywordMap = readKeywordMapping(new commend().readFileFromJar(thepath).getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Map<String, String> readKeywordMapping(String filePath) throws IOException {
        Map<String, String> keywordMap = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String tempName="";
            String TempColor = "";
            StringBuffer temp = new StringBuffer("");
            while ((line = br.readLine()) != null) {
                int i =0;
                for (i=i;i<line.length();i++) {
                    if (line.charAt(i)!=' ') {
                        temp.append(line.substring(i, i + 1));
                    } else {
                        tempName = temp.toString();
                        temp = new StringBuffer("");
                        break;
                    }
                }
                    for (i=i;i<line.length();i++){
                        temp.append(line.substring(i, i + 1));
                    }
                    TempColor = temp.delete(0,1).toString();
                    temp = new StringBuffer("");
                    keywordMap.put(tempName,TempColor);
            }
        }
        return keywordMap;
    }
}
