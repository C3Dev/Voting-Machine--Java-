
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class Ballot extends JPanel implements ActionListener
{

    private JLabel label;
    private ArrayList<JToggleButton> buttons;

    public Ballot()
    {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        label = new JLabel();
        buttons = new ArrayList<JToggleButton>();
    }

    public void setLabelText(String text)
    {
        label.setText(text);
        this.add(label);
    }

    public void addButton(JToggleButton button)
    {
        button.addActionListener(this);
        buttons.add(button);
        this.add(button);
    }

    public void disableButtons()
    {
        for(int i=0;i<buttons.size();i++)
            buttons.get(i).setEnabled(false);
    }

    public void enableButtons()
    {
        for(int i=0;i<buttons.size();i++)
            buttons.get(i).setEnabled(true);
    }

    public void write2File()
    {
        try
        {
            PrintWriter pw = new PrintWriter(new FileWriter(label.getText()+".txt"));
            for(int i=0;i<buttons.size();i++)
                pw.println(buttons.get(i).getText()+":"+((buttons.get(i).isSelected())?"1":"0"));
            pw.close();
        }
        catch(Exception ex)
        {
            
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        JToggleButton button = (JToggleButton)e.getSource();
        if(button.isSelected())
        {
            button.setForeground(Color.RED);
        }
        for(int i=0;i<buttons.size();i++)
        {
            if(buttons.get(i) != button)
            {
                buttons.get(i).setSelected(false);
                buttons.get(i).setForeground(Color.BLACK);
            }
        }
    }
}
