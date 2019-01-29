package siga.login;

import siga.login.exceptions.BadCredentialsException;

import java.util.Map;

public interface Login {
    public Map<String, String> doLogin() throws BadCredentialsException;
}
