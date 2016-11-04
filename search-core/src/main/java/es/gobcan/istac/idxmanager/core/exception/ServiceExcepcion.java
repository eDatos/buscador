package es.gobcan.istac.idxmanager.core.exception;

/**
 * Excepcion para ser usada por los metodos de la librer�a de Servicio Cada excepci�n tiene un tipo <code>ExcepcionTipo</code> y un mensaje.
 *
 * @author arte
 */

public class ServiceExcepcion extends Exception {

    /**
     * 
     */
    private static final long    serialVersionUID = -2548661408533934349L;

    private ServiceExcepcionTipo reasonType;
    private String               messageToUser    = null;

    /**
     * Constructor privado para prevenir construir excepciones sin mensajes.
     */
    private ServiceExcepcion() {
    }

    public ServiceExcepcion(ServiceExcepcionTipo reasonType, String messageToUser) {
        super(messageToUser);
        this.messageToUser = messageToUser;
        this.reasonType = reasonType;
    }

    /**
     * El mensaje por defecto es el definido en el tipo de la excepci�n.
     *
     * @param reasonType
     */
    public ServiceExcepcion(ServiceExcepcionTipo reasonType) {
        super(reasonType.getMessageForReasonType());
        messageToUser = reasonType.getMessageForReasonType();
        this.reasonType = reasonType;
    }

    public String getMessageToUser() {
        return messageToUser;
    }

    public ServiceExcepcionTipo getReasonType() {
        return reasonType;
    }

    @Override
    @Deprecated
    public String getMessage() {
        return super.getMessage();
    }
}
