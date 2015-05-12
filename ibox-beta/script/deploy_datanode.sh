#!/usr/bin/expect -c
set timeout 10 
set ip   [lindex $argv 0]
set mserver [lindex $argv 1] 
set muser [lindex $argv 2] 
set mpasswd [lindex $argv 3] 
set suser [lindex $argv 4]
set spassword [lindex $argv 5]
set sname   [lindex $argv  6]
spawn ssh -l $suser $ip
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"password:" { send "$spassword\r" } 
} 
expect "#"
send "ssh-keygen -t rsa -P '' -f /home/$suser/.ssh/id_rsa\r"
expect "#"
send "ssh-copy-id -i /home/cherry/.ssh/id_rsa.pub $suser@$ip\r"
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"*password:" { send "$spassword\r" } 
} 
expect "#"
send "ssh-copy-id -i /home/$suser/.ssh/id_rsa.pub $muser@$mserver\r"
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"*password:" { send "$mpasswd\r" } 
}
expect "#" 
send "logout\r"
spawn ssh -l $muser $mserver 
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"password:" { send "$mpasswd\r" } 
} 
expect "#"
send "echo $ip $sname>>/etc/hosts\r"
expect "#"
send "source /etc/hosts\r"
expect "#"
send "echo $ip >>/home/cherry/hadoop/hadoop-1.2.1/conf/slaves\r"
expect "#"
send "echo $ip >>/home/cherry/hadoop_backcup/hadoop-1.2.1/conf/slaves\r"
expect "#"
send "ssh-copy-id -i /home/cherry/.ssh/id_rsa.pub $suser@$ip\r"
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"*password:" { send "$spassword\r" } 
} 
expect "#"
send "scp  /etc/hosts $suser@$ip:/etc/hosts\r"
expect "#"
set timeout 60
send "scp -r /home/cherry/hadoop_backcup $suser@$ip:/home/$suser/\r"
expect "#"
set timeout 5
send "ssh $suser@$ip\r"
expect "#"
send "source /etc/hosts\r"
expect "#"
send "cd /home/$suser/\r"
expect "#"
send "echo  export JAVA_HOME=/home/$suser/hadoop_backcup/jdk1.8.0_40 >> /home/$suser/hadoop_backcup/hadoop-1.2.1/conf/hadoop-env.sh\r"
expect "#"
send "echo   export HADOOP_CLASSPATH=/home/$suser/hadoop_backcup/myclass >> /home/$suser/hadoop_backcup/hadoop-1.2.1/conf/hadoop-env.sh\r"
expect "#"
send "echo export JAVA_HOME=/home/$suser/hadoop_backcup/jdk1.8.0_40 >>/home/$suser/.bashrc\r"
expect "#"
send "echo export PATH=\\\$JAVA_HOME/bin:\\\$PATH >>/home/$suser/.bashrc\r"
expect "#"
send "echo export CLASSPATH=.:\\\$JAVA_HOME/lib/dt.jar:\\\$JAVA_HOME/lib/tools.jar >>/home/$suser/.bashrc\r"
expect "#"
send "sed 's/cherry/$suser/g' /home/$suser/hadoop_backcup/hadoop-1.2.1/conf/core-site.xml\r\r"
expect "#"
send "source /home/$suser/.bashrc\r"
expect "#"
send "cd /home/$suser/hadoop_backcup/hadoop-1.2.1/bin\r"
expect "#"
send "./hadoop-daemon.sh start datanode\r"
expect "#"
send "./hadoop-daemon.sh start tasktracker \r"
expect "#"
send "./start-balancer.sh \r"
expect "#"
send "./hadoop dfsadmin -report\r"
expect "#"
send "exit\r"
expect eof

