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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/* ���� � ����� ���������� �������� ���������� �������� ����� ��� ���������� ��� ������������ ���
 ������� ��� �� ���������� �� ��� ������ */
public class Statistics {

	/* �� ������ ������� ������ ���������� ���� �������� */
	private int maximumLengthOfReadyProcessesList = 0;
	/* � ������ ��������� ������� ���������� */
	public int totalNumberOfProcesses = 0;

	/* constructor ��� ������ */
	public Statistics() {
		totalNumberOfProcesses = Main.newProcessList.getTotalProcessCount();
	}

	/*
	 * ������� �� ����� ��� ������ ������� ���������� ��� ����������, �� �����
	 * ����������, ��� ���� ��� ���������� maximumLengthOfReadyProcessesList
	 */
	public void updateMaximumListLength() {
		maximumLengthOfReadyProcessesList = Main.readyProcessList.getSize();
	}

	/* ���������� ��� ���� ����� �������� */
	private String calculateAverageWaitingTime(ArrayList<Process> pList) {
		float averageWaitingTime = 0;
		for (Process p : pList) {
			averageWaitingTime += p.getWaitingTime();
		}
		return String.valueOf(averageWaitingTime / totalNumberOfProcesses);
	}

	/* ���������� ��� �������� ����� �������� */
	private String calculateTotalWaitingTime(ArrayList<Process> pList) {
		int totalWaitingTime = 0;
		for (Process p : pList) {
			totalWaitingTime += p.getWaitingTime();
		}
		return String.valueOf(totalWaitingTime);
	}

	/* ���������� ��� ���� ����� ��������� */
	private String calculateAverageResponseTime(ArrayList<Process> pList) {
		float averageResponseTime = 0;
		for (Process p : pList) {
			averageResponseTime += p.getResponseTime();
		}
		return String.valueOf(averageResponseTime / totalNumberOfProcesses);
	}

	/* ���������� ��� ���� ����� ���������� */
	private String calculateAverageTurnaroundTime(ArrayList<Process> pList) {
		float averageTurnaroundTime = 0;
		for (Process p : pList) {
			averageTurnaroundTime += p.getTurnaroundTime();
		}
		return String.valueOf(averageTurnaroundTime / totalNumberOfProcesses);
	}

	/* ��������� ��� ��� ������ �� �� �������� ���������� ��� ������ outputFile */
	public void writeStatisticsToFile(ArrayList<Process> pList) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("statistics_" + Main.SJFSSheduler.getType() + ".txt"));
		out.write("# Date: " + new Date().toString());
		out.newLine();
		out.write("Ticks Needed: " + Main.clock.getTicks());
		out.newLine();
		out.newLine();
		out.write("Total Number Of Processes: " + totalNumberOfProcesses);
		out.newLine();
		out.write("Maximum Ready List Entries: " + maximumLengthOfReadyProcessesList);
		out.newLine();
		out.newLine();
		out.write("Total Waiting Time: " + calculateTotalWaitingTime(pList));
		out.newLine();
		out.write("Average Waiting Time: " + calculateAverageWaitingTime(pList));
		out.newLine();
		out.write("Average Response Time: " + calculateAverageResponseTime(pList));
		out.newLine();
		out.write("Average Turnaround Time: " + calculateAverageTurnaroundTime(pList));
		out.newLine();
		out.close();
	}
}
