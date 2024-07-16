//SJF_Preemptive
import java.util.Scanner;

public class SJF_Preemptive {

    static void border(int z) {
        for (int i = 0; i < z; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    static void arrangeArrival(int n, int[] arrival_time, int[] burst_time, int[] pid, int[] burst_time_copy) {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arrival_time[i] > arrival_time[j]) {
                    int temp = arrival_time[j];
                    arrival_time[j] = arrival_time[i];
                    arrival_time[i] = temp;

                    temp = burst_time[j];
                    burst_time[j] = burst_time[i];
                    burst_time[i] = temp;

                    temp = burst_time_copy[j];
                    burst_time_copy[j] = burst_time_copy[i];
                    burst_time_copy[i] = temp;

                    temp = pid[j];
                    pid[j] = pid[i];
                    pid[i] = temp;
                }
            }
        }
    }

    static void arrangeBurst(int n, int[] arrival_time, int[] burst_time, int[] pid, int[] burst_time_copy) {
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

                        temp = burst_time_copy[j];
                        burst_time_copy[j] = burst_time_copy[i];
                        burst_time_copy[i] = temp;

                        temp = pid[j];
                        pid[j] = pid[i];
                        pid[i] = temp;
                    }
                }
            }
        }
    }

    static void timeCalc(int n, int[] arrival_time, int[] burst_time, int[] pid, int[] burst_time_copy) {
        int is_completed = 0;
        int current_time = 0;
        int cp = 0;
        int count = 0;
        int max = 1000;
        int[] chart = new int[1000]; // Assuming maximum 1000 time units
        int[] completion_time = new int[n];
        int[] waiting_time = new int[n];
        int[] tat = new int[n];

        while (is_completed == 0) {
            if (count == n) {
                is_completed = 1;
            }
            chart[current_time] = cp + 1;
            current_time++;
            if (burst_time[cp] > 0) {
                burst_time[cp]--;
                if (burst_time[cp] == 0) {
                    count++;
                    completion_time[cp] = current_time;
                    max = 1000;
                }
            }
            int prevcp = cp;
            for (int i = 0; i < n; i++) {
                if (arrival_time[i] <= current_time && burst_time[i] < max && burst_time[i] > 0) {
                    cp = i;
                    max = burst_time[i];
                }
            }
            if (prevcp != cp) {
                waiting_time[prevcp] = current_time;
            }
        }

        for (int i = 0; i < n; i++) {
            waiting_time[i] = completion_time[i] - arrival_time[i] - burst_time_copy[i];
            if (waiting_time[i] < 0) {
                waiting_time[i] = 0;
            }
            tat[i] = waiting_time[i] + burst_time_copy[i];
        }

        int total_wt = 0;
        int total_tat = 0;

        border(121);
        System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", "Process Id", "Burst time", "Arrival time", "Waiting time", "Turn around time", "Completion time");
        border(121);
        for (int i = 0; i < n; i++) {
            total_wt += waiting_time[i];
            total_tat += tat[i];
            int completion = arrival_time[i] + tat[i];
            System.out.printf("|%-18s|%-20s|%-18s|%-20s|%-18s|%-20s|\n", pid[i], burst_time_copy[i], arrival_time[i], waiting_time[i], tat[i], completion);
        }
        border(121);

        double avgwt = (double) total_wt / n;
        System.out.println("Average waiting time = " + avgwt);
        double avgtat = (double) total_tat / n;
        System.out.println("Average turn around time = " + avgtat);

        int count_cols = 1;
        int[] cols_id = new int[1000]; // Assuming maximum 1000 time units
        int[] cols = new int[1000]; // Assuming maximum 1000 time units
        cols_id[0] = chart[0];
        cols[0] = 0;
        int j = 1;
        for (int i = 1; i < current_time; i++) {
            if (chart[i] != chart[i - 1]) {
                count_cols++;
                cols[j] = i;
                cols_id[j] = chart[i];
                j++;
            }
        }

        System.out.println();

        border(8 * count_cols + count_cols + 1);
        for (int i = 0; i < count_cols; i++) {
            System.out.print("|   ");
            System.out.print("P" + cols_id[i]);
            System.out.print("   ");
        }
        System.out.println("|");

        border(8 * count_cols + count_cols + 1);
        System.out.print("0\t");
        for (int i = 1; i < count_cols; i++) {
            System.out.print(cols[i]);
            System.out.print("\t");
        }
        System.out.println(current_time);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] pid = new int[n];
        int[] arrival_time = new int[n];
        int[] burst_time = new int[n];
        int[] burst_time_copy = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter Process Id: ");
            pid[i] = scanner.nextInt();
            System.out.print("Enter arrival time: ");
            arrival_time[i] = scanner.nextInt();
            System.out.print("Enter burst time: ");
            burst_time[i] = scanner.nextInt();
            burst_time_copy[i] = burst_time[i];
        }

        arrangeArrival(n, arrival_time, burst_time, pid, burst_time_copy);
        arrangeBurst(n, arrival_time, burst_time, pid, burst_time_copy);
        timeCalc(n, arrival_time, burst_time, pid, burst_time_copy);
    }
}
