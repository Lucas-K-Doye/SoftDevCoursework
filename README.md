# Coursework
 
## Running the program

Once code has been cloned into local repository from that directory you can compile the java code using  the bash commad

```bash
javac -d bin src/*.java
```

Once this command has run you can navigate to the bin directory and run the command 

```bash
java CardGame
```

To run the tests in junit4 you can run the following bash command
```bash
java -cp "bin:junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore CardGameTest 
```
and then,
```bash
java -cp "bin:lib/junit-4.13.2.jar:hamcrest-core-1.3.jar" org.junit.runner.JUnitCore CardGameTest
```
