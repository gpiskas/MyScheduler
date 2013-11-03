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

/* � ����� ���� ���������� �� ���������� ��� ����������� ���������� ���
 ����������, ������������ ���������� ��� ��� ���� �� CPU. ��������� ���������� ��� ����� �������
 ���������� ������� �� ��� ��������� ������������ SJF (preemptive / non-preemptive). */
public class SJFScheduler {

	/*
	 * �� � ���� ��� ���������� ����� ������, � ������������ �����
	 * ��������������
	 */
	private boolean isPreemptive;

	/* constructor ��� ������ */
	public SJFScheduler(boolean isPreemptive) {
		this.isPreemptive = isPreemptive;
	}

	/*
	 * ��������� ��� ����� ���������� ���� ���������� ������ ��� ������ ���
	 * ������� ����������
	 */
	public void addProcessesToReadyList(ArrayList<Process> p) {
		Main.readyProcessList.addAllProcesses(p);
	}

	/*
	 * ������� ��� �������� ���������� ��� CPU �� ���� �� ����� �������
	 * ���������� ��� �� ����� ��� ���������� ������������ (preemptive /
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

	/* ���������� true ��� ������ SRTF */	
	public boolean isPreemptive() {
		return isPreemptive;
	}

	/* ���������� ��� ������������ ��� ����������� ���� ��������� */
	public String getType() {
		if (isPreemptive()) {
			return "SRTF";
		} else {
			return "SJF";
		}
	}
}
