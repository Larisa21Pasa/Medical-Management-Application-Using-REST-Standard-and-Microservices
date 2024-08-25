package com.pacients.pacientservice.utils.Logs;

public class ProgramLogs {
    /*--------- RESOURCE LOGS----------*/
    public static final String ID_RESOURCE_INVALID = "ID provided is invalid format. Please try again !";
    public static final String NOT_FOUND_EXCEPTION = "Resource required not found.";
    public static final String UNPROCESSABLE_CONTENT_EXCEPTION_FIELDS = "Invalid value/s for body field/s.";
    public static final String UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY = "Invalid value/s for query parameter/s.";
    public static final String CONFLIC_EXCEPTION_NOT_SAME_ID = "Something wrong! Check your ID provided in PATH and in BODY.";
    public static final String CONFLIC_EXCEPTION = "Something wrong! Conflict between data provided and data from database .";
    public static final String CONFLIC_EXCEPTION_SAME_PHONE = "Something wrong! Phone number provided seems already registered. Try again with different phone number .";

    public static final String BAD_REQUEST_EXCEPTION= "Bad request. Please check the request parameters.";

    public static final String NOT_ACCEPTABLE_EXCEPTION = "Request data provided is not acceptable or is missing.";
    public static final String RESOURCE_ALREADY_EXIST = "Client already exists. Please retry to register with different data or log in.";
    public static final String DESERIALIZATION_EXCEPTION = "Provided a resource/query with different type than required or is invalid format for specific type resource";

    public static final String SAVE_ERROR = "Error durring saving resource: ";
    public static final String UPDATE_ERROR = "Error durring updating resource: ";



    /*--------- EXCEPTIONS LOGS----------*/


    /*--------- RESOURCE LOGS----------*/

}
