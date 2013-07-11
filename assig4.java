
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JToggleButton;

public class assig4
{
    public static void main(String[] args)
    {
        if(args.length == 1)
        {
            try
            {
                ArrayList<Ballot> ballots = new ArrayList<Ballot>();
                Scanner scanner = new Scanner(new File(args[0]));
                int num = Integer.parseInt(scanner.nextLine());
                for(int i=0;i<num;i++)
                {
                    String line = scanner.nextLine().trim();
                    System.out.println(line);
                    Ballot ballot = new Ballot();
                    ballot.setLabelText(line.substring(0, line.indexOf(':')));
                    String[] buttonTitles = line.substring(line.indexOf(':')+1, line.length()).split(",");
                    for(int j=0;j<buttonTitles.length;j++)
                        ballot.addButton(new JToggleButton(buttonTitles[j]));
                    ballots.add(ballot);
                }

                JButton vote = new JButton("Login to Vote");
                JButton cast = new JButton("Cast Your Vote");

                new Window(ballots, vote, cast);
            }
            catch(Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
    }
}
