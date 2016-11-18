package de.florianstendel.apps.rest.entity;

/**
 * Entity for specifing fault conditions.
 *
 * @author Florian Stendel
 */
public class Fault {

    private String errorCode;
    private String message;
    private String description;


    public Fault(final String errorCode, final String message, final String description){
        this.errorCode = errorCode;
        this.message = message;
        this.description = description;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
