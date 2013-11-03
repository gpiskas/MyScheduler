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

/* Η κλάση αυτή αναπαριστά τη λειτουργία του δρομολογητή διεργασιών του
 συστήματος, μεταφέροντας διεργασίες από και προς τη CPU. Προσθέτει διεργασίες στη λίστα έτοιμων
 διεργασιών σύμφωνα με τον αλγόριθμο δρομολόγησης SJF (preemptive / non-preemptive). */
public class SJFScheduler {

	/*
	 * αν η τιμή της μεταβλητής είναι αληθής, ο δρομολογητής είναι
	 * προεκχωρήσιμος
	 */
	private boolean isPreemptive;

	/* constructor της κλάσης */
	public SJFScheduler(boolean isPreemptive) {
		this.isPreemptive = isPreemptive;
	}

	/*
	 * τοποθετεί μια λίστα διεργασίων στις κατάλληλες θέσεις της λίστας των
	 * έτοιμων διεργασιών
	 */
	public void addProcessesToReadyList(ArrayList<Process> p) {
		Main.readyProcessList.addAllProcesses(p);
	}

	/*
	 * εκτελεί την εναλλαγή διεργασίας στη CPU με βάση τη λίστα έτοιμων
	 * διεργασιών και το είδος του αλγορίθμου δρομολόγησης (preemptive /
	 * non-preemptive)
	 */
	public void Schedule() {
		if (!Main.cpu.containsProcess()) {
			Main.cpu.addProcess(Main.readyProcessList.getFirst());
		} else if (isPreemptive && Main.cpu.containsProcess()) {
			if (Main.readyProcessList.inspectFirst().getCpuBurstTime() < Main.cpu.getRunningProcess().getCpuRemainingTime()) {
				Main.cpu.switchProcess(Main.readyProcessList.getFirst());
			}
		}
	}

	/* επιστρέφει true εάν έχουμε SRTF */	
	public boolean isPreemptive() {
		return isPreemptive;
	}

	/* επιστρέφει την συμβολοσειρά που αντιστοιχεί στον αλγόριθμο */
	public String getType() {
		if (isPreemptive()) {
			return "SRTF";
		} else {
			return "SJF";
		}
	}
}
