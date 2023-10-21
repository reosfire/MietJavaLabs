package ru.reosfire.lab3.authentication;

public interface AuthorityVerifier {
    boolean authorize(Credentials credentials);
}
