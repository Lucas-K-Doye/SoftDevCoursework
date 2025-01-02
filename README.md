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

To run the tests in junit4 you can run the bash command
```bash
javac -cp .:libs/junit-4.13.2.jar:libs/hamcrest-core-1.3.jar src/*.java tests/*.java
```