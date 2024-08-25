package com.projectmedicine.gateway.gateway.GatewayComponent.Clients.sql;

import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.DoctorRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.PatientRequest;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import java.util.Optional;

import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.DOCTORS_URL;
import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.PATIENTS_URL;

@Log4j2
@Component
public class DoctorsClient {
    private final RestTemplate restTemplate;

    @Autowired
    public DoctorsClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> getAllDoctors(
            Optional<Integer> page,
            Optional<Integer> itemsPerPage,
            Optional<String> lastname,
            Optional<String> firstname,
            Optional<String> email,
            Optional<String> phone,
            Optional<String> specialization
    ) {
        /* Treat all exception in patient client so gateway controller can be clean and just route resources */
        log.info("getAllDoctors => page{}, itemsPerPage{}, lastname:{}, firstname:{}, email:{}, phone:{}, specialization:{}",
                page, itemsPerPage, lastname, firstname, email, phone, specialization);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(DOCTORS_URL)
                .queryParamIfPresent("page", page)
                .queryParamIfPresent("itemsPerPage", itemsPerPage)
                .queryParamIfPresent("lastname", lastname)
                .queryParamIfPresent("firstname", firstname)
                .queryParamIfPresent("email", email)
                .queryParamIfPresent("phone", phone)
                .queryParamIfPresent("specialization",specialization);

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

    public ResponseEntity<?> getDoctorByIdUser(
            Integer idUser
    ) {
        log.info("sunt in client ");
        UriTemplate uriTemplate = new UriTemplate(DOCTORS_URL + "/user/" +idUser);
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


    public ResponseEntity<?> getDoctorByIdDoctor(
                Integer idDoctor
    ) {
        log.info("sunt in client ");
        UriTemplate uriTemplate = new UriTemplate(DOCTORS_URL + "/" +idDoctor);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(idDoctor).toString();
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



    public ResponseEntity<?> createDoctor(
            DoctorRequest doctorRequest
    ) {
        UriTemplate uriTemplate = new UriTemplate(DOCTORS_URL);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.POST,  new HttpEntity<>(doctorRequest), responseType);
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
    public ResponseEntity<?> updateDoctor(
            Integer idDoctor,
            DoctorRequest doctorRequest
    ) {
        UriTemplate uriTemplate = new UriTemplate(DOCTORS_URL + "/" + idDoctor);
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(idDoctor).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {

            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.PUT,  new HttpEntity<>(doctorRequest), responseType);
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
