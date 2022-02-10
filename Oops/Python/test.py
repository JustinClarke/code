import os
import re
with open("file.txt") as f:
    t = f.read().strip()

lists = []
m = re.findall(r"^[\d.E\s-]+$", t, re.MULTILINE) # 45 steps
for x in m:
    a = [float(x) for x in " ".join(x.strip().split("\n")).split()]
    lists.append(a)

os.listdir()
print(lists)