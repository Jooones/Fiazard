package be.swsb.fiazard.common.exceptions;

import be.swsb.fiazard.common.error.AppErrorCode;
import com.google.common.collect.Lists;

public class IllegalIdFiazardException extends FiazardException {

    private static final long serialVersionUID = 7524886230064484746L;
    private Iterable<String> idFields = Lists.newArrayList("id");

    public IllegalIdFiazardException(String id) {
        super(String.format("id invalid: %s", id));
    }

    @Override
    public AppErrorCode getErrorCode() {
        return AppErrorCode.ILLEGAL_ID;
    }

    @Override
    public int getStatus() {
        return 400; // bad request
    }

    @Override
    public Iterable<String> getFields() {
        return idFields;
    }
}
