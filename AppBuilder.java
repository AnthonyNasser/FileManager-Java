import java.awt.BorderLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class AppBuilder extends JFrame{

    JPanel midPanel, topPanel;
    JFrame baseFrame;
    JMenuBar menuBar;
    JToolBar toolBar;
    public static JDesktopPane dp;
    FileManagerFrame initialFrame;
    FilePanel initialFilePanel;

    public static final int WIDTH_FM = 750;
    public static final int HEIGHT_FM = 450;

    private final int WIDTH_JF = 950;
    private final int HEIGHT_JF = 700;

    public AppBuilder() {
        midPanel = new JPanel();
        topPanel = new JPanel();
        baseFrame = this;
        menuBar = new MenuBar();
        toolBar = new ToolBar();
        dp = new JDesktopPane();
        initialFrame = new FileManagerFrame();
        initialFilePanel = new FilePanel();

        String userDrive = ToolBar.drives.getSelectedItem().toString().substring(0, 3);
        initialFilePanel.leftDirPanel.treeRenderer.rootPath = userDrive;
        ToolBar.activeDrive = userDrive;
        initialFilePanel.leftDirPanel.treeRenderer.buildTree();
        initialFrame.setTitle(ToolBar.activeDrive);

        initialFrame.setSize(WIDTH_FM, HEIGHT_FM);
        initialFrame.setTitle(ToolBar.activeDrive);
    }
    
    public void buildApp() {
        this.setLocationRelativeTo(null);
        this.setSize(WIDTH_JF, HEIGHT_JF);
        this.setTitle("Epic Java File-Manager");

        topPanel.setLayout(new BorderLayout());
        midPanel.setLayout(new BorderLayout());

        topPanel.add(menuBar, BorderLayout.NORTH);
        topPanel.add(toolBar, BorderLayout.SOUTH);

        initialFrame.add(initialFilePanel);
        dp.add(initialFrame);

        midPanel.add(dp, BorderLayout.CENTER);
        midPanel.add(topPanel, BorderLayout.NORTH);

        this.add(midPanel, BorderLayout.CENTER);

        initialFrame.setVisible(true);
        midPanel.setVisible(true);
        this.setVisible(true);
    }
}