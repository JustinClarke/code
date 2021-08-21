internDID = []
internPassword = []

for a in range(0,49):
  internDID[a] = a*5000

def Day(day):
  return (Day(day-1) + 5000 + day)


print(Day(1))
