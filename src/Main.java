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

import java.io.IOException;

/* Από την κλάση αυτή ξεκινά η εκτέλεση της εφαρμογής. Θα πρέπει την εκκίνηση της εκτέλεσης να
 δημιουργείται ένα αντικείμενο για κάθε κλάση από τις κλάσεις: Clock, NewProcessTemporaryList,
 ReadyProcessesList, ProcessGenerator, CPU, και Statistics. Τα αντικείμενα αυτά θα πρέπει να ορίζονται
 ως static μεταβλητές. Επιπλέον, ανάλογα με το είδος του δρομολογητή θα πρέπει να δημιουργείται το
 αντίστοιχο αντικείμενο (ομοίως ως static μεταβλητή) SJFScheduler ή RRScheduler. Για τις ανάγκες της
 εργασίας θα πρέπει να τρέξετε (τουλάχιστον) δύο φορές την προσομοίωση για τις αντίστοιχες δύο
 περιπτώσεις του δρομολογητή (preemptive / non-preemptive για τον SJF). Την πρώτη φορά θα ακολουθείται
 η βασική ροή εκτέλεσης με παραγωγή διεργασιών με ψευδοτυχαία χαρακτηριστικά και εγγραφή τους σε κατάλληλο
 αρχείο, ενώ τη δεύτερη φορά θα ακολουθείται η δευτερεύουσα ροή εκτέλεσης ώστε και για τις δύο περιπτώσεις
 να έχουμε την ίδια είσοδο διεργασιών. */
public class Main {

	public static Clock clock;
	public static NewProcessTemporaryList newProcessList;
	public static ReadyProcessesList readyProcessList;
	public static TerminatedProcessesList terminatedProcessList;
	public static ProcessGenerator processGenerator;
	public static CPU cpu;
	public static Statistics statistics;
	public static SJFScheduler SJFSSheduler;

	/* κύρια συνάρτηση από την οποία εκκινεί η εκτέλεση της προσομοίωσης */
	public static void main(String[] args) {
		
		// Διάβασμα των args.
		boolean generator,srtf;
		if(args.length==2) {
			try {
				if (args[0].equals("-read")) {
					generator = true;
				} else if (args[0].equals("-gen")){
					generator = false;
				} else {
					throw new Exception();
				}
			
				if (args[1].equals("-srtf")) {
					srtf = true;
				} else if (args[1].equals("-sjf")){
					srtf = false;
				} else {
					throw new Exception();
				}
				
			} catch(Exception e) {
				System.err.println("Invalid argument error. Valid arguments:");
				System.err.println("Argument 1: \n -read: Read processes.txt \n -gen: Generate processes.txt");
				System.err.println("Argument 2: \n -srtf: Use SRTF scheduler \n -sjf: Use SJF scheduler");
				return;
			}
		} else {
			System.err.println("Too few arguments. Two needed.");
			System.err.println("Argument 1: \n -read: Read processes.txt \n -gen: Generate processes.txt");
			System.err.println("Argument 2: \n -srtf: Use SRTF scheduler \n -sjf: Use SJF scheduler");
			return;
		}
		
		clock = new Clock();
		newProcessList = new NewProcessTemporaryList();
		readyProcessList = new ReadyProcessesList();
		terminatedProcessList = new TerminatedProcessesList();
		
		try {
			processGenerator = new ProcessGenerator(Utils.PROCESSES_FILENAME, generator);
			newProcessList.addAllProcesses(processGenerator.parseProcessFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		cpu = new CPU();
		
		// Utils.SJF or Utils.SRTF
		SJFSSheduler = new SJFScheduler(srtf);
		
		int totalProcesses = newProcessList.getTotalProcessCount();
		statistics = new Statistics();
		
		// --------------------------------------------------------------------------------------
		while (terminatedProcessList.getSize() < totalProcesses) {
			
			// ’υξηση του συνολικού χρόνου.
			clock.incrementTicks();
			
			// Γέμισμα του ready list από το new list.
			if (!newProcessList.isEmpty()) {
				SJFSSheduler.addProcessesToReadyList(newProcessList.getFirst(clock.getTicks()));
				statistics.updateMaximumListLength();
			}
			
			// Έλεγχος για το άν τελείωσε η διεργασία που βρίσκεται στη cpu
			if (cpu.containsProcess() && cpu.getRunningProcess().hasFinished()){
				cpu.endRunningProcess();
			}
			
			// Δρομολόγηση διεργασίας εάν η ready list έχει διεργασίες.
			if (!readyProcessList.isEmpty()) {
				SJFSSheduler.Schedule();
			}
			
			// Εκτέλεση.
			cpu.execute();
		}

		try {
			statistics.writeStatisticsToFile(terminatedProcessList.getList());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//newProcessList.printList();
		//readyProcessList.printList();
		//terminatedProcessList.printList();
		System.out.println("\nTotal time: " + clock.getTicks());
	}

}
