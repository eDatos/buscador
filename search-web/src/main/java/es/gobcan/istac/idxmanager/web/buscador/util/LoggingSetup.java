package es.gobcan.istac.idxmanager.web.buscador.util;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LoggingSetup {

    private final Logger logger = LoggerFactory.getLogger(LoggingSetup.class);

    private String logConfigurationFile = null;

    public void setLogConfigurationFile(String logConfigurationFile) {
        this.logConfigurationFile = logConfigurationFile;
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception {
        try {
            if (logConfigurationFile == null) {
                logger.error("Logging configuration file has not been set \"logConfigurationFile\"");
                throw new RuntimeException("Logging configuration file has not been set \"logConfigurationFile\"");
            }
            File file = new File(logConfigurationFile);
            if (!file.exists()) {
                logger.error("Logging configuration file doesnt exist: " + logConfigurationFile);
                throw new RuntimeException("Logging configuration file doesnt exist: " + logConfigurationFile);
            }

            LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
            try {
                JoranConfigurator configurator = new JoranConfigurator();
                configurator.setContext(loggerContext);
                loggerContext.reset();

                configurator.doConfigure(logConfigurationFile);
            } catch (JoranException e) {
                logger.error("Error configuring logging system");
                throw new RuntimeException("Error configuring logging system", e);
            }
        } catch (Exception e) {
            logger.error("Error configuring logging system");
            throw new Exception(e);
        }
    }

}
