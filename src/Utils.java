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

import java.util.ArrayList;
import java.util.Comparator;

/* Βοηθητική κλάση */
public class Utils {
	
	/*
	 * περιέχει την τρέχουσα κατάσταση της διεργασίας: 
	 * 0 – Created/New
	 * 1 – Ready/Waiting
	 * 2 – Running 
	 * 3 – Terminated
	 */
	public static final int CREATED_NEW = 0;
	public static final int READY_WAITING = 1;
	public static final int RUNNING = 2;
	public static final int TERMINATED = 3;

	public static final String CREATED_NEW_STRING = "CREATED_NEW";
	public static final String READY_WAITING_STRING = "READY";
	public static final String RUNNING_STRING = "RUNNING";
	public static final String TERMINATED_STRING = "TERMINATED";

	public static final String PROCESSES_FILENAME = "processes.txt";

	/* επιστρέφει την string μορφή του state */
	public static String stateToString(int state) {
		switch (state) {
		case CREATED_NEW:
			return CREATED_NEW_STRING;
		case READY_WAITING:
			return READY_WAITING_STRING;
		case RUNNING:
			return RUNNING_STRING;
		case TERMINATED:
			return TERMINATED_STRING;
		}
		return "Invalid state";
	}

	/* εκτύπωση του περιεχομένου της δοθείσας λίστας στην οθόνη */
	public static void printList(ArrayList<Process> processList, String type) {
		System.out.println(">>> " + type);
		for (Process p : processList) {
			System.out.println(p);
		}
		System.out.println("<<< End Of " + type);
	}
	
	/* συγκρίνει δύο διεργασίες με βάση τον εναπομείναντα χρόνο cpu.
	 * Χρησιμοποιείται για ταξινόμηση της readyList.
	 * -1 = pi.crt < p2.crt 
	 * 0 = pi.crt = p2.crt
	 * 1 = pi.crt > p2.crt 
	 */
	public static final Comparator<Process> CMP_CPUREMTIME = new Comparator<Process>() {
		@Override
		public int compare(Process p1, Process p2) {
			if (p1.getCpuRemainingTime() < p2.getCpuRemainingTime()) {
				return -1;
			} else if (p1.getCpuRemainingTime() > p2.getCpuRemainingTime()) {
				return 1;
			} else {
				return 0;
			}
		}
	};

	/* συγκρίνει δύο διεργασίες με βάση τον χρόνο άφιξης.
	 * Χρησιμοποιείται για ταξινόμηση της newList.
	 * -1 = pi.at < p2.at 
	 * 0 = pi.at = p2.at
	 * 1 = pi.at > p2.at 
	 */
	public static final Comparator<Process> CMP_ARRIVALTIME = new Comparator<Process>() {
		@Override
		public int compare(Process p1, Process p2) {
			if (p1.getArrivalTime() < p2.getArrivalTime()) {
				return -1;
			} else if (p1.getArrivalTime() > p2.getArrivalTime()) {
				return 1;
			} else {
				return 0;
			}
		}
	};
}
