def fcfs_scheduling(processes):
    processes.sort(key=lambda x: x['arrival'])
    start_time = 0
    for process in processes:
        if start_time < process['arrival']:
            start_time = process['arrival']
        process['start'] = start_time
        process['completion'] = start_time + process['burst']
        start_time += process['burst']
    return processes

# Input from user
n = int(input("Enter the number of processes: "))
processes = []
for _ in range(n):
    pid = int(input("Enter process ID: "))
    arrival = int(input("Enter arrival time: "))
    burst = int(input("Enter burst time: "))
    processes.append({'id': pid, 'arrival': arrival, 'burst': burst})

scheduled_processes = fcfs_scheduling(processes)
for process in scheduled_processes:
    print(f"Process {process['id']} - Start: {process['start']}, Completion: {process['completion']}")