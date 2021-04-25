package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Review;


public class ServerResponseReview {
    private String response_code;
    private String status;
    private String message;
    private ServerResponseErrorReview error;

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ServerResponseErrorReview getError() {
        return error;
    }

    public void setError(ServerResponseErrorReview error) {
        this.error = error;
    }
}

class ServerResponseErrorReview{

}
