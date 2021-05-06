import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.Font;

public class FilePanel extends JSplitPane {
    public FileActionListener listener = new FileActionListener();
    private RightSideMouse listenRight = new RightSideMouse();
    public JScrollPane rightSide;
    public DefaultListModel model;
    public JList rightList;
    public ArrayList<File> rightFileList;
  
    public DirectoryPanel leftDirPanel;
    public static String leftDirectoryPath = ToolBar.activeDrive;
    public static String defaultPathway = "";
    public static JTree leftTree;
    public static Boolean detailsState; 


    public FilePanel() {
    
        leftDirPanel = new DirectoryPanel(leftDirectoryPath);
        leftTree = leftDirPanel.getTree();

        rightSide = new JScrollPane();
        model = new DefaultListModel<>();
        rightList = new JList<>( model );
        rightFileList = new ArrayList<File>();
        rightSide.setViewportView(rightList);
        rightSide.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
        rightList.setFont(new Font("Roboto", Font.PLAIN, 12));

        this.setLeftComponent(leftDirPanel);
        this.setRightComponent(rightSide);
        this.setOneTouchExpandable(true);

        leftTree.addTreeSelectionListener(listener);
        leftTree.addMouseListener((MouseListener) listener);
        rightList.addMouseListener((MouseListener) listenRight);
        
        this.setAutoscrolls(true);
        this.setSize(new Dimension(950, 700));
        this.setVisible(true);

        // rightList.setCellRenderer(new DefaultListCellRenderer() {
        //   @Override
        //   public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean cellHasFocus) {
        //       super.getListCellRendererComponent(list, value, index, selected, cellHasFocus);
        //       if (value instanceof String) {
        //           if(rightFileList.size() > 0) {
        //               if (rightFileList.get(index).isDirectory()) {
        //                   setIcon(UIManager.getIcon("FileChooser.newFolderIcon"));
        //               } else {
        //                   setIcon(UIManager.getIcon("FileView.fileIcon"));
        //               }
        //               String name = (String) value;
        //               setText(name);
        //           }
        //       }
        //       return this;
        //   }
        // });

        detailsState = true;
    }

    public static String formatToDetails( File file ) {
        if (!FilePanel.detailsState)
          return "";
        int cutBy = 10;
        String fileName = file.getName();

        if (fileName.length() > cutBy) {
          fileName = fileName.substring(0, cutBy) + "...";
        }
        if (fileName.length() < cutBy) {
          int spacesToAdd = cutBy - fileName.length();
          for (int i = 0; i < spacesToAdd; i ++) {
            fileName += " ";
          }
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("MM / dd / yyyy");
        DecimalFormat decFormat = new DecimalFormat("#, ###");

        String formatted = String.format("%-55s %-30s %-30s", fileName, dateFormat.format(file.lastModified()), decFormat.format(file.lastModified()));
        return formatted;
      }

      public void deleteFile(int index) {
        model.remove(index);
        rightFileList.remove(index);
      }

      public void rename(String newName, int indexToChange) {
        File oldFile = rightFileList.get(indexToChange);
        File newFile = new File(newName);
        System.out.println("Renaming to: " + oldFile.renameTo(newFile));
        String n = newFile.getName();
        model.set(indexToChange, n);
      }

    public class FileActionListener extends MouseAdapter implements TreeSelectionListener {

        public TreeSelectionEvent event = null;
        FileExecute executor = new FileExecute();

        @Override
        public void valueChanged(TreeSelectionEvent e) {
          this.event = e;
          updatePanel(event);
        }
        
        public void updatePanel(TreeSelectionEvent e) {
          this.event = e;
          TreePath p = event.getPath();
          String path = p.getPathComponent(0).toString();
          for(int i = 1; i < p.getPathCount(); i++) {
            path += "\\" + p.getPathComponent(i);
          }
          DefaultMutableTreeNode node = (DefaultMutableTreeNode) leftTree.getLastSelectedPathComponent();
          if (node == null) {
            return;
          }
          File newFile = new File(path);
          if (newFile.isDirectory() && newFile.exists()) {
            try {
              if (newFile.list() != null) {
                for (String file : newFile.list()) {
                  if (new File(file).isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                  }
                }
                displayRight(newFile);
              }
            } catch (Exception err) {
              System.out.println("Cannot open File! " + err);
            }
          }
        }

        public void displayRight(File directory) {
          File[] files;
          files = directory.listFiles();
          if(files == null){
              return;
          }

          model.clear();
          rightList.removeAll();
          rightFileList.clear();
          for (int i = 0; i < files.length; i++) {
              if(files[i].isDirectory()){
                  model.addElement("+++ " + files[i].getName());
                  rightFileList.add(files[i]);
              }
          }
          for (int i = 0; i < files.length; i++){
            if (!files[i].isDirectory()){
              if (FilePanel.this.detailsState){
                  String fileWithDetails = FilePanel.this.formatToDetails(files[i]);
                  model.addElement("-- " + fileWithDetails);
              } else {
                  model.addElement("-- " + files[i].getName());
              }
            rightFileList.add(files[i]);
          }
        }
        rightList.setModel(model);
      }
      
      public void mouseClicked(MouseEvent e) {
        if (FilePanel.this.rightFileList.size() > 0) {
          if (e.getClickCount() > 2) {
            TreePath pathS = event.getPath();
            String path = "";
            for(int i = 0; i < pathS.getPathCount(); i++) {
                path += "\\" + pathS.getPathComponent(i);
            }
            executor.execute(path);
          }
        }
      }

    /*
    * @desc: get all files and folders within specified file name
    * @param: file to get stuff from
    * @return: an arraylist full of the folders and files
    */
    public File[] fetchFiles(String fileName) {
          File[] files;
          File baseFile = new File(fileName);

          files = baseFile.listFiles();
          return files;
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
    public class RightSideMouse extends MouseAdapter {
      FileExecute executor = new FileExecute();
      public void mouseClicked(MouseEvent e) {
        if (FilePanel.this.rightFileList.size() > 0) {
          if (e.getClickCount() > 1) {
            String pathS = FilePanel.this.rightFileList.get(FilePanel.this.rightList.getSelectedIndex()).toString();
            executor.execute(pathS);
          }
          if (e.getButton() == 3) {
            System.out.println("Right click detected");
            
            String pathS = FilePanel.this.rightFileList.get(FilePanel.this.rightList.getSelectedIndex()).getAbsolutePath();
            System.out.println( FilePanel.this.rightFileList.get(FilePanel.this.rightList.getSelectedIndex()).getPath());
            
            FilePanel.this.rename("BOOOGER", FilePanel.this.rightList.getSelectedIndex());
          }
        }
      }
    }



  public static class ExpandTreeActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FilePanel.setNodeExpandedState(leftTree, (DefaultMutableTreeNode) leftTree.getLastSelectedPathComponent(), true);
    }
  }

  public static class CollapseTreeActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        FilePanel.setNodeExpandedState(leftTree, (DefaultMutableTreeNode) leftTree.getLastSelectedPathComponent(), false);
    }
  }
  
  public static void setNodeExpandedState(JTree tree, DefaultMutableTreeNode node, boolean expanded) {
        if (node != null) {
          ArrayList<DefaultMutableTreeNode> nodeList = new ArrayList<DefaultMutableTreeNode>();
          for (int i = 0; i < node.getChildCount(); i++) {
            nodeList.add( (DefaultMutableTreeNode) node.getChildAt(i));
          }

          for (DefaultMutableTreeNode treeNode : nodeList) {
              setNodeExpandedState(tree, treeNode, expanded);
          }
          if (!expanded && node.isRoot()) {
              return;
          }
          TreePath path = new TreePath(node.getPath());
          if (expanded) {
              tree.expandPath(path);
          } else {
              tree.collapsePath(path);
          }
      }   
    }
}