package be.craftsmen.fiazard.representation.error;


import be.craftsmen.fiazard.common.error.AppErrorCode;
import be.craftsmen.fiazard.exceptions.FiazardException;
import be.craftsmen.fiazard.representation.Representation;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class ErrorR implements Representation {
    private int status;
    private String errorCode;
    private Iterable<String> fields;
    private String message;

    ErrorR(int status, String errorCode, String message, Iterable<String> fields){
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.fields = fields;
    }

    ErrorR(int status, String errorCode, String message, String... fields){
        new ErrorR(status, errorCode, message, Lists.newArrayList(fields));
    }

    public static ErrorR from(FiazardException exception){
        AppErrorCode errorCode = exception.getErrorCode();
        int status = exception.getStatus();
        return new ErrorR(exception.getStatus(), errorCode.getErrorCode(), exception.getMessage(), exception.getFields());
    }

    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Iterable<String> getFields() {
        return fields;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorR other = (ErrorR) o;
        return Objects.equal(this.errorCode, other.errorCode)
        && Objects.equal(this.status, other.status)
        && Objects.equal(this.message, other.message)
        && Objects.equal(this.fields, other.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(errorCode, status, message, fields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("status", status)
            .add("message", message)
            .add("errorCode", errorCode)
            .add("fields", fields)
            .toString();
    }
}
