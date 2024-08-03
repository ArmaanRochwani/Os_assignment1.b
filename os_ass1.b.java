import java.util.LinkedList;
import java.util.Queue;

class Process {
    int pid;            // Process ID
    int burstTime;      // Burst Time
    int arrivalTime;    // Arrival Time
    int remainingTime;  // Remaining Time
    int completionTime; // Completion Time
    int waitingTime;    // Waiting Time
    int turnAroundTime; // Turn Around Time
    int responseTime;   // Response Time
    boolean started;    // Flag to check if process has started

    public Process(int pid, int burstTime, int arrivalTime) {
        this.pid = pid;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingTime = burstTime;
        this.started = false;
    }
}

public class abc {
    private static int contextSwitchTime = 1;

    public static void main(String[] args) {
        int timeQuantum = 8; // Time quantum for Round Robin
        Queue<Process> readyQueue = new LinkedList<>();

        // Add processes to the ready queue
        readyQueue.add(new Process(1, 8, 0));
        readyQueue.add(new Process(2, 4, 1));
        readyQueue.add(new Process(3, 9, 2));

        simulateRoundRobin(readyQueue, timeQuantum);
    }

    public static void simulateRoundRobin(Queue<Process> readyQueue, int timeQuantum) {
        int currentTime = 0;
        int contextSwitches = 0;
        LinkedList<Process> completedProcesses = new LinkedList<>();

        while (!readyQueue.isEmpty()) {
            Process currentProcess = readyQueue.poll();

            if (!currentProcess.started) {
                currentProcess.responseTime = currentTime - currentProcess.arrivalTime;
                currentProcess.started = true;
            }

            int executionTime = Math.min(currentProcess.remainingTime, timeQuantum);
            System.out.println("Time " + currentTime + ": Process " + currentProcess.pid + " is running");

            currentTime += executionTime;
            currentProcess.remainingTime -= executionTime;

            if (currentProcess.remainingTime > 0) {
                readyQueue.add(currentProcess);
                currentTime = currentTime + contextSwitchTime; // Context switch time
                contextSwitches++;
            } else {
                currentProcess.completionTime = currentTime;
                currentProcess.turnAroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                currentProcess.waitingTime = currentProcess.turnAroundTime - currentProcess.burstTime;
                completedProcesses.add(currentProcess);
            }
        }

        printStatistics(completedProcesses, contextSwitches);
    }

    public static void printStatistics(LinkedList<Process> processes, int contextSwitches) {
        int totalWaitingTime = 0;
        int totalTurnAroundTime = 0;
        int totalResponseTime = 0;
        int totalBurstTime = 0;

        System.out.println("\nProcess\t\tCompletion\t\tTurn Around\t\tWaiting\t\tResponse");
        for (Process process : processes) {
            System.out.println("\t" + process.pid + "\t\t\t" + process.completionTime + "\t\t\t\t" + process.turnAroundTime + "\t\t\t\t" + process.waitingTime + "\t\t\t" + process.responseTime);
            totalWaitingTime += process.waitingTime;
            totalTurnAroundTime += process.turnAroundTime;
            totalResponseTime += process.responseTime;
            totalBurstTime += process.burstTime;
        }

        int n = processes.size();
        System.out.println("\nAverage CPU Burst Time: " + (totalBurstTime / (float) n));
        System.out.println("Average Waiting Time: " + (totalWaitingTime / (float) n));
        System.out.println("Average Turn Around Time: " + (totalTurnAroundTime / (float) n));
        System.out.println("Average Response Time: " + (totalResponseTime / (float) n));
        System.out.println("Total Number of Context Switches: " + contextSwitches);
    }
}
