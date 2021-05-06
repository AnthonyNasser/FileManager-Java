import javax.swing.JButton;
import javax.swing.JPopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RightClickMenu extends JPopupMenu {

  JButton renameButton;
  JButton copyButton;
  JButton pasteButton;
  JButton deleteButton;

  public RightClickMenu() {
    renameButton = new JButton("Rename");
    copyButton = new JButton("Copy");
    pasteButton = new JButton("Paste");
    deleteButton = new JButton("Delete");

    ActionListener renameAL = new RenamePopUp();
    renameButton.addActionListener(renameAL);

    this.add(renameButton);
    this.add(copyButton);
    this.add(pasteButton);
    this.add(deleteButton);
    }
    public class RenamePopUp implements ActionListener {
        RenameBox renameBox;
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(RenameBox.textField.getText());
            System.out.println(RenameBox.textField_1.getText());
            renameBox = new RenameBox();
            renameBox.setLocationRelativeTo(null);
            renameBox.setVisible(true);
        }
    }
}
