#!/usr/bin/python3
from scapy.all import *

print("sniffing packets")

def print_pkt(pkt):
  pkt.show()

pkt = sniff(filter='icmp', prn=print_pkt)
#pkt = sniff(filter='tcp and dst port 23 and src host 10.0.2.15', prn=print_pkt)
#pkt = sniff(filter='dst net 104.17.96.0/24', prn=print_pkt)
