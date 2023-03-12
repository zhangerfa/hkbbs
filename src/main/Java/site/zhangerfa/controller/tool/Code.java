package site.zhangerfa.controller.tool;

public class Code {
    public static final int SAVE_OK = 20011;
    public static final int DELETE_OK = 20021;
    public static final int UPDATE_OK = 20031;
    public static final int GET_OK = 20041;

    public static final int SAVE_ERR = 20010;
    public static final int DELETE_ERR = 20020;
    public static final int UPDATE_ERR = 20030;
    public static final int GET_ERR = 20040;

    public static final int SYSTEM_EXCEPTION = 40000; // 系统异常
    public static final int BUSINESS_EXCEPTION = 50000; // 业务异常(用户引起的异常)
    public static final int SYSTEM_UNKNOW_EXCEPTION = 60000; // 后端bug引起的异常

}
