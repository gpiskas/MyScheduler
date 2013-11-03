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
import java.util.Collections;

/* Αναπαριστά μια λίστα στην οποία τοποθετούνται νέες διεργασίες που μόλις έχουν δημιουργηθεί και
 βρίσκονται στην κατάσταση new */
public class NewProcessTemporaryList {

	/* η λίστα που περιέχει τις νέες διεργασίες */
	private ArrayList<Process> processList;

	/* συνολικός αριθμός διεργασιών που θα εκτελεστούν */
	private int totalProcessCount;

	/* constructor - αρχικοποίηση της λίστας και άλλων πεδίων */
	public NewProcessTemporaryList() {
		processList = new ArrayList<Process>();
	}

	/* εισαγωγή μιας λίστας νέων διεργασίων στη λίστα */
	public void addAllProcesses(ArrayList<Process> pList) {
		processList.addAll(pList);
		Collections.sort(processList, Utils.CMP_ARRIVALTIME);
		totalProcessCount = processList.size();
	}

	/* επιστρέφει τις πρώτες διεργασίες με arrival time = ticks */
	public ArrayList<Process> getFirst(int ticks) {
		ArrayList<Process> pList = new ArrayList<Process>();
		while (!processList.isEmpty()
				&& processList.get(0).getArrivalTime() == ticks) {
			pList.add(processList.get(0));
			processList.remove(0);
		}
		return pList;
	}

	/* ελέγχει αν είναι άδεια η λίστα */
	public boolean isEmpty() {
		return processList.isEmpty();
	}

	/* εκτύπωση της λίστας με τις νέες διεργασίες στην οθόνη */
	public void printList() {
		Utils.printList(processList, "New Process List");
	}

	/* επιστρέφει τον συνολικό αριθμό των διεργασιών */
	public int getTotalProcessCount() {
		return totalProcessCount;
	}
}
