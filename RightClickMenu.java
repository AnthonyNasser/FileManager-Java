import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RightClickMenu extends JPopupMenu {

  JMenuItem renameButton;
  JMenuItem copyButton;
  JMenuItem pasteButton;
  JMenuItem deleteButton;

  public RightClickMenu() {
    renameButton = new JMenuItem("Rename");
    copyButton = new JMenuItem("Copy");
    deleteButton = new JMenuItem("Delete");

    ActionListener renameAL = new RenamePopUp();
    renameButton.addActionListener(renameAL);

    ActionListener copyAL = new CopyPopUp();
    copyButton.addActionListener(copyAL);

    ActionListener deleteAL = new DeleteActionListener();
    deleteButton.addActionListener(deleteAL);

    this.add(renameButton);
    this.add(copyButton);
    this.add(deleteButton);
  }

  public static class RenamePopUp implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      RenameBox renameBox;
      FilePanel fp = FilePanel.getActiveFilePanel();

      renameBox =
        new RenameBox(
          fp.rightFileList.get(fp.rightList.getMaxSelectionIndex()).getName(),
          "Rename"
        );
      renameBox.setLocationRelativeTo(null);
      renameBox.setVisible(true);
    }
  }

  public static class CopyPopUp implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      RenameBox renameBox;
      FilePanel fp = FilePanel.getActiveFilePanel();

      renameBox =
        new RenameBox(
          fp.rightFileList.get(fp.rightList.getMaxSelectionIndex()).getName(),
          "Copying"
        );
      renameBox.setLocationRelativeTo(null);
      renameBox.setVisible(true);
    }
  }

  public static class DeleteActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      FilePanel fp = FilePanel.getActiveFilePanel();
      fp.deleteFile(fp.rightList.getMaxSelectionIndex());
    }
  }
}
