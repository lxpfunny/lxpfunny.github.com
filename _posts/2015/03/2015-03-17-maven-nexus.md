---
layout: post
title:使用nexus配置maven代理服务器学习笔记
---
 <p>
 	要用nexus配置maven私服，首先需要在服务器中安装nexus。nexus下载地址：http://www.sonatype.org/nexus/
 </p>
 <p>
 	下载之后解压到随意目录，在安装目录下D:\nexus\nexus-2.11.2-04\bin\jsw\找到对应系统目录，
 	点击console-nexus.bat启动nexus服务器。在留言器中输入http://localhost:8081/nexus进入nexus，用admin/admin123登陆 。可以看到：
 </p>
 <img src='/images/nexus-01.png' alt='nexus-01'/>
 <p>
 	点击respositories在右边窗口中看到所有仓库。仓库类型分为：group、hosted、proxy,group是仓库组，可以包括多个hosted，proxy仓库，hosted是本地仓库使用mvn depoy上传的仓库，proxy是官方代理仓库。
 </p>
 <p>

 	在maven项目中的parent pom.xml中配置
 </p>
 <p>
 	<repositories></br>
  	<repository></br>
  		<id>nexus</id></br>
  		<name>nexus respo</name></br>
  		<url>http://localhost:8081/nexus/content/groups/public/</url></br>
  		<releases></br>
  			<enabled>true</enabled></br>
  		</releases></br>
  		<snapshots></br>
  			<!-- 默认是关闭的 --></br>
  			<enabled>true</enabled></br>
  		</snapshots></br>
  	</repository></br>
  </repositories></br>
 </p>
 <p>指定nexus私服，项目在添加新的依赖后会在配置的私服中下载新的jar包，如果没有私服会自动去中央仓库中下载</p>
 <p>
 	在项目中，会因为模块比较多原因，这样就要在每个模块中添加上面的配置，一般这种情况都把配置写在maven的/conf/settings.xml中
 </p>
 <p>
 	在settings.xml中添加profile标签
 </p>
 <p>
 	 <profile></br>
      <id>nexusprofile</id></br>
     

      <repositories></br>
        <repository></br>
          <id>nexus</id></br>
          <name>nexus respo</name></br>
          <url>http://localhost:8081/nexus/content/groups/public/</url></br>
          <releases></br>
            <enabled>true</enabled></br>
          </releases></br>
          <snapshots></br>
            <!-- 默认是关闭的 --></br>
            <enabled>true</enabled></br>
          </snapshots></br>
      </repository></br>
    </repositories></br>
    </profile></br>
 </p>
 <p>配置了profile以后还需要把这个profile打开，才会使用到</p>
 <p>
 	使用 </br>
 	<activeProfiles></br>
    <!--激活生效--></br>
    <activeProfile>centralprofile</activeProfile></br>
  </activeProfiles></br>
  将上面得profile打开
 </p>
 <p>
 	这种配置当项目添加依赖后，在私服中不到，就会去中央仓库中下载，如果不需要去中央仓库中下载，需要配置镜像。
 </p>
 <p>
	<mirror></br>
      <id>nexusMirror</id></br>
      <mirrorOf>*</mirrorOf><!--repositoryId--></br>
      <name>Human Readable Name for this Mirror.</name></br>
      <url>http://localhost:8081/nexus/content/groups/public/</url></br>
    </mirror></br>
 </p>
 <p>这个配置的意思是如果项目中添加新的依赖,maven所有的仓库都会先在私服中下载，如果没有，不会再去中央仓库中下载</p>
 <p>

 </p>