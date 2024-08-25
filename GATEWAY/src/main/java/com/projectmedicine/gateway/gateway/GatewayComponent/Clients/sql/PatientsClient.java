package com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql;

import com.projectmedicine.gateway.gateway.Utils.Exceptions.NotAcceptableException;
import com.projectmedicine.gateway.gateway.Utils.Exceptions.NotFoundException;
import com.projectmedicine.gateway.gateway.Utils.Exceptions.UnprocessableContentException;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.PatientRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.AppointmentResponse;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.PatientResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_ACCEPTABLE_CONTENT_MESSAGE;
import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_FOUND_EXCEPTION;
import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.PATIENTS_URL;
import static com.projectmedicine.gateway.gateway.Utils.Others.Macros.UNPROCESSABLE_CONTENT_EXCEPTION_QUERRY;

@Log4j2
@Component
public class PatientsClient {

    private final RestTemplate restTemplate;

    @Autowired
    public PatientsClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> getAllPatients(
            Optional<String> lastname,
            Optional<String> firstname,
            Optional<String> email,
            Optional<String> phone,
            @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> birthdate
    ) {
        /* Treat all exception in patient client so gateway controller can be clean and just route resources */
        log.info("getAllPatients => lastname:{}, firstname:{}, email:{}, phone:{}, birthdate:{}",
                lastname, firstname, email, phone, birthdate);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(PATIENTS_URL)
                .queryParamIfPresent("lastname", lastname)
                .queryParamIfPresent("firstname", firstname)
                .queryParamIfPresent("email", email)
                .queryParamIfPresent("phone", phone)
                .queryParamIfPresent("birthdate", birthdate.map(Object::toString)); // converteste Optional<Date> in Optional<String>

        String uri = uriBuilder.build().toUriString();
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<CollectionModel<EntityModel<?>>> responseType =
                new ParameterizedTypeReference<CollectionModel<EntityModel<?>>>() {
                };

        try {
            log.info("try getting patients ");
            ResponseEntity<CollectionModel<EntityModel<?>>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, null, responseType);
            log.info("responseEntity = {} ", responseEntity);
            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
        catch (HttpClientErrorException.UnprocessableEntity exception) {
            log.info("catch: unprocessableEntityException {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotFound exception) {
            log.info("catch: NotFound {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotAcceptable exception) {
            log.info("catch: NotAcceptable {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;
                log.info("catch: HttpClientErrorException {} {}", exception.getMessage(), clientErrorException.getRawStatusCode());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(clientErrorException.getRawStatusCode()));
            } else {
                log.error("Unexpected RestClientException: {}", exception.getMessage());
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }


    public ResponseEntity<?> getPatientByIdUser(
            Integer idUser
    ) {
        log.info("sunt in client ");
        UriTemplate uriTemplate = new UriTemplate(PATIENTS_URL + "/user/" +idUser);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(idUser).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, null, responseType);
            log.info("responseEntity = {} ", responseEntity);

            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
        catch (HttpClientErrorException.UnprocessableEntity exception) {
            log.info("catch: unprocessableEntityException {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotFound exception) {
            log.info("catch: NotFound {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotAcceptable exception) {
            log.info("catch: NotAcceptable {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;
                log.info("catch: HttpClientErrorException {} {}", exception.getMessage(), clientErrorException.getRawStatusCode());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(clientErrorException.getRawStatusCode()));
            } else {
                log.error("Unexpected RestClientException: {}", exception.getMessage());
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }


    public ResponseEntity<?> getPatientByCnp(
            String cnp
    ) {
        log.info("sunt in client ");
        UriTemplate uriTemplate = new UriTemplate(PATIENTS_URL + "/" +cnp);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(cnp).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.GET, null, responseType);
            log.info("responseEntity = {} ", responseEntity);

            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
        catch (HttpClientErrorException.UnprocessableEntity exception) {
            log.info("catch: unprocessableEntityException {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotFound exception) {
            log.info("catch: NotFound {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotAcceptable exception) {
            log.info("catch: NotAcceptable {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;
                log.info("catch: HttpClientErrorException {} {}", exception.getMessage(), clientErrorException.getRawStatusCode());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(clientErrorException.getRawStatusCode()));
            } else {
                log.error("Unexpected RestClientException: {}", exception.getMessage());
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }


    public ResponseEntity<?> createOrUpdatePatient(
            String cnp,
            PatientRequest patientRequest
    ) {
        UriTemplate uriTemplate = new UriTemplate(PATIENTS_URL + "/" + cnp);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(cnp).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.PUT,  new HttpEntity<>(patientRequest), responseType);
            log.info("responseEntity = {} ", responseEntity);

            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }

        catch (HttpClientErrorException.Conflict exception) {
            log.info("catch: Conflict {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.UnprocessableEntity exception) {
            log.info("catch: unprocessableEntityException {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotFound exception) {
            log.info("catch: NotFound {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotAcceptable exception) {
            log.info("catch: NotAcceptable {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;
                log.info("catch: HttpClientErrorException {} {}", exception.getMessage(), clientErrorException.getRawStatusCode());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(clientErrorException.getRawStatusCode()));
            } else {
                log.error("Unexpected RestClientException: {}", exception.getMessage());
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }

    public ResponseEntity<?> deletePatient(
            String cnp
    ) {
        UriTemplate uriTemplate = new UriTemplate(PATIENTS_URL + "/" +cnp);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(cnp).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.DELETE, null, responseType);
            log.info("responseEntity = {} ", responseEntity);

            return new ResponseEntity<>(responseEntity.getBody(), responseEntity.getStatusCode());
        }
        catch (HttpClientErrorException.UnprocessableEntity exception) {
            log.info("catch: unprocessableEntityException {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotFound exception) {
            log.info("catch: NotFound {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (HttpClientErrorException.NotAcceptable exception) {
            log.info("catch: NotAcceptable {} {}", exception.getMessage(), exception.getStatusCode());
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(exception.getStatusCode().value()));
        }
        catch (RestClientException exception) {
            if (exception instanceof HttpClientErrorException) {
                HttpClientErrorException clientErrorException = (HttpClientErrorException) exception;
                log.info("catch: HttpClientErrorException {} {}", exception.getMessage(), clientErrorException.getRawStatusCode());
                return new ResponseEntity<>(exception.getMessage(), HttpStatus.valueOf(clientErrorException.getRawStatusCode()));
            } else {
                log.error("Unexpected RestClientException: {}", exception.getMessage());
                return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    }


}
