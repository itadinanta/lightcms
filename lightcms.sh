#!/bin/sh

PID_FILE=lightcms.pid
CLASSPATH="bin"
JAVA_OPTS=-server

for i in lib/*; do
    CLASSPATH=$CLASSPATH:$i
done;

for i in modules/* ; do
    CLASSPATH=$CLASSPATH:$i:$i/bin
done;

start () {
    echo -e "\n\nStarting lightCms\n" 
    echo -e "CLASSPATH = $CLASSPATH\n"
    java $JAVA_OPTS -cp "$CLASSPATH" LightCms &
    if [ -t "$?" ] ; then
	echo $! > $PID_FILE
    else
	rm $PID_FILE
	echo "Unable to start lightcms";
    fi
}


stop() {
    if [ -e $PID_FILE ] ; then
	kill `cat $PID_FILE` && rm $PID_FILE && echo -e "\n\nShutdown completed.\n"
    else
	echo "lightcms not running"
    fi;
}


case $1 in

    start) start
    ;;

    stop) stop
    ;;

    restart) stop && start
    ;;

    *) echo "Use lightcms (start | stop)"
    ;;

esac;