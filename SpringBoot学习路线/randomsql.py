import random, string

names = ["alex","bella","charles","diana","edward","fiona","george","henry","isabel","jack",
         "kate","leo","maria","nathan","olivia","paul","queen","ryan","sophia","tom",
         "uma","victor","wendy","xavier","yara","zoe"]

def random_key():
    chars = string.ascii_letters + string.digits + "!@#$%^&*"
    return ''.join(random.choice(chars) for _ in range(12))

for i in range(1, 101):
    cust_id = 100372 + i - 1
    phone = 13800138000 + i - 1
    name = names[(i-1) % len(names)]
    key = random_key()
    print(f"INSERT INTO `user_acct` VALUES ({i}, {cust_id}, '{name}', '{key}', '{name}@example.com', '{phone}', 1, '2025-12-15 15:14:11', '2025-12-15 15:14:11');")
