package t8studio.com.br.worker.api;

/**
 * Created by rafael on 20/03/15.
 */
public interface Listener {
    /** Called when a success response is received. */
    public void onSuccess();
    /** Called when a error response is received. */
    public void onError(TypeErrors type);
}
