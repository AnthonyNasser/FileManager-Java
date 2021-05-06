import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class RenameBox extends JDialog {

  private final JPanel contentPanel = new JPanel();
  public static JTextField textField = new JTextField("", 50);
  public static JTextField textField_1 = new JTextField("", 50);

  /**
   * Create the dialog.
   */
  public RenameBox() {
    setBounds(100, 100, 399, 181);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    contentPanel.setLayout(null);
    {
      textField = new JTextField();
      textField.setBounds(96, 55, 170, 20);
      contentPanel.add(textField);
      textField.setColumns(10);
    }
    {
      JButton btnNewButton = new JButton("Rename");
      btnNewButton.addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if (AppBuilder.dp == null) {
                return;
              }
              FileManagerFrame activeFrame = (FileManagerFrame) AppBuilder.dp.getSelectedFrame();
              if (activeFrame == null) {
                return;
              }
              activeFrame.fp.rename(new File(textField.getText()), textField_1.getText());
          }
        }
      );
      btnNewButton.setBounds(297, 104, 71, 23);
      btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
      contentPanel.add(btnNewButton);
    }
    {
      JButton btnNewButton_1 = new JButton("Cancel");
      btnNewButton_1.setBounds(297, 54, 65, 23);
      contentPanel.add(btnNewButton_1);
    }

    textField_1 = new JTextField();
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
    lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
    lblNewLabel_2.setBounds(40, 30, 272, 14);
    contentPanel.add(lblNewLabel_2);

    JLabel lblNewLabel_3 = new JLabel("New label");
    lblNewLabel_3.setBounds(161, 30, 46, 14);
    contentPanel.add(lblNewLabel_3);
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }

    this.setTitle("Rename");
  }
}
