package com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql;

import com.projectmedicine.gateway.gateway.Utils.Exceptions.NotFoundException;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.AppointmentRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.PatientRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.AppointmentResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.projectmedicine.gateway.gateway.Utils.Logs.ConsultationsLog.NOT_FOUND_EXCEPTION;
import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.APPOINTMENTS_URL;
import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.PATIENTS_URL;

@Log4j2
@Component
public class AppointmentsClient {

    private final RestTemplate restTemplate;

    @Autowired
    public AppointmentsClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> getAllAppointments(
            Optional<Integer> page,
            Optional<Integer> itemsPerPage,
            Optional<String> status
    ) {
        /* Treat all exception in patient client so gateway controller can be clean and just route resources */
        log.info("getAllAppointments => page{}, itemsPerPage{}, status:{}",
                page, itemsPerPage, status);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(APPOINTMENTS_URL)
                .queryParamIfPresent("page", page)
                .queryParamIfPresent("itemsPerPage", itemsPerPage)
                .queryParamIfPresent("status", status);

        String uri = uriBuilder.build().toUriString();
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<CollectionModel<EntityModel<?>>> responseType =
                new ParameterizedTypeReference<CollectionModel<EntityModel<?>>>() {
                };

        try {
            log.info("try getting all appointments ..  ");
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







    public ResponseEntity<?> getAppointmentByOwnId(String idPatient, Integer idDoctor, LocalDateTime appointmentTime) {
        log.info("getAppointment => {} {} {} ", idPatient, idDoctor, appointmentTime);
        UriTemplate uriTemplate = new UriTemplate(APPOINTMENTS_URL + "/{idPatient}/{idDoctor}/{appointmentTime}");
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(idPatient, idDoctor, appointmentTime).toString();
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {
            log.info("try getting all appointments ..  ");
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


    public ResponseEntity<?> getPatientAppointments( String cnp, Optional<Integer> date,  Optional<String> type, Optional<String> status) {
        log.info("getPatientAppointments => {} {} {}  {}", cnp, date, type, status);
        UriTemplate uriTemplate = new UriTemplate(APPOINTMENTS_URL + "/patient/{cnp}");
        String uriTemplateString = uriTemplate.expand(cnp).toString();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(String.valueOf(uriTemplateString))
                .queryParamIfPresent("date", date)
                .queryParamIfPresent("type", type)
                .queryParamIfPresent("status", status);

        String uri = uriBuilder.build().toUriString();

        log.info("uriTemplate  = {} ", uriTemplate);
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {
            log.info("try getting all appointments ..  ");
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


    public ResponseEntity<?> getDoctorAppointments( Integer idDoctor, Optional<Integer> date,  Optional<String> type, Optional<String> status) {
        log.info("getPatientAppointments => {} {} {}  {}", idDoctor, date, type, status);
        UriTemplate uriTemplate = new UriTemplate(APPOINTMENTS_URL + "/doctor/{idDoctor}");
        String uriTemplateString = uriTemplate.expand(idDoctor).toString();

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(String.valueOf(uriTemplateString))
                .queryParamIfPresent("date", date)
                .queryParamIfPresent("type", type)
                .queryParamIfPresent("status", status);

        String uri = uriBuilder.build().toUriString();

        log.info("uriTemplate  = {} ", uriTemplate);
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {
            log.info("try getting all appointments ..  ");
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

    public ResponseEntity<?> createORUpdateAppointment(
             final String cnp,
             final Integer idDoctor,
             AppointmentRequest appointmentRequest
    ) {
        UriTemplate uriTemplate = new UriTemplate(APPOINTMENTS_URL + "/" + "patient/{cnp}/doctor/{idDoctor}");
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(cnp,idDoctor).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.PUT,  new HttpEntity<>(appointmentRequest), responseType);
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

}
