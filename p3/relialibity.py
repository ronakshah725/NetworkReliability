from connected import connected
import random


# Generate 10 bit binary numbers from 0 to 1024

def make_combination():
    edge_mat = [get_combination(i) for i in range(1024)]
    return edge_mat

# Convert given number to a 10 bit binary number

def get_combination(n):
    combination = [i for i in range(10)]
    ne = len(combination)
    for i in range(ne - 1, -1, -1):
        combination[i] = n % 2
        n /= 2
    return combination




# Calculate the connectivity for the given combination matrix edge_matrix and
# link reliability p
def calc_reliability(edge_mat, p):
    if p == 0.0:
        return 0.0

    comb_reliability = 0.0
    for i in edge_mat:

        adj_mat = [[0 for x in range(5)] for x in range(5)] #generate a 5X5 matrix to transform edge-mat to adj-mat
        adj_i, adj_j = 0, 1
        for j in range(10):

            if adj_j == 5 and adj_i == 0:
                adj_i, adj_j = 1, 2
            if adj_j == 5 and adj_i == 1:
                adj_i, adj_j = 2, 3
            if adj_j == 5 and adj_i == 2:
                adj_i, adj_j = 3, 4

            if adj_j <= 4:
                # print m,',',n
                adj_mat[adj_i][adj_j] = i[j]
                adj_mat[adj_j][adj_i] = i[j]
                adj_j += 1

        if connected(adj_mat):
            comb_reliability += get_reliability(i, p) # sum up the reliability to find the total network reliability
            # count += 1
            # print True , ' | ', i, ' | ', adj_mat, ' | ', count

    return comb_reliability
    # print  count


def get_reliability(edge_mat, p):
    rel = 1.0
    for col in edge_mat:
        if col == 1:
            rel = rel * p # p is the probability that the link is up
        else:
            rel = rel * (1.0 - p) # 1-p is the probability that the link is down
    return rel


def drange(start, stop, step):
    r = start
    while r < stop:
        yield r
        r += step


def k_reliability(edge_mat, k):

    while k>0:
        i = random.randint(0,1023)
        edge_mat[i] = flip(edge_mat[i])
        k -= 1

    return calc_reliability(edge_mat, 0.9)

# toggle state of the link
def flip(edge_mat):

    for i in range(10):
        if edge_mat[i] == 1:
            edge_mat[i] = 0
        elif edge_mat[i] == 0:
            edge_mat[i] = 1
    return edge_mat


def run_exp1(edge_mat):

    print 'p\t\tReliability'
    listp = []
    listr = []
    for p in drange(0.0, 1.0, 0.05):
        listp.append(p)
        r = calc_reliability(edge_mat, p)
        listr.append(r)
        print p, '\t', r
    # print listp
    # print listr
    print


def run_exp2(edge_mat):
    print 'p=0.9'
    print 'k\treliability'

    for k in range(26):
        krel = k_reliability(edge_mat, k)
        print k, '\t', krel


def main():
    edge_mat = make_combination()
    run_exp1(edge_mat)
    run_exp2(edge_mat)


if __name__ == '__main__':
    main()
