package siga.login;

import siga.login.exceptions.BadCredentialsException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import siga.Siga;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.sun.xml.internal.ws.commons.xmlutil.Converter.UTF_8;
import static java.lang.Thread.sleep;
import static java.net.URLDecoder.decode;
import static java.util.Objects.isNull;
import static org.jsoup.Connection.Method.GET;
import static org.jsoup.Connection.Method.POST;

public class SinapLogin implements Login {
    public static final int ONE_SECOND_AND_A_HALF = 1500;
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.90 Safari/537.36";

    private final String password;
    private final String username;

    private Document loginAttemptPage;

    public SinapLogin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Map<String, String> doLogin() throws BadCredentialsException {
        try {
            return attemptLogin();
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> attemptLogin() throws IOException, InterruptedException, BadCredentialsException {
        Map<String, String> sigaCookies = Jsoup.connect(Siga.URL).execute().cookies();

        sleep(ONE_SECOND_AND_A_HALF);

        Connection.Response loginPage = goToLoginPageWith(sigaCookies);

        sleep(ONE_SECOND_AND_A_HALF);

        Connection.Response loginAttempt = attemptLoginAt(loginPage);

        if(!areCredentialsValid()) throw new BadCredentialsException();

        sleep(ONE_SECOND_AND_A_HALF);

        Map<String, String> sessionCookies = obtainLoggedInSession(fuseCookies(loginPage, loginAttempt));

        sleep(ONE_SECOND_AND_A_HALF);

        return sessionCookies;
    }

    private Boolean areCredentialsValid() {
        // si tenemos las credenciales correctas la pagina de login va a devolver
        // un html pelado que nos hace clickear un boton ante la falta de javascript
        // caso contrario las credenciales son invalidas
        return !isNull(loginAttemptPage)
                && loginAttemptPage.body().text().contains("you must press the button");
    }

    private Map<String, String> fuseCookies(Connection.Response res1, Connection.Response res2) {
        HashMap<String, String> cookies = new HashMap<>();

        cookies.putAll(res1.cookies());
        cookies.putAll(res2.cookies());

        return cookies;
    }

    private Map<String, String> obtainLoggedInSession(Map<String, String> loginCookies) throws IOException {
        Elements form = loginAttemptPage.body().getElementsByTag("form");
        String action = form.attr("action");

        String SAMLResponse = form.select("input").get(1).attr("value");
        String RelayState = form.select("input").get(2).attr("value");

        return Jsoup.connect(action)
                .followRedirects(true)
                .data("SAMLResponse", SAMLResponse)
                .data("RelayState", RelayState)
                .cookies(loginCookies)
                .userAgent(USER_AGENT)
                .method(POST)
                .execute()
                .cookies();
    }

    private Connection.Response attemptLoginAt(Connection.Response loginPage) throws IOException {
        String authState = loginPage.url().getQuery().replace("AuthState=", "");

        Connection.Response loginAttemptResponse = Jsoup.connect(loginPage.url().toString())
                .cookies(loginPage.cookies())
                .cookie("SimpleSAMLAuthToken", authState.replaceFirst("c%3.*", ""))
                .data("username", username)
                .data("password", password)
                .data("AuthState", decode(authState, UTF_8))
                .followRedirects(true)
                .userAgent(USER_AGENT)
                .method(POST)
                .execute();

        this.loginAttemptPage = loginAttemptResponse.parse();

        return loginAttemptResponse;
    }

    private Connection.Response goToLoginPageWith(Map<String, String> sigaCookies) throws IOException {
        return Jsoup
                .connect("http://siga.frba.utn.edu.ar/try/sinap.do")
                .cookies(sigaCookies)
                .followRedirects(true)
                .userAgent(USER_AGENT)
                .referrer(Siga.URL)
                .method(GET)
                .execute();
    }
}
