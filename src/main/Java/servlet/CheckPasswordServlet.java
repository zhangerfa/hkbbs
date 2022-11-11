package servlet;

import pojo.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
验证密码和验证码
 */
// 密码只接受 POST 请求
@WebServlet("/checkPasswordServlet")
public class CheckPasswordServlet extends HttpServlet {
    private UserService userService = new UserService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String stuId = req.getParameter("stuId");

        // 判断密码是否相同
        User user = userService.selectUserById(stuId);
        boolean flag = user!=null? user.getPassword().equals(password): false;
        resp.getWriter().write(flag+"");
    }
}
