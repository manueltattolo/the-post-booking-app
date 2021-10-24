package the.postbooking.app.exception;

/**
 * An enumeration of error codes and associated i18n message keys for order
 * related validation errors.
 *
 **/

public enum ErrorCode {
    // Internal Errors: 1 to 0999
    GENERIC_ERROR("0001", "The system is unable to complete the request. Contact system support."),
    HTTP_MEDIATYPE_NOT_SUPPORTED("0002", "Requested media type is not supported. Please use application/json or application/xml as 'Content-Type' header value"),
    HTTP_MESSAGE_NOT_WRITABLE("0003", "Missing 'Accept' header. Please add 'Accept' header."),
    HTTP_MEDIA_TYPE_NOT_ACCEPTABLE("0004", "Requested 'Accept' header value is not supported. Please use application/json or application/xml as 'Accept' value"),
    JSON_PARSE_ERROR("0005", "Make sure request payload should be a valid JSON object."),
    HTTP_MESSAGE_NOT_READABLE("0006", "Make sure request payload should be a valid JSON or XML object according to 'Content-Type'."),
    CUSTOMER_NOT_FOUND("0007", "Requested customer not found"),
    RESOURCE_NOT_FOUND("0008", "Resource not found"),
    GENERIC_ALREADY_EXISTS("0013", "Already exists."),
    ACCESS_DENIED("0014", "Access Denied."),
    UNAUTHORIZED("0015", "Unauthorized");


    private String errCode;
    private String errMsgKey;

    ErrorCode(final String errCode, final String errMsgKey) {
        this.errCode = errCode;
        this.errMsgKey = errMsgKey;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @return the errMsgKey
     */
    public String getErrMsgKey() {
        return errMsgKey;
    }

}
