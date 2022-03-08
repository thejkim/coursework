#!usr/bin/python
from scapy.all import *

a = IP()
a. dst= '1.2.3.4'
a.ttl = 10
b = ICMP()
p = a/b
send(p)
