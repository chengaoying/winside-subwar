package submarine;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;



public class SubmarineMIDlet extends MIDlet {
	
	private static SubmarineMIDlet instance;
	
	public SubmarineMIDlet(){
		instance = this;
	}
	
	public static SubmarineMIDlet getInstance() {
		return instance;
	}

	protected void destroyApp(boolean unconditional)throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(SubmarineGameEngine.instance);
		new Thread(SubmarineGameEngine.instance).start();
	}

}
