/*
 * Copyright 2014, Geeks In Space.
 * see http://github.com/geeksinspace/capsule-agent/blob/master/LICENSE
 */
package gis.capsule;

import java.io.IOException;
import java.util.Date;

import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.QualifiedCoordinates;

import net.sourceforge.mtcs.location.Locator;
import net.sourceforge.mtcs.net.WebserviceConnection;
import net.sourceforge.mtcs.net.rest.JSONRESTRequest;

/**
 * Handles the runtime of the capsule agent.
 * @author deanydean
 */
public class Worker implements Runnable {

    public static final String NO_CONNECTION = "No Connection";
    public static final String NO_LOCATION = "No Location";

    public static final String PHASE_TRACKING = "Tracking";
    
    public static final String TYPE_LOCATION = "location";
    public static final String TYPE_ERROR = "error";
    
    private static final long TWEET_INTERVAL = 5*60*1000; // 5 mins
    
    private CapsuleAgent parent;
    private int interval;
    private boolean running = false;
    private Locator locator = null;
    private Location location = null;
    private String missionID = "TESTING";
    
    private TwitterBot twitterBot = null;
    private float lastTweetAtMeters = 0f;
    private long lastTweetTime = 0L;
    
    private WebserviceConnection connection;
    
    public Worker(CapsuleAgent parent, int interval){
        this.parent = parent;
        this.interval = interval;
        this.missionID = this.parent.getMissionID().getString();
        
        this.connection = new WebserviceConnection();
        
        try{
            this.twitterBot = new TwitterBot();
        }catch(Exception e){
            this.parent.updateConnStatus("Failed to connect to twitter: "+e);
        }
    }
    
    public void run(){
        this.running = true;
        
        try{
            // Init locator
            this.locator = new Locator(this.interval);
        }catch(LocationException le){
            // Report error
            this.parent.updateTrackerStatus("Failed to init locator : "+le);
            return;
        }
        
        // Set initial tracking phase
        this.parent.getPhaseString().setText(PHASE_TRACKING);                
                
        while(this.running){
            // Get the current locations
            updateLocation();
            
            // Module Software Logic
            // 1. Check if have any actions to take.
            processActions();
            
            // 2. Report the location.
            publishData();
            
            // 3. Log data
            logData();
            
            // 4. Tweet (if we are allowed to)
            tweet("Tracking update");
            
            // Wait until next run
            try{
                Thread.sleep(this.interval*1000);
            }catch(InterruptedException ie){
                // TODO: Review possible spinning?
            }
        }
    }
    
    public void stop(){
        if(this.running) this.running = false;
    }
    
    public boolean isRunning(){
        return this.running;
    }
    
    public void updateLocation(){
        try{
            this.location = this.locator.getCurrentLocation();
            this.parent.updateTrackerStatus("Location updated");
        }catch(LocationException le){
            this.parent.updateTrackerStatus("Failed to get location : "+le);
            this.location = null;
        }
        
        // Update UI
        if(this.location != null){
            QualifiedCoordinates coords = 
                this.location.getQualifiedCoordinates();
            this.parent.getLatitudeString().setText(
                Double.toString(coords.getLatitude()));
            this.parent.getLongitudeString().setText(
                Double.toString(coords.getLongitude()));
            this.parent.getAltitudeString().setText(
                Double.toString(coords.getAltitude()));
        }else{
            this.parent.getLongitudeString().setText(NO_LOCATION);
            this.parent.getLatitudeString().setText(NO_LOCATION);
            this.parent.getAltitudeString().setText(NO_LOCATION);
        }    
    }
    
    public void processActions(){
        
    }
    
    public void publishData(){
        this.parent.updateConnStatus("Publishing location");
        
        // Build the request
        JSONRESTRequest request = new JSONRESTRequest();
        request.setMethod("POST");
        
        String id = Long.toString(new Date().getTime());
        request.put("ts", id);
        
        if(this.location != null){
            QualifiedCoordinates coords = this.location.getQualifiedCoordinates();
            request.put("type", TYPE_LOCATION);
            request.put("lat", Double.toString(coords.getLatitude()));
            request.put("long", Double.toString(coords.getLongitude()));
            request.put("alt", Double.toString(coords.getAltitude()));
            request.put("hacc", Double.toString(coords.getHorizontalAccuracy()));
            request.put("vacc", Double.toString(coords.getVerticalAccuracy()));
        }else{
            request.put("type", TYPE_ERROR);
            request.put("err", "No location from device");
            tweet("I don't know where I am at the moment");
        }
        
        // Add battery level
        //request.put("bat", System.getProperty("com.nokia.mid.batterylevel"));
        
        // Add signal strength
        //request.put("sig", System.getProperty("com.nokia.mid.networksignal"));
        
        // Try primary url
        request.setUrl(
            "http://"+this.parent.getMcUrlAInput().getString()+"/"+id);
        try{
            this.connection.send(request);
        }catch(Throwable ta){
            this.parent.updateConnStatus(NO_CONNECTION+" URL A, trying URL B");
            tweet("Lost contact with mission control A");
            
            // Something went wrong, try url B
            request.setUrl(
                "http://"+this.parent.getMcUrlBInput().getString()+"/"+id);
            try{
                this.connection.send(request);
            }catch(Throwable tb){
                // No comms links, report
                this.parent.updateConnStatus(NO_CONNECTION+": "+tb);
                tweet("Unable to contact mission control");
                
                // TODO: Log data and error to file
            }
        }
    }
    
    public void logData(){
        this.parent.updateTrackerStatus("Logging data");
    }
    
    public void tweet(String msg){
        if(System.currentTimeMillis() < lastTweetTime+TWEET_INTERVAL)
            return;
            
        if(twitterBot == null){
            try{
                twitterBot = new TwitterBot();
            }catch(IOException ioe){
                this.parent.updateConnStatus(
                    "Failed to connect to twitter: "+ioe);
            }
        }
        
        if(twitterBot != null){
            if(this.location != null){
                // Check alt
                QualifiedCoordinates coords = 
                    this.location.getQualifiedCoordinates();
                if(coords != null){
                    float alt = coords.getAltitude();
                    //if(alt > lastTweetAtMeters+10){
                        try{
                            twitterBot.tweet(
                                this.missionID+": "+msg+". alt="+alt+"m speed="+
                                this.location.getSpeed()+"m/s");
                                //coords.getLongitude(), coords.getLatitude());
                            lastTweetAtMeters = alt;
                            return;
                        }catch(IOException ioe){
                            this.parent.updateConnStatus("Tweet failed: "+ioe);
                        }
                    //}
                }
            }

            try{
                twitterBot.tweet(this.missionID+": "+msg);
                lastTweetTime = System.currentTimeMillis();
            }catch(IOException ioe){
                this.parent.updateConnStatus("Tweet failed: "+ioe);
            }
        }
    }
}
