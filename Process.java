//Shani Saar
// CS340 section 02

public class Process { // PCB
	private int _pid;
	private int _timeSlice;
	private String _filename;
	private int _queueLevel;
	
	public Process(int pid) {
		_pid = pid;
		_timeSlice = 0;
		_filename = "";
		_queueLevel = 0;
	}

	public int get_pid() {
		return _pid;
	}

	public void set_pid(int _pid) {
		this._pid = _pid;
	}

	public int get_timeSlice() {
		return _timeSlice;
	}

	public void set_timeSlice(int _timeSlice) {
		this._timeSlice = _timeSlice;
	}

	public String get_filename() {
		return _filename;
	}

	public void set_filename(String _filename) {
		this._filename = _filename;
	}

	public int get_queueLevel() {
		return _queueLevel;
	}

	public void set_queueLevel(int _queueLevel) {
		this._queueLevel = _queueLevel;
	}
	
	@Override
	public String toString() {
		return "<Process: " + _pid + " - " + "Queue: " + _queueLevel+">";
	}
}
