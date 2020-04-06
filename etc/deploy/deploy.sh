#!/bin/bash

HOME_PATH=search
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/edatos-external/tomcats/edatos-external01/webapps

LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml
RESTART=1

if [ "$1" == "--no-restart" ]; then
    RESTART=0
fi

scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" -r etc/deploy deploy@192.168.10.16:$TRANSFER_PATH
scp -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" search-web/target/buscador*.war deploy@192.168.10.16:$TRANSFER_PATH/search.war
ssh -o ProxyCommand="ssh -W %h:%p deploy@estadisticas.arte-consultores.com" deploy@192.168.10.16 <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh

    ###
    # BUSCADOR
    ###

    if [ $RESTART -eq 1 ]; then
        sudo service edatos-external01 stop
        checkPROC "edatos-external"
    fi

    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/search
    sudo mv $TRANSFER_PATH/search.war $DEPLOY_TARGET_PATH/search.war
    sudo unzip $DEPLOY_TARGET_PATH/search.war -d $DEPLOY_TARGET_PATH/search
    sudo rm -rf $DEPLOY_TARGET_PATH/search.war

    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/search/WEB-INF/classes/buscador/environment.xml

    if [ $RESTART -eq 1 ]; then
        sudo chown -R edatos-external.edatos-external /servers/edatos-external        
        sudo service edatos-external01 start
    fi

EOF
