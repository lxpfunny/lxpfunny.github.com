---
layout: post
title: 使用ssh密匙连接github
---
<p> 
git使用https协议，每次pull, push都要输入密码，相当的烦。
使用git协议，然后使用ssh密钥。这样可以省去每次都输密码。
</p>
<p>
大概需要三个步骤：
一、本地生成密钥对；
二、设置github上的公钥；
三、修改git的remote url为git协议。
</p>
<p>
一、生成密钥对。
=============
大多数 Git 服务器都会选择使用 SSH 公钥来进行授权。系统中的每个用户都必须提供一个公钥用于授权，没有的话就要生成一个。生成公钥的过程在所有操作系统上都差不多。首先先确认一下是否已经有一个公钥了。SSH 公钥默认储存在账户的主目录下的 ~/.ssh 目录。进去看看：
</p>


<p>


$ cd ~/.ssh 
$ ls
authorized_keys2  id_dsa       known_hosts config            id_dsa.pub
</p>
<p>
关键是看有没有用 something 和 something.pub 来命名的一对文件，这个 something 通常就是 id_dsa 或 id_rsa。有 .pub后缀的文件就是公钥，另一个文件则是密钥。假如没有这些文件，或者干脆连 .ssh 目录都没有，可以用 ssh-keygen 来创建。该程序在 Linux/Mac 系统上由 SSH 包提供，而在 Windows 上则包含在 MSysGit 包里：
</p>
<p>


$ ssh-keygen -t rsa -C "your_email@youremail.com"
# Creates a new ssh key using the provided email # Generating public/private rsa key pair. 
# Enter file in which to save the key (/home/you/.ssh/id_rsa):
</p>
<p>
完了之后，大概是这样。
</p>
<p>


Your identification has been saved in /home/you/.ssh/id_rsa. 
# Your public key has been saved in /home/you/.ssh/id_rsa.pub. 
# The key fingerprint is: # 01:0f:f4:3b:ca:85:d6:17:a1:7d:f0:68:9d:f0:a2:db your_email@youremail.com
</p>
<p>
这样。你本地生成密钥对的工作就做好了。
</p>

<p>
二、添加公钥到你的github帐户
</p>
========================
1、查看你生成的公钥：大概如下：

$ cat ~/.ssh/id_rsa.pub  
ssh-rsa AAAAB3NzaC1yc2EAAAABIwAAAQEAklOUpkDHrfHY17SbrmTIpNLTGK9Tjom/BWDSU GPl+nafzlHDTYW7hdI4yZ5ew18JH4JW9jbhUFrviQzM7xlE
LEVf4h9lFX5QVkbPppSwg0cda3 Pbv7kOdJ/MTyBlWXFCR+HAo3FXRitBqxiX1nKhXpHAZsMciLq8V6RjsNAQwdsdMFvSlVK/7XA t3FaoJoAsncM1Q9x5+3V
0Ww68/eIFmb1zuUFljQJKprrX88XypNDvjYNby6vw/Pb0rwert/En mZ+AW4OZPnTPI89ZPmVMLuayrD2cE86Z/il8b+gw3r3+1nKatmIkjn2so1d01QraTlMqVSsbx NrRFi9wrf+M7Q== schacon@agadorlaptop.local
<p>
2、登陆你的github帐户。然后 Account Settings -> 左栏点击 SSH Keys -> 点击 Add SSH key
3、然后你复制上面的公钥内容，粘贴进“Key”文本域内。 title域，你随便填一个都行。
4、完了，点击 Add key。
</p>
<p>
这样，就OK了。然后，验证下这个key是不是正常工作。
$ ssh -T git@github.com
# Attempts to ssh to github
</p>
<p>
如果，看到：
Hi username! You've successfully authenticated, but GitHub does not # provide shell access.


就表示你的设置已经成功了。
</p>

<p>
三、修改你本地的ssh remote url. 不用https协议，改用git 协议
================================================
可以用git remote -v 查看你当前的remote url
$ git remote -v



origin https://github.com/someaccount/someproject.git (fetch)
origin https://github.com/someaccount/someproject.git (push)
</p>



可以看到是使用https协议进行访问的。

你可以使用浏览器登陆你的github，在上面可以看到你的ssh协议相应的url。类似如下：
git@github.com:someaccount/someproject.git




这时，你可以使用 git remote set-url 来调整你的url。
git remote set-url origin git@github.com:someaccount/someproject.git




完了之后，你便可以再用 git remote -v 查看一下。

OK。




至此，OK。

你可以用git fetch, git pull , git push， 现在进行远程操作，应该就不需要输入密码那么烦了。



 