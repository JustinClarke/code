def second_largest(numbers):
    count = 0
    m1 = m2 = 0
    for x in numbers:
        count += 1
        if x > m2:
            if x >= m1:
                m1, m2 = x, m1            
            else:
                m2 = x
    return m2 if count >= 2 else None

list = []

for i in range(0, 5):
    ele = int(input())
    list.append(ele)

print("ans ",second_largest(list))