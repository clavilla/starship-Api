package com.clavilla.w2wchallenge.StarshipApi.exception;

public class BadRequestException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public BadRequestException() {
        }

        public BadRequestException(String message) {
            super(message);
        }
}
