package com.projectmedicine.gateway.gateway.Utils.Logs;

public class ConsultationsLog {
    public static final String CONFLIC_EXCEPTION_NOT_SAME_ID = "Something wrong! Check your ID provided in PATH and in BODY.";

    public static final String NOT_FOUND_EXCEPTION = "Consultation not found.";
    public static final String UNPROCESSABLE_CONTENT_MESSAGE_FIELDS = "Invalid values for request fields";
    public static final String UNPROCESSABLE_CONTENT_MESSAGE_QUERRY = "Invalid values for querry paratemers.";
    public static final String NOT_ACCEPTABLE_CONTENT_MESSAGE = "The request content is not acceptable.";
    public static final String BAD_REQUEST_MESSAGE = "Bad request. Please check the request parameters.";
    public static final String CONFLICT_MESSAGE = "Conflict. There is a data conflict. Please check again your action.";
    public static final String DESERIALIZATION_EXCEPTION = "Provided a resource/query with different type than required or is invalid format for specific type resource";
    public static final String NOT_ACCEPTABLE_EXCEPTION = "Request data provided is not acceptable or is missing.";
    public static final String CONFLICT_EXCEPTION_USER_BLOCKED = " Seems that account is blocked or deleted !";

    public static final String CONFLIC_MESSAGE_NOT_SAME_CNP = "Something wrong! Check your value provided in path and in register field.";
    public static final String PATIENT_EMAIL_ALREADY_EXIST = "Consultation with identical email already in database. You may already be registered. Try to log in.";
    public static final String PATIENT_ALREADY_EXIST = "Consultation already exists. Please retry to register with different data or log in.";
    public static final String NOT_ACCEPTABLE_REQUEST = "Request data provided is not acceptable or in wrong format.";
    /*---------------------- CORS CONFIG ------------------------*/
    public static final Long MAX_AGE = 3600L;
    public static final int CORS_FILTER_ORDER = -102;

    /*------------------------------------------  TOKEN -----------------------------------------------------------*/
    public static final long TOKEN_ACCESS_VALIDITY_TIME = 1*60 * 1000;

    public  static final String SECRET_KEY = "3A7B2F9C5E1D8AF03456789B0CFE2D1876543210ABCDEF0987654321FEDCBA98";
    public static final String TOKEN_EXPIRED_EXCEPTION="Token expired. Try login again!";

}
