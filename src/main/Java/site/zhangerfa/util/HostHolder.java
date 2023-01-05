package site.zhangerfa.util;

import site.zhangerfa.pojo.User;

// 使用ThreadLocal实现每个线程有一个在服务器本地的空间
// 对于每个客户端会话服务器使用一个单独线程处理
// 所以一个会话就有一个ThreadLocal的共享数据的空间
// 底层基于Map实现，key就是线程对象，值是该线程要共享的数据
// 相当于自己创建的session
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    /**
     * 清除共享数据
     */
    public void clear(){
        users.remove();
    }
}
