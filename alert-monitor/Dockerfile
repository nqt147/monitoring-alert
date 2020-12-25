FROM openjdk:8-jre-alpine3.9
ENV APPNAME=stg.RepaymentPartnerService
ENV JAVA_HOME=/smartpay/java
ENV JMXTRANS_HOME=/smartpay/jmxtrans
ENV HOME=/smartpay/apps/stg.repayment-partner-service
ENV PORT=5935
ENV HOST=localhost
ENV MIN_THREADS=32
ENV MAX_THREADS=256
ENV JMXHOST=localhost
ENV JMXPORT=15935
ENV APPENV=stg
ENV XMS=1G
ENV CONF=conf
ENV JARFILE=alert-monitor-0.0.1-SNAPSHOT.jar
ENV pid_file=tmp/service.pid
ENV log_file=tmp/service.log

# Arguments to pass to the JVM
ENV JVM_OPTS=" \
-Dapplication.name=$APPNAME \
-Dforeground=true \
-Xmx$XMS \
-XX:SurvivorRatio=8 \
-XX:+UseParNewGC \
-XX:+UseConcMarkSweepGC \
-XX:+CMSParallelRemarkEnabled \
-XX:MaxTenuringThreshold=1 \
-XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly \
-XX:+HeapDumpOnOutOfMemoryError \
-Djava.rmi.server.hostname=$JMXHOST \
-Dcom.sun.management.jmxremote.port=$JMXPORT \
-Dcom.sun.management.jmxremote.ssl=false \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dconfig=$CONF \
-Dapppath=$HOME \
-Dappenv=$APPENV \
-Dspring.profiles.active=$APPENV \
-Dserver.port=$PORT \
-Dzminthread=$MIN_THREADS \
-Dzmaxthread=$MAX_THREADS \
-Dlog.file=log/$APPNAME "
#-javaagent:$JMXTRANS_HOME/jmxtrans-agent-1.2.6.jar=$JMXTRANS_HOME/jmxtrans-agent.xml "
VOLUME /tmp
WORKDIR /smartpay/$APPNAME/
ADD $JARFILE /smartpay/$APPNAME/app.jar
COPY runserver.sh /home/smartpay/$APPNAME/
ENTRYPOINT exec java $JVM_OPTS -jar  /smartpay/$APPNAME/app.jar
