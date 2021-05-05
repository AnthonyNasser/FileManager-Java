import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;

public class DirectoryPanel extends JScrollPane {

  String path;
  public TreeRenderer treeRenderer;
  public JTree tree;

  public DirectoryPanel(String p) {
    this.path = "C:\\";
    this.treeRenderer = new TreeRenderer(path);
    this.tree = treeRenderer.getTree();
    this.setMaximumSize(new Dimension(AppBuilder.WIDTH_FM, AppBuilder.HEIGHT_FM));
    this.setViewportView(tree);
  }

  public JTree getTree() {
      return this.tree;
  }
}