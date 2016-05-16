#!/bin/bash
auto_login_ssh () {
    expect -c "set timeout -1;
                spawn -noecho ssh -o StrictHostKeyChecking=no $2 ${@:3};
                expect *assword:*;
                send -- $1\r;
                send -- ps -ef | grep -i  java | grep -v grep|awk java |xargs -n1 kill -9\r;
                send -- /usr/local/apache-tomcat-6.0.44/bin/startup.sh\r;
                interact;";
}


scp_war(){
	expect -c "set timeout -1;
                spawn scp /Users/lhxia/Documents/jee/workspace1/bu/target/baihuo.war root@51hotmm.com:/usr/local/apache-tomcat-6.0.44/webapps/;
                expect *assword:*;
                send -- $1\r;
                interact;";
    
    expect -c "set timeout -1;
                spawn scp /Users/lhxia/Documents/jee/workspace1/bu/target/textile.war root@51hotmm.com:/usr/local/apache-tomcat-6.0.44/webapps/;
                expect *assword:*;
                send -- $1\r;
                interact;"
}

#maven_build
scp_war Xlh.123
#auto_login_ssh Xlh.123 root@51hotmm.com