
def connected(adj_mat):

    seen = [False for i in range(5)]
    s = 0
    a_one = False
    not_connected = False

    # for j in range(5):
    #     if adj_mat[0][j] == 1:
    #         s = j
    #         a_one = True
    #         break
    #
    # not_connected = False if a_one is True else True
    #
    # if not_connected:
    #     return False

    # seen[0] = True
    dfs(adj_mat, seen, 0)
    for i in seen:
        if i is False:
            return False
    return True


def dfs(adj_mat,seen,j):
    seen[j] = True
    for k in range(5):
        if adj_mat[j][k] == 1 and seen[k] == False:
            dfs(adj_mat,seen,k)





