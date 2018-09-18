#!/bin/sh
#该脚本为Linux下启动java程序的通用脚本。即可以作为开机自启动service脚本被调用， 
#也可以作为启动java程序的独立脚本来使用。 
#警告!!!：该脚本stop部分使用系统kill命令来强制终止指定的java程序进程。 
#在杀死进程前，未作任何条件检查。在某些情况下，如程序正在进行文件或数据库写操作， 
#可能会造成数据丢失或数据不完整。如果必须要考虑到这类情况，则需要改写此脚本， 
#增加在执行kill命令前的一系列检查。 
################################### 
#环境变量及程序执行参数
#需要根据实际环境以及Java程序名称来修改这些参数
################################### 
#JDK所在路径
#JAVA_HOME=/root/JDK/jdk1.6.0_24

#脚本所在的目录 
APP_HOME=$(cd "$(dirname "$0")"; pwd)

#class名，编译时替换
APP_CLASS={main_class}
#版本号，编译时替换
VERSION={version}
#JVM运行参数，编译时替换
JVM_RUN_ARGS="{jvm_run_args}"

#支持JVM运行参数，带group 不带group
GROUP=" -Dgroup"
SYSTEM_NAME="system.name="
SERVICE_NAME=${JVM_RUN_ARGS##*$SYSTEM_NAME}
ARGS_GROUP=${SERVICE_NAME##*$GROUP}
ARGS_SERVICE_NAME_SYSTEM="${SERVICE_NAME% *}"


CLASS_PATH=$APP_HOME/../conf
JAR_LIB=$APP_HOME/../lib:$JAVA_HOME/jre/lib/ext:$JAVA_HOME/lib/ext
JAR_LOG=$APP_HOME/../log

#java虚拟机启动参数 
JAVA_OPTS="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m"
JAVA_OPTS_JMX="-ms512m -mx512m -Xmn256m -Djava.awt.headless=true -XX:MaxPermSize=128m -Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false"
#调试参数
debug_port=$2
DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,address=$debug_port,server=y,suspend=n"


################################### 
#(函数)判断程序是否已启动 
#说明： 
#使用JDK自带的JPS命令及grep命令组合，准确查找pid 
#jps 加 l 参数，表示显示java的完整包路径 
#使用awk，分割出pid ($1部分)，及Java程序名称($2部分) 
################################### 
#初始化psid变量（全局） 
psid=0
checkpid() {
if [ "$ARGS_SERVICE_NAME_SYSTEM" == "$ARGS_GROUP" ];then
javaps=`ps ax|grep java|grep "$ARGS_SERVICE_NAME_SYSTEM" |grep -v "$GROUP" | grep -v grep`
else
javaps=`ps ax|grep java|grep "$ARGS_SERVICE_NAME_SYSTEM" |grep "$ARGS_GROUP" | grep -v grep`
fi
if [ -n "$javaps" ]; then
psid=`echo $javaps | awk '{print $1}'`
else
psid=0
fi
}

################################### 
#(函数)启动程序 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动 
#3. 如果程序没有被启动，则执行启动命令行 
#4. 启动命令执行后，再次调用checkpid函数 
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed] 
#注意：echo -n 表示打印字符后，不换行 
#注意: "nohup 某命令 &gt;/dev/null 2&gt;&amp;1 &amp;" 的用法 
################################### 

start() {
echo "****************************"
checkpid
if [ $psid -ne 0 ]; then
echo "================================" 
echo "程序: $SERVICE_NAME 已经启动! (进程号=$psid)"
echo "================================" 
else
echo "启动 $SERVICE_NAME ..."
nohup java $JAVA_OPTS -Djava.ext.dirs=$JAR_LIB -Dlogback.configurationFile=$CLASS_PATH/logback.xml $JVM_RUN_ARGS -classpath $CLASS_PATH $APP_CLASS >> nohup.out 2>&1 &
checkpid
if [ $psid -ne 0 ]; then
echo "程序: $SERVICE_NAME 启动成功! (进程号=$psid) [OK]"
else
echo "程序: $SERVICE_NAME 启动失败! [Failed]"
fi
fi
echo "****************************"
}

################################### 
#(函数)启动程序 (加监控,开启JMX端口)
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动 
#3. 如果程序没有被启动，则执行启动命令行 
#4. 启动命令执行后，再次调用checkpid函数 
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed] 
#注意：echo -n 表示打印字符后，不换行 
#注意: "nohup 某命令 &gt;/dev/null 2&gt;&amp;1 &amp;" 的用法 
################################### 

startjmx() {
echo "****************************"
checkpid
if [ $psid -ne 0 ]; then
echo "================================" 
echo "程序: $SERVICE_NAME 已经启动! (进程号=$psid)"
echo "================================" 
else
echo "启动 $SERVICE_NAME ..."
nohup java $JAVA_OPTS $JAVA_OPTS_JMX -Djava.ext.dirs=$JAR_LIB -Dlogback.configurationFile=$CLASS_PATH/logback.xml $JVM_RUN_ARGS -classpath $CLASS_PATH $APP_CLASS >> nohup.out 2>&1 &
checkpid
if [ $psid -ne 0 ]; then
echo "程序: $SERVICE_NAME 启动成功! (进程号=$psid) [OK]"
else
echo "程序: $SERVICE_NAME 启动失败! [Failed]"
fi
fi
echo "****************************"
}
################################### 
#(函数)debug启动程序 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示程序已启动 
#3. 如果程序没有被启动，则执行启动命令行 
#4. 启动命令执行后，再次调用checkpid函数 
#5. 如果步骤4的结果能够确认程序的pid,则打印[OK]，否则打印[Failed] 
#注意：echo -n 表示打印字符后，不换行 
#注意: "nohup 某命令 &gt;/dev/null 2&gt;&amp;1 &amp;" 的用法 
################################### 

debug() {
echo "****************************"
if [ ! -n "$debug_port" ]; then
echo "=======未输入调试端口=========" 
echo "[Failed]"
return
fi
checkpid
if [ $psid -ne 0 ]; then
echo "================================" 
echo "程序: $SERVICE_NAME 已经启动! (进程号=$psid)"
echo "================================" 
else
echo "调试启动 $SERVICE_NAME ..."
nohup java $JAVA_OPTS -Djava.ext.dirs=$JAR_LIB -Dlogback.configurationFile=$CLASS_PATH/logback.xml $JVM_RUN_ARGS -classpath $CLASS_PATH $DEBUG_OPTS $APP_CLASS >> nohup.out 2>&1 &
checkpid
if [ $psid -ne 0 ]; then
echo "调试程序: $SERVICE_NAME 启动成功! (进程号=$psid,调试端口=$debug_port) [OK]"
else
echo "程序: $SERVICE_NAME 启动失败! [Failed]"
fi
fi
echo "****************************"
}
################################### 
#(函数)停止程序 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则开始执行停止，否则，提示程序未运行 
#3. 使用kill -2 pid命令进行强制杀死进程 
#4. 执行kill命令行紧接其后，马上查看上一句命令的返回值: $? 
#5. 如果步骤4的结果$?等于0,则打印[OK]，否则打印[Failed] 
#6. 为了防止java程序被启动多次，这里增加反复检查进程，反复杀死的处理（递归调用stop）。 
#注意：echo -n 表示打印字符后，不换行 
#注意: 在shell编程中，"$?" 表示上一句命令或者一个函数的返回值 
################################### 

stop() {
echo "****************************"
checkpid
if [ $psid -ne 0 ]; then
echo "正在停止 $SERVICE_NAME ...(pid=$psid)"
kill -15 $psid
if [ $? -eq 0 ]; then
echo "停止 $SERVICE_NAME 成功!(pid=$psid) [OK]"
else
echo "正在停止 $SERVICE_NAME 失败!(pid=$psid) [Failed]"
fi

sleep 2
checkpid
if [ $psid -ne 0 ]; then
kill -9 $psid
fi
else
echo "warn: $SERVICE_NAME 没有运行..."
fi
echo "****************************"
}
################################### 
#(函数)检查程序运行状态 
# 
#说明： 
#1. 首先调用checkpid函数，刷新$psid全局变量 
#2. 如果程序已经启动（$psid不等于0），则提示正在运行并表示出pid 
#3. 否则，提示程序未运行 
################################### 

status() {
echo "****************************"
checkpid
if [ $psid -ne 0 ]; then
echo "$SERVICE_NAME 正在运行! (进程号=$psid)"
else
echo "$SERVICE_NAME 没有运行"
fi
echo "****************************"
}
################################### 
#(函数)打印系统环境参数 
################################### 
info() {
echo "System Information:"
echo "****************************"
echo `head -n 1 /etc/issue`
echo `uname -a`
echo
echo "JAVA_HOME=$JAVA_HOME" 
echo `java -version`
echo
echo "APP_HOME=$APP_HOME" 
echo "APP_JAR=$SERVICE_NAME"
echo "****************************"
}
################################### 
#(函数)打印log
################################### 
log() {
tailf -500 $JAR_LOG/console.log
}

################################### 
#(函数)查看版本
################################### 
version() {
echo "version:${VERSION}"
}
###################################
#读取脚本的第一个参数($1)，进行判断
#参数取值范围：{start|debug|stop|restart|status|info|log} 
#如参数不在指定范围之内，则打印帮助信息 
###################################

case "$1" in
'start')
start
;;
'debug')
debug
;;
'stop')
stop
;;
'restart')
stop
start
;;
'status')
status
;;
'info')
info
;;
'log')
log
;;
'version')
version
;;
'startjmx')
startjmx
;;
*)

echo "=======参数错误========="
echo "exp ：$0 start {start|debug|stop|restart|status|info|log}"
exit 1
esac
exit 0
