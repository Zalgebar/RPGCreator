/* Text RPG Console
 * Name:                Anthony R. Garcia
 * Filename:            RPGChoice.java
 * Date Last Modified:  26 April 2014
 */
import java.io.Serializable;

public class RPGChoice implements Serializable
{
  private String input; // input value in order to return mv:response
  private String response; // returned value from mv:input
  private boolean isProgressKey; // allows story to progress || !
//// Constructors & Overridden Methods ////
  public RPGChoice(String in, String resp)
  {
    input = in;
    response = resp;
    isProgressKey = false;
  /*The above defaults to false so that the programmer can
    enter multiple RPGChoice objects without having to type
    false every time (as one would if using constructor below).*/
  }
  public RPGChoice(String in, String resp, boolean key)
  {
    input = in;
    response = resp;
    isProgressKey = key;
  }
  public String toString()
  {
    return ("INPUT: \"" + input + "\"\n" +
            "RESPONSE: \"" + response + "\"\n" +
            "PROGRESS KEY: " + isProgressKey + "\n");
  }

//// Accessors ////
  public String getInput()
  {
    return input;
  }
  public String getResponse()
  {
    return response;
  }
  public boolean getProgress()
  {
    return isProgressKey;
  }
//// Mutators ////
  public void setChoice(String s)
  {
    input = s;
  }
  public void setResponse(String s)
  {
    response = s;
  }
  public void setProgress(boolean b)
  {
    isProgressKey = b;
  }
}