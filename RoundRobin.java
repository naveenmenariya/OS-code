//RoundRobin
import java.util.Scanner;

public class RoundRobin {
    static int n;
    static int[] pid, arrival_time, burst_time, arrival_time_copy, burst_time_copy;
    static int quantum;

    static void sort() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arrival_time[j] > arrival_time[j + 1]) {
                    // swap
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[j + 1];
                    arrival_time[j + 1] = temp;

                    temp = burst_time[j];
                    burst_time[j] = burst_time[j + 1];
                    burst_time[j + 1] = temp;

                    temp = pid[j];
                    pid[j] = pid[j + 1];
                    pid[j + 1] = temp;

                    temp = burst_time_copy[j];
                    burst_time_copy[j] = burst_time_copy[i];
                    burst_time_copy[i] = temp;

                    temp = arrival_time_copy[j];
                    arrival_time_copy[j] = arrival_time_copy[i];
                    arrival_time_copy[i] = temp;
                } else if (arrival_time[j] == arrival_time[j + 1]) {
                    if (pid[j] == pid[j + 1]) {
                        int temp = arrival_time[j];
                        arrival_time[j] = arrival_time[j + 1];
                        arrival_time[j + 1] = temp;

                        temp = burst_time[j];
                        burst_time[j] = burst_time[j + 1];
                        burst_time[j + 1] = temp;

                        temp = pid[j];
                        pid[j] = pid[j + 1];
                        pid[j + 1] = temp;

                        temp = burst_time_copy[j];
                        burst_time_copy[j] = burst_time_copy[i];
                        burst_time_copy[i] = temp;

                        temp = arrival_time_copy[j];
                        arrival_time_copy[j] = arrival_time_copy[i];
                        arrival_time_copy[i] = temp;
                    }
                }
            }
        }
    }

    static void calcWaitingTime() {
        int t = 0;
        int arrival = 0;
        int is_completed = 0;
        int[] chart = new int[5000];

        while (is_completed == 0) {
            is_completed = 1;
            for (int i = 0; i < n; i++) {
                chart[t] = i + 1;
                if (burst_time[i] > 0) {
                    is_completed = 0;
                    if (burst_time[i] > quantum && arrival_time[i] <= arrival) {
                        for (int j = 0; j < quantum; j++) {
                            chart[t] = i + 1;
                            t++;
                        }
                        burst_time[i] -= quantum;
                        arrival++;
                    } else {
                        if (arrival_time[i] <= arrival) {
                            arrival++;
                            for (int j = 0; j < burst_time[i]; j++) {
                                chart[t] = i + 1;
                                t++;
                            }
                            burst_time[i] = 0;
                        }
                    }
                }
            }
            if (is_completed == 1) {
                t = t + quantum;
            }
        }

        int[] completion_time = new int[n];
        int[] tat = new int[n];
        int[] waiting_time = new int[n];

        int total_wt = 0;
        int total_tat = 0;
        System.out.println("-----------------------------");
        System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", "Process Id", "Burst time", "Arrival time", "Waiting time", "Turn around time", "Completion time");
        System.out.println("-----------------------------");
        for (int i = 0; i < n; i++) {
            tat[i] = completion_time[i] - arrival_time_copy[i];
            waiting_time[i] = tat[i] - burst_time_copy[i];
            total_wt += waiting_time[i];
            total_tat += tat[i];
            completion_time[i] = arrival_time[i] + tat[i];
            System.out.printf("|%-18d|%-20d|%-18d|%-20d|%-18d|%-20d|\n", pid[i], burst_time_copy[i], arrival_time_copy[i], waiting_time[i], tat[i], completion_time[i]);
        }
        System.out.println("-----------------------------");

        double avgwt = (double) total_wt / n;
        System.out.println("Average waiting time = " + avgwt);
        double avgtat = (double) total_tat / n;
        System.out.println("Average turn around time = " + avgtat);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        n = scanner.nextInt();
        pid = new int[n];
        arrival_time = new int[n];
        burst_time = new int[n];
        arrival_time_copy = new int[n];
        burst_time_copy = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process Id: ");
            pid[i] = scanner.nextInt();
            System.out.print("Enter arrival time: ");
            arrival_time[i] = scanner.nextInt();
            arrival_time_copy[i] = arrival_time[i];
            System.out.print("Enter burst time: ");
            burst_time[i] = scanner.nextInt();
            burst_time_copy[i] = burst_time[i];
        }

        System.out.print("Enter quantum size: ");
        quantum = scanner.nextInt();
        sort();
        calcWaitingTime();
    }
}
