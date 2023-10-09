package JVIM;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;

public class todo {
    public void fileReader(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int theline = 0;
            while ((line = br.readLine()) != null) {
                new JVIM().TempStringSet(line, theline);
                theline++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileWrite(StringBuffer[] theSTR, String where) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(where));
            System.out.println(theSTR.length);
            for (int i = 0; i < JVIM.nowShowHow(); i++) {
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

    static File fontFile;

    public static Font fontget(int how) {
        try {
            // 加载字体文件
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // 设置字体样式和大小
            return customFont.deriveFont(Font.BOLD, how);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Color getRandomColor() {
        int min = 0;
        int max = 5;
        int s = (int) min + (int) (Math.random() * (max - min));
        Color[] colors = {new Color(251, 56, 56), new Color(107, 255, 10), new Color(241, 214, 63), new Color(255, 0, 222), new Color(20, 255, 220), new Color(7, 253, 248)};
        return colors[s];
    }

    public static String getPaste() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasText) {
            try {
                return (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void saveToClipboard(String input) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(input);
        clipboard.setContents(selection, null);
    }
}