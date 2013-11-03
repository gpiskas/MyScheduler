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

/* � ������������ ����� ���������� ��� ��������� ��� ���������� */
public class Process {

	/* �������� ��� ������� ������ ������ ��� ���������� ��� ������� */
	private int arrivalTime;
	
	/* �������� �� pid ��� ���������� */
	private int pid;
	
	/* �������� ��� ����� burst */
	private int cpuBurstTime;
	
	/* �������� �� �������� ����� ����������� ��� CPU ��� �� ��������� */
	private int cpuTotalTime;
	
	/* �������� ��� ������������� ����� ����������� ��� CPU ��� �� ��������� */
	private int cpuRemainingTime;
	
	/*
	 * �������� ��� �������� ��������� ��� ����������:
	 * 0 � Created/New
	 * 1 � Ready/Waiting
	 * 2 � Running
	 * 3 � Terminated
	 */
	private int currentState;

	/* �������� ��� ����� ���������, � ������ ������ �� ����������� ���� ��� ����,
	 * �� ����� ��������� �� ��� �������� boolean
	 */	
	private int responseTime;
	private boolean rTCalculated;

	/* �������� ��� ����� ��������� */
	private int waitingTime;
	
	/* �������� ��� ����� ���������� */
	private int turnaroundTime;

	/* constructor � ������������ ��� ������ */
	public Process(int pid, int arrivalTime, int cpuBurstTime) {
		currentState = Utils.CREATED_NEW;
		this.pid = pid;
		this.arrivalTime = arrivalTime;
		this.cpuBurstTime = cpuBurstTime;
		cpuTotalTime = 0;
		cpuRemainingTime = cpuBurstTime;
		rTCalculated = false;
		responseTime = 0;
		waitingTime = 0;
		turnaroundTime = 0;
	}

	/*
	 * ����� ��� ��������� ��� ���������� ��� �� ��� ��������� newState (������
	 * ��� ���������� ���)
	 */
	public void setProcessState(int newState) {
		currentState = newState;
	}

	/*
	 * ������� ��� ������������� ����� ����������� ��� CPU ��� �� ��������� ����
	 * ���
	 */
	public void decrementCpuRemainingTime() {
		cpuRemainingTime -= 1;
	}

	/* ������� ��� �������� ����� ����������� ��� CPU ��� �� ��������� ���� ��� */
	public void incrementCpuTotalTime() {
		cpuTotalTime += 1;
	}

	public int getPid() {
		return pid;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getCpuBurstTime() {
		return cpuBurstTime;
	}

	public int getCpuRemainingTime() {
		return cpuRemainingTime;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	/* ���������� ��� ����� ���������, � ������ ����� � ������ ��� ������������ ����� ����
	 * � ��������� ����� ��� ����� ������. ������������ ��� ����, ���� ���� ��� ����������.
	 */
	public void calculateResponseTime() {
		if (rTCalculated == false) {
			responseTime = Main.clock.getTicks() - arrivalTime;
			rTCalculated = true;
		}
	}

	/* ���������� ��� ����� ��������, � ������ ����� � ������ ��� ������������ 
	 * � ��������� ����� ��� ����� ������ ����� �� ����� �����������. ������������ ��� ����,
	 * �� ��� ���������� ��� ����������.
	 */
	public void calculateWaitingTime() {
		waitingTime = Main.clock.getTicks() - arrivalTime - cpuBurstTime;
	}

	/* ���������� ��� ����� ����������, � ������ ����� � ������ �������� ��� � ������ �����������.
	 * ������������ ��� ����, �� ��� ���������� ��� ����������.
	 */
	public void calculateTurnaroundTime() {
		turnaroundTime = waitingTime + cpuBurstTime;
	}

	/* ������� �� � �������� ��������. */
	public boolean hasFinished() {
		return (cpuRemainingTime == 0);
	}

	@Override
	public String toString() {
		return "PID: " + pid + " | State: " + Utils.stateToString(currentState)
				+ " | Cpu Burst Time " + cpuBurstTime + " | Arrival Time: "
				+ arrivalTime + " | Cpu Total Time: " + cpuTotalTime
				+ " | Cpu Remaining Time: " + cpuRemainingTime;
	}

}
