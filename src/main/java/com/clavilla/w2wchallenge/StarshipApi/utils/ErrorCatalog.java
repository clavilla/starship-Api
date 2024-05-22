package com.clavilla.w2wchallenge.StarshipApi.utils;

public enum ErrorCatalog {

        STARSHIP_NOT_FOUND("ERR_STARSHIP_001", "Starship not found."),
        INVALID_STARSHIP("ERR_STARSHIP_002", "Invalid starship parameters."),
        GENERIC_ERROR("ERR_GENERIC_001", "An unexpected error occurred.");

        private final String code;
        private final String message;

        ErrorCatalog(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
}
