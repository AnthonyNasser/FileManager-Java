import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class FilePanel extends JSplitPane {

  public FileActionListener listener;
  private RightSideMouse listenRight;
  public JScrollPane rightSide;
  public DefaultListModel model;
  public JList rightList;
  public ArrayList<File> rightFileList;

  public DirectoryPanel leftDirPanel;
  public static String leftDirectoryPath = ToolBar.activeDrive;
  public static String defaultPathway = "";
  public JTree leftTree;
  public static Boolean detailsState;

  public FilePanel() {
    listener = new FileActionListener();
    listenRight = new RightSideMouse();
    leftDirPanel = new DirectoryPanel(leftDirectoryPath);
    leftTree = leftDirPanel.getTree();

    rightSide = new JScrollPane();
    model = new DefaultListModel<>();
    rightList = new JList<>(model);
    rightFileList = new ArrayList<File>();
    rightSide.setViewportView(rightList);
    rightSide.setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL));
    rightList.setFont(new Font("Courier New", Font.PLAIN, 12));

    this.setLeftComponent(leftDirPanel);
    this.setRightComponent(rightSide);
    this.setOneTouchExpandable(true);

    leftTree.addTreeSelectionListener(listener);
    leftTree.addMouseListener((MouseListener) listener);
    rightList.addMouseListener((MouseListener) listenRight);

    this.setAutoscrolls(true);
    this.setSize(new Dimension(950, 700));
    rightList.setDragEnabled(true);
    rightList.setDropTarget(this.new listDropTarget());
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

  public static String formatToDetails(File file) {
    if (!FilePanel.detailsState) return "";
    int cutBy = 10;
    String fileName = file.getName();

    if (fileName.length() > cutBy) {
      fileName = fileName.substring(0, cutBy) + "...";
    }
    if (fileName.length() < cutBy) {
      int spacesToAdd = cutBy - fileName.length();
      for (int i = 0; i < spacesToAdd; i++) {
        fileName += " ";
      }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM / dd / yyyy");
    DecimalFormat decFormat = new DecimalFormat("#, ###");

    String formatted = String.format(
      "%-30s %-20s %-20s",
      fileName,
      dateFormat.format(file.lastModified()),
      decFormat.format(file.lastModified())
    );
    return formatted;
  }

  public void deleteFile(int index) {
    model.remove(index);
    rightFileList.remove(index);
  }

  public void rename(File oldFile, String newName) {
    int indexToChange = FilePanel.this.rightList.getSelectedIndex();
    File newFile = new File(newName);
    oldFile.renameTo(newFile);
    String n = newFile.getName();
    model.set(indexToChange, n);
  }

  public void copy(File fileToCopy) {
    File newFile = new File(fileToCopy.getName() + " - Copy");
    model.addElement(newFile);
    rightFileList.add(newFile);
    rightList.setModel(model);
  }

  public FilePanel getActive() {
    if (AppBuilder.dp == null) {
      return new FilePanel();
    }
    FileManagerFrame activeFrame = (FileManagerFrame) AppBuilder.dp.getSelectedFrame();
    if (activeFrame == null) {
      return new FilePanel();
    }
    return activeFrame.fp;
  }
  class listDropTarget extends DropTarget {

    public void drop(DropTargetDropEvent evt) {
      String temp = "";

      evt.acceptDrop(DnDConstants.ACTION_COPY);
      List result = new ArrayList();
      if (
        evt.getTransferable().isDataFlavorSupported(DataFlavor.stringFlavor)
      ) {
        try {
          temp =
            (String) evt
              .getTransferable()
              .getTransferData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
        String[] next = temp.split("\n");

        for (int i = 0; i < next.length; i++) model.addElement(next[i]);
      } else {
        try {
          result =
            (List) evt
              .getTransferable()
              .getTransferData(DataFlavor.javaFileListFlavor);
        } catch (UnsupportedFlavorException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        //process the input
        for (Object o : result) {
          System.out.println(o.toString());
          model.addElement(o.toString());
        }
      }
    }
  }

  public class FileActionListener
    extends MouseAdapter
    implements TreeSelectionListener {

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
      for (int i = 1; i < p.getPathCount(); i++) {
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
      if (files == null) {
        return;
      }

      model.clear();
      rightList.removeAll();
      rightFileList.clear();
      for (int i = 0; i < files.length; i++) {
        if (files[i].isDirectory()) {
          model.addElement("+++ " + files[i].getName());
          rightFileList.add(files[i]);
        }
      }
      for (int i = 0; i < files.length; i++) {
        if (!files[i].isDirectory()) {
          if (FilePanel.this.detailsState) {
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
          for (int i = 0; i < pathS.getPathCount(); i++) {
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

  public static FilePanel getActiveFilePanel() {
    if (AppBuilder.dp == null) {
      return new FilePanel();
    }
    FileManagerFrame activeFrame = (FileManagerFrame) AppBuilder.dp.getSelectedFrame();
    if (activeFrame == null) {
      return new FilePanel();
    }
    return activeFrame.fp;
  }

  public static class FileExecute {

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

    JPopupMenu rightPop;

    public void mouseClicked(MouseEvent e) {
      if (FilePanel.this.rightFileList.size() > 0) {
        if (e.getClickCount() > 1) {
          String pathS =
            FilePanel.this.rightFileList.get(
                FilePanel.this.rightList.getSelectedIndex()
              )
              .toString();
          executor.execute(pathS);
        }
        if (e.getButton() == 3) {
          rightPop = new RightClickMenu();
          rightPop.show(e.getComponent(), e.getX(), e.getY());
          rightPop.setVisible(true);
        }
      }
    }
  }

  /*
   * LOGIC FOR EXPANDING / COLLAPSING TREE
   */
  public static class ExpandTreeActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      FilePanel.setNodeExpandedState(
        FilePanel.getActiveFilePanel().leftTree,
        (DefaultMutableTreeNode) FilePanel.getActiveFilePanel().leftTree.getLastSelectedPathComponent(),
        true
      );
    }
  }

  public static class CollapseTreeActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      FilePanel.setNodeExpandedState(
        FilePanel.getActiveFilePanel().leftTree,
        (DefaultMutableTreeNode) FilePanel.getActiveFilePanel().leftTree.getLastSelectedPathComponent(),
        false
      );
    }
  }

  public static void setNodeExpandedState(
    JTree tree,
    DefaultMutableTreeNode node,
    boolean expanded
  ) {
    if (node != null) {
      ArrayList<DefaultMutableTreeNode> nodeList = new ArrayList<DefaultMutableTreeNode>();
      for (int i = 0; i < node.getChildCount(); i++) {
        nodeList.add((DefaultMutableTreeNode) node.getChildAt(i));
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
