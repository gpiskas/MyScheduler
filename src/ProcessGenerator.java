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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* Η συγκεκριμένη κλάση αναπαριστά μια γεννήτρια διεργασιών για την προσομοίωση */
public class ProcessGenerator {

	/* αρχείο που αποθηκεύονται τα δεδομένα των νέων διεργασιών */
	private String inputFile;

	private int pid;
	private BufferedWriter out;

	/*
	 * constructor της κλάσης; αν readFile == false δημιουργεί το αρχείο
	 * inputFile με όνομα filename για αποθήκευση, αλλιώς ανοίγει το αρχείο
	 * inputFile για ανάγνωση
	 */
	public ProcessGenerator(String filename, boolean readFile) throws IOException {
		inputFile = filename;
		if (!readFile) {
			int entries = 5 + (int) (Math.random() * 96); // πεδίο τιμών [5,100]
			out = new BufferedWriter(new FileWriter(inputFile));
			out.write("PID ARRIVAL-TIME BURST-TIME");
			out.newLine();
			for (int i = 0; i < entries; i++) {
				storeProcessToFile();
			}
			out.close();
		}
	}

	/* δημιουργία μιας νέας διεργασίας με ψευδοτυχαία χαρακτηριστικά */
	public Process createProcess() {
		int rArrivalTime = (int) (Math.random() * 15); // πεδίο τιμών [0,14]
		int rCpuBurstTime = 1 + (int) (Math.random() * 50); // πεδίο τιμών [1,50]
		Process p = new Process(pid++, rArrivalTime, rCpuBurstTime);
		return p;
	}

	/* αποθήκευση των στοιχείων της νέας διεργασίας στο αρχείο inputFile */
	public void storeProcessToFile() throws IOException {
		Process p = createProcess();
		out.write(p.getPid() + " " + p.getArrivalTime() + " " + p.getCpuBurstTime());
		out.newLine();
	}

	/* ανάγνωση των στοιχείων νέων διεργασιών από το αρχείο inputFile χρησιμοποιώντας κανονικές εκφράσεις */
	public ArrayList<Process> parseProcessFile() {

		ArrayList<Process> processList = new ArrayList<Process>();
		String line;
		Matcher m;
		int inPid, inArrivalTime, inCpuBurstTime;

		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(inputFile));
			in.readLine();
			while ((line = in.readLine()) != null) {
				m = Pattern.compile("\\d+").matcher(line);

				m.find();
				inPid = Integer.parseInt(m.group());
				m.find();
				inArrivalTime = Integer.parseInt(m.group());
				m.find();
				inCpuBurstTime = Integer.parseInt(m.group());

				processList.add(new Process(inPid, inArrivalTime,
						inCpuBurstTime));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return processList;
	}
}
