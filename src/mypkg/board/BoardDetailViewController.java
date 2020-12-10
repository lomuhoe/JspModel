package mypkg.board;

import mypkg.bean.Board;
import mypkg.bean.Member;
import mypkg.common.SuperClass;
import mypkg.dao.BoardDao;
import mypkg.utility.FlowParameters;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BoardDetailViewController extends SuperClass {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int no = Integer.parseInt(request.getParameter("no"));
        BoardDao dao = new BoardDao();
        Board bean = dao.SelectDataByPk(no);

        FlowParameters parameters = new FlowParameters(request.getParameter("pageNumber"), request.getParameter("pageSize"), request.getParameter("mode"), request.getParameter("keyword"));

        System.out.println(this.getClass() + " : " + parameters.toString());

        super.doGet(request, response);

        if(bean != null){
            Member login = (Member)super.session.getAttribute("loginfo");
            if(!bean.getWriter().equals(login.getId())){
                dao.UpdateReadhit(no);
            }
            request.setAttribute("bean", bean);
            request.setAttribute("parameters", parameters.toString());

            super.GotoPage("/board/boDetailView.jsp");
        } else {
            // 다시 목록페이지로 돌아가기. request와 response 객체가 그대로 다시 담겨서 넘어감.
            new BoardListController().doGet(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
    }
}
