package id.alin.espeedboat.MyRetrofit.ServiceResponseModels.Card;

import java.util.List;

import id.alin.espeedboat.MyRoom.Entity.CardEntity;

public class ServerResponseCards {
    private String response_code;
    private String status;
    private String message;
    private Object error;
    private List<CardEntity> cards;

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

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public List<CardEntity> getCards() {
        return cards;
    }

    public void setCards(List<CardEntity> cards) {
        this.cards = cards;
    }
}
