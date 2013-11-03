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

/* ���������� ��� ����� ���� ����� ������������� ���� ���������� ��� ����� ����� ������������ ���
 ���������� ���� ��������� new */
public class NewProcessTemporaryList {

	/* � ����� ��� �������� ��� ���� ���������� */
	private ArrayList<Process> processList;

	/* ��������� ������� ���������� ��� �� ����������� */
	private int totalProcessCount;

	/* constructor - ������������ ��� ������ ��� ����� ������ */
	public NewProcessTemporaryList() {
		processList = new ArrayList<Process>();
	}

	/* �������� ���� ������ ���� ���������� ��� ����� */
	public void addAllProcesses(ArrayList<Process> pList) {
		processList.addAll(pList);
		Collections.sort(processList, Utils.CMP_ARRIVALTIME);
		totalProcessCount = processList.size();
	}

	/* ���������� ��� ������ ���������� �� arrival time = ticks */
	public ArrayList<Process> getFirst(int ticks) {
		ArrayList<Process> pList = new ArrayList<Process>();
		while (!processList.isEmpty()
				&& processList.get(0).getArrivalTime() == ticks) {
			pList.add(processList.get(0));
			processList.remove(0);
		}
		return pList;
	}

	/* ������� �� ����� ����� � ����� */
	public boolean isEmpty() {
		return processList.isEmpty();
	}

	/* �������� ��� ������ �� ��� ���� ���������� ���� ����� */
	public void printList() {
		Utils.printList(processList, "New Process List");
	}

	/* ���������� ��� �������� ������ ��� ���������� */
	public int getTotalProcessCount() {
		return totalProcessCount;
	}
}
