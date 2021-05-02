
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class App {
    public static void main(String[] args) {
        final int DIMENSION = 1500;
        JFrame jf = new JFrame();
        // ToolBar tb = new ToolBar();
        JPanel jp = new JPanel();
        // MenuBar mb = new MenuBar();
        FilePanel fp = new FilePanel();

        jf.setSize(DIMENSION, DIMENSION);
        // jp.add(tb, BorderLayout.SOUTH);
        
        // jf.add(mb);
        jp.add(fp);
        jf.add(jp);
        jf.setVisible(true);

    }
}
