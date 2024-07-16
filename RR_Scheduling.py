def rr_scheduling(processes, quantum):
    queue = []
    time = 0
    processes = sorted(processes, key=lambda x: x['arrival'])
    queue.append(processes[0])
    processes[0]['remaining'] = processes[0]['burst']
    processes[0]['start'] = -1
    i = 1
    while queue:
        process = queue.pop(0)
        if process['start'] == -1:
            process['start'] = time
        if process['remaining'] > quantum:
            process['remaining'] -= quantum
            time += quantum
            while i < len(processes) and processes[i]['arrival'] <= time:
                processes[i]['remaining'] = processes[i]['burst']
                processes[i]['start'] = -1
                queue.append(processes[i])
                i += 1
            queue.append(process)
        else:
            time += process['remaining']
            process['remaining'] = 0
            process['completion'] = time
            while i < len(processes) and processes[i]['arrival'] <= time:
                processes[i]['remaining'] = processes[i]['burst']
                processes[i]['start'] = -1
                queue.append(processes[i])
                i += 1
    return processes

# Input from user
n = int(input("Enter the number of processes: "))
processes = []
for _ in range(n):
    pid = int(input("Enter process ID: "))
    arrival = int(input("Enter arrival time: "))
    burst = int(input("Enter burst time: "))
    processes.append({'id': pid, 'arrival': arrival, 'burst': burst})
quantum = int(input("Enter the quantum time: "))

scheduled_processes = rr_scheduling(processes, quantum)
for process in scheduled_processes:
    print(f"Process {process['id']} - Start: {process['start']}, Completion: {process['completion']}")
