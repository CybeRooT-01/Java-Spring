package com.webgram.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @param <T> cette classe permet d'uniformiser le format de réponse envoyer au client.
 *            Elle est composée de:
 *            status: le statut de la réponse (Ex: OK, BAD_REQUEST, UNAUTHORIZED etc... )
 *            data: C'est la donnée envoyé au client, elle est générique donc peut prendre tout type de données
 *            errors: Stock les erreur s'il y a en
 *            message: Stock un message de succes!
 *            metadata: Stock les donnees de la pagination
 */

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T> {
    private Status status;
    private T data;
    private Object errors;
    private Object metadata;
    private Object message;

    public static <T> Response<T> firstConnexion() {
        Response<T> response = new Response<>();
        response.setStatus(Status.FIRST_CONNECTION);
        return response;
    }

    public static <T> Response<T> disabledAccount() {
        Response<T> response = new Response<>();
        response.setStatus(Status.DISABLED_ACCOUNT);
        return response;
    }

    public static <T> Response<T> badRequest() {
        Response<T> response = new Response<>();
        response.setStatus(Status.BAD_REQUEST);
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(Status.OK);
        return response;
    }

    public static <T> Response<T> unauthorized() {
        Response<T> response = new Response<>();
        response.setStatus(Status.UNAUTHORIZED);
        return response;
    }

    public static <T> Response<T> validationException() {
        Response<T> response = new Response<>();
        response.setStatus(Status.VALIDATION_EXCEPTION);
        return response;
    }

    public static <T> Response<T> wrongCredentials() {
        Response<T> response = new Response<>();
        response.setStatus(Status.WRONG_CREDENTIALS);
        return response;
    }

    public static <T> Response<T> accessDenied() {
        Response<T> response = new Response<>();
        response.setStatus(Status.ACCESS_DENIED);
        return response;
    }

    public static <T> Response<T> exception() {
        Response<T> response = new Response<>();
        response.setStatus(Status.EXCEPTION);
        return response;
    }

    public static <T> Response<T> notFound() {
        Response<T> response = new Response<>();
        response.setStatus(Status.NOT_FOUND);
        return response;
    }

    public static <T> Response<T> duplicateMatricule() {
        Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public static <T> Response<T> duplicateEntity() {
        Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_ENTITY);
        return response;
    }

    public static <T> Response<T> duplicateEmail() {
        Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_EMAIL);
        return response;
    }

    public static <T> Response<?> duplicateTelephone() {

        Response<T> response = new Response<>();
        response.setStatus(Status.DUPLICATE_TELEPHONE);
        return response;
    }


    public static <T> Response<?> tokenExpired() {
        Response<T> response = new Response<>();
        response.setStatus(Status.TOKEN_EXPIRED);
        return response;
    }

    public static <T> Response<?> tokenUsed() {
        Response<T> response = new Response<>();
        response.setStatus(Status.TOKEN_USED);
        return response;
    }

    public static <T> Response<?> tokenInvalid() {
        Response<T> response = new Response<>();
        response.setStatus(Status.TOKEN_INVALID);
        return response;
    }

    public void addErrorMsgToResponse(String errorMsg, Exception ex) {
        ResponseError error = new ResponseError()
                .setDetails(errorMsg)
                .setMessage(ex.getMessage())
                .setTimestamp(DateUtils.today());
        setErrors(error);
    }

    public enum Status {
        OK, BAD_REQUEST, UNAUTHORIZED, VALIDATION_EXCEPTION, EXCEPTION, WRONG_CREDENTIALS, ACCESS_DENIED,
        NOT_FOUND, DUPLICATE_ENTITY, DUPLICATE_EMAIL, DUPLICATE_MATRICULE, DUPLICATE_TELEPHONE, TOKEN_EXPIRED,
        FIRST_CONNECTION, DISABLED_ACCOUNT, TOKEN_USED, TOKEN_INVALID
    }

    @Getter
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageMetadata {
        private final int size;
        private final long totalElements;
        private final int totalPages;
        private final int number;

        public PageMetadata(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }
    }
}
