/* Text RPG Console
 * Name:                Anthony R. Garcia
 * Filename:            RPGStage.java
 * Date Last Modified:  26 April 2014
 */
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import java.io.Serializable;

public class RPGStage implements Serializable
{
  private ArrayList<RPGChoice> choices;
  private String prompt;
  private String other;
  private String shortcut;
  private String hint;
  private String help;
  private String quit;
//// Constructors & Overriden Methods ////
  public RPGStage(ArrayList<RPGChoice> list)
  {
    this.other = "Enter a valid command.";
    this.prompt = "That doesn't do anything.";
    this.choices = list;
    this.quit = "You have quit the game.";
  }
  public RPGStage(String prompt, String other, ArrayList<RPGChoice> list)
  {
// A default is required so that the user knows that their response is invalid.
    this.other = other;
// A prompt is required so that the user knows what to input.
    this.prompt = prompt;
    this.choices = list;
    this.quit = "You have quit the game.";
  }
  public String toString()
  {
    System.out.println("--------\n" +
                       "PROMPT:  \"" + prompt + "\"\n" +
                       "DEFAULT: \"" + other + "\"\n" +
                       "HINT:    \"" + hint + "\"\n" +
                       "HELP:    \"" + help + "\"\n" +
                       "QUIT:    \"" + quit + "\"\n");
    for(RPGChoice element : choices)
      System.out.println(element);
    System.out.println("Total responses this stage: " + numResponses());
    return ("----------------");
  }

//// "Main" Method ////
  public boolean input()
  {
    Scanner input = new Scanner(System.in);
    String resp;
    boolean flag = false;
    while(!flag)
    {
      System.out.println(prompt);
      System.out.print("> ");
      resp = input.nextLine();
      if(hint!=null && resp.toLowerCase().contains("hint"))
      {
        System.out.println("\nHINT:\n" + hint);
      }
      else if(help!=null && resp.toLowerCase().contains("help"))
      {
        System.out.println("\nHELP:\n" + help);
      }
      else if(resp.toLowerCase().contains("commands"))
      {
        System.out.println("\nThere are " + numResponses() + " possible commands.");
      }
      else if(quit!=null && resp.toLowerCase().contains("quit"))
      {
        System.out.println("\n" + quit);
        if(shortcut!=null)
        {
          System.out.println("To return to this place in the game enter this shortcut key: " + shortcut);
          System.out.print("Press ENTER");
          input.nextLine();
        }
        System.exit(0);
      }
      else if(findKey(resp)>-1)
      {
        RPGChoice foundObject = choices.get(findKey(resp));
        System.out.println("\n" + foundObject.getResponse());
        if(foundObject.getProgress())
          return true;
      }
      else
      {
        System.out.println("\n" + other);
      }
    }
    return false;
  }
//// Mutators ////
  public void setPrompt(String s)
  {
    this.prompt = s;
  }
  public void setDefault(String s)
  {
    this.other = s;
  }
  public void setShortcut(String s)
  {
    shortcut = s;
  }
  public void setHint(String s)
  {
    hint = s;
  }
  public void setHelp(String s)
  {
    help = s;
  }
  public void setQuit(String s)
  {
    quit = s;
  }
//// Member Methods ////
  public int numResponses()
  {
    return choices.size();
  }
  private int findKey(String key)
  {
    ListIterator choiceIterator = choices.listIterator();
    while (choiceIterator.hasNext())
    {
      RPGChoice temp = (RPGChoice)choiceIterator.next();
      if(temp.getInput().toLowerCase().contains(key))
        return choiceIterator.previousIndex();
    }
    return -1;
  }
}