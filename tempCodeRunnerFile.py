import numpy as np

def calculate_need(allocate, max_matrix, n, m):
    # Initialize the need matrix
    need = np.zeros((n, m), dtype=int)
    for i in range(n):
        for j in range(m):
            need[i][j] = max_matrix[i][j] - allocate[i][j]
    return need

def check(need, avail, i, m):
    # Check if the process's needs can be satisfied with available resources
    for j in range(m):
        if avail[j] < need[i][j]:
            return False
    return True

def is_safe(allocate, need, avail, n, m):
    # Initialize finish list and work vector
    finish = [False] * n
    work = np.copy(avail)
    
    while True:
        found = False
        for i in range(n):
            # Check if process i can be completed
            if not finish[i] and check(need, work, i, m):
                # If so, simulate resource allocation
                for j in range(m):
                    work[j] += allocate[i][j]
                finish[i] = True
                found = True
        
        if not found:
            # If no process was found in this iteration, check if all processes are finished
            return all(finish)

def main():
    n = int(input("Enter the number of processes: "))
    m = int(input("Enter the number of resource types: "))

    # Initialize matrices and vectors
    allocate = np.zeros((n, m), dtype=int)
    max_matrix = np.zeros((n, m), dtype=int)
    avail = np.zeros(m, dtype=int)

    # Input allocation matrix
    print("Enter the allocation matrix:")
    for i in range(n):
        allocate[i] = list(map(int, input().split()))

    # Input max matrix
    print("Enter the max matrix:")
    for i in range(n):
        max_matrix[i] = list(map(int, input().split()))

    # Input available resources
    print("Enter the available resources:")
    avail = list(map(int, input().split()))

    # Calculate need matrix
    need = calculate_need(allocate, max_matrix, n, m)

    # Check if the system is in a safe state
    if is_safe(allocate, need, avail, n, m):
        print("The system is in a safe state.")
    else:
        print("The system is not in a safe state.")

if __name__ == "__main__":
    main()
