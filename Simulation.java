//Shani Saar
// CS340 section 02

import java.util.Vector;

public class Simulation {
	private RAM _ramMemory;
	private Process _CPU;
	private Vector<Process> _q0;
	private Vector<Process> _q1;
	private Vector<Process> _q2;
	private Vector<Process>[] _ioqueues;

	@SuppressWarnings("unchecked")
	public Simulation(int ramMemory, int pageSize, int diskAmount) {
		_q0 = new Vector<Process>(); // queue level 0 (RR)
		_q1 = new Vector<Process>(); // queue level 1 (RR)
		_q2 = new Vector<Process>(); // queue level 2 (FCFS)
		_ramMemory = new RAM(ramMemory, pageSize);
		_CPU = null;

		_ioqueues = new Vector[diskAmount];
		for (int i = 0; i < _ioqueues.length; i++) {
			_ioqueues[i] = new Vector<Process>();
		}

	}

	public void InsertNewProcess(Process p, int page) {
		if (_CPU == null) {
			_CPU = p;
		} else {
			_q0.addElement(p);
		}
		_ramMemory.InsertNewProcess(p, page);
	}

	public void TerminateProcess() {
		_ramMemory.RemoveProcessRecord(_CPU);
		_CPU = null;
		PopAnotherProcess();
	}

	public void AccessHD(int hdNumber, String filename) {
		_CPU.set_filename(filename);
		_CPU.set_timeSlice(0);
		_ioqueues[hdNumber].addElement(_CPU);
		_CPU = null;
		PopAnotherProcess();
	}

	public void RemoveFromHD(int hdNumber) {
		if (!_ioqueues[hdNumber].isEmpty()) {
			Process p = _ioqueues[hdNumber].remove(0);
			if (p.get_queueLevel() == 0) {
				_q0.addElement(p);
			} else if (p.get_queueLevel() == 1) {
				_q1.addElement(p);
			} else if (p.get_queueLevel() == 2) {
				_q2.addElement(p);
			}
		}
	}

	public void MemoryOperationRequest(int logicalAddress) {
		_ramMemory.MemoryOperationRequest(_CPU, logicalAddress);
	}

	public void SetTimeQuantum() {
		_CPU.set_timeSlice(_CPU.get_timeSlice() + 1);

		if (_CPU.get_queueLevel() == 0) {
			_CPU.set_timeSlice(0);
			_CPU.set_queueLevel(1);
			_q1.addElement(_CPU);
			_CPU = null;
			PopAnotherProcess();
		} else if (_CPU.get_queueLevel() == 1) {
			if (_CPU.get_timeSlice() == 2) {
				_CPU.set_timeSlice(0);
				_CPU.set_queueLevel(2);
				_q2.addElement(_CPU);
				_CPU = null;
				PopAnotherProcess();
			}
		}
	}

	public void PrintMemoryState() {
		_ramMemory.PrintMemoryState();
	}
	// Prints all the ready queues
	public void PrintQueues() {
		System.out.println("Q0:");
		if (!_q0.isEmpty()) {
			for (Process process : _q0) {
				System.out.print(process.toString() + " ");
			}
		} else {
			System.out.print("Empty Ready Queue.");
		}
		System.out.println();
		System.out.println("Q1:");
		if (!_q1.isEmpty()) {
			for (Process process : _q1) {
				System.out.print(process.toString() + " ");
			}
		} else {
			System.out.print("Empty Ready Queue.");
		}
		System.out.println();
		System.out.println("Q2:");
		if (!_q2.isEmpty()) {
			for (Process process : _q2) {
				System.out.print(process.toString() + " ");
			}
		} else {
			System.out.print("Empty Ready Queue.");
		}
		System.out.println();
	}

	// Prints the I/O queue for every Hard Disk
	public void PrintIOQueues() {
		for (int i = 0; i < _ioqueues.length; i++) {
			System.out.println("HD number " + i + ":");
			if (_ioqueues[i].size() > 0) {
				System.out.println("Currently using: Process " + _ioqueues[i].get(0).get_pid() + " - Filename: " 
						+ _ioqueues[i].get(0).get_filename());
				System.out.println("Waiting: ");
				for (int j = 1; j < _ioqueues[i].size(); j++) {
					System.out.print("Process " + _ioqueues[i].get(j).get_pid() + " - Filename: " 
							+ _ioqueues[i].get(j).get_filename() + " ");
				}
				if (_ioqueues[i].size() > 1) {
					System.out.println();
				} else {
					System.out.println("No pending processes.");
				}
			} else {
				System.out.println("EMPTY I/O QUEUE");
			}
		}
	}

	public Process getCPU()	{
		return _CPU;
	}

	// Moves the next process from the ready queue to the CPU
	private void PopAnotherProcess() {
		if (!_q0.isEmpty()) {
			_CPU = _q0.remove(0);
		} else if (!_q1.isEmpty()) {
			_CPU = _q1.remove(0);
		} else if (!_q2.isEmpty()) {
			_CPU = _q2.remove(0);
		}
	}
}
