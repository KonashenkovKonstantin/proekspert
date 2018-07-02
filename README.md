## proekspert
Test task for proekspert

## Requirements
Java 8
Maven 3

## How to build solution
Go to 'proekspert/LogParser/' and run maven command 'mvn clean install'.
JUnit tests will be run (unit tests and functional test ) and a 'proekspert/LogParser/target' folder will be created. The target folder contains ProekspertTestTask.jar

## How to run solution  
You need to use command to run solution  
```java -jar ProekspertTestTask.jar <file name> <how much to print of resources with highest average request duration>```  
For example  
```java -jar ProekspertTestTask.jar C:\tmp\timing_ext.log 100```  

## Changes, assumptions and limitations about current implemetation  
- Ant has been removed by Maven. This change has been confirmed by 'Grete Tammlo'  
- This program was written with assumption that until of file isn't finished program doesn't print any info statistics about log file
- This program was written with assumption that if log file contains any invalid lines, these lines are ignored
- This program was written with limitation of <how much to print of resources with highest average request duration> parameter. This limit is 1000. It's an artificial limitation used for sake of test task.
  
  
  
