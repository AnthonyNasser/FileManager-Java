import java.io.File;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

// Creates and Renders tree of files
public class TreeRenderer {

  public JTree tree;
  private DefaultTreeModel treeModel;
  private String path;

  public TreeRenderer(String p) {
    this.path = p;
    tree = new JTree();
    buildTree();
  }

  public void buildTree() {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
    treeModel = new DefaultTreeModel(root);

    FileFetcher fetcher = new FileFetcher(); 

    File[] baseFile = fetcher.fetchFiles(this.path);
    fetcher.nodify(baseFile, root);
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
    public void nodify(File[] files, DefaultMutableTreeNode root) {
          if (files != null) {
            for (int i = 0; i < files.length; i++) {
  
              DefaultMutableTreeNode node = new DefaultMutableTreeNode(
                  files[i].getAbsolutePath()
              );
              // Directory Detector
              if (files[i].isDirectory()) {
                  DefaultMutableTreeNode subroot = new DefaultMutableTreeNode(
                    files[i].getAbsolutePath()
                  );
                  root.add(subroot);
  
                  ArrayList<String> dFiles = this.directoryFiles(files[i].getAbsolutePath());
  
                  if (dFiles.size() != 0 && dFiles.get(0).equals("empty")) {
                    break;
                  }
  
                  for (String k : dFiles) {
                    DefaultMutableTreeNode subnode = new DefaultMutableTreeNode(k);
                    if (new File(k).isDirectory()) 
                        subroot.add(subnode);
                  }
              } else {
                  root.add(node);
              }
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

        if (files.length != 0) { 
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
  }
}