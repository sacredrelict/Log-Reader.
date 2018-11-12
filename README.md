# README #
Log reader - application for checking large log files in real time.
Application was tested with big log files (1GB) and works stable.

### Frameworks ###
* AngularJs 1.6
* Spring Boot 1.5.9

### Base requirements ###
* Java 8 or higher.
* Maven 3.

### Who to run in IDE? ###
* Download and unzip sources.
* Open project with some IDE.
* Open "application.yml" and edit log.file.path property. Or use embedded file sample.
* Run maven command: "mvn clean install"
* Run LogReaderApplication.java with VM options: "-Dspring.profiles.active=dev"
* Program will be run in embedded Tomcat.

### Who to run without IDE? ###
* Download and unzip sources.
* Open folder with project.
* Open "application.yml" and edit log.file.path property. Or use embedded file sample.
* Open console.
* Run maven command: "mvn clean install" in project directory root folder.
* Run Java command to run project: "java -jar target/web.jar -Dspring.profiles.active=dev"
* Program will be run in embedded Tomcat.

### Who to build war file? ###
* In web module open pom.xml.
* Change build type into war type.