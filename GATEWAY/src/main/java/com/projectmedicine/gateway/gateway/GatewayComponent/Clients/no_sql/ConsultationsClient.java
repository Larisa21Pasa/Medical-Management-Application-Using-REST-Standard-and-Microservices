package com.projectmedicine.gateway.gateway.GatewayComponent.Clients.no_sql;


import com.projectmedicine.gateway.gateway.Utils.HttpBody.requests.InvestigationRequest;
import com.projectmedicine.gateway.gateway.Utils.HttpBody.responses.ConsultationsResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.projectmedicine.gateway.gateway.Utils.Logs.Urls.*;

@Log4j2
@Component
public class ConsultationsClient {

    private final RestTemplate restTemplate;
    private String consultationsServiceUrl = "http://localhost:8081/api/medical_office/consultations";
    @Autowired
    public ConsultationsClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    public ResponseEntity<?> getAllConsultations(
        Optional<Integer> page,
        Optional<Integer> itemsPerPage,
        Optional<String> idPatient,
        Optional<Integer> idDoctor,
        Optional<String> diagnostic,
        Optional<LocalDateTime> appointmentTime
    ) {
        log.info("getAllConsultations: page:{}, itemsPerPage:{}, idPatient:{}, idDoctor:{}, diagnostic:{}, appointmentTime:{}",
                 page, itemsPerPage, idPatient, idDoctor, diagnostic, appointmentTime);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(CONSULTATIONS_URL)
                .queryParamIfPresent("page", page)
                .queryParamIfPresent("itemsPerPage", itemsPerPage)
                .queryParamIfPresent("idPatient", idPatient)
                .queryParamIfPresent("idDoctor", idDoctor)
                .queryParamIfPresent("diagnostic", diagnostic)
                .queryParamIfPresent("appointmentTime", appointmentTime);

        String uri = uriBuilder.build().toUriString();
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<CollectionModel<EntityModel<?>>> responseType =
                new ParameterizedTypeReference<CollectionModel<EntityModel<?>>>() {
                };

        try {
            log.info("try getting all consultations ..  ");
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




    public ResponseEntity<?>  createConsultation(
        ConsultationsResponse consultationsResponse
    ) {

        log.info("createConsultation: consultation:{}", consultationsResponse);


        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(CONSULTATIONS_URL);


        String uri = uriBuilder.build().toUriString();
        log.info("uri = {} ", uri);

        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {
            log.info("try creating consultation ..  ");
            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.POST,  new HttpEntity<>(consultationsResponse), responseType);
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

    public ResponseEntity<?>  createInvestigation( InvestigationRequest investigationRequest ) {

        log.info("createInvestigation: investigation:{}", investigationRequest);


        UriTemplate uriTemplate = new UriTemplate(CONSULTATIONS_URL + "/consultation/{_id}/investigation");
        log.info("uriTemplate  = {} ", uriTemplate);
        String uri = uriTemplate.expand(investigationRequest.get_id()).toString();
        log.info("uri = {} ", uri);
        ParameterizedTypeReference<EntityModel<?>> responseType =
                new ParameterizedTypeReference<EntityModel<?>>() {};

        try {
            log.info("try creating consultation ..  ");
            ResponseEntity<EntityModel<?>> responseEntity =
                    restTemplate.exchange(uri, HttpMethod.POST,  new HttpEntity<>(investigationRequest), responseType);
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