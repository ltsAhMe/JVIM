package JVIM;

import java.io.*;

public class todo {
    public void fileReader(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int theline=0;
            while ((line = br.readLine()) != null) {
              new JVIM().TempStringSet(line,theline);
                theline++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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

    public static void executeCommand(String command) {
        try {
            ProcessBuilder processBuilder;
            if (System.getProperty("os.name").startsWith("Windows")) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            } else {
                processBuilder = new ProcessBuilder("bash", "-c", command);
            }

            Process process = processBuilder.start();

            // 读取命令执行的输出结果
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
            System.out.println("Command execution completed with exit code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
