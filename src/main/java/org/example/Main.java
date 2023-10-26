package org.example;

import java.util.*;

class Process {
    int processID;
    int burstTime;
    int priority;

    public Process(int processID, int burstTime, int priority) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

class ProcessScheduler {
    List<Process> processes;

    public ProcessScheduler() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(int processID, int burstTime, int priority) {
        processes.add(new Process(processID, burstTime, priority));
    }

    public void scheduleProcesses() {
        processes.sort(Comparator.comparingInt(p -> p.priority));
        int n = processes.size();
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];
        waitingTime[0] = 0;
        turnaroundTime[0] = processes.get(0).burstTime;
        for (int i = 1; i < n; i++) {
            waitingTime[i] = waitingTime[i - 1] + processes.get(i - 1).burstTime;
            turnaroundTime[i] = waitingTime[i] + processes.get(i).burstTime;
        }
        System.out.println("ProcessID\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < n; i++) {
            Process process = processes.get(i);
            System.out.println(process.processID + "\t\t" + process.burstTime + "\t\t" + process.priority + "\t\t"
                    + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }
    }
}

class WaitingTimeCalculator {
    public int[] calculateWaitingTime(List<Process> processes) {
        int n = processes.size();
        int[] waitingTime = new int[n];
        waitingTime[0] = 0;
        for (int i = 1; i < n; i++) {
            waitingTime[i] = waitingTime[i - 1] + processes.get(i - 1).burstTime;
        }
        return waitingTime;
    }
}

class TurnaroundTimeCalculator {
    public int[] calculateTurnaroundTime(List<Process> processes, int[] waitingTime) {
        int n = processes.size();
        int[] turnaroundTime = new int[n];
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = waitingTime[i] + processes.get(i).burstTime;
        }
        return turnaroundTime;
    }
}

class ProcessManager {
    private List<Process> processes;

    public ProcessManager() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(Process process) {
        processes.add(process);
    }

    public List<Process> getProcesses() {
        return processes;
    }
}

public class Main {
    public static void main(String[] args) {
        ProcessManager processManager = new ProcessManager();
        processManager.addProcess(new Process(1, 10, 3));
        processManager.addProcess(new Process(2, 1, 1));
        processManager.addProcess(new Process(3, 2, 4));
        processManager.addProcess(new Process(4, 1, 5));

        List<Process> processes = processManager.getProcesses();

        ProcessScheduler scheduler = new ProcessScheduler();
        scheduler.processes = processes;
        scheduler.scheduleProcesses();

        WaitingTimeCalculator waitingTimeCalculator = new WaitingTimeCalculator();
        int[] waitingTime = waitingTimeCalculator.calculateWaitingTime(processes);

        TurnaroundTimeCalculator turnaroundTimeCalculator = new TurnaroundTimeCalculator();
        int[] turnaroundTime = turnaroundTimeCalculator.calculateTurnaroundTime(processes, waitingTime);
    }
}
