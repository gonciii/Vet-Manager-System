package dev.patika.vet.manager.system.core.result;


import lombok.Getter;


// generic yapıda bir result data sınıfı !

@Getter
public class ResultData<T> extends Result {
    private T data;


    public ResultData(boolean status, String message, String code,T data) {
        super(status, message, code);
        this.data = data;
    }
}
