def calculate_need(allocate, max_matrix, n, m):
    need = [[0] * m for _ in range(n)]
    for i in range(n):
        for j in range(m):
            need[i][j] = max_matrix[i][j] - allocate[i][j]
    return need

def check(need, avail, i, m):
    for j in range(m):
        if avail[j] < need[i][j]:
            return False
    return True

def is_safe(allocate, need, avail, n, m):
    finish = [False] * n
    work = avail[:]
    
    while True:
        found = False
        for i in range(n):
            if not finish[i] and check(need, work, i, m):
                for j in range(m):
                    work[j] += allocate[i][j]
                finish[i] = True
                found = True
        
        if not found:
            return all(finish)

def main():
    n = int(input("Enter the number of processes: "))
    m = int(input("Enter the number of resource types: "))

    allocate = []
    max_matrix = []
    avail = []

    print("Enter the allocation matrix:")
    for i in range(n):
        allocate.append(list(map(int, input().split())))

    print("Enter the max matrix:")
    for i in range(n):
        max_matrix.append(list(map(int, input().split())))

    print("Enter the available resources:")
    avail = list(map(int, input().split()))

    need = calculate_need(allocate, max_matrix, n, m)

    if is_safe(allocate, need, avail, n, m):
        print("The system is in a safe state.")
    else:
        print("The system is not in a safe state.")

if __name__ == "__main__":
    main()
