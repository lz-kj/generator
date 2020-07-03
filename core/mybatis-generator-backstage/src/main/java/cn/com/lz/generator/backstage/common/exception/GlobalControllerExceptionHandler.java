package cn.com.lz.generator.backstage.common.exception;

import cn.com.lz.generator.mysql.plugins.base.common.R;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * The type Global controller exception handler.
 *
 * @Author : Houds
 * @Description : 全局异常处理
 * @Date: Created by 下午4:07 on 2018/3/14.
 * @Modified By :
 */
@RestControllerAdvice
public class GlobalControllerExceptionHandler {

  /**
   * Handle conflict r.
   *
   * @param ex the ex
   * @return the r
   */
  @ExceptionHandler(ResultException.class)
  public R handleResultException(ResultException ex) {
    return R.error(new Date(), ex.getStatus(), ex.getMessage());
  }

  /**
   * Valid exception handler r.
   *
   * @param e the e
   * @return the r
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public R validExceptionHandler(MethodArgumentNotValidException e) {
//    FieldError fieldError = (FieldError) e.getBindingResult().getAllErrors().iterator().next();
//    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), fieldError.getField() + fieldError.getDefaultMessage());
    String errorMsg = e.getBindingResult().getAllErrors().iterator().next().getDefaultMessage();
    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), errorMsg);
  }

  /**
   * Handle sql exception r.
   *
   * @param e the e
   * @return the r
   */
  @ExceptionHandler(SQLException.class)
  public R handleSQLException(SQLException e) {
    return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
  }

  /**
   * Handle invalid format exception r.
   *
   * @param e the e
   * @return the r
   */
  @ExceptionHandler(InvalidFormatException.class)
  public R handleInvalidFormatException(InvalidFormatException e) {
    List<JsonMappingException.Reference> path = e.getPath();
//    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), path.get(path.size()-1).getFieldName() + "数据格式错误");
   return R.error(HttpStatus.NOT_ACCEPTABLE.value(),  "数据格式错误");
  }


  /**
   * Handle illegal argument exception r.
   *
   * @param e the e
   * @return the r
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public R handleIllegalArgumentException(IllegalArgumentException e) {
    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
  }

  /**
   * Handle illegal argument exception r.
   *
   * @param e the e
   * @return the r
   */
  @ExceptionHandler(ValidationException.class)
  public R handleValidationException(ValidationException e) {
    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
  }

  @ExceptionHandler(MultipartException.class)
  public R handleMultipartException(MultipartException e) {
    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
  }

  @ExceptionHandler(BindException.class)
  public R handleBindException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors().iterator().next().getDefaultMessage();
    return R.error(HttpStatus.NOT_ACCEPTABLE.value(), errorMsg);
  }
}
