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

  public class ButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      /*
       *  dp(DesktopPane) is now static
       *  Jinternal is created and filePanel is added in it
       *  Jinternal is added to static dp
       */
      FilePanel filePanel = new FilePanel();
      JInternalFrame fileManager = FileManagerFrame.this;
      FileManagerFrame.this.fp = filePanel;

      String userDrive = ToolBar.drives
        .getSelectedItem()
        .toString()
        .substring(0, 3);
      fileManager.add(fp);
      fp.leftDirPanel.treeRenderer.rootPath = userDrive;
      ToolBar.activeDrive = userDrive;
      fp.leftDirPanel.treeRenderer.buildTree();
      fileManager.setTitle(ToolBar.activeDrive);

      AppBuilder.dp.add(fileManager);
    }
  }
}
