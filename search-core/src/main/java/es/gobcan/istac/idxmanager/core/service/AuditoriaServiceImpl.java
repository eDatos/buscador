package es.gobcan.istac.idxmanager.core.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arte.acom.configuration.ConfigurationService;

import es.gobcan.istac.idxmanager.core.constants.Constantes;
import es.gobcan.istac.idxmanager.core.util.CSVFormatFactory;
import es.gobcan.istac.idxmanager.core.util.JSR310DateTimeFormatters;
import es.gobcan.istac.idxmanager.domain.mvc.Busqueda;

@Service
public class AuditoriaServiceImpl implements AuditoriaService {

    protected static Log         LOGGER    = LogFactory.getLog(AuditoriaService.class);

    protected static String      AUDITORIA = "auditoria";

    @Autowired
    private ConfigurationService configurationService;

    @Override
    public void auditar(Busqueda busquedausuario) {
        LOGGER.debug("Guardando log con búsqueda de usuario");

        ZonedDateTime now = ZonedDateTime.now();

        try {
            CSVPrinter csvFilePrinter = new CSVPrinter(new FileWriter(getAuditFile(now), true), CSVFormatFactory.getCSVFormat());

            ArrayList<Object> record = new ArrayList<Object>();

            record.add(now);

            record.add(busquedausuario.getUserQuery());
            record.add(busquedausuario.getSearchType());

            record.add(busquedausuario.getFiltroTexto());
            record.add(busquedausuario.getFiltroTextoQuery());
            record.add(busquedausuario.getFiltroSeccion());
            record.add(busquedausuario.getFiltroSeccionArea());
            record.add(busquedausuario.getFiltroSeccionOperacion());

            record.add(busquedausuario.getStartResult());
            record.add(busquedausuario.getPestania());
            record.add(busquedausuario.getSort());

            record.add(busquedausuario.getSvyCodFF());
            record.add(busquedausuario.getSubCodFF());
            record.add(busquedausuario.getCvgTCodFF());
            record.add(busquedausuario.getCvgSCodFF());
            record.add(busquedausuario.getFormato());

            record.add(busquedausuario.getFf_select());

            csvFilePrinter.printRecord(record);
            csvFilePrinter.close();
        } catch (Exception e) {
            LOGGER.debug("Se ha producido una excepción al logear el mensaje de búsqueda", e);
        }
    }

    private File getAuditFile(ZonedDateTime now) throws IOException {
        String dataUrl = configurationService.getProperties().getProperty(Constantes.BUSCADOR_DATA_URL);

        String fileName = JSR310DateTimeFormatters.formatInYYYYMM(now);

        StringBuilder fullPath = new StringBuilder(dataUrl);
        if (!dataUrl.endsWith("/")) {
            fullPath.append("/");
        }
        fullPath.append(AUDITORIA).append("/").append(fileName).append(".csv");

        File auditFile = new File(fullPath.toString());
        if (!auditFile.exists()) {
            // Crear cabecera
            CSVPrinter csvFilePrinter = new CSVPrinter(new FileWriter(auditFile, true), CSVFormatFactory.getCSVFormat());
            csvFilePrinter.printRecord(getCsvHeader());
            csvFilePrinter.close();
        }

        return auditFile;
    }
    private ArrayList<String> getCsvHeader() {
        ArrayList<String> header = new ArrayList<String>();

        header.add("time");

        header.add("userQuery");
        header.add("searchType");

        header.add("filtroTexto");
        header.add("filtroTextoQuery");
        header.add("filtroSeccion");
        header.add("filtroSeccionArea");
        header.add("filtroSeccionOperacion");

        header.add("startResult");
        header.add("pestania");
        header.add("sort");

        header.add("svyCodFF");
        header.add("subCodFF");
        header.add("cvgTCodFF");
        header.add("cvgSCodFF");
        header.add("formato");

        header.add("ff_select");

        return header;
    }
}
