//Shani Saar
// CS340 section 02

import java.util.Vector;

public class RAM {
	private static int TIME_STAMP = 1;
	private int _size;
	private Vector<Vector<Integer>> _memory;
	
	public RAM(int ramMemory, int pageSize) {
		_size = Math.abs(ramMemory/pageSize);
		_memory = new Vector<Vector<Integer>>();
	}
	
	public void InsertNewProcess(Process p, int page) {
		if (_memory.size() < _size) {
			InsertRecordOfProcess(p, page);
		} else {
			int minTimeStamp = Integer.MAX_VALUE;
			int minIndex = -1, index = 0;
			for (Vector<Integer> vec : _memory) {
				if (vec.get(3) <= minTimeStamp) {
					minTimeStamp = vec.get(3);
					minIndex = index;
				}
				index++;
			}
			if (minIndex >= 0) {
				_memory.remove(minIndex);
				InsertRecordOfProcess(p, page);
			}
		}
	}
	
	public void RemoveProcessRecord(Process p) {
		int index = 0;
		for (Vector<Integer> vector : _memory) {
			if (p.get_pid() == vector.get(2)) {
				break;
			} else {
				index++;
			}
		}
		_memory.remove(index);
	}
	
	public void MemoryOperationRequest(Process p, int logicalAddress) {
		int newPageNumber = logicalAddress / _size;
		for (Vector<Integer> vector : _memory) {
			if (vector.get(2) == p.get_pid()) {
				if (vector.get(1) == newPageNumber) {
					vector.remove(3);
					vector.addElement(TIME_STAMP++);
				} else {
					InsertNewProcess(p, newPageNumber);
				}
				break;
			}
		}
	}
	
	public void PrintMemoryState() {
		for (int i = 0; i < _memory.size(); i++) {
			System.out.println("Frame: " + i + " Page number: " + _memory.get(i).get(1) + 
					" Process: " + _memory.get(i).get(2) + " Time Stamp: " + _memory.get(i).get(3));
		}
	}
	
	private void InsertRecordOfProcess(Process p, int page) {
		Vector<Integer> a = new Vector<>();
		a.add(_memory.size() - 1);
		a.add(page);
		a.add(p.get_pid());
		a.add(TIME_STAMP++);
		_memory.add(a);
	}
}
