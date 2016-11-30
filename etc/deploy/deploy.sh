#!/bin/sh

HOME_PATH=search
TRANSFER_PATH=$HOME_PATH/tmp
DEPLOY_TARGET_PATH=/servers/metamac/tomcats/metamac01/webapps

LOGBACK_RELATIVE_PATH_FILE=WEB-INF/classes/logback.xml

scp -r etc/deploy deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH
scp search-web/target/buscador*.war deploy@estadisticas.arte-consultores.com:$TRANSFER_PATH/search.war
ssh deploy@estadisticas.arte-consultores.com <<EOF

    chmod a+x $TRANSFER_PATH/deploy/*.sh;
    . $TRANSFER_PATH/deploy/utilities.sh
    
    sudo service metamac01 stop
    checkPROC "metamac"
    
    ###
    # BUSCADOR
    ###
    
    # Update Process
    sudo rm -rf $DEPLOY_TARGET_PATH/search
    sudo mv $TRANSFER_PATH/search.war $DEPLOY_TARGET_PATH/search.war
    sudo unzip $DEPLOY_TARGET_PATH/search.war -d $DEPLOY_TARGET_PATH/search
    sudo rm -rf $DEPLOY_TARGET_PATH/search.war
    
    # Restore Configuration
    sudo cp $HOME_PATH/environment.xml $DEPLOY_TARGET_PATH/search/WEB-INF/classes/buscador/environment.xml



    sudo chown -R metamac.metamac /servers/metamac
    sudo service metamac01 start
#    checkURL "http://estadisticas.arte-consultores.com/search/" "metamac01"

EOF
