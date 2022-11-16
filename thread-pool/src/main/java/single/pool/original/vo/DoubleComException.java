package single.pool.original.vo;

public class DoubleComException extends RuntimeException {

    public DoubleComException(String msg){
        new RuntimeException(msg);
    }
    public DoubleComException(Exception ex){
        new RuntimeException(ex);
    }

}
