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

/* ����� ���� ����� ������������� �� ������� (Ready) ����������. � ��������� ��� ��������� ��� ��� ��������
 ��������� ��� �� ��������������. ���������� ��� ���������� �� ����� ��������� � ����� ������� ����� �������
 ������������ ������, ����� ��������������, � ������� ����� ����� ��������� */
public class ReadyProcessesList {

	/* � ����� ��� �������� ��� ������� ���������� */
	private ArrayList<Process> processList;

	/* constructor ��� ������ */
	public ReadyProcessesList() {
		processList = new ArrayList<Process>();
	}

	/* �������� ���� ������� ���������� ��� ����� */
	public void addSingleProcess(Process p) {
		processList.add(p);
		Collections.sort(processList, Utils.CMP_CPUREMTIME);
	}

	/* �������� ���� ������ �� ���� ������� ���������� ��� ����� */
	public void addAllProcesses(ArrayList<Process> pList) {
		for (int i = 0; i < pList.size(); i++) {
			pList.get(i).setProcessState(Utils.READY_WAITING);
		}
		processList.addAll(pList);
		Collections.sort(processList, Utils.CMP_CPUREMTIME);
	}

	/*
	 * ��������� ��� ���������� ��� ������ � ����� ����� �� ���������� ��� CPU
	 * ��� �������� ��� ��� �� �����.
	 */
	public Process getFirst() {
		return processList.remove(0);
	}

	/*
	 * ��������� ��� ���������� ��� ������ � ����� ����� �� ���������� ��� CPU 
	 * ����� �������� ��� �� �����.
	 */
	public Process inspectFirst() {
		return processList.get(0);
	}

	/* ������� �� ����� ����� � ����� */
	public boolean isEmpty() {
		return processList.isEmpty();
	}

	/* �������� ��� ������������ ��� ������ ���� ����� */
	public void printList() {
		Utils.printList(processList, "Ready Process List");
	}

	/* ���������� �� ������ ������� ��� ������ */
	public int getSize() {
		return processList.size();

	}

}
