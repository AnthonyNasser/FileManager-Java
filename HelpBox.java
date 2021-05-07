import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class HelpBox extends JDialog {

  private final JPanel contentPanel = new JPanel();

  public HelpBox() {
    setBounds(100, 100, 527, 258);
    getContentPane().setLayout(new BorderLayout());
    contentPanel.setLayout(new FlowLayout());
    contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    getContentPane().add(contentPanel, BorderLayout.CENTER);
    {
      JLabel lblNewLabel = new JLabel(
        "Project by Anthony Nasser and Malhar Pandya."
      );
      lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 19));
      contentPanel.add(lblNewLabel);
    }
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      {
        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);
        okButton.addActionListener(new helpActionListener());
      }
      {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new helpActionListener());
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
      }
    }
    // this.setLocationRelativeTo(null);
  }

  class helpActionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      HelpBox.this.setVisible(false);
    }
  }
}
