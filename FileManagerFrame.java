import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JInternalFrame;

public class FileManagerFrame extends JInternalFrame {
    public FileManagerFrame() {
        this.setResizable(true);
        this.setMaximizable(true);
        this.setIconifiable(true);
        this.setClosable(true);
        this.setSize(800,500);
        this.setVisible(true);
    }
    public static class ButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /* 
            *  dp(DesktopPane) is now static
            *  Jinternal is created and filePanel is added in it
            *  Jinternal is added to static dp
            */
            FilePanel fp = new FilePanel();
            JInternalFrame fileManager = new FileManagerFrame();
            fileManager.add(fp);
            AppBuilder.dp.add(fileManager);
        }
        // @Override
        // public void actionPerformed(ActionEvent e) {
        //     if (e.getActionCommand().equals("Window"))
        // }
    } 
}