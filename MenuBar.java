import java.io.File;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.BorderLayout;

public class MenuBar extends JPanel {

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
    JMenuItem exit = new JMenuItem("Exit");
    exit.addActionListener(new ExitActionListener());
    file.add(exit);
  }

  private void helpItems() {
    JMenuItem h = new JMenuItem("Help");
    help.add(h);
  }

  private void treeItems() {
    JMenuItem t = new JMenuItem("Tree");
    tree.add(t);
  }

  private void windowItems() {
    JMenuItem w = new JMenuItem("New Window");
    ActionListener buttonAL = new FileManagerFrame.ButtonActionListener();
    w.addActionListener(buttonAL);
    window.add(w);

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
}