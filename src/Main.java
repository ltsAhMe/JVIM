
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {
    static StringBuffer[] TempString = new StringBuffer[100];
    static int Hz = 30;
    static JFrame frame;
    static int TextLine =0;
    static int KickNow =0;
    static JPanel panel;
    static Font TextFont = new Font("Arial", Font.BOLD, 13);
    static Boolean isKickShow = true;
    static Boolean isInput = false;
    static FontMetrics fontMetrics = getPanel().getFontMetrics(TextFont);
    public static void main(String[] args) {
        init("JVIM - swing",new Dimension(500,400));
    }
    public static void init(String title, Dimension Size){
        frame = new JFrame(title);
        panel = getPanel();
        PanelgetKey();
        frame.setSize(Size);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        TempString[TextLine] = new StringBuffer("");
        Timer timer = new Timer(Hz/1000, e -> {
            panel.repaint();
        });
        Timer KickShow = new Timer(800, e -> {
            isKickShow = !isKickShow;
        });
        timer.start();
        KickShow.start();
    }
   private static JPanel getPanel(){
        return new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                //background
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0,0,frame.getWidth(),frame.getHeight());
                //text
                g2d.setFont(TextFont);
                g2d.setColor(Color.white);
                for (int i=0;i<nowShowHow();i++){
                    g2d.drawString(TempString[i].toString(),10,20 + (i*fontMetrics.getHeight()));
                }


                //kick
                if (isInput) {
                    if (isKickShow) {
                        g2d.setColor(Color.white);
                        g2d.fillRect(10 + fontMetrics.stringWidth(TempString[TextLine].substring(0, KickNow)), 5+ (TextLine*fontMetrics.getHeight()), 2, 18);
                    }
                }
                //
            }
        };
   }
   private static void PanelgetKey(){
        panel.setFocusable(true);
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
             if (isInput){
                if (isSpecialKey(keyCode)) {
                    switch (keyCode) {
                        case KeyEvent.VK_BACK_SPACE:
                            //退格
                            if (KickNow > 0) {
                                TempString[TextLine].delete(KickNow - 1, KickNow);
                                KickNow--;
                            }
                            //返回上一行
                            if (TextLine>0 && KickNow==0){
                                KickNow = TempString[TextLine-1].length();
                                TempString[TextLine-1].append(TempString[TextLine]);
                                TempString[TextLine] = null;
                                TextLine--;
                            }
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
                            if (TempString[TextLine+1] == null){
                                TempString[TextLine+1] = new StringBuffer("");
                            }
                                TempString[TextLine+1].append(TempString[TextLine].substring(KickNow,TempString[TextLine].length()));
                            TempString[TextLine].delete(KickNow, TempString[TextLine].length());
                            KickNow=TempString[TextLine+1].length();
                            TextLine++;
                            break;
                        case KeyEvent.VK_UP:
                           if (TextLine>0){
                               if (KickNow > TempString[TextLine-1].length()){
                                   KickNow=TempString[TextLine-1].length();
                               }
                               TextLine--;
                           }
                            break;
                        case KeyEvent.VK_DOWN:
                                if (TempString[TextLine+1]!=null) {
                                    if (KickNow > TempString[TextLine + 1].length()) {
                                        KickNow = TempString[TextLine + 1].length();
                                    }
                                    TextLine++;
                                }

                            break;
                    }
                    System.out.println(KickNow);

                } else {
                    // 不是特殊按键
                    TempString[TextLine].insert(KickNow, e.getKeyChar());
                    KickNow++;
                    System.out.println(TempString[TextLine].toString());
                }
            }else {
                 switch (e.getKeyCode()){
                     case KeyEvent.VK_I:
                         isInput = true;
                 }
             }
                isKickShow = true;
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

        // 判断是否为 ESC、F1 到 F12、DEL、HOME、PgUp 和 PgDn 键
        if (keyCode == KeyEvent.VK_ESCAPE ||
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
                keyCode == KeyEvent.VK_PAGE_DOWN) {
            return true;
        }
        return false;
    }
    private static int nowShowHow(){
        int s = 0;
        for (int i=0;i<=TempString.length;i++){
            if (TempString[i] == null){
               break;
            }else {
                s++;
            }
        }
        return s;
    }
}