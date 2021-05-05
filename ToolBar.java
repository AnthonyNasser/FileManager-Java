import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar{
    public static JComboBox drives;
    private JButton details;
    private JButton simple;
    private JPanel toolPanel;

    public static String activeDrive;

    File[] filePaths;
    ActionListener tbActionListener = new ToolBarActionListener();
    
    ArrayList<String> fileNames;

    public ToolBar() {
        buildToolBar();
    }

    private void buildToolBar() {
        // FileSystemView fsView = FileSystemView.getFileSystemView();
        filePaths = File.listRoots();

        drives = new JComboBox();
        drives.addActionListener(tbActionListener);

        System.out.println(filePaths[0].toString());
        activeDrive = filePaths[0].toString();
        for (File file : filePaths) {
        	String fileName = file.toString();
            drives.addItem(fileName);
        }

        details = new JButton("Details");
        DetailsActionBarListener dListener = new DetailsActionBarListener();
        details.addActionListener(dListener);

        simple = new JButton("Simple");
        SimpleActionBarListener sListener = new SimpleActionBarListener();
        simple.addActionListener(sListener);

        fileNames = new ArrayList<String>();
        toolPanel = new JPanel();
        toolPanel.add(drives);
        toolPanel.add(details);
        toolPanel.add(simple);

        toolPanel.setVisible(true);

        this.add(toolPanel);
        this.setFloatable(false);
        this.setVisible(true);
    }

    // private void detailButton() {
    //   ActionListener detailsAL = new ToolBarActionListener();
    //   ActionListener cascadeAL = new FileManagerFrame.CascadeActionListener();
    //   w.addActionListener(buttonAL);
    //   w2.addActionListener(cascadeAL);
    //   window.add(w);
    //   window.add(w2);
    // }

    public void directoryItems() {
        filePaths = File.listRoots();
        for (File i : filePaths) {
          fileNames.add(i.getAbsolutePath());
        }
      }
    
    public class ToolBarActionListener implements ActionListener{
      @Override
      public void actionPerformed(ActionEvent e) {
        String fileName = (String) drives.getSelectedItem();
        FilePanel.leftDirectoryPath = fileName;
        FilePanel.defaultPathway = FilePanel.leftDirectoryPath;

        String userDrive = drives.getSelectedItem().toString().substring(0, 3);

        userDrive += "\\";
        
        if (AppBuilder.dp == null) {
          return;
        }
        FileManagerFrame activeFrame = (FileManagerFrame) AppBuilder.dp.getSelectedFrame();
        if (activeFrame == null) {
          return;
        }
        activeFrame.setTitle(userDrive);
        FilePanel.leftDirPanel.treeRenderer.rootPath = userDrive;
        FilePanel.leftDirPanel.treeRenderer.buildTree();
      }	
    }

    public static class DetailsActionBarListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        FilePanel.detailsState = true;
        System.out.println(FilePanel.detailsState);
      }
    }

    public static class SimpleActionBarListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
        FilePanel.detailsState = false;
        System.out.println(FilePanel.detailsState);
      }
    }
}