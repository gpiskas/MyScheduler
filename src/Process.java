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

/* Η συγκεκριμένη κλάση αναπαριστά μια διεργασία του συστήματος */
public class Process {

	/* περιέχει την χρονική στιγμή άφιξης της διεργασίας στο σύστημα */
	private int arrivalTime;
	
	/* περιέχει το pid της διεργασίας */
	private int pid;
	
	/* περιέχει τον χρόνο burst */
	private int cpuBurstTime;
	
	/* περιέχει το συνολικό χρόνο απασχόλησης της CPU από τη διεργασία */
	private int cpuTotalTime;
	
	/* περιέχει τον εναπομείναντα χρόνο απασχόλησης της CPU από τη διεργασία */
	private int cpuRemainingTime;
	
	/*
	 * περιέχει την τρέχουσα κατάσταση της διεργασίας:
	 * 0 – Created/New
	 * 1 – Ready/Waiting
	 * 2 – Running
	 * 3 – Terminated
	 */
	private int currentState;

	/* περιέχει τον χρόνο απόκρισης, ο οποίος πρέπει να υπολογιστεί μόνο μία φορα,
	 * το οποίο ελέγχεται με την παρακάτω boolean
	 */	
	private int responseTime;
	private boolean rTCalculated;

	/* περιέχει τον χρόνο ανανμονής */
	private int waitingTime;
	
	/* περιέχει τον χρόνο επιστροφής */
	private int turnaroundTime;

	/* constructor – αρχικοποίηση των πεδίων */
	public Process(int pid, int arrivalTime, int cpuBurstTime) {
		currentState = Utils.CREATED_NEW;
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.cpuBurstTime = cpuBurstTime;
		cpuTotalTime = 0;
		cpuRemainingTime = cpuBurstTime;
		rTCalculated = false;
		responseTime = 0;
		waitingTime = 0;
		turnaroundTime = 0;
	}

	/*
	 * θέτει την κατάσταση της διεργασίας ίση με την παράμετρο newState (αλλαγή
	 * της κατάστασής της)
	 */
	public void setProcessState(int newState) {
		currentState = newState;
	}

	/*
	 * μειώνει τον εναπομείναντα χρόνο απασχόλησης της CPU από τη διεργασία κατά
	 * ένα
	 */
	public void decrementCpuRemainingTime() {
		cpuRemainingTime -= 1;
	}

	/* αυξάνει τον συνολικό χρόνο απασχόλησης της CPU από τη διεργασία κατά ένα */
	public void incrementCpuTotalTime() {
		cpuTotalTime += 1;
	}

	public int getPid() {
		return pid;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getCpuBurstTime() {
		return cpuBurstTime;
	}

	public int getCpuRemainingTime() {
		return cpuRemainingTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	/* υπολογίζει τον χρόνο απόκρισης, ο οποίος είναι ο χρόνος που πυροδοτήθηκε πρώτη φορα
	 * η διεργασία μείον τον χρόνο άφιξης. Υπολογίζεται μία φορα, στην αρχή της διεργασίας.
	 */
	public void calculateResponseTime() {
		if (rTCalculated == false) {
			responseTime = Main.clock.getTicks() - arrivalTime;
			rTCalculated = true;
		}
	}

	/* υπολογίζει τον χρόνο αναμονής, ο οποίος είναι ο χρόνος που ολοκληρώθηκε 
	 * η διεργασία μείον τον χρόνο άφιξης μείον το χρόνο καταιγισμού. Υπολογίζεται μία φορά,
	 * με τον τερματισμό της διεργασίας.
	 */
	public void calculateWaitingTime() {
		waitingTime = Main.clock.getTicks() - arrivalTime - cpuBurstTime;
	}

	/* υπολογίζει τον χρόνο επιστροφής, ο οποίος είναι ο χρόνος αναμονής σύν ο χρόνος καταιγισμού.
	 * Υπολογίζεται μία φορά, με τον τερματισμό της διεργασίας.
	 */
	public void calculateTurnaroundTime() {
		turnaroundTime = waitingTime + cpuBurstTime;
	}

	/* ελέγχει αν η διερασία τελείωσε. */
	public boolean hasFinished() {
		return (cpuRemainingTime == 0);
	}

	@Override
	public String toString() {
		return "PID: " + pid + " | State: " + Utils.stateToString(currentState)
				+ " | Cpu Burst Time " + cpuBurstTime + " | Arrival Time: "
				+ arrivalTime + " | Cpu Total Time: " + cpuTotalTime
				+ " | Cpu Remaining Time: " + cpuRemainingTime;
	}

}
