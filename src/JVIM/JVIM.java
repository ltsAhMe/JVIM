package JVIM;

import JVIM.Commend.commend;
import JVIM.code.HighLight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Collections;

public class JVIM {
    static Color textColor = Color.white;
    static String theCHLmode = "java";
    static String nowWhereis = null;
    static StringBuffer CommendInput = new StringBuffer();
    static boolean isCommendInput = false;
    static boolean isHighlight = false;
    static StringBuffer[] TempString = new StringBuffer[9999];
    static JFrame frame;
    static boolean isShadow = false;
    static int startLine = 0;
    static int TextLine = 0;
    static int KickNow = 0;
    static JPanel panel;
    static Font TextFont;
    static Boolean isInput = false;
    static FontMetrics fontMetrics;
    Color test = new Color(157, 57, 57);

    public static void main(String[] args) {
        init("JVIM", new Dimension(500, 400));
    }

    public static void init(String title, Dimension Size) {
        System.out.println(nowShowHow());
        TextFont = todo.fontget();
        fontMetrics = getPanel().getFontMetrics(TextFont);
        new HighLight().setCHLfile(theCHLmode);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, Collections.emptySet());
        frame = new JFrame(title);
        panel = getPanel();
        PanelgetKey();
        frame.setSize(Size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //LMAO
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(frame, ":q quit");
            }
        });
        //LMAO
        frame.add(panel);
        frame.setVisible(true);
        TempString[0] = new StringBuffer();
    }

    public static StringBuffer[] getTempStr() {
        return TempString;
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
                    for (int i = 0; i < nowShowHow(); i++) {
                        if (i != nowShowHow() - startLine) {
                            g2d.drawString(TempString[i].toString(), 12, 18 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                        }
                    }
                }
                //text
                if (!isHighlight) {
                    g2d.setColor(textColor);
                    for (int i = 0; i < nowShowHow(); i++) {
                        if (i != nowShowHow() - startLine) {
                            g2d.drawString(TempString[i].toString(), 10, 20 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                        }
                    }
                }
                //code highlight
                if (isHighlight) {
                    for (int i = 0; i < nowShowHow(); i++) {
                        for (int s = 0; s < TempString[i].length(); s++) {
                            try {
                                if (!TempString[i].substring(s, s + 1).equals(" ")) {
                                    g2d.setColor(new HighLight().colorReader(TempString[i].toString(), s));
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            if (i != nowShowHow() - startLine) {
                                g2d.drawString(TempString[i].substring(s, s + 1), 10 + fontMetrics.stringWidth(TempString[i].substring(0, s)), 20 + (i * fontMetrics.getHeight()) - (startLine * fontMetrics.getHeight()));
                            }
                        }
                    }
                }
                //kick
                if (isInput) {
                    g2d.setColor(Color.white);
                    g2d.fillRect(10 + fontMetrics.stringWidth(TempString[TextLine].substring(0, KickNow)), 7 + ((TextLine - startLine) * fontMetrics.getHeight()), 2, fontMetrics.getHeight());
                }
                //mode show
                g2d.setColor(Color.black);
                g2d.fillRect(0, frame.getHeight() - 85, frame.getWidth(), 100);
                g2d.setColor(Color.darkGray);
                g2d.fillRect(0, 0, 5, panel.getHeight() - 40);
                g2d.setColor(Color.white);
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
                                    TempString[TextLine].delete(KickNow - 1, KickNow);
                                    KickNow--;
                                }
                                //返回上一行
                                if (TextLine > 0 && KickNow == 0) {
                                    KickNow = TempString[TextLine - 1].length();
                                    TempString[TextLine - 1].append(TempString[TextLine]);
                                    TempString[TextLine] = new StringBuffer();
                                    TextLine--;
                                }
                                new JVIM().checkPanelPageUP();
                                break;
                            //left
                            case KeyEvent.VK_LEFT:
                                if (KickNow > 0) {
                                    KickNow--;
                                }
                                break;
                            //right
                            case KeyEvent.VK_RIGHT:
                                if (KickNow < TempString[TextLine].length()) {
                                    KickNow++;
                                }
                                break;
                            //off input mode
                            case KeyEvent.VK_ESCAPE:
                                isInput = false;
                                break;
                            //回车
                            case KeyEvent.VK_ENTER:
                                if (TempString[TextLine + 1] == null) {
                                    TempString[TextLine + 1] = new StringBuffer();
                                }
                                if (TempString[TextLine+1]!=null&& !TempString[TextLine+1].toString().equals("")) {
                                    StringBuffer[] temp = new StringBuffer[TempString.length];
                                    for (int i = 0; i < TempString.length; i++) {
                                        if (TempString[i] != null) {
                                            temp[i] = new StringBuffer(TempString[i].toString());
                                        }
                                    }

                                    for (int i = 0; i <= nowShowHow(); i++) {
                                        if (i==0){
                                            temp[TextLine + 1] = new StringBuffer(TempString[TextLine].substring(KickNow,TempString[TextLine].length()));
                                        }else {
                                            temp[TextLine + 1 + i] = TempString[TextLine + i];
                                        }
                                    }
                                    TempString = temp;
                                }else {
                                    TempString[TextLine+1].append(TempString[TextLine].substring(KickNow, TempString[TextLine].length()));
                                    TempString[TextLine].delete(KickNow, TempString[TextLine].length());
                                }
                                KickNow = TempString[TextLine + 1].length();

                                TextLine++;
                                new JVIM().checkPanelPageDown();
                                break;
                            case KeyEvent.VK_UP:
                                if (TextLine > 0) {
                                    if (KickNow > TempString[TextLine - 1].length()) {
                                        KickNow = TempString[TextLine - 1].length();
                                    }
                                    TextLine--;
                                }
                                new JVIM().checkPanelPageUP();
                                break;
                            case KeyEvent.VK_DOWN:
                                if (TempString[TextLine + 1] != null) {
                                    if (KickNow > TempString[TextLine + 1].length()) {
                                        KickNow = TempString[TextLine + 1].length();
                                    }
                                    TextLine++;
                                }
                                new JVIM().checkPanelPageDown();

                                break;
                        }

                    } else {
                        // 不是特殊按键
                        TempString[TextLine].insert(KickNow, e.getKeyChar());
                        KickNow++;
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
                System.out.println(nowShowHow());
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

    public static int nowShowHow() {
        int s = 0;
        for (int i = 0; i <= TempString.length; i++) {
            if (TempString[i] != null) {
                s++;
            } else {
                break;
            }
        }
        return s;
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

    public void TempStringSet(String str, int line) {
        TempString[line] = new StringBuffer(str);
    }

    private void checkPanelPageDown() {
        if (fontMetrics.getHeight() * (TextLine - startLine) >= frame.getHeight() - 100) {
            System.out.println("down!!");
            startLine++;
            TempString[nowShowHow() + 1] = new StringBuffer();
        }
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