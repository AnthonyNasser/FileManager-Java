import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;

public class DirectoryPanel extends JScrollPane {

  String path;
  public TreeRenderer treeRenderer;
  public JTree tree;

  public DirectoryPanel(String p) {

    this.path = p;
    this.treeRenderer = new TreeRenderer(path);
    this.tree = treeRenderer.getTree();
    this.setMinimumSize(new Dimension(500,500));
    this.setViewportView(tree);
  }

  public JTree getTree() {
      return this.tree;
  }
}