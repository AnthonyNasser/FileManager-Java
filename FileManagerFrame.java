import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;

public class FileManagerFrame extends JInternalFrame {

  FilePanel fp;

  public FileManagerFrame() {
    fp = new FilePanel();
    this.setResizable(true);
    this.setMaximizable(true);
    this.setIconifiable(true);
    this.setClosable(true);
    this.setSize(750, 450);
    this.setVisible(true);
  }

  public static class CascadeActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JInternalFrame[] frames = AppBuilder.dp.getAllFrames();
      int x = 30 * (frames.length - 1);
      int y = 30 * (frames.length - 1);
      if (frames.length > 1) {
        for (int i = 0; i < frames.length; i++) {
          frames[i].setLocation(x, y);
          x -= 30;
          y -= 30;
        }
      }
    }
  }
}
