/* Text RPG Console
 * Name:                Anthony R. Garcia
 * Filename:            RPGmain.java
 * Date Last Modified:  26 April 2014
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RPGmain
{
  public static void main(String [] args)
  {
    Scanner input = new Scanner(System.in);
    ArrayList<RPGStage> currentGame = null;
    System.out.println("Welcome to the TextRPG Creator!");
    String options[] = {"Select a file","Play a game","Create a new game","Game Creation Help","Game visualizer (SPOILERS!)","Exit"};
    for(int i = 0; i < options.length; i++)
    {
      System.out.println((i+1) + " -" + options[i]);
    }
    boolean flag = true;
    while(flag)
    {
      System.out.print("\nMAIN MENU\nPlease enter a selection.\n> ");
      String resp = input.nextLine();
      switch(resp.charAt(0))
      {
        case '1':
          currentGame = selectFile();
          break;
        case '2':
          if(currentGame==null)
            System.out.println("No game currently selected.");
          else
            playGame(currentGame);
          break;
        case '3':
          createGame();
          break;
        case '4':
          creatorHelp();
          break;
        case '5':
          if(currentGame==null)
            System.out.println("No game currently selected.");
          else
            gameVisualizer(currentGame);
          break;
        case '6'://quit
          return;
        default:
          System.out.println("Please make a valid selection.");
      }
    }
  }
  public static ArrayList<RPGStage> selectFile()
  {
    Scanner input = new Scanner(System.in);
    ArrayList<RPGStage> newGame = null;
    System.out.print("\nFILE SELECTION\nEnter the filename of the game you wish to play ('.dat' not required).\n> ");
    String filename = input.nextLine();
    if(!(filename.toLowerCase().contains(".dat")))
      filename += ".dat";

    System.out.print("Reading file '" + filename + "'. Please wait... ");
    ObjectInputStream fileread;
    try
    {
      fileread = new ObjectInputStream(new FileInputStream(filename));
      newGame = (ArrayList<RPGStage>)fileread.readObject();
      System.out.println("Complete.");
    }
    catch(ClassNotFoundException e)
    {
      System.out.println("This file has not been formatted as required by this program.\nCreate a new game using this program.");
    }
    catch(FileNotFoundException e)
    {
      System.out.println("File not found.");
    }
    catch(IOException e)
    {
      System.out.println("File read error. Try another file.");
    }
    return newGame;
  }
  public static void playGame(ArrayList<RPGStage> game)
  {
    System.out.println("Enter 'hint', 'help', or 'quit'.");
    game.get(0).numResponses();
    for(RPGStage element : game)
      element.input();
    System.out.println("Game complete.");
  }
  public static void gameVisualizer(ArrayList<RPGStage> game)
  {
    System.out.println("\nGAME VISUALIZER\n");
    for(int i = 0; i < game.size(); i++)
    {
      System.out.println("Stage " + i);
      System.out.println(game.get(i));
    }
    System.out.println("Total stages this game: " + game.size() + "\n");
  }
  public static void createGame()
  {
    System.out.println("\nGAME CREATION LAB");
    Scanner input = new Scanner(System.in);
    String resp;
    ArrayList<RPGStage> game = new ArrayList<RPGStage>();
    boolean doneStages;
    boolean doneChoices;
    boolean progressExists;
    // begin create stages //
    doneStages = false;
    while(!doneStages)
    {
      progressExists = false; // prepare for ensuring every stage has a progress key
      // STAGE CREATION //
      RPGStage tempStage;
      System.out.println("\nNEW STAGE");
      System.out.println("Enter a prompt for the player to respond to, to begin this stage:");
      String tempPrompt = input.nextLine();

      System.out.println("Enter a default that will let the player know the choice they made was invalid:");
      String tempOther = input.nextLine();

      System.out.println("Enter a hint for this stage:");
      String tempHint = input.nextLine();

      System.out.println("Enter a message that will help the player use the game:");
      String tempHelp = input.nextLine();

      System.out.println("Enter a quit message:");
      String tempQuit = input.nextLine();
      // END STAGE CREATION //

      // begin create choices //
      doneChoices = false;
      progressExists = false;
      ArrayList<RPGChoice> list = new ArrayList<RPGChoice>();
      while(!doneChoices)
      {
        // CHOICE CREATION //
        System.out.println("\nNEW INPUT");
        System.out.println("Enter the input that the player must use to get a specific response:");
        String tempInput = input.nextLine();

        System.out.println("Enter the response to the input you entered above:");
        String tempResponse = input.nextLine();

        boolean tempIsProgress;
        while(true) // isProgress key check
        {
          System.out.print("Will this input cause the player to move on to the next stage? (y/n) ");
          resp = input.nextLine();
          if(resp.toLowerCase().charAt(0)=='y')
          {
            tempIsProgress = true;
            progressExists = true;
            break;
          }
          else if(resp.toLowerCase().charAt(0)=='n')
          {
            tempIsProgress = false;
            break;
          }
          else
          {
            System.out.println("Please enter y or n.");
          }
        }
        list.add(new RPGChoice(tempInput,tempResponse,tempIsProgress));
        // END CHOICE CREATION //
        while(true) // more choices check
        {
          if(!progressExists) // no progress key
            System.out.println("\nYou have not yet created a progress key for this stage.\n(You must create AT LEAST ONE PER STAGE!)");
          System.out.print("Would you like to create another choice? (y/n) ");
          resp = input.nextLine();
          if(resp.toLowerCase().charAt(0)=='y')
          {
            doneChoices = false;
            break;
          }
          else if(resp.toLowerCase().charAt(0)=='n' && progressExists)
          {
            doneChoices = true;
            break;
          }
          else
          {
            System.out.println("Please enter y or n.");
          }
        }
      }
      tempStage = new RPGStage(tempPrompt,tempOther,list);
      tempStage.setHint(tempHint);
      tempStage.setHelp(tempHelp);
      tempStage.setQuit(tempQuit);
      game.add(tempStage);

      while(true) // more stages check
      {
        System.out.print("Would you like to create another stage? (y/n) ");
        resp = input.nextLine();
        if(resp.toLowerCase().charAt(0)=='y')
        {
          doneStages = false;
          break;
        }
        else if(resp.toLowerCase().charAt(0)=='n')
        {
          doneStages = true;
          break;
        }
        else
        {
          System.out.println("Please enter y or n.");
        }
      }



      //// Begin File Write ////
      System.out.println("Choose a filename to save this under. ('.dat' will be added)");
      String filename = input.nextLine();
      filename += ".dat";
      System.out.print("Writing file '" + filename + "'. Please wait... ");
      ObjectOutputStream filewrite;
      try
      {
        filewrite = new ObjectOutputStream(new FileOutputStream(filename));
        filewrite.writeObject(game);
        System.out.println("Complete.");
        filewrite.close();
      }
      catch(FileNotFoundException e)
      {
        System.out.println("File not found.");
      }
      catch(IOException e)
      {
        System.out.println("File read error. Try another file.");
      }
    }
  }
  public static void creatorHelp()
  {
    String s = "Press ENTER to continue";
    Scanner input = new Scanner(System.in);
    System.out.println("GAME CREATION HELP\n\n" +
    "The games rely on text input. In order to create a successful game, its \n" +
    "important to plan ahead and create a game-plan.\n" +
    "It's up to you how specific or helpful you want your game to be.\n");
    System.out.print(s);
    input.nextLine();
    System.out.println("\nEach game contains STAGES; there can be as many as you like.\n" +
    "Each STAGE contains INPUTS; each INPUT elicits a RESPONSE from the console.\n");
    System.out.print(s);
    input.nextLine();
    System.out.println("For example, if the INPUT you want is \"look around\", and the RESPONSE is \"You \n" +
    "see you are in a large room.\", gameplay could look like this:\n\n" +
    " > look around\n" +
    " You see you are in a large room.\n\n" +
    " > _\n");
    System.out.print(s);
    input.nextLine();
    System.out.println("\nEach STAGE can have as many inputs (and their associated responses) as you'd \n" +
    "like.\n");
    System.out.print(s);
    input.nextLine();
    System.out.println("\nEvery STAGE also requires a:\n" +
    "PROMPT which tells the player to enter a command.\n" +
    "       (Will be displayed before every user input field.)\n" +
    "DEFAULT which tells the player that the input they entered does not have an\n" +
    "        associated response.\n" +
    "HELP which is meant to help the player interact with and use the program.\n" +
    "     (Will be displayed when the user enters 'help'.)\n" +
    "HINT which is meant to offer advice about the in-game content. (Will be\n" +
    "     displayed when the user enters 'hint'.)\n" +
    "QUIT which displays when the user chooses to quit (enters 'quit').\n");
    System.out.print(s);
    input.nextLine();
    System.out.println("\nOnce created, the game will ask for a filename to save under.\n" +
    "The default path is the local path (where the program is located.) and\n" +
    "the automatic file extension is '.dat'.\n" +
    "After that, you can share the program and the game you created with anyone!");
  }
}
