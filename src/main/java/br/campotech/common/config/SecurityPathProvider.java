package br.campotech.common.config;

public interface SecurityPathProvider {
    String[] getPublicAnyPaths();
    String[] getAuthenticatedPaths();
}