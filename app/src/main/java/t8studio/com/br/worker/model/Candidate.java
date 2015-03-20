package t8studio.com.br.worker.model;


/**
 * Created by rafael on 19/03/15.
 */
public class Candidate {
    private String name;
    private String email;
    private int html;
    private int css;
    private int javaScript;
    private int python;
    private int django;
    private int ios;
    private int android;

    public Candidate(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHtml() {
        return html;
    }

    public void setHtml(int html) {
        this.html = html;
    }

    public int getCss() {
        return css;
    }

    public void setCss(int css) {
        this.css = css;
    }

    public int getJavaScript() {
        return javaScript;
    }

    public void setJavaScript(int javaScript) {
        this.javaScript = javaScript;
    }

    public int getPython() {
        return python;
    }

    public void setPython(int python) {
        this.python = python;
    }

    public int getDjango() {
        return django;
    }

    public void setDjango(int django) {
        this.django = django;
    }

    public int getIos() {
        return ios;
    }

    public void setIos(int ios) {
        this.ios = ios;
    }

    public int getAndroid() {
        return android;
    }

    public void setAndroid(int android) {
        this.android = android;
    }
}