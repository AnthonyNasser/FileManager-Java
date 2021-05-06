import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

// Creates and Renders tree of files
public class TreeRenderer {

  public JTree tree;
  public DefaultTreeModel treeModel;
  public String path;
  public String rootPath = "";
  public DefaultMutableTreeNode root;
  int index = 0;

  public TreeRenderer(String p) {
    this.path = p;
    tree = new JTree();
    tree.setDropTarget(new TreeDropTarget());

    tree.setExpandsSelectedPaths(false);

    DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
    renderer.setLeafIcon(renderer.getClosedIcon());
    buildTree();
  }

  public void buildTree() {
    // DefaultMutableTreeNode root = new DefaultMutableTreeNode(FilePanel.defaultPathway);

    root = new DefaultMutableTreeNode(rootPath);
    treeModel = new DefaultTreeModel(root);

    FileFetcher fetcher = new FileFetcher();

    fetcher.nodify(rootPath, root);
    tree.setModel(treeModel);
  }

  public JTree getTree() {
    return this.tree;
  }

  public class FileFetcher {

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

    /*
     * @desc: get all files within file array and puts them in a root file, gets files only if false specified
     * @params: File array to put into root, the root node, directory flag tells whether to get directories
     */
    public void nodify(String baseFile, DefaultMutableTreeNode root) {
      File baseF = new File(baseFile);
      File[] fileNames = baseF.listFiles();

      if (fileNames == null) {
        return;
      }

      for (File f : fileNames) {
        String sf = f.getAbsolutePath();
        String showName = f.getName();
        DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(showName);
        root.add(subroot);

        if (f.isDirectory()) {
          File subF = new File(sf);
          File[] subFileName = subF.listFiles();
          String[] subFileNames = subF.list();

          if (subFileNames != null) {
            for (File sF : subFileName) {
              String subName = sF.getName();
              DefaultMutableTreeNode subFF = new DefaultMutableTreeNode(
                subName
              );
              subroot.add(subFF);
            }
          }
        }
        root.add(subroot);
      }
    }
  }

  /*
   * @desc: called when fetcher finds a folder, gets all files within folder and returns them
   * @param: Directory to get files from
   * @return: All files within the directory as an ArrayList
   * !! MAKE SURE ALL ARRAYS ARE ARRAYLISTS !!
   */
  public ArrayList<String> directoryFiles(String directoryName) {
    ArrayList<String> directoryFiles = new ArrayList<String>();
    File baseDirectory = new File(directoryName);
    File[] files;
    files = baseDirectory.listFiles();

    if (files != null) {
      for (int i = 0; i < files.length; i++) {
        directoryFiles.add(files[i].getAbsolutePath());
      }
      return directoryFiles;
    } else {
      ArrayList<String> empty = new ArrayList<String>();
      empty.add("empty");
      return empty;
    }
  }

  public class TreeDropTarget extends DropTarget {

    public void drop(DropTargetDropEvent evt) {
      try {
        evt.acceptDrop(DnDConstants.ACTION_COPY);
        List draggedFiles = new ArrayList();

        if (
          (
            evt
              .getTransferable()
              .isDataFlavorSupported((DataFlavor.stringFlavor))
          )
        ) {
          String temp = (String) evt
            .getTransferable()
            .getTransferData(DataFlavor.stringFlavor);
          String[] next = temp.split("\\n");

          for (int i = 0; i < next.length; i++) {
            System.out.println(next[i]);
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.add(new DefaultMutableTreeNode(next[i]));
            model.reload(root);
            index++;
          }
        } else {
          draggedFiles =
            (List) evt
              .getTransferable()
              .getTransferData(DataFlavor.javaFileListFlavor);
          for (Object f : draggedFiles) {
            System.out.println(f.toString());
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.add(new DefaultMutableTreeNode(f.toString()));
            model.reload(root);
            index++;
          }
        }
      } catch (UnsupportedFlavorException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
