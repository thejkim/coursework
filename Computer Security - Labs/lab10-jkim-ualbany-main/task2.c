#include <linux/module.h> 
#include <linux/kernel.h>
#include <linux/netfilter.h> 
#include <linux/netfilter_ipv4.h>
#include <linux/ip.h>
#include <linux/tcp.h>

static struct nf_hook_ops telnetFilterHook;
/* This is the hook function itself */
unsigned int telnetFilter(void *priv, struct sk_buff *skb, const struct nf_hook_state *state)
{
  struct iphdr *iph;
  struct tcphdr *tcph;
  
  unsigned int s1,s2,s3,s4; 
  unsigned int d1,d2,d3,d4;

  iph = ip_hdr(skb);
  tcph = (void *) iph+iph->ihl*4;

  s1 = ((unsigned char *)&iph->saddr)[0];
  s2 = ((unsigned char *)&iph->saddr)[1];
  s3 = ((unsigned char *)&iph->saddr)[2];
  s4 = ((unsigned char *)&iph->saddr)[3];

  d1 = ((unsigned char *)&iph->daddr)[0];
  d2 = ((unsigned char *)&iph->daddr)[1];
  d3 = ((unsigned char *)&iph->daddr)[2];
  d4 = ((unsigned char *)&iph->daddr)[3];

  printk(KERN_INFO "Checking for TCP packet to %d.%d.%d.%d\n",d1,d2,d3,d4);
   
   // Prevent A(10.0.2.4) from doing telnet to B(10.0.2.5)
   if(iph->protocol == IPPROTO_TCP && tcph->dest == htons(23) && s1==10 && s1==0 && s1==2 && s1==4 && d1==10 && d2==0 && d3==2 && d4==5)
   {
      printk(KERN_INFO "Dropping telnet packet to %d.%d.%d.%d\n",
       ((unsigned char *)&iph->daddr) [0],
       ((unsigned char *)&iph->daddr) [1],
       ((unsigned char *)&iph->daddr) [2],
       ((unsigned char *)&iph->daddr) [3]
      );
      return NF_DROP;
   }// Prevent B from doing telnet to A
   else if(iph->protocol == IPPROTO_TCP && tcph->dest == htons(23) && s1==10 && s1==0 && s1==2 && s1==5 && d1==10 && d2==0 && d3==2 && d4==4)
   {
      printk(KERN_INFO "Dropping telnet packet to %d.%d.%d.%d\n",
       ((unsigned char *)&iph->daddr) [0],
       ((unsigned char *)&iph->daddr) [1],
       ((unsigned char *)&iph->daddr) [2],
       ((unsigned char *)&iph->daddr) [3]
      );
      return NF_DROP;
   }// Prevent A from TCP HTTP/HHTPS connection to an external website(facebook)
   else if(iph->protocol == IPPROTO_TCP && (tcph->dest == htons(80) || tcph->dest == htons(443)) && && s1==10 && s1==0 && s1==2 && s1==5 && d1==10 && d2==0 && d3==2 && d4==4)
   {
      printk(KERN_INFO "Dropping telnet packet to %d.%d.%d.%d\n",
       ((unsigned char *)&iph->daddr) [0],
       ((unsigned char *)&iph->daddr) [1],
       ((unsigned char *)&iph->daddr) [2],
       ((unsigned char *)&iph->daddr) [3]
      );
      return NF_DROP;
   }
   // Prevent A(10.0.2.4) from doing ssh to B(10.0.2.5)
   if(iph->protocol == IPPROTO_TCP && tcph->dest == htons(22) && s1==10 && s1==0 && s1==2 && s1==4 && d1==10 && d2==0 && d3==2 && d4==5)
   {
      printk(KERN_INFO "Dropping telnet packet to %d.%d.%d.%d\n",
       ((unsigned char *)&iph->daddr) [0],
       ((unsigned char *)&iph->daddr) [1],
       ((unsigned char *)&iph->daddr) [2],
       ((unsigned char *)&iph->daddr) [3]
      );
      return NF_DROP;
   }// Prevent B from doing ssh to A
   else if(iph->protocol == IPPROTO_TCP && tcph->dest == htons(22) && s1==10 && s1==0 && s1==2 && s1==5 && d1==10 && d2==0 && d3==2 && d4==4)
   {
      printk(KERN_INFO "Dropping telnet packet to %d.%d.%d.%d\n",
       ((unsigned char *)&iph->daddr) [0],
       ((unsigned char *)&iph->daddr) [1],
       ((unsigned char *)&iph->daddr) [2],
       ((unsigned char *)&iph->daddr) [3]
      );
      return NF_DROP;
   }
   else
   {
      return NF_ACCEPT;
   }
   
}

int setUpFilter(void)
{ 
  printk(KERN_INFO "Registering filters.\n");
  telnetFilterHook.hook = telnetFilter;
  telnetFilterHook.hooknum = NF_INET_POST_ROUTING; 
  telnetFilterHook.pf = PF_INET;
  telnetFilterHook.priority = NF_IP_PRI_FIRST;
  // Register the hook.
  nf_register_hook(&telnetFilterHook);

  return 0; 
}

/* Cleanup routine */ 
void removeFilter(void) 
{
  printk(KERN_INFO "Telnet filter removed.\n");
  nf_unregister_hook(&telnetFilterHook);
}

module_init(setUpFilter);
module_exit(removeFilter);

MODULE_LICENSE("GPL");
