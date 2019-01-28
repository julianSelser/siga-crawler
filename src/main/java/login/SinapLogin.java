package login;

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
import static org.jsoup.Connection.Method.GET;
import static org.jsoup.Connection.Method.POST;

public class SinapLogin {
    public static final int ONE_SECOND_AND_A_HALF = 1500;
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.90 Safari/537.36";

    private final String password;
    private final String username;

    public SinapLogin(String username, String password) throws IOException, InterruptedException {
        this.username = username;
        this.password = password;
    }

    public Map<String, String> doLogin() throws InterruptedException, IOException {
        Map<String, String> sigaCookies = Jsoup.connect(Siga.URL).execute().cookies();

        sleep(ONE_SECOND_AND_A_HALF);

        Connection.Response loginPage = goToLoginPage(sigaCookies);

        sleep(ONE_SECOND_AND_A_HALF);

        Connection.Response loginAttempt = attemptLogin(loginPage);

        sleep(ONE_SECOND_AND_A_HALF);

        Map<String, String> sessionCookies = obtainSession(loginPage, loginAttempt);

        sleep(ONE_SECOND_AND_A_HALF);

        return sessionCookies;
    }

    private Map<String, String> obtainSession(Connection.Response loginPage, Connection.Response loginAttempt) throws IOException {
        Document loginAttemptPage = loginAttempt.parse();

        Elements form = loginAttemptPage.body().getElementsByTag("form");
        String action = form.attr("action");

        String SAMLResponse = form.select("input").get(1).attr("value");
        String RelayState = form.select("input").get(2).attr("value");

        HashMap<String, String> cookies = new HashMap<>();
        cookies.putAll(loginAttempt.cookies());
        cookies.putAll(loginPage.cookies());

        return Jsoup.connect(action).followRedirects(true).userAgent("curl").data("SAMLResponse", SAMLResponse).data("RelayState", RelayState)
                .cookies(cookies)
                .userAgent(USER_AGENT)
                .method(POST)
                .execute()
                .cookies();
    }

    private Connection.Response attemptLogin(Connection.Response loginPage) throws IOException {
        String authState = loginPage.url().getQuery().replace("AuthState=", "");

        return Jsoup.connect(loginPage.url().toString())
                .cookies(loginPage.cookies())
                .cookie("SimpleSAMLAuthToken", authState.replaceFirst("c%3.*", ""))
                .data("username", username)
                .data("password", password)
                .data("AuthState", decode(authState, UTF_8))
                .followRedirects(true)
                .userAgent(USER_AGENT)
                .method(POST)
                .execute();
    }

    private Connection.Response goToLoginPage(Map<String, String> sigaCookies) throws IOException {
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
