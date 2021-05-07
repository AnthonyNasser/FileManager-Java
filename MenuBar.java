import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuBar extends JMenuBar {

  JMenu file;
  JMenu help;
  JMenu tree;
  JMenu window;
  File[] paths;

  JMenuBar menubar;

  public MenuBar() {
    menubar = new JMenuBar();
    buildMenuBar();
    this.setLayout(new BorderLayout());
    this.setSize(10000, 50);
    this.add(menubar);
    setVisible(true);
  }

  private void fileItems() {
    JMenuItem rename = new JMenuItem("Rename");
    JMenuItem copy = new JMenuItem("Copy");
    JMenuItem delete = new JMenuItem("Delete");
    JMenuItem run = new JMenuItem("Run");
    JMenuItem exit = new JMenuItem("Exit");

    rename.addActionListener(new RightClickMenu.RenamePopUp());
    copy.addActionListener(new RightClickMenu.CopyPopUp());
    delete.addActionListener(new RightClickMenu.DeleteActionListener());
    run.addActionListener(new RunExecute());
    exit.addActionListener(new ExitActionListener());

    file.add(rename);
    file.add(copy);
    file.add(delete);
    file.add(run);
    file.add(exit);
  }

  private void helpItems() {
    JMenuItem h = new JMenuItem("Help");
    h.addActionListener(new helpButtonActionListener());
    help.add(h);
  }

  private void treeItems() {
    JMenuItem t = new JMenuItem("Expand Branch");
    JMenuItem t2 = new JMenuItem("Collapse Branch");
    ActionListener expand = new FilePanel.ExpandTreeActionListener();
    ActionListener collapse = new FilePanel.CollapseTreeActionListener();
    t.addActionListener(expand);
    t2.addActionListener(collapse);
    tree.add(t);
    tree.add(t2);
  }

  private void windowItems() {
    JMenuItem w = new JMenuItem("New Window");
    JMenuItem w2 = new JMenuItem("Cascade");
    ActionListener buttonAL = new ButtonActionListener();
    ActionListener cascadeAL = new FileManagerFrame.CascadeActionListener();
    w.addActionListener(buttonAL);
    w2.addActionListener(cascadeAL);
    window.add(w);
    window.add(w2);
  }

  private void buildMenuBar() {
    file = new JMenu("File");
    tree = new JMenu("Tree");
    window = new JMenu("Window");
    help = new JMenu("Help");

    fileItems();
    treeItems();
    windowItems();
    helpItems();

    menubar.add(file);
    menubar.add(tree);
    menubar.add(window);
    menubar.add(help);
  }

  private class ExitActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
  }

  private class helpButtonActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JDialog helpBox = new HelpBox();
      helpBox.setVisible(true);
    }
  }

  private class RunExecute implements ActionListener {

    FilePanel.FileExecute executor = new FilePanel.FileExecute();

    @Override
    public void actionPerformed(ActionEvent e) {
      FilePanel fp = FilePanel.getActiveFilePanel();
      executor.execute(
        new File(
          fp.rightFileList
            .get(fp.rightList.getMaxSelectionIndex())
            .getAbsolutePath()
        )
      );
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
      FileManagerFrame fileManager = new FileManagerFrame();
      fileManager.fp = filePanel;
      
      String userDrive = ToolBar.drives
      .getSelectedItem()
      .toString()
      .substring(0, 3);
      
      fileManager.add(fileManager.fp);
      fileManager.fp.leftDirPanel.treeRenderer.rootPath = userDrive;
      ToolBar.activeDrive = userDrive;
      fileManager.fp.leftDirPanel.treeRenderer.buildTree();
      fileManager.setTitle(ToolBar.activeDrive);
      
      AppBuilder.dp.add(fileManager);
    }
  }
}
