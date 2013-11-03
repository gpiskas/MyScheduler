/*
*  MyScheduler
*  Copyright (C) 2013  George Piskas
*
*  This program is free software; you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation; either version 2 of the License, or
*  (at your option) any later version.
*
*  This program is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  You should have received a copy of the GNU General Public License along
*  with this program; if not, write to the Free Software Foundation, Inc.,
*  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
*  Contact: geopiskas@gmail.com
*/

/* Η κλάση αυτή παριστάνει τη μονάδα επεξεργασίας CPU του συστήματος */
public class CPU {

	/* περιέχει τη διεργασία που χρησιμοποιεί τη CPU την τρέχουσα στιγμή */
	private Process runningProcess;

	/* constructor – αρχικοποίηση των πεδίων */
	public CPU() {
		runningProcess = null;
	}

	/* εισαγωγή της διεργασίας προς εκτέλεση στη CPU */
	public void addProcess(Process p) {
		runningProcess = p;
		runningProcess.setProcessState(Utils.RUNNING);
		System.out.println("Running:" + p.getPid() + " Ticks: "
				+ Main.clock.getTicks());
	}

	/* εναλλαγή της τρέχουσας διεργασίας προς εκτέλεση στη CPU */
	public void switchProcess(Process newProcess) {
		System.out.println("\nSwitching " + runningProcess.getPid() + " with "
				+ newProcess.getPid());
		runningProcess.setProcessState(Utils.READY_WAITING);
		Main.readyProcessList.addSingleProcess(runningProcess);
		addProcess(newProcess);
	}

	/* ελέγχει άν χρησιμοποιείται η CPU */
	public boolean containsProcess() {
		if (runningProcess != null) {
			return true;
		} else {
			return false;
		}
	}

	/* επιστρέφει την τρέχουσα διεργασία */
	public Process getRunningProcess() {
		return runningProcess;
	}

	/* τερματίζει την τρέχουσα διεργασία και τη τοποθετεί στη terminated List */
	public void endRunningProcess() {
		runningProcess.calculateWaitingTime();
		runningProcess.calculateTurnaroundTime();
		System.out.println("\nFinished: " + runningProcess.getPid()
				+ " Ticks: " + Main.clock.getTicks());
		Main.terminatedProcessList.addSingleProcess(runningProcess);
		runningProcess = null;
	}
	
	/* εκτέλεση της διεργασίας με αντίστοιχη μέιωση του χρόνου εκτέλεσής της */
	public void execute() {
		if (containsProcess()) {
			System.out.print(".");
			runningProcess.calculateResponseTime();
			runningProcess.decrementCpuRemainingTime();
			runningProcess.incrementCpuTotalTime();
		}
	}
}
