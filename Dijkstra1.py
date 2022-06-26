# SystemA made by 1ces2205 JoKikou
def Dijkstra(network, s, d):

    path = []
    n = len(network)
    fmax = float('inf')
    w = [[0 for _ in range(n)] for j in range(n)]

    book = [0 for _ in range(n)]
    dis = [fmax for i in range(n)]  # 最短距離
    book[s - 1] = 1
    midpath = [-1 for i in range(n)]
    for i in range(n):
        for j in range(n):
            if network[i][j] != 0:
                w[i][j] = network[i][j]  # 0→max
            else:
                w[i][j] = fmax
            if i == s - 1 and network[i][j] != 0:  # # network[i][j]
                dis[j] = network[i][j]
    for i in range(n - 1):
        min = fmax
        for j in range(n):
            if book[j] == 0 and dis[j] < min:
                min = dis[j]
                u = j
        book[u] = 1
        for v in range(n):
            if dis[v] > dis[u] + w[u][v]:
                dis[v] = dis[u] + w[u][v]
                midpath[v] = u + 1
    j = d - 1
    path.append(d)
    while (midpath[j] != -1):
        path.append(midpath[j])
        j = midpath[j] - 1
    path.append(s)
    path.reverse()
    print("dist:", dis[d - 1], end='   ')

    for i in range(len(path)):
        if i != 0:
            print(">", end="")

        print(f'{path[i]}', end="")
    print()
    # print("path:", path)
    # print(midpath)

    # return path


def ALL_Point_Dijkstra(network):
    print("Start Dijstra Path……")
    num = len(network[0])
    for i in range(1, num + 1):
        for j in range(i + 1, num + 1):
            print("点", i, "から", j, "点", end='  ')
            Dijkstra(network, i, j)


if __name__ == '__main__':
    network = [[0, 1, 0, 2, 0, 0, 2, 4, 1, 4],
               [1, 0, 2, 4, 3, 0, 0, 1, 4, 2],
               [1, 0, 2, 4, 3, 0, 1, 4, 2, 3],
               [1, 0, 2, 4, 3, 0, 1, 0, 2, 0],
               [1, 0, 2, 4, 3, 0, 2, 4, 1, 3],
               [1, 0, 2, 4, 3, 0, 1, 4, 0, 2],
               [0, 2, 0, 0, 1, 4, 0, 0, 1, 1],
               [2, 4, 0, 0, 6, 0, 1, 0, 3, 2],
               [0, 3, 1, 6, 0, 2, 1, 2, 4, 0],
               [0, 0, 4, 0, 2, 0, 1, 2, 0, 3]]
    # Dijkstra(network, 1,1)
    ALL_Point_Dijkstra(network)
