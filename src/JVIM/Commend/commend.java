package JVIM.Commend;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import JVIM.todo;
import JVIM.*;


public class commend {
    File commends = readFileFromJar("JVIM/Commend/Commends");
    Boolean isArgs = false;
     String args = "";

    public void runCommend(String commend) {
        Docommend(whatcommend(commend));
    }

    private int whatcommend(String commend) {

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
                if (commend.contains(String.valueOf(' '))){
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
    public File readFileFromJar(String filePath) {
        InputStream inputStream = getClass().getResourceAsStream("/" + filePath);
        if (inputStream != null) {
            File tempFile;
            try {
                tempFile = File.createTempFile("temp", ".txt");
                tempFile.deleteOnExit();
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return tempFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    private void setArgs(String Targs){
        args = Targs.substring(1,Targs.length());
    }


    private void Docommend(int Number){
            switch (Number){
                case 1:
                    System.out.println("write");
                    //TODO write
                    if (isArgs) {
                        new JVIM().setNowWhere(args);
                        new todo().fileWrite(JVIM.getTempStr(),args);
                    }if (!isArgs){
                        new todo().fileWrite(JVIM.getTempStr(),new JVIM().getNowWhere());
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
                case 3:
                        new todo().fileWrite(JVIM.getTempStr(),new JVIM().getNowWhere());
                    System.exit(0);
                    break;
                case 4:
                    System.out.println("now is "+JVIM.nowShowHow());
                    break;
                case 5:
                    todo.executeCommand(args);
                    break;
                case 6:
                    //Code highlight
                    //todo
                    if (args.equals("")) {
                        new JVIM().changeCodelight();
                    }else {
                        new JVIM().setTheCHLmode(args);
                    }
                    break;
                case 7:
                    //shadow
                    new JVIM().changeshadow();
                    break;
                case 8:
                    if (args.equals("")) {
                        new todo().fileReader(new JVIM().getNowWhere());
                    }else {
                        new todo().fileReader(args);
                        new JVIM().setNowWhere(args);
                    }
                    break;
                case 9:
                    //TODO config save
                    break;
                case 10:
                    //del
                    if (args.equals("")) {
                        new JVIM().TempStringSet("", new JVIM().getTextLine());
                    }else {
                        if (args.equals("all")){
                            new JVIM().setTextLine(0,0);
                            new JVIM().TempStringClear();
                        }else {
                            new JVIM().TempStringSet("", Integer.parseInt(args));
                        }
                        }
                    break;
                case 11:
                    //TODO paste
                    break;
                case 12:
                    //TODO set
                    break;
                case 13:
                    //TODO find
                case 15:
                    new JVIM().changeBD();
                    break;
                case 99:
                    System.out.println("error");
            }
            args="";
    }
}
