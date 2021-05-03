import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileSystemView;

public class ToolBar extends JToolBar{
    JComboBox drives;
    JButton details;
    JButton simple;
    JPanel toolPanel;

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
        for (File file : filePaths) {
        	String fileName = file.toString();
            drives.addItem(fileName);
        }

        details = new JButton("Details");
        simple = new JButton("Simple");
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

    public void directoryItems() {
        filePaths = File.listRoots();
        for (File i : filePaths) {
          System.out.println(i.getAbsolutePath());
          fileNames.add(i.getAbsolutePath());
        }
      }
    
    public class ToolBarActionListener implements ActionListener{
      @Override
      public void actionPerformed(ActionEvent e) {
        String fileName = (String) drives.getSelectedItem();
        FilePanel.leftDirectoryPath = fileName;
        FilePanel.defaultPathway = FilePanel.leftDirectoryPath;
      }	
    }
}