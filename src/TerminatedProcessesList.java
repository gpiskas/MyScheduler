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

/* Λίστα στην οποία τοποθετούνται οι τερματισμένες (Terminated) διεργασίες.*/
public class TerminatedProcessesList {

	/* η λίστα που περιέχει τις τερματισμένες διεργασίες */
	private ArrayList<Process> processList;

	/* constructor της κλάσης */
	public TerminatedProcessesList() {
		processList = new ArrayList<Process>();
	}

	/* προσθήκη μιας τερματισμένης διεργασίας στη λίστα */
	public void addSingleProcess(Process p) {
		p.setProcessState(Utils.TERMINATED);
		processList.add(p);
	}

	/* εκτύπωση του περιεχομένου της λίστας στην οθόνη */
	public void printList() {
		Utils.printList(processList, "Terminated Process List");
	}

	public ArrayList<Process> getList() {
		return processList;
	}
	
	/* επιστρέφει το μέγεθος της λίστας */
	public int getSize() {
		return processList.size();
	}
}
