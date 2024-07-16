//Fcfs
import java.util.Scanner;
import java.util.Arrays;

public class Fcfs {
    static int[] pid;
    static int[] arrivalTime;
    static int[] burstTime;
    static int[] waitingTime;
    static int[] turnaroundTime;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        pid = new int[n];
        arrivalTime = new int[n];
        burstTime = new int[n];
        waitingTime = new int[n];
        turnaroundTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process Id: ");
            pid[i] = scanner.nextInt();
            System.out.print("Enter arrival time: ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time: ");
            burstTime[i] = scanner.nextInt();
        }

        findAverageTime(n);
        scanner.close();
    }

    static void findAverageTime(int n) {
        sort(n);
        findWaitingTime(n);
        findTurnAroundTime(n);

        int totalWaitingTime = Arrays.stream(waitingTime).sum();
        int totalTurnaroundTime = Arrays.stream(turnaroundTime).sum();

        double avgWaitingTime = (double) totalWaitingTime / n;
        double avgTurnaroundTime = (double) totalTurnaroundTime / n;

        System.out.println("Average waiting time = " + avgWaitingTime);
        System.out.println("Average turnaround time = " + avgTurnaroundTime);

        // Display Gantt Chart
        System.out.print("Gantt Chart: ");
        int completionTime = 0;
        for (int i = 0; i < n; i++) {
            completionTime += burstTime[i];
            System.out.print(" P" + pid[i] + " " + completionTime);
        }
    }

    static void sort(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrivalTime[j] > arrivalTime[j + 1]) {
                    // swap
                    int temp = arrivalTime[j];
                    arrivalTime[j] = arrivalTime[j + 1];
                    arrivalTime[j + 1] = temp;
                    temp = burstTime[j];
                    burstTime[j] = burstTime[j + 1];
                    burstTime[j + 1] = temp;
                    temp = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = temp;
                } else if (arrivalTime[j] == arrivalTime[j + 1]) {
                    if (pid[j] == pid[j + 1]) {
                        int temp = arrivalTime[j];
                        arrivalTime[j] = arrivalTime[j + 1];
                        arrivalTime[j + 1] = temp;
                        temp = burstTime[j];
                        burstTime[j] = burstTime[j + 1];
                        burstTime[j + 1] = temp;
                        temp = pid[j];
                        pid[j] = pid[j + 1];
                        pid[j + 1] = temp;
                    }
                }
            }
        }
    }

    static void findWaitingTime(int n) {
        int[] serviceTime = new int[n];
        serviceTime[0] = 0;
        waitingTime[0] = 0;
        for (int i = 1; i < n; i++) {
            serviceTime[i] = serviceTime[i - 1] + burstTime[i - 1];
            waitingTime[i] = Math.max(0, serviceTime[i] - arrivalTime[i]);
        }
    }

    static void findTurnAroundTime(int n) {
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = waitingTime[i] + burstTime[i];
        }
    }
}
