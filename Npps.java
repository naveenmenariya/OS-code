//Npps
//Priority Scheduling Algorithm (non pre-emptive)
import java.util.Scanner;

public class Npps {
    static void border(int z) {
        for (int i = 0; i < z; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    static void arrangeArrival(int n, int[] arrival_time, int[] burst_time, int[] priority, int[] pid) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] > arrival_time[j]) {
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[i];
                    arrival_time[i] = temp;

                    temp = burst_time[j];
                    burst_time[j] = burst_time[i];
                    burst_time[i] = temp;

                    temp = priority[j];
                    priority[j] = priority[i];
                    priority[i] = temp;

                    temp = pid[j];
                    pid[j] = pid[i];
                    pid[i] = temp;
                }
            }
        }
    }

    static void arrangePriority(int n, int[] arrival_time, int[] burst_time, int[] priority, int[] pid) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] == arrival_time[j]) {
                    if (priority[i] > priority[j]) {
                        int temp = arrival_time[j];
                        arrival_time[j] = arrival_time[i];
                        arrival_time[i] = temp;

                        temp = burst_time[j];
                        burst_time[j] = burst_time[i];
                        burst_time[i] = temp;

                        temp = priority[j];
                        priority[j] = priority[i];
                        priority[i] = temp;

                        temp = pid[j];
                        pid[j] = pid[i];
                        pid[i] = temp;
                    }
                }
            }
        }
    }

    static void findWaitingTime(int n, int[] service_time, int[] waiting_time, int[] arrival_time, int[] burst_time) {
        service_time[0] = 0;
        waiting_time[0] = 0;
        for (int i = 1; i < n; i++) {
            int y = i - 1;
            service_time[i] = service_time[y] + burst_time[y];
            waiting_time[i] = service_time[i] - arrival_time[i];
            if (waiting_time[i] < 0) {
                waiting_time[i] = 0;
            }
        }
    }

    static void findTurnAroundTime(int n, int[] tat, int[] waiting_time, int[] burst_time) {
        for (int i = 0; i < n; i++) {
            tat[i] = waiting_time[i] + burst_time[i];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] pid = new int[n];
        int[] arrival_time = new int[n];
        int[] burst_time = new int[n];
        int[] priority = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process Id: ");
            pid[i] = scanner.nextInt();
            System.out.print("Enter arrival time: ");
            arrival_time[i] = scanner.nextInt();
            System.out.print("Enter burst time: ");
            burst_time[i] = scanner.nextInt();
            System.out.print("Enter priority: ");
            priority[i] = scanner.nextInt();
        }

        arrangeArrival(n, arrival_time, burst_time, priority, pid);
        arrangePriority(n, arrival_time, burst_time, priority, pid);

        int[] service_time = new int[n];
        int[] waiting_time = new int[n];
        int[] tat = new int[n];

        findWaitingTime(n, service_time, waiting_time, arrival_time, burst_time);
        findTurnAroundTime(n, tat, waiting_time, burst_time);

        int total_wt = 0;
        int total_tat = 0;

        border(121);
        System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", "Process Id", "Burst time", "Arrival time", "Waiting time", "Turn around time", "Completion time");
        border(121);
        for (int i = 0; i < n; i++) {
            total_wt += waiting_time[i];
            total_tat += tat[i];
            int completion_time = arrival_time[i] + tat[i];
            System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", pid[i], burst_time[i], arrival_time[i], waiting_time[i], tat[i], completion_time);
        }
        border(121);

        double avgwt = (double) total_wt / n;
        System.out.println("Average waiting time = " + avgwt);
        double avgtat = (double) total_tat / n;
        System.out.println("Average turn around time = " + avgtat);

        for (int i = 0; i < 8 * n + n + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {
            System.out.print("|   ");
            System.out.print("P" + pid[i]);
            System.out.print("   ");
        }
        System.out.println("|");

        for (int i = 0; i < 8 * n + n + 1; i++) {
            System.out.print("-");
        }
        System.out.println();

        System.out.print("0\t");
        for (int i = 0; i < n; i++) {
            System.out.print((arrival_time[i] + tat[i]) + "\t");
        }
        System.out.println();
    }
}
