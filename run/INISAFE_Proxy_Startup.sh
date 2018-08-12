###########################
###########################


###########################
JAVA_HOME=/usr/lib/jvm/java-6-openjdk-amd64
JAVA_RUN=${JAVA_HOME}/bin/java
###########################


APP_HOME=''
CLASSPATH='';
###########################
APP_PACKAGE='INISAFE-Proxy-1.0.0-alpha.jar'
MAIN_CLASS='com.initech.crossweb.proxy.ProxyDaemon';
PID_FILE='daemon.pid'
VM_OPTION='-Xms64m -Xmx512m'
LOG_MODE='INFO'
###########################
APP_OUT=''
APP_ERR=''
APP_RUN_MODE='DEBUG'

######################################################
function find_apphome(){ 
cd ..;
APP_HOME=${PWD}
cd bin;
}

function find_classpath(){ 
    for i in `ls ${APP_HOME}/libs/*.jar`
    do
        CLASSPATH=${CLASSPATH}:${i}
    done
}
######################################################

find_apphome;
find_classpath;


LOG_PATH="${APP_HOME}/log"
CONF_PATH="${APP_HOME}/conf"
CLASSPATH="${APP_HOME}/dist/${APP_PACKAGE}${CLASSPATH}"

######################################################

echo "HOME=${HOME}"
echo "JAVA_HOME=${JAVA_HOME}"
echo "APP_HOME=${APP_HOME}"
echo "APP_PACKAGE=${APP_PACKAGE}"
echo "MAIN_CLASS=${MAIN_CLASS}"
echo "CLASSPATH=${CLASSPATH}"
echo "LOG_PATH=${LOG_PATH}"
echo "CONF_PATH=${CONF_PATH}"

######################################################

###########################
###########################
echo ${PWD}

nohup ${JAVA_RUN} \
    -classpath ${CLASSPATH} \
    ${VM_OPTION} \
    -Dlog.path=${LOG_PATH} \
    -Dlog.mode=${LOG_MODE} \
    -Dconf.path=${CONF_PATH} \
    ${MAIN_CLASS} &

echo "$!" > ${PID_FILE}
