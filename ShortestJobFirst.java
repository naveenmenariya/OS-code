//ShortestJobFirst
import java.util.Scanner;

public class ShortestJobFirst {
    static void border() {
        int z = 121;
        for (int i = 0; i < z; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    static void arrangeArrival(int n, int[] arrival_time, int[] burst_time, int[] pid) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] > arrival_time[j]) {
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[i];
                    arrival_time[i] = temp;

                    temp = burst_time[j];
                    burst_time[j] = burst_time[i];
                    burst_time[i] = temp;

                    temp = pid[j];
                    pid[j] = pid[i];
                    pid[i] = temp;
                }
            }
        }
    }

    static void arrangeBurst(int n, int[] arrival_time, int[] burst_time, int[] pid) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] == arrival_time[j]) {
                    if (burst_time[i] > burst_time[j]) {
                        int temp = arrival_time[j];
                        arrival_time[j] = arrival_time[i];
                        arrival_time[i] = temp;

                        temp = burst_time[j];
                        burst_time[j] = burst_time[i];
                        burst_time[i] = temp;

                        temp = pid[j];
                        pid[j] = pid[i];
                        pid[i] = temp;
                    }
                }
            }
        }
    }

    static void completionTime(int n, int[] arrival_time, int[] burst_time, int[] pid,
                               int[] completion_time, int[] tat, int[] waiting_time) {
        completion_time[0] = arrival_time[0] + burst_time[0];
        tat[0] = completion_time[0] - arrival_time[0];
        waiting_time[0] = tat[0] - burst_time[0];

        for (int i = 1; i < n; i++) {
            int temp = completion_time[i - 1];
            int low = burst_time[i];
            int val = i;

            for (int j = i; j < n; j++) {
                if (temp >= arrival_time[j]) {
                    if (low >= burst_time[j]) {
                        low = burst_time[j];
                        val = j;
                    }
                }
            }

            completion_time[val] = temp + burst_time[val];
            tat[val] = completion_time[val] - arrival_time[val];
            waiting_time[val] = tat[val] - burst_time[val];

            if (val != i) {
                temp = arrival_time[val];
                arrival_time[val] = arrival_time[i];
                arrival_time[i] = temp;

                temp = burst_time[val];
                burst_time[val] = burst_time[i];
                burst_time[i] = temp;

                temp = pid[val];
                pid[val] = pid[i];
                pid[i] = temp;

                temp = completion_time[val];
                completion_time[val] = completion_time[i];
                completion_time[i] = temp;

                temp = waiting_time[val];
                waiting_time[val] = waiting_time[i];
                waiting_time[i] = temp;

                temp = tat[val];
                tat[val] = tat[i];
                tat[i] = temp;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] pid = new int[n];
        int[] arrival_time = new int[n];
        int[] burst_time = new int[n];
        int[] completion_time = new int[n];
        int[] tat = new int[n];
        int[] waiting_time = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process Id: ");
            pid[i] = scanner.nextInt();
            System.out.print("Enter arrival time: ");
            arrival_time[i] = scanner.nextInt();
            System.out.print("Enter burst time: ");
            burst_time[i] = scanner.nextInt();
        }

        arrangeArrival(n, arrival_time, burst_time, pid);
        arrangeBurst(n, arrival_time, burst_time, pid);
        completionTime(n, arrival_time, burst_time, pid, completion_time, tat, waiting_time);

        int total_wt = 0;
        int total_tat = 0;

        border();
        System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", "Process Id", "Burst time", "Arrival time", "Waiting time", "Turn around time", "Completion time");
        border();
        for (int i = 0; i < n; i++) {
            total_wt += waiting_time[i];
            total_tat += tat[i];
            int completion = arrival_time[i] + tat[i];
            System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", pid[i], burst_time[i], arrival_time[i], waiting_time[i], tat[i], completion);
        }
        border();

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
            System.out.print(arrival_time[i] + tat[i] + "\t");
        }
        System.out.println();
    }
}
