import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class AppBuilder {

    static JDesktopPane dp;

    public AppBuilder() {
        final int DIMENSION_X = 950;
        final int DIMENSION_Y = 700;

        JPanel mb = new MenuBar();
        JToolBar tb = new ToolBar();
        dp = new JDesktopPane();
    
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        FilePanel fp = new FilePanel();
        JInternalFrame jif = new FileManagerFrame();

        
        jif.add(fp);
        dp.add(jif,BorderLayout.CENTER);
        jf.add(dp);
        jf.add(mb,BorderLayout.NORTH);
        jf.add(tb, BorderLayout.PAGE_START);
    
        jf.setSize(DIMENSION_X, DIMENSION_Y);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
}