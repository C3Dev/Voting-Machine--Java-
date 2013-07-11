
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Window extends JFrame implements ActionListener
{

    private String userId;
    private ArrayList<Ballot> ballots;
    private JButton vote;
    private JButton cast;

    public Window(ArrayList<Ballot> ballots, JButton vote, JButton cast)
    {
        userId = "";
        this.ballots = ballots;
        this.vote = vote;
        this.cast = cast;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
        this.setTitle("E-Vote version 1.0");
        this.setSize(550, 250);
        
        vote.setActionCommand("vote");
        vote.addActionListener(this);
        cast.setActionCommand("cast");
        cast.addActionListener(this);

        for(int i=0;i<ballots.size();i++)
        {
            this.add(ballots.get(i));
            ballots.get(i).disableButtons();
        }
        this.add(vote);
        this.add(cast);
        cast.setEnabled(false);

        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("vote"))
        {
            System.out.println("vote");
            userId = JOptionPane.showInputDialog(this, "Please enter your voter id:");
            try
            {
                Scanner scanner = new Scanner(new File("voters.txt"));
                while(scanner.hasNext())
                {
                    String line = scanner.nextLine();
                    String[] str = line.split(":");
                    if(str[0].equals(userId))
                    {
                        if(str[2].trim().equals("true"))
                        {
                            JOptionPane.showMessageDialog(this, str[1] + ", you have already voted");
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, str[1] + ", please make your choices");

                            vote.setEnabled(false);
                            cast.setEnabled(true);
                            for(int i=0;i<ballots.size();i++)
                                ballots.get(i).enableButtons();
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                
            }

        }
        if(e.getActionCommand().equals("cast"))
        {
            System.out.println("cast");
            for(int i=0;i<ballots.size();i++)
                ballots.get(i).write2File();
            for(int i=0;i<ballots.size();i++)
                ballots.get(i).disableButtons();
            cast.setEnabled(false);
            vote.setEnabled(true);
            updateVoters();
            JOptionPane.showMessageDialog(this, "Thank you for your vote!");
        }
    }

    private void updateVoters()
    {
        try
        {
            PrintWriter pw = new PrintWriter(new FileWriter("tmpVoters.txt"));
            Scanner scanner = new Scanner(new File("voters.txt"));
            while(scanner.hasNext())
            {
                String line = scanner.nextLine();
                String str[] = line.trim().split(":");
                if(str[0].equals(userId))
                {
                    line = str[0]+":"+str[1]+":" + "true";
                }
                pw.println(line);
            }
            scanner.close();
            pw.close();
            File file = new File("voters.txt");
            File fileNew = new File("tmpVoters.txt");
            if(file.exists())
            {
                file.delete();
                fileNew.renameTo(new File("voters.txt"));
            }
        }
        catch(Exception ex)
        {

        }
    }
}
