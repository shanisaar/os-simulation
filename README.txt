It is assumed that you are using a Linux Lab machine with a Java compiler!
==================================================================
Compiling instructions:
Open the terminal and make sure you run the commands from the directory of the files 
to compile all files:
javac *.java
to execute:
java Main
================================================================
Simulation Input instructions:

Program would continue to run and receive inputs until the user prompts "exit"
Possible inputs are:
A       ‘A’ input means that a new process has been created. 

Q       The currently running process has spent a time quantum using the CPU. If the same process continues to use the CPU and one more Q command arrives, it means that the process has spent one more time quantum.

t         The process that is currently using the CPU terminates. It leaves the system immediately. 

d <number> <file_name>       The process that currently uses the CPU requests the hard disk #number. It wants to read or write file file_name.

D <number>   The hard disk #number has finished the work for one process.

m <address>   The process that is currently using the CPU requests a memory operation for the logical address.

S r     Shows what process is currently using the CPU and what processes are waiting in the ready-queue. 

S i      Shows what processes are currently using the hard disks and what processes are waiting to use them. 

S m   Shows the state of memory. For each used frame display the process number that occupies it and the page number stored in it. The enumeration of pages and frames starts from 0.