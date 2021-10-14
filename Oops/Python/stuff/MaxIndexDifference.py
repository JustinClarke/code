def findMaximumDiff(a, n):
	ind1 = 0
	for i in range(n-1, -1, -1):
		if (a[0] != a[i]):
			ind1 = i
			break

	ind2 = 0

	for i in range(n - 1):

		if (a[n - 1] != a[i]):
			ind2 = (n - 1 - i)
			break

	return max(ind1, ind2)



n = int(input("N="))
list =[]


for i in range(0, n):
    ele = int(input())
    list.append(ele)

print(findMaximumDiff(list, n))
