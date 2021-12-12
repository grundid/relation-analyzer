# Relation Analyzer for OSM

This project contains the source code for the Relation Analyzer 
which is available online at http://ra.osmsurround.org.

The Relation Analyzer is written in Java. It is build using the 
SpringFramework http://www.springframework.org/.
Apache Maven http://maven.apache.org is used as the build system.

## Building

To build the Relation Analyzer launch:

mvn package

This will create a WAR file in the target folder with the name "relation-analyzer.war".
Place this file in the webapps folder of your 
servlet container http://tomcat.apache.org and launch the server.

You can then access the Relation Analyzer with

http://localhost:8080/relation-analyzer

## Hacking

The source code has some test case coverage so you should be fine to be 
able to do some hacking without braking major functionality.

As for the IDE, I recommend the SpringSource Tool Suite: http://www.springsource.com/downloads/sts
It already has some useful plugins on board. 

Basically you can also use the Web Developer Editon of Eclipse and install all the plugins you need yourself.

Here is a list of plugins you'll definitely need:

M2Eclipse - http://www.eclipse.org/m2e/
JAXB2 Extension for M2Eclipse - https://github.com/hwellmann/m2eclipse-extras/raw/master/p2
Use the above URL as an update site in Eclipse. If you open it in the browser it will give you 404 - file not found.
EGit - http://eclipse.org/egit/

To set things up, please follow the steps below:

1. Setup the Git repo in eclipse.
2. Checkout the amenity-editor project
3. Right click on the amenity-editor project and choose "Configure > Convert to Maven Project" 
4. Right click on the amenity-editor project and choose "Run As... > maven generate-sources"

At this point the project should compile fine. If it does not, please do the following:

Right click on the amenity-editor project and choose "Maven > Update Project Configuration..."

Now you should be able to run the tests:

5. Right click on the amenity-editor project and choose "Run As... > JUnit test"

Is the bar green? Good.

## Running in Eclipse

You can launch the Relation Analyzer directly in Eclipse. You will need to have a Servlet Container setup.
If everything is OK, you can right click on the project and choose "Run As... > Run on Server". Then open this link:
http://localhost:8080/relation-analyzer
