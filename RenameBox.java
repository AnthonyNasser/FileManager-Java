import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RenameBox extends JDialog {

  private final JPanel contentPanel = new JPanel();
  public JTextField textField;
  public JTextField textField_1;

  /**
   * Create the dialog.
   */
  public RenameBox(String fileName, String command) {
    this.setTitle(command);
    textField = new JTextField(fileName);
    textField_1 = new JTextField("");

    setBounds(100, 100, 399, 181);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    {
      textField.setBounds(96, 55, 170, 20);
      contentPanel.add(textField);
      textField.setColumns(10);
      textField.requestFocusInWindow();
    }
    {
      JButton btnNewButton = new JButton(command);
      btnNewButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            FilePanel fp = FilePanel.getActiveFilePanel();
            if (command == "Rename") {
              fp.rename(new File(textField.getText()), textField_1.getText());
            } else if (command == "Copying") {
              fp.copy(new File(textField.getText()));
            }
          }
        }
      );
      btnNewButton.setBounds(297, 104, 100, 23);
      btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
      contentPanel.add(btnNewButton);
    }
    {
      JButton btnNewButton_1 = new JButton("Cancel");
      btnNewButton_1.setBounds(295, 54, 100, 23);
      contentPanel.add(btnNewButton_1);
    }

    textField_1.setBounds(96, 106, 170, 20);
    contentPanel.add(textField_1);
    textField_1.setColumns(10);

    JLabel lblNewLabel = new JLabel("From: ");
    lblNewLabel.setBounds(40, 58, 46, 14);
    contentPanel.add(lblNewLabel);

    JLabel lblNewLabel_1 = new JLabel("To: ");
    lblNewLabel_1.setBounds(40, 109, 46, 14);
    contentPanel.add(lblNewLabel_1);

    JLabel lblNewLabel_2 = new JLabel("Current Directory");
    lblNewLabel_2.setFont(new Font("Roboto", Font.BOLD, 12));
    lblNewLabel_2.setBounds(40, 30, 272, 14);
    contentPanel.add(lblNewLabel_2);

    JLabel lblNewLabel_3 = new JLabel(ToolBar.activeDrive);
    lblNewLabel_3.setBounds(161, 30, 46, 14);
    contentPanel.add(lblNewLabel_3);
    {
      JPanel buttonPane = new JPanel();
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }
  }
}
