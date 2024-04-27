package org.project.resourceservice.exception;

public class ResourceException extends Exception{
    private String code;

    public ResourceException(String code, String message) {
//        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
