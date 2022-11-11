package servlet;

import com.alibaba.fastjson2.JSON;
import pojo.Card;
import service.CardService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/*
查询一页的cards 并以json返回
 */
@WebServlet("/selectCardsServlet")
public class SelectCardsServlet extends HttpServlet {
    private CardService cardService = new CardService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Card[] cards = cardService.selectOnePageCards();
        // 设置响应数据的编码方式
        resp.setContentType("text/json;charset=UTF-8");
        String res = new String(JSON.toJSONString(cards).getBytes(), "utf-8");

        resp.getWriter().write(res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
