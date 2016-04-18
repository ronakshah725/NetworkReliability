# check if the graph is connected
def connected(adj_mat):
    seen = [False for i in range(5)]
    dfs(adj_mat, seen, 0)
    for i in seen:
        if i is False:
            return False
    return True

# Performs Depth First Search

def dfs(adj_mat, seen, j):
    seen[j] = True
    for k in range(5):
        if adj_mat[j][k] == 1 and seen[k] == False:
            dfs(adj_mat, seen, k)
