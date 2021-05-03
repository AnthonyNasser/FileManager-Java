import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.tree.TreePath;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class FilePanel extends JSplitPane {
    private DirectoryPanel leftDirPanel;
    private DirectoryPanel rightDirPanel;
    public static String leftDirectoryPath = "C:\\";
    public static String rightDirectoryPath = "";
    public static String defaultPathway = "";
    private JTree leftTree;
    private JTree rightTree;
    public String pathLeft;
    public String pathRight;

    private TreeSelectionListener listener = new FileActionListener();

    // instantiates JSplitPane with two Directory Panels C:\\Users\\Antho\\Documents\\Projects
    public FilePanel() {
    
        leftDirPanel = new DirectoryPanel(leftDirectoryPath);
        rightDirPanel = new DirectoryPanel(rightDirectoryPath);

        leftTree = leftDirPanel.getTree();
        rightTree = rightDirPanel.getTree();

        this.setLeftComponent(leftDirPanel);
        this.setRightComponent(rightDirPanel);

        this.setSize(150, 150);
        this.setVisible(true);


        leftTree.addTreeSelectionListener(listener);
        leftTree.addMouseListener((MouseListener) listener);
        rightTree.addTreeSelectionListener(listener);
        rightTree.addMouseListener((MouseListener) listener);
    }

    // Action listener to Rename
    // Action Listener to Delete
    // Action Listener to Copy/Paste

    public class FileActionListener extends MouseAdapter implements TreeSelectionListener {

      private TreeSelectionEvent event;
      FileExecute executor = new FileExecute();

      /* 
      * @desc: execute a file on click
      * @param: The file thats clicked on as a TreeSelectionEvent
      */
      @Override
      public void valueChanged(TreeSelectionEvent e) {
        this.event = e;
        TreePath path = event.getPath();
        File pathFile = new File(path.getLastPathComponent().toString());
        if (pathFile.isDirectory()) {
          displayRight(e);
        }
      }

      public void displayRight(TreeSelectionEvent e) {
        e = event;
        FilePanel.rightDirectoryPath = event.getPath().getLastPathComponent().toString();
        FilePanel.defaultPathway = rightDirectoryPath;
        rightDirPanel = new DirectoryPanel(event.getPath().getLastPathComponent().toString());
        JTree t = rightDirPanel.getTree();
        t.addTreeSelectionListener(listener);
        t.addMouseListener((MouseListener) listener);
        FilePanel.this.setRightComponent(rightDirPanel);
        rightDirPanel.setViewportView(rightDirPanel.getTree());
      }

      public void mouseClicked(MouseEvent e) {
          if (e.getClickCount() > 1) {
            executor.execute(event.getPath().getLastPathComponent());
          }
          if (e.getButton() == 3) {
            System.out.println("Right click detected");
          }
      }
    }
    public class FileExecute {
    /*
    * @desc: opens or executes file
    * @param: object to execute
    */
    public void execute(Object object) {
        String path = object.toString();
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(path));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }
  }
}