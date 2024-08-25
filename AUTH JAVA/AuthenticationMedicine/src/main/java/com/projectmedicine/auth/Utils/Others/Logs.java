package com.projectmedicine.auth.Utils.Others;

public class Logs {
    /*------------------------------------------  TOKEN -----------------------------------------------------------*/
    public static final long TOKEN_ACCESS_VALIDITY_TIME = 30*60 * 1000;
    public static final long TOKEN_REFRESH_VALIDITY_TIME = 2 * 60 * 1000;

    public static final String SECRET_KEY = "3A7B2F9C5E1D8AF03456789B0CFE2D1876543210ABCDEF0987654321FEDCBA98";

    /*------------------------------------------  NOT FOUND -----------------------------------------------------------*/
    public static final String NOT_FOUND_EXCEPTION = "Required resource not found!";

    /*------------------------------------------  UNPROCESSABLE -----------------------------------------------------------*/
    public static final String UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY = "Invalid value/s for query parameter/s.";
    public static final String UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS = "Invalid value/s for body field/s.";
    public static final String PASSWORD_CHANGE_EXCEPTION_NOT_SAME_NEW= "New password and confirmation password are not the same!";
    public static final String PASSWORD_CHANGE_EXCEPTION_NOT_SAME_CURRENT = "Current password not match. Provide your current password!";

    public static final String DESERIALIZATION_EXCEPTION = "Provided a resource/query with different type than required or is invalid format for specific type resource";


    /*------------------------------------------  NOT ACCEPTABLE-----------------------------------------------------------*/
    public static final String NOT_ACCEPTABLE_EXCEPTION = "Request data provided is not acceptable or is missing.";
    public static final String EMPTY_INVALID_EXCEPTION_TOKEN = "Something happen to your access token. Seems empty.";

    /*------------------------------------------  CONFLICT-----------------------------------------------------------*/
    public static final String CONFLIC_EXCEPTION_ALREADY_REGISTERED = "Something wrong! You look already registered. Try to login instead. ";
    public static final String CONFLIC_EXCEPTION_NOT_REGISTERED = "Something wrong! You look not registered or you provided wrong credentials.";
    public static final String CONFLIC_EXCEPTION_NOT_SAME_ID = "Something wrong! Check your ID provided in PATH and in BODY.";
    public static final String CONFLIC_EXCEPTION = "Something wrong! Conflict between data provided and data from database .";
    public static final String CONFLICT_EXCEPTION_USER_BLOCKED = " Seems that account is blocked or deleted !";

    public static final String BAD_REQUEST_EXCEPTION = "Bad request exception";
    /*------------------------------------------  TOKEN EXPIRED -----------------------------------------------------------*/
    public static final String TOKEN_EXPIRED_EXCEPTION="Token expired. Try login again!";

    /*------------------------------------------  VALIDATORS-----------------------------------------------------------*/
    public static final String PASSWORD_VALIDATOR_MESSAGE = "Password must be formed from 1 to 20 letters";

    /*------------------------------------------  UPDATE -----------------------------------------------------------*/
    public static final String UPDATE_ERROR = "Error durring updating resource: ";

    /*---------------------- CORS CONFIG ------------------------*/
    public static final Long MAX_AGE = 3600L;
    public static final int CORS_FILTER_ORDER = -102;

}
