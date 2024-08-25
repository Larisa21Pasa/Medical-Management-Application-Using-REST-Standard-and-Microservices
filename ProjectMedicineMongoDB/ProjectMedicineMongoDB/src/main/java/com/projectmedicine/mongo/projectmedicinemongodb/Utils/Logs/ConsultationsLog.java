package com.projectmedicine.mongo.projectmedicinemongodb.Utils.Logs;

public class ConsultationsLog {
    public static final String NOT_FOUND_EXCEPTION = "Consultation not found.";
    public static final String UNPROCESSABLE_CONTENT_MESSAGE_FIELDS = "Invalid values for request fields";
    public static final String UNPROCESSABLE_CONTENT_MESSAGE_QUERRY = "Invalid values for querry paratemers.";
    public static final String NOT_ACCEPTABLE_CONTENT_MESSAGE = "The request content is not acceptable.";
    public static final String BAD_REQUEST_MESSAGE = "Bad request. Please check the request parameters.";
    public static final String CONFLICT_MESSAGE = "Conflict. The resource already exists or there is a data conflict.";
    public static final String DESERIALIZATION_EXCEPTION = "Provided a resource/query with different type than required or is invalid format for specific type resource";
    public static final String NOT_ACCEPTABLE_EXCEPTION = "Request data provided is not acceptable or is missing.";

    public static final String CONFLIC_MESSAGE_NOT_SAME_CNP = "Something wrong! Check your value provided in path and in register field.";
    public static final String PATIENT_EMAIL_ALREADY_EXIST = "Consultation with identical email already in database. You may already be registered. Try to log in.";
    public static final String PATIENT_ALREADY_EXIST = "Consultation already exists. Please retry to register with different data or log in.";
    public static final String NOT_ACCEPTABLE_REQUEST = "Request data provided is not acceptable or in wrong format.";
}
