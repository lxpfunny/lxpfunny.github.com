---
layout:post
title: 在本地安装jekyll测试环境
---
 <p>第一次安装jekyll的同学肯定会痛苦的说，这鸟玩样真费劲。这里我只能说一个简单的步骤。不敢保证操作过程中不会出现问题。</p>
 <p>1.首先下载rubyinstaller ,下载DevKit。</p>
 <p>2.安装rubyinstaller,解压devkit。rubyinstaller默认安装目录在c盘下。</p>
 <p>3.进入devkit解压目录下，双击msys.bat，运行ruby dk.rb init,这时目录下会生成一个config.yml文件，打开按照提示配置ruby的安装目录</p>
<p>需要注意的是- 后面要有一个空格</p>
<p>4.运行gem sources 查看使用的是哪个资源库，把资源库换成http://ruby.taobao.org/，gem sources remove 《url》 删除资源库   gem sources add 《URL》 添加资源库<p>
<p>5.一次运行 gem install rubygems-update  gem update --system gem install jekyll，如果下载过程中没出现什么问题，结束后jekyll就安装成功。就可以使用jekyll在本地测试了。
</p>