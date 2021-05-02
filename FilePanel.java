import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import java.awt.Dimension;

public class FilePanel extends JSplitPane {
    private DirectoryPanel leftDirPanel;
    private DirectoryPanel rightDirPanel;

    private JTree leftTree;
    private JTree rightTree;

    public String pathLeft;
    public String pathRight;

    private TreeSelectionListener listener = new FileActionListener();
    // Cascading logic goes here
    // instantiates JSplitPane with two Directory Panels
    public FilePanel() {
        leftDirPanel = new DirectoryPanel("C:\\Users\\Antho\\Documents\\Projects");
        rightDirPanel = new DirectoryPanel("C:\\Users\\Antho\\Documents\\Projects");

        leftTree = leftDirPanel.getTree();
        rightTree = rightDirPanel.getTree();

        this.setLeftComponent(leftDirPanel);
        this.setRightComponent(rightDirPanel);

        this.setSize(200, 200);
        this.setVisible(true);


        leftTree.addTreeSelectionListener(listener);
        rightTree.addTreeSelectionListener(listener);
    }

    // Action Listener to Execute Files
    // Action Listener to change right directory panel based on left click

    // Action listener to Rename
    // Action Listener to Delete
    // Action Listener to Copy/Paste

    public class FileActionListener implements TreeSelectionListener {

        private TreeSelectionEvent event;
      
        /* 
        * @desc: execute a file on click
        * @param: The file thats clicked on as a TreeSelectionEvent
        */
        @Override
        public void valueChanged(TreeSelectionEvent e) {
          this.event = e;
          TreePath path = event.getPath();
          FileExecute executor = new FileExecute();
          File pathFile = new File(path.getLastPathComponent().toString());
          
          System.out.println(path.getLastPathComponent());
          if (pathFile.isDirectory()) {
            displayRight(e);
            executor.execute(path.getLastPathComponent());
            System.out.println(path.getLastPathComponent());
          } else {
            executor.execute(path.getLastPathComponent());
          }
        }
      
        public void displayRight(TreeSelectionEvent e) {
          e = event;
          rightDirPanel = new DirectoryPanel(event.getPath().getLastPathComponent().toString());
          JTree t = rightDirPanel.getTree();
          t.addTreeSelectionListener(listener);
          FilePanel.this.setRightComponent(rightDirPanel);
          rightDirPanel.setViewportView(rightDirPanel.getTree());
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
