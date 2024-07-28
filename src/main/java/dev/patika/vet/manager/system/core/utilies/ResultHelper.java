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
    public static <T> ResultData<T> animalNotFoundError(String msg) {
        return new ResultData<>(false, msg, "400", null);
    }
    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true,Msg.OK,"200",data);
    }

    public static Result notFoundError(String msg) {
        return new Result(true,msg,"404");
    }
    public static Result validationError(String message) {
        return new Result(false, message, "400");
    }

    public static Result pageError(String msg) {
        return new Result(false,Msg.NOT_PAGE_ERROR,"404");

    }
    public static Result scheduleConflictError(String message) {
        return new Result(false, message, "409");
    }
    public static Result deleted(){
        return new Result(true ,Msg.DELETED , "200");
    }

    public static  Result ok() {
        return new Result(true,Msg.OK,"200");
    }
    public static Result null_date() {
        return new Result(false,Msg.NULL_DATE,"400");
    }
    public static <T> ResultData <T> animalNotFoundError() {
        return new ResultData<>(false, Msg.ANIMAL_NOT_FOUND, "404", null);
    }
    public static <T> ResultData<T> EmailExists() {
        return new ResultData<>(false, Msg.SAME_EMAIL, "400", null);
    }

    public static <T> ResultData<T> vaccineNameAndCodeExists() {
        return new ResultData<>(false, Msg.SAME_VACCINE_NAME_AND_CODE, "400", null);
    }
}
