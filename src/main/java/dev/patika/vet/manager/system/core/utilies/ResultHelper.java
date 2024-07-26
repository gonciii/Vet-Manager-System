package dev.patika.vet.manager.system.core.utilies;

import dev.patika.vet.manager.system.core.result.Result;
import dev.patika.vet.manager.system.core.result.ResultData;

public class ResultHelper {

    public static <T> ResultData<T> created(T data) {
        return new ResultData<>(true,Msg.CREATED,"201",data);
    }

    public static <T> ResultData<T> validateError(T data) {
        return new ResultData<>(false, Msg.VALIDATE_ERROR,"400",data);
    }

    public static <T> ResultData<T> badRequest(T data) {
        return new ResultData<>(false, Msg.BAD_REQUEST,"400",data);
    }


    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true,Msg.OK,"200",data);
    }

    public static Result notFoundError(String msg) {
        return new Result(true,msg,"404");
    }

    public static Result pageError(String msg) {
        return new Result(false,Msg.NOT_PAGE_ERROR,"404");

    }

    public static Result deleted(){
        return new Result(true ,Msg.DELETED , "200");
    }

    public static  Result ok() {
        return new Result(true,Msg.OK,"200");
    }
}
