package single.pool.version.a.common.executor.util;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ExceptionUtils {
    public static Throwable getRawThrowable(Throwable ex) {
        if (ex == null) {
            return null;
        } else if (ex instanceof ExecutionException) {
            ExecutionException iEx1 = (ExecutionException) ex;
            return iEx1.getCause();
        } else if (ex instanceof InvocationTargetException) {
            InvocationTargetException iEx = (InvocationTargetException) ex;
            return iEx.getTargetException();
        } else {
            return ex;
        }
    }

    public static String getCommonExceptionInfo(Throwable arg) {
        Throwable th = getRawThrowable(arg);
        if (th == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("class=").append(th.getClass().getSimpleName()).append(",");
            if (th.getMessage() != null) {
                sb.append("message=").append(th.getMessage()).append(",");
            }
            StackTraceElement[] arg5 = th.getStackTrace();
            if (arg5 != null && arg5.length != 0) {
                sb.append("1st stack=").append(arg5[0]);
                return sb.toString();
            } else {
                return sb.toString();
            }
        }
    }

    public static String getExFirstStackInfo(Exception ex) {
        Throwable th = getRawThrowable(ex);
        String clazz = th.getClass().getSimpleName();
        StackTraceElement[] traces = th.getStackTrace();
        return traces != null && traces.length != 0 ? clazz + ": " + traces[0].toString() : clazz;
    }

    public static String getSimpleString(Exception ex) {
        if (ex == null) {
            return null;
        } else {
            Throwable th = getRawThrowable(ex);
            ToStringBuilder tsb = null;
            tsb = (new ToStringBuilder(th, ToStringStyle.MULTI_LINE_STYLE))
                    .append("catalog", th.getClass().getSimpleName()).append("message", th.getMessage());
            if (ex.getCause() != null) {
                tsb.append("cause", ex.getCause().getMessage());
            }

            return tsb.toString();
        }
    }

    public static String getStackString(Exception ex) {
        if (ex == null) {
            return null;
        } else {
            Throwable th = getRawThrowable(ex);
            ToStringBuilder tsb = null;

            tsb = (new ToStringBuilder(th, ToStringStyle.MULTI_LINE_STYLE))
                    .append("catalog", th.getClass().getSimpleName()).append("message", th.getMessage());

            if (ex.getCause() != null) {
                String fullCause1 = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(ex.getCause());
                tsb.append("cause", fullCause1);
            }

            return tsb.toString();
        }
    }

    public static String getMessage(Throwable th) {
        Throwable rootCause = org.apache.commons.lang.exception.ExceptionUtils.getRootCause(th);
        String msg;
        if (rootCause != null) {
              msg = rootCause.getMessage();
           
        }else {
            msg=th.getMessage();
        }
        
        if (StringUtils.isEmpty(msg)) {
            msg = org.apache.commons.lang.exception.ExceptionUtils.getMessage(th);
        }
        
        if(msg.contains("Not found exported service")) {
            msg="远程服务未发现";
        }else if(msg.contains("com.alibaba.dubbo.rpc.RpcException: No provider available")) {
            msg="远程服务不可用";
        }else if(msg.contains("com.alibaba.dubbo")) {
            msg="远程调用发生错误";
        }else if(msg.contains("No provider available")) {
            msg="远程服务器未发现";
        }else if(msg.contains("net.doublecom.common.exception.DBException:")) {
            msg=msg.replace("net.doublecom.common.exception.DBException:", "");
        }else if(msg.contains("Failed to invoke the method invoke in the service")) {
            msg="远程服务调用失败";
        }else if(msg.contains("Please check registry access list (whitelist/blacklist)")) {
            msg="服务未注册,请检查后台进程是否启动";
        }else if(msg.contains("NoSuchBeanDefinitionException")){
            msg="Spring服务未注册";
        }else if(msg.contains("Waiting server-side response timeout by scan timer")) {
            msg="远程服务调用超时";
        }else if(msg.contains("Waiting server-side response timeout")) {
            msg="远程服务调用超时";
        }
        
        
        return msg;
    }

}