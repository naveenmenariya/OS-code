def sjf_non_preemptive_scheduling(processes):
    processes.sort(key=lambda x: (x['arrival'], x['burst']))
    start_time = 0
    scheduled_processes = []
    while processes:
        available_processes = [p for p in processes if p['arrival'] <= start_time]
        if not available_processes:
            start_time = processes[0]['arrival']
            available_processes = [processes[0]]
        next_process = min(available_processes, key=lambda x: x['burst'])
        processes.remove(next_process)
        if start_time < next_process['arrival']:
            start_time = next_process['arrival']
        next_process['start'] = start_time
        next_process['completion'] = start_time + next_process['burst']
        start_time += next_process['burst']
        scheduled_processes.append(next_process)
    return scheduled_processes

# Input from user
n = int(input("Enter the number of processes: "))
processes = []
for _ in range(n):
    pid = int(input("Enter process ID: "))
    arrival = int(input("Enter arrival time: "))
    burst = int(input("Enter burst time: "))
    processes.append({'id': pid, 'arrival': arrival, 'burst': burst})

scheduled_processes = sjf_non_preemptive_scheduling(processes)
for process in scheduled_processes:
    print(f"Process {process['id']} - Start: {process['start']}, Completion: {process['completion']}")
