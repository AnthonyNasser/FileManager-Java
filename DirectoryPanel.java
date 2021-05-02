import javax.swing.JScrollPane;
import javax.swing.JTree;

public class DirectoryPanel extends JScrollPane {

  String path;
  public TreeRenderer treeRenderer;
  public JTree tree;

  public DirectoryPanel(String p) {
    this.path = p;
    this.treeRenderer = new TreeRenderer(path);
    this.tree = treeRenderer.getTree();
    this.setViewportView(tree);
  }

  public JTree getTree() {
      return this.tree;
  }

}