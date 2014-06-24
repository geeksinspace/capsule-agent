The GIS Capsule Agent
=====================

A lightweight JME app that runs on a phone inside the GIS capsule. It tracks
the capsule coordinates (from phone's GPS) and sends them back to mission
control (over phone's GSM connection).

Building
========

You need netbeans and the JME runtime installed. You'll also need to download
the following jars and put them in a ./lib directory:

| kxml2-2.2.0.jar        | http://sourceforge.net/projects/kxml | 
| mtcs.jar               | http://sourceforge.net/projects/mtcs/ |
| twitter_api_me-1.7.jar | https://kenai.com/projects/twitterapime |

Running
=======
Once built, put the agent on your Java ME phone and run it. That's it.
