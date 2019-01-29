package siga.login;

import siga.login.exceptions.BadCredentialsException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;

public class ClaveSigmaLogin implements Login {
    @Override
    public Map<String, String> doLogin() throws BadCredentialsException {
        throw new NotImplementedException();
    }
}
