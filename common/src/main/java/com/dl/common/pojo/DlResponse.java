package com.dl.common.pojo;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Global response entity.
 *
 * @author johnniang
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DlResponse<T> {

    public enum Status{
        OK(200),ERROR(500);

        private int value;

        Status(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }

    /**
     * Response status.
     */
    private Integer status;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response development message
     */
    private String devMessage;

    /**
     * Response data
     */
    private T data;

    public DlResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    @NonNull
    public static <T> DlResponse<T> badRequest(@Nullable String message) {
        return new DlResponse<>(Status.ERROR.value, message, null);
    }

    @NonNull
    public static <T> DlResponse<T> badRequest(@Nullable String message, @Nullable T data) {
        return new DlResponse<>(Status.ERROR.value, message, data);
    }

    /**
     * Creates an ok result with message and data. (Default status is 200)
     *
     * @param data    result data
     * @param message result message
     * @return ok result with message and data
     */
    @NonNull
    public static <T> DlResponse<T> ok(@Nullable String message, @Nullable T data) {
        return new DlResponse<>(Status.OK.value, message, data);
    }

    /**
     * Creates an ok result with message only. (Default status is 200)
     *
     * @param message result message
     * @return ok result with message only
     */
    @NonNull
    public static <T> DlResponse<T> ok(@Nullable String message) {
        return ok(message, null);
    }

    /**
     * Creates an ok result with message only. (Default status is 200)
     *
     * @return ok result with success message
     */
    @NonNull
    public static <T> DlResponse<T> ok() {
        return ok("操作成功", null);
    }

    /**
     * Creates an ok result with data only. (Default message is OK, status is 200)
     *
     * @param data data to response
     * @param <T>  data type
     * @return base response with data
     */
    public static <T> DlResponse<T> ok(@NonNull T data) {
        return new DlResponse<>(Status.OK.value, "操作成功", data);
    }
}
