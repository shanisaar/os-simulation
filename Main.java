//Shani Saar
// CS340 section 02

import java.util.Scanner;

public class Main {
	private static int PID_GENERATOR = 1;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Shani's OS Simulation!");
		
		System.out.println("How much RAM memory is there on the simulated computer?");
		int ramMemory = input.nextInt();
		
		System.out.println("What is the size of a page/frame?");
		int pageSize = input.nextInt();
		
		System.out.println("How many hard disks does the simulated computer have?");
		int diskAmount = input.nextInt();
		
		Simulation simulation = new Simulation(ramMemory, pageSize, diskAmount);
		
		System.out.println("Let's begin the simulation");
		String user_input = "";
		
		// Goes twice to clear the carriage
		user_input = input.nextLine();
		
		user_input = input.nextLine();
		
		while (!user_input.equals("exit")) {
			if (user_input.equals("A")) {
				Process new_process = new Process(PID_GENERATOR++);
				simulation.InsertNewProcess(new_process, 0);
			} else if (user_input.equals("Q")) {
				simulation.SetTimeQuantum();
			} else if (user_input.equals("t")) {
				simulation.TerminateProcess();
			} else if (user_input.length() > 1) {
				if (user_input.charAt(0) == 'd') {
					String[] a = user_input.split(" ");
					String filename = a[2];
					int hardDiskNumber = Integer.parseInt(a[1]);
					
					if (hardDiskNumber >= 0) {
						simulation.AccessHD(hardDiskNumber, filename);
					} else {
						System.out.println("ERROR: enter a valid HD number.");
					}
				} else if (user_input.charAt(0) == 'D')	{
					String[] a = user_input.split(" ");
					int hardDiskNumber = Integer.parseInt(a[1]);
					if (hardDiskNumber >= 0) {
						simulation.RemoveFromHD(hardDiskNumber);
					} else {
						System.out.println("ERROR: enter a valid HD number.");
					}
				} else if (user_input.charAt(0) == 'm') {
					String[] a = user_input.split(" ");
					int logicalAddress = Integer.parseInt(a[1]);
					simulation.MemoryOperationRequest(logicalAddress);
				} else if (user_input.charAt(0) == 'S') {
					String[] a = user_input.split(" ");
					
					if (a[1].equals("r")) {
						System.out.println("CPU: " + simulation.getCPU().toString());
						System.out.println("Ready Queues:");
						simulation.PrintQueues();
					} else if (a[1].equals("i")) {
						simulation.PrintIOQueues();
					} else if (a[1].equals("m")) {
						simulation.PrintMemoryState();
					} else {
						System.out.println("ERROR: please enter a valid option.");
					}
					
				} else {
					System.out.println("ERROR: please enter a valid input.");
				}
			} else {
				System.out.println("ERROR: please enter a valid input.");
			}
			user_input = input.nextLine();
		}
		
		System.out.println("bye bye!");
		input.close();
	}

}