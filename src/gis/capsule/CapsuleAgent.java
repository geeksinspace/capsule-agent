/*
 * Copyright 2014, Geeks In Space.
 * see http://github.com/geeksinspace/capsule-agent/blob/master/LICENSE
 */
package gis.capsule;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * CapsuleAgent MIDlet.
 * @author deanydean
 */
public class CapsuleAgent extends MIDlet implements CommandListener {
    
    private Thread workerThread;
    private Worker worker;
    
    private boolean midletPaused = false;
    
    
//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
private Form AgentActive;
private StringItem statusString;
private StringItem latitudeString;
private StringItem connStatusString;
private StringItem altitudeString;
private StringItem longitudeString;
private StringItem phaseString;
private Form CapsuleAgent;
private TextField mcUrlAInput;
private TextField mcUrlBInput;
private TextField updateIntervalInput;
private TextField missionID;
private Command exit;
private Command start;
private Command stop;
//</editor-fold>//GEN-END:|fields|0|

    /**
     * The Tracker constructor.
     */
    public CapsuleAgent() {
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
/**
 * Initializes the application.
 * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
 */
private void initialize () {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
}//GEN-BEGIN:|0-initialize|2|
//</editor-fold>//GEN-END:|0-initialize|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
/**
 * Performs an action assigned to the Mobile Device - MIDlet Started point.
 */
public void startMIDlet () {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
switchDisplayable (null, getCapsuleAgent ());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
}//GEN-BEGIN:|3-startMIDlet|2|
//</editor-fold>//GEN-END:|3-startMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
/**
 * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
 */
public void resumeMIDlet () {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
}//GEN-BEGIN:|4-resumeMIDlet|2|
//</editor-fold>//GEN-END:|4-resumeMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
/**
 * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
 * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
 * @param nextDisplayable the Displayable to be set
 */
public void switchDisplayable (Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
Display display = getDisplay ();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
if (alert == null) {
display.setCurrent (nextDisplayable);
} else {
display.setCurrent (alert, nextDisplayable);
}//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
}//GEN-BEGIN:|5-switchDisplayable|2|
//</editor-fold>//GEN-END:|5-switchDisplayable|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
/**
 * Called by a system to indicated that a command has been invoked on a particular displayable.
 * @param command the Command that was invoked
 * @param displayable the Displayable where the command was invoked
 */
public void commandAction (Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
if (displayable == AgentActive) {//GEN-BEGIN:|7-commandAction|1|36-preAction
if (command == stop) {//GEN-END:|7-commandAction|1|36-preAction
                stopTracking();
switchDisplayable (null, getCapsuleAgent ());//GEN-LINE:|7-commandAction|2|36-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|3|34-preAction
} else if (displayable == CapsuleAgent) {
if (command == exit) {//GEN-END:|7-commandAction|3|34-preAction
                stopTracking();
exitMIDlet ();//GEN-LINE:|7-commandAction|4|34-postAction
                // write post-action user code here
} else if (command == start) {//GEN-LINE:|7-commandAction|5|35-preAction
                startTracking();
switchDisplayable (null, getAgentActive ());//GEN-LINE:|7-commandAction|6|35-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|7|7-postCommandAction
}//GEN-END:|7-commandAction|7|7-postCommandAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|8|
//</editor-fold>//GEN-END:|7-commandAction|8|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: AgentActive ">//GEN-BEGIN:|14-getter|0|14-preInit
/**
 * Returns an initiliazed instance of AgentActive component.
 * @return the initialized component instance
 */
public Form getAgentActive () {
if (AgentActive == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
AgentActive = new Form ("Agent Active", new Item[] { getStatusString (), getConnStatusString (), getLatitudeString (), getLongitudeString (), getAltitudeString (), getPhaseString () });//GEN-BEGIN:|14-getter|1|14-postInit
AgentActive.addCommand (getStop ());
AgentActive.setCommandListener (this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
}//GEN-BEGIN:|14-getter|2|
return AgentActive;
}
//</editor-fold>//GEN-END:|14-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exit ">//GEN-BEGIN:|16-getter|0|16-preInit
/**
 * Returns an initiliazed instance of exit component.
 * @return the initialized component instance
 */
public Command getExit () {
if (exit == null) {//GEN-END:|16-getter|0|16-preInit
            // write pre-init user code here
exit = new Command ("Exit", Command.EXIT, 0);//GEN-LINE:|16-getter|1|16-postInit
            // write post-init user code here
}//GEN-BEGIN:|16-getter|2|
return exit;
}
//</editor-fold>//GEN-END:|16-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: start ">//GEN-BEGIN:|19-getter|0|19-preInit
/**
 * Returns an initiliazed instance of start component.
 * @return the initialized component instance
 */
public Command getStart () {
if (start == null) {//GEN-END:|19-getter|0|19-preInit
            // write pre-init user code here
start = new Command ("Start", Command.OK, 0);//GEN-LINE:|19-getter|1|19-postInit
            // write post-init user code here
}//GEN-BEGIN:|19-getter|2|
return start;
}
//</editor-fold>//GEN-END:|19-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: statusString ">//GEN-BEGIN:|23-getter|0|23-preInit
/**
 * Returns an initiliazed instance of statusString component.
 * @return the initialized component instance
 */
public StringItem getStatusString () {
if (statusString == null) {//GEN-END:|23-getter|0|23-preInit
            // write pre-init user code here
statusString = new StringItem ("Agent Status", null);//GEN-LINE:|23-getter|1|23-postInit
            // write post-init user code here
}//GEN-BEGIN:|23-getter|2|
return statusString;
}
//</editor-fold>//GEN-END:|23-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: connStatusString ">//GEN-BEGIN:|24-getter|0|24-preInit
/**
 * Returns an initiliazed instance of connStatusString component.
 * @return the initialized component instance
 */
public StringItem getConnStatusString () {
if (connStatusString == null) {//GEN-END:|24-getter|0|24-preInit
            // write pre-init user code here
connStatusString = new StringItem ("Connection Status", null);//GEN-LINE:|24-getter|1|24-postInit
            // write post-init user code here
}//GEN-BEGIN:|24-getter|2|
return connStatusString;
}
//</editor-fold>//GEN-END:|24-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: latitudeString ">//GEN-BEGIN:|25-getter|0|25-preInit
/**
 * Returns an initiliazed instance of latitudeString component.
 * @return the initialized component instance
 */
public StringItem getLatitudeString () {
if (latitudeString == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
latitudeString = new StringItem ("Latitude", null);//GEN-LINE:|25-getter|1|25-postInit
            // write post-init user code here
}//GEN-BEGIN:|25-getter|2|
return latitudeString;
}
//</editor-fold>//GEN-END:|25-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: longitudeString ">//GEN-BEGIN:|26-getter|0|26-preInit
/**
 * Returns an initiliazed instance of longitudeString component.
 * @return the initialized component instance
 */
public StringItem getLongitudeString () {
if (longitudeString == null) {//GEN-END:|26-getter|0|26-preInit
            // write pre-init user code here
longitudeString = new StringItem ("Longitude", null);//GEN-LINE:|26-getter|1|26-postInit
            // write post-init user code here
}//GEN-BEGIN:|26-getter|2|
return longitudeString;
}
//</editor-fold>//GEN-END:|26-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: altitudeString ">//GEN-BEGIN:|27-getter|0|27-preInit
/**
 * Returns an initiliazed instance of altitudeString component.
 * @return the initialized component instance
 */
public StringItem getAltitudeString () {
if (altitudeString == null) {//GEN-END:|27-getter|0|27-preInit
            // write pre-init user code here
altitudeString = new StringItem ("Altitude", null);//GEN-LINE:|27-getter|1|27-postInit
            // write post-init user code here
}//GEN-BEGIN:|27-getter|2|
return altitudeString;
}
//</editor-fold>//GEN-END:|27-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: phaseString ">//GEN-BEGIN:|28-getter|0|28-preInit
/**
 * Returns an initiliazed instance of phaseString component.
 * @return the initialized component instance
 */
public StringItem getPhaseString () {
if (phaseString == null) {//GEN-END:|28-getter|0|28-preInit
            // write pre-init user code here
phaseString = new StringItem ("Phase", null);//GEN-LINE:|28-getter|1|28-postInit
            // write post-init user code here
}//GEN-BEGIN:|28-getter|2|
return phaseString;
}
//</editor-fold>//GEN-END:|28-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stop ">//GEN-BEGIN:|21-getter|0|21-preInit
/**
 * Returns an initiliazed instance of stop component.
 * @return the initialized component instance
 */
public Command getStop () {
if (stop == null) {//GEN-END:|21-getter|0|21-preInit
            // write pre-init user code here
stop = new Command ("Stop", Command.STOP, 0);//GEN-LINE:|21-getter|1|21-postInit
            // write post-init user code here
}//GEN-BEGIN:|21-getter|2|
return stop;
}
//</editor-fold>//GEN-END:|21-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: CapsuleAgent ">//GEN-BEGIN:|29-getter|0|29-preInit
/**
 * Returns an initiliazed instance of CapsuleAgent component.
 * @return the initialized component instance
 */
public Form getCapsuleAgent () {
if (CapsuleAgent == null) {//GEN-END:|29-getter|0|29-preInit
            // write pre-init user code here
CapsuleAgent = new Form ("GIS Capsule Agent", new Item[] { getMissionID (), getUpdateIntervalInput (), getMcUrlAInput (), getMcUrlBInput () });//GEN-BEGIN:|29-getter|1|29-postInit
CapsuleAgent.addCommand (getExit ());
CapsuleAgent.addCommand (getStart ());
CapsuleAgent.setCommandListener (this);//GEN-END:|29-getter|1|29-postInit
            // write post-init user code here
}//GEN-BEGIN:|29-getter|2|
return CapsuleAgent;
}
//</editor-fold>//GEN-END:|29-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: updateIntervalInput ">//GEN-BEGIN:|41-getter|0|41-preInit
/**
 * Returns an initiliazed instance of updateIntervalInput component.
 * @return the initialized component instance
 */
public TextField getUpdateIntervalInput () {
if (updateIntervalInput == null) {//GEN-END:|41-getter|0|41-preInit
            // write pre-init user code here
updateIntervalInput = new TextField ("Update Interval (seconds)", "30", 32, TextField.NUMERIC);//GEN-LINE:|41-getter|1|41-postInit
            // write post-init user code here
}//GEN-BEGIN:|41-getter|2|
return updateIntervalInput;
}
//</editor-fold>//GEN-END:|41-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: mcUrlAInput ">//GEN-BEGIN:|42-getter|0|42-preInit
/**
 * Returns an initiliazed instance of mcUrlAInput component.
 * @return the initialized component instance
 */
public TextField getMcUrlAInput () {
if (mcUrlAInput == null) {//GEN-END:|42-getter|0|42-preInit
            // write pre-init user code here
mcUrlAInput = new TextField ("Mission Control URL A", "", 64, TextField.ANY);//GEN-LINE:|42-getter|1|42-postInit
            // write post-init user code here
}//GEN-BEGIN:|42-getter|2|
return mcUrlAInput;
}
//</editor-fold>//GEN-END:|42-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: mcUrlBInput ">//GEN-BEGIN:|43-getter|0|43-preInit
/**
 * Returns an initiliazed instance of mcUrlBInput component.
 * @return the initialized component instance
 */
public TextField getMcUrlBInput () {
if (mcUrlBInput == null) {//GEN-END:|43-getter|0|43-preInit
            // write pre-init user code here
mcUrlBInput = new TextField ("Mission Control URL B", "", 64, TextField.ANY);//GEN-LINE:|43-getter|1|43-postInit
            // write post-init user code here
}//GEN-BEGIN:|43-getter|2|
return mcUrlBInput;
}
//</editor-fold>//GEN-END:|43-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: missionID ">//GEN-BEGIN:|44-getter|0|44-preInit
/**
 * Returns an initiliazed instance of missionID component.
 * @return the initialized component instance
 */
public TextField getMissionID () {
if (missionID == null) {//GEN-END:|44-getter|0|44-preInit
 // write pre-init user code here
missionID = new TextField ("Mission ID", "TESTING", 32, TextField.ANY);//GEN-LINE:|44-getter|1|44-postInit
 // write post-init user code here
}//GEN-BEGIN:|44-getter|2|
return missionID;
}
//</editor-fold>//GEN-END:|44-getter|2|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    public void updateTrackerStatus(String newStatus){
        getStatusString().setText(newStatus);
    }
    
    public void updateConnStatus(String newStatus){
        getStatusString().setText(newStatus);
    }
    
    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }
    
    public void startTracking(){
        // Just in case we're already tracking
        stopTracking();
        
        // Init worker and thread
        this.worker = new Worker(this, 
            Integer.parseInt(getUpdateIntervalInput().getString()));
        this.workerThread = new Thread(worker, "Agent Worker");
        this.workerThread.start();
        
        updateTrackerStatus("Agent worker started");
    }
    
    public void stopTracking(){
        if(this.worker != null && this.worker.isRunning()){
            this.worker.stop();
            updateTrackerStatus("Agent worker stopped");
            this.worker = null;
        }
    }
}
