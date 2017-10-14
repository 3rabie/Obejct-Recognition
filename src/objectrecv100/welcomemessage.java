
package objectrecv100;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class welcomemessage extends Thread {
private static final String VOICENAME_kevin = "kevin";
 //private String s; // string to speech
 
 public welcomemessage() {
//  this.text = text;
//  //System.load("/home/rabie/Downloads/mbrola/mbrola.exe");
//System.setProperty("mbrola.base", "/home/rabie/Downloads/mbrola/mbr301h/");
}
 
 public void speak(String s) {
  Voice voice;
  VoiceManager voiceManager = VoiceManager.getInstance();
  voice = voiceManager.getVoice(VOICENAME_kevin);
  voice.allocate();
  voice.speak(s);
 }
 
    
}
