#!/usr/bin/expect -c
set timeout 10 
set ip [lindex $argv 0]
set mserver [lindex $argv 1] 
set muser [lindex $argv 2] 
set mpasswd [lindex $argv 3]
set suser [lindex $argv 4]
spawn ssh -l $muser $mserver 
expect { 
"(yes/no)" { send "yes\r"; exp_continue } 
"password:" { send "$mpasswd\r" } 
} 
expect "#"
send "cd /home/cherry/hadoop/hadoop-1.2.1/conf/\r"
expect "#"
send "echo $ip >>excludes \r"
expect "#"
send  "cd /home/cherry/hadoop//hadoop-1.2.1/bin/\r"
expect "#"
send "./hadoop dfsadmin -refreshNodes\r"
expect "#"
send "./hadoop dfsadmin -report\r"
expect "#"
send "exit\r"
expect eof
