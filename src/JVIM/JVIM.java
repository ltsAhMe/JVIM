package JVIM;

import JVIM.Commend.commend;
import JVIM.code.CodeClear;
import JVIM.code.CodeLSP;
import JVIM.code.HighLight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class JVIM {
    static boolean isLSP = true;
    static boolean isRainbow = false;
    static Color textColor = Color.white;
    static String theCHLmode = "java";
    static String nowWhereis = null;
    static StringBuffer CommendInput = new StringBuffer();
    static boolean isCommendInput = false;
    static boolean isHighlight = true;
    static StringBuffer TempCodeString = new StringBuffer("");
    static StringBuffer[] CodeString = new StringBuffer[9999];
    static JFrame frame;
    static boolean isShadow = true;
    static int startLine = 0;
    static int TextLine = 0;
    static int KickNow = 0;
    static JPanel panel;
    static Font TextFont;
    static boolean isInput = false;
    static boolean isBD = true;
    static FontMetrics fontMetrics;
    Color test = new Color(157, 57, 57);

    public static void main(String[] args) {
        init("JVIM", new Dimension(1280, 720));
    }

    public static void init(String title, Dimension Size) {

        CodeLSP.setLSPfile(theCHLmode);
        todo.fontFile = new commend().readFileFromJar("JVIM/font.ttf");
        TextFont = todo.fontget(13);
        fontMetrics = getPanel().getFontMetrics(TextFont);
        new HighLight().setCHLfile(theCHLmode);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        frame = new JFrame(title);
        panel = getPanel();
        PanelgetKey();
        frame.setSize(Size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
        CodeString[0] = new StringBuffer("int mandw");
        new JVIM().startAllTimer();
    }
    private void startAllTimer(){
        Timer CodeLSPget = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CodeLSP.getNew();
            }
        });
        CodeLSPget.start();
    }
    public static StringBuffer[] getTempStr() {
        return CodeString;
    }
    private static JPanel getPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //background
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0, 0, frame.getWidth(), frame.getHeight());
                //text shadow
                g2d.setFont(TextFont);
                if (isShadow) {
                    g2d.setColor(new Color(107, 107, 107));
                    for (int i = 0; i < nowShowHow(getTempStr()); i++) {
                        g2d.drawString(CodeString[i].toString(), 12, 18 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                    }
                }
                //text BD
                if (isBD) {
                    g2d.setColor(new Color(255, 84, 84));
                    g2d.setFont(todo.fontget(14));
                    for (int i = 0; i < nowShowHow(getTempStr()); i++) {
                        g2d.drawString(CodeString[i].toString(), 10, 20 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                    }
                }
                //text
                if (!isHighlight) {
                    g2d.setFont(TextFont);
                    g2d.setColor(textColor);
                    for (int i = 0; i < nowShowHow(getTempStr()); i++) {
                        g2d.drawString(CodeString[i].toString(), 10, 20 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                    }
                }
                //code highlight
                if (isHighlight) {
                    for (int i = 0; i < nowShowHow(getTempStr()); i++) {
                        for (int s = 0; s < CodeString[i].length(); s++) {
                            try {
                                if (!isRainbow) {
                                    if (!CodeString[i].substring(s, s + 1).equals(" ")) {
                                        g2d.setColor(new HighLight().colorReader(CodeString[i].toString(), s));
                                    }
                                } else {
                                    g2d.setColor(todo.getRandomColor());
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            g2d.drawString(CodeString[i].substring(s, s + 1), 10 + fontMetrics.stringWidth(CodeString[i].substring(0, s)), 20 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                        }
                    }
                }
                if (isInput) {
                    //kick
                    g2d.setColor(Color.white);
                    g2d.fillRect(10 + fontMetrics.stringWidth(CodeString[TextLine].substring(0, KickNow)), 7 + ((TextLine - startLine) * fontMetrics.getHeight()), 2, fontMetrics.getHeight());
                    //lsp
                    if (isLSP) {
                        if (!TempCodeString.toString().equals("")) {
                            String[] theLSP;
                            theLSP = CodeLSP.getLSP(TempCodeString.toString());
                            //panel
                            g2d.fillRect(12 + fontMetrics.stringWidth(CodeString[TextLine].substring(0, KickNow)), 7 + ((TextLine - startLine) * fontMetrics.getHeight()) + fontMetrics.getHeight(), 100, (nowShowHowString(theLSP) * fontMetrics.getHeight()));
                            //LSP text show
                            int chooseone = 0;
                            for (int i = 0; i < nowShowHowString(theLSP); i++) {
                                //choose one like
                                if (i == chooseone) {
                                    g2d.setColor(Color.yellow);
                                    g2d.fillRect(12 + fontMetrics.stringWidth(CodeString[TextLine].substring(0, KickNow)), 7 + ((TextLine - startLine) * fontMetrics.getHeight()) + fontMetrics.getHeight() + (i * fontMetrics.getHeight()), 100, fontMetrics.getHeight());
                                }
                                //text
                                String lspString = theLSP[i];
                                if (lspString != null) {
                                    g2d.setColor(Color.black);
                                    g2d.drawString(lspString, 12 + fontMetrics.stringWidth(CodeString[TextLine].substring(0, KickNow)), 40 + ((TextLine - startLine) * fontMetrics.getHeight()) + (i * fontMetrics.getHeight() - 2));
                                }
                            }
                        }
                    }
                }
                //mode show
                g2d.setColor(Color.black);
                g2d.fillRect(0, frame.getHeight() - 85, frame.getWidth(), 100);
                g2d.setColor(Color.darkGray);
                g2d.fillRect(0, 0, 5, panel.getHeight() - 40);
                g2d.setColor(textColor);
                g2d.fillRect(0, frame.getHeight() - 85, frame.getWidth(), 20);

                g2d.setColor(Color.black);
                if (!isInput) {
                    g2d.drawString("COMMEND", 0, frame.getHeight() - 70);
                } else {
                    g2d.drawString("INPUT", 14, frame.getHeight() - 70);
                }
                //Line show
                g2d.drawString(TextLine + "," + KickNow, frame.getWidth() - 80, frame.getHeight() - 70);
                //now where show
                if (nowWhereis != null) {
                    g2d.drawString(nowWhereis, frame.getWidth() / 2, frame.getHeight() - 70);
                }

                //commend input show
                if (!isInput && isCommendInput) {
                    g2d.setColor(Color.white);
                    g2d.drawString(CommendInput.toString(), 0, frame.getHeight() - 50);
                }


            }
        };
    }

    private static void PanelgetKey() {
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if (isInput) {
                    if (isSpecialKey(keyCode)) {
                        switch (keyCode) {
                            case KeyEvent.VK_BACK_SPACE:
                                //退格
                                if (KickNow > 0) {
                                    CodeString[TextLine].delete(KickNow - 1, KickNow);
                                    KickNow--;
                                }
                                if (TempCodeString.length() >0){
                                    TempCodeString.delete(TempCodeString.length()-1,TempCodeString.length());
                                }
                                //返回上一行
                                if (TextLine > 0 && KickNow == 0) {
                                    KickNow = CodeString[TextLine - 1].length();
                                    CodeString[TextLine - 1].append(CodeString[TextLine]);

                                    StringBuffer[] temp = new StringBuffer[CodeString.length];

                                    //init
                                    for (int i=0;i<nowShowHow(CodeString);i++){
                                        temp[i] = new StringBuffer(CodeString[i].toString());
                                    }
                                    //define
                                    for (int i=TextLine;i<nowShowHow(CodeString);i++){
                                        temp[i-1] = new StringBuffer(CodeString[i]);
                                    }
                                    //redefine
                                    for (int i=TextLine;i<nowShowHow(CodeString);i++){
                                        CodeString[i] = new StringBuffer(temp[i].toString());
                                    }
                                    CodeString[nowShowHow(CodeString)] = null;
                                    TextLine--;
                                }
                                new JVIM().checkPanelPageUP();
                                break;
                            //left
                            case KeyEvent.VK_LEFT:
                                if (KickNow > 0) {
                                    KickNow--;
                                }
                                CodeString =new CodeClear().getClearCode(CodeString);
                                TempCodeString = new StringBuffer("");
                                break;
                            //right
                            case KeyEvent.VK_RIGHT:
                                if (KickNow < CodeString[TextLine].length()) {
                                    KickNow++;
                                }
                                CodeString =new CodeClear().getClearCode(CodeString);
                                break;
                            //off input mode
                            case KeyEvent.VK_ESCAPE:
                                isInput = false;
                                TempCodeString = new StringBuffer("");
                                break;
                            //回车
                            case KeyEvent.VK_ENTER:
                                if (CodeString[TextLine + 1] == null) {
                                    CodeString[TextLine + 1] = new StringBuffer();
                                }
                                if (CodeString[TextLine + 1] != null && !CodeString[TextLine + 1].toString().equals("")) {
                                    StringBuffer[] temp = new StringBuffer[CodeString.length];
                                    for (int i = 0; i < CodeString.length; i++) {
                                        if (CodeString[i] != null) {
                                            temp[i] = new StringBuffer(CodeString[i].toString());
                                        }
                                    }

                                    for (int i = 0; i <= nowShowHow(getTempStr()); i++) {
                                        if (i == 0) {
                                            temp[TextLine + 1] = new StringBuffer(CodeString[TextLine].substring(KickNow, CodeString[TextLine].length()));
                                        } else {
                                            temp[TextLine + 1 + i] = CodeString[TextLine + i];
                                        }
                                    }
                                    CodeString = temp;
                                } else {
                                    CodeString[TextLine + 1].append(CodeString[TextLine].substring(KickNow, CodeString[TextLine].length()));
                                }
                                CodeString[TextLine].delete(KickNow, CodeString[TextLine].length());
                                KickNow = CodeString[TextLine + 1].length();

                                TextLine++;
                                new JVIM().checkPanelPageDown();
                                CodeString =new CodeClear().getClearCode(CodeString);
                                TempCodeString = new StringBuffer("");
                                break;
                            case KeyEvent.VK_UP:
                                if (TextLine > 0) {
                                    if (KickNow > CodeString[TextLine - 1].length()) {
                                        KickNow = CodeString[TextLine - 1].length();
                                    }
                                    TextLine--;
                                }
                                new JVIM().checkPanelPageUP();
                                CodeString =new CodeClear().getClearCode(CodeString);
                                TempCodeString = new StringBuffer("");
                                break;
                            case KeyEvent.VK_DOWN:
                                if (CodeString[TextLine + 1] != null) {
                                    if (KickNow > CodeString[TextLine + 1].length()) {
                                        KickNow = CodeString[TextLine + 1].length();
                                    }
                                    TextLine++;
                                    new JVIM().checkPanelPageDown();
                                    CodeString =new CodeClear().getClearCode(CodeString);
                                }
                                TempCodeString = new StringBuffer("");
                                break;
                        }

                    } else {
                        // 不是特殊按键
                        CodeString[TextLine].insert(KickNow, e.getKeyChar());
                        TempCodeString.append(e.getKeyChar());
                        KickNow++;
                        if (e.getKeyChar() == ' ' || e.getKeyChar() == '.'){
                            TempCodeString = new StringBuffer("");
                        }
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_I:
                            if (!isCommendInput) {
                                isInput = true;
                            }
                            break;
                        case KeyEvent.VK_ESCAPE:
                            new JVIM().commendExit();
                            break;
                        case KeyEvent.VK_ENTER:
                            //TODO do commend
                            new commend().runCommend(CommendInput.substring(1, CommendInput.length()));
                            new JVIM().commendExit();
                            break;
                        case KeyEvent.VK_BACK_SPACE:
                            if (isCommendInput && CommendInput.toString() != "") {
                                CommendInput.delete(CommendInput.length() - 1, CommendInput.length());
                            }
                            if (CommendInput.length() == 0) {
                                new JVIM().commendExit();
                            }
                            break;
                    }
                    //字符判duan 我不知道为什么找不到冒号：（
                    switch (e.getKeyChar()) {
                        case ':':
                            new JVIM().commendStart();
                            break;
                    }
                    if (isCommendInput && !isSpecialKey(e.getKeyCode())) {
                        CommendInput.append(e.getKeyChar());
                    }
                }
                panel.repaint();
                System.out.println(TempCodeString);
            }
        });
    }

    private static boolean isSpecialKey(int keyCode) {
        // 判断是否为控制键、Shift 键、Alt 键、退格键
        if (keyCode == KeyEvent.VK_CONTROL ||
                keyCode == KeyEvent.VK_SHIFT ||
                keyCode == KeyEvent.VK_ALT ||
                keyCode == KeyEvent.VK_BACK_SPACE) {
            return true;
        }

        // 判断是否为 ESC、F1 到 F12、DEL、HOME、PgUp 和 PgDn 键 window
        return keyCode == KeyEvent.VK_ESCAPE ||
                keyCode == KeyEvent.VK_WINDOWS ||
                keyCode == KeyEvent.VK_F1 ||
                keyCode == KeyEvent.VK_F2 ||
                keyCode == KeyEvent.VK_F3 ||
                keyCode == KeyEvent.VK_F4 ||
                keyCode == KeyEvent.VK_F5 ||
                keyCode == KeyEvent.VK_F6 ||
                keyCode == KeyEvent.VK_F7 ||
                keyCode == KeyEvent.VK_F8 ||
                keyCode == KeyEvent.VK_F9 ||
                keyCode == KeyEvent.VK_F10 ||
                keyCode == KeyEvent.VK_F11 ||
                keyCode == KeyEvent.VK_F12 ||
                keyCode == KeyEvent.VK_LEFT ||
                keyCode == KeyEvent.VK_DOWN ||
                keyCode == KeyEvent.VK_UP ||
                keyCode == KeyEvent.VK_RIGHT ||
                keyCode == KeyEvent.VK_DELETE ||
                keyCode == KeyEvent.VK_HOME ||
                keyCode == KeyEvent.VK_END ||
                keyCode == KeyEvent.VK_PAGE_UP ||
                keyCode == KeyEvent.VK_ENTER ||
                keyCode == KeyEvent.VK_PAGE_DOWN;
    }

    public static int nowShowHow(StringBuffer[] input) {
        int s = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] != null) {
                s++;
            } else {
                break;
            }
        }
        return s;
    }
    public static int nowShowHowString(String[] input) {
        int s = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] != null) {
                s++;
            } else {
                break;
            }
        }
        return s;
    }

    public void getRainbow() {
        isRainbow = !isRainbow;
    }

    public void setStartLine(int num) {
        startLine = num;
    }

    public void changeBD() {
        isBD = !isBD;
    }

    public void setIsLSP() {
        isLSP = !isLSP;
    }

    public String getNowWhere() {
        return nowWhereis;
    }

    public void setNowWhere(String path) {
        nowWhereis = path;
    }

    public void setTheCHLmode(String str) {
        theCHLmode = str;
        new HighLight().setCHLfile(theCHLmode);
    }

    public int getTextLine() {
        return TextLine;
    }

    public void changeshadow() {
        isShadow = !isShadow;
    }

    public void changeCodelight() {
        isHighlight = !isHighlight;
    }

    public void setTextLine(int num, int kick) {
        TextLine = num;
        KickNow = kick;
    }

    public void TempStringSet(String str, int line) {
        CodeString[line] = new StringBuffer(str);
    }

    public void TempStringClear() {
        CodeString = new StringBuffer[9999];
        CodeString[0] = new StringBuffer();
    }

    private void checkPanelPageDown() {
        if (fontMetrics.getHeight() * (TextLine - startLine) >= frame.getHeight() - 100) {
            System.out.println("down!!");
            startLine++;
            CodeString[nowShowHow(getTempStr()) + 1] = new StringBuffer();
        }
    }

    public int getKickNow() {
        return KickNow;
    }

    //Color test = new Color(59, 250, 0);
    private void checkPanelPageUP() {
        if (startLine > 0 && fontMetrics.getHeight() * (TextLine - startLine) <= 10) {
            System.out.println("UP!!");
            startLine--;
        }
    }

    public String getCHLmode() {
        return theCHLmode;
    }

    private void commendExit() {
        isCommendInput = false;
        CommendInput = new StringBuffer();
    }

    private void commendStart() {
        System.out.println("reset");
        isCommendInput = true;
    }
}