package mypkg.member;

import mypkg.bean.Member;
import mypkg.board.BoardListController;
import mypkg.common.SuperClass;
import mypkg.dao.MemberDao;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberLoginController extends SuperClass {
    private String id = null;
    private String password = null;

    @Override
    public boolean validate(HttpServletRequest request) {
        boolean isCheck = true;

        if(this.id.length() < 4 || this.id.length() > 10){
            request.setAttribute(super.PREFIX + "id", "아이디는 4자리 이상 10자리 이하여야 합니다.");
            isCheck = false;
        }

        if(this.password.length() < 4 || this.password.length() > 10){
            request.setAttribute(super.PREFIX + "password", "비밀번호는 4자리 이상 10자리 이하여야 합니다.");
            isCheck = false;
        }

        return isCheck;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.id = request.getParameter("id") ;
        this.password = request.getParameter("password") ;

        String gotopage = "";

        if(this.validate(request) == false){
            gotopage = "/member/meLoginForm.jsp";
            request.setAttribute("id", this.id);
            request.setAttribute("password", this.password);
            super.doPost(request, response);
            super.GotoPage(gotopage);
        } else {
            MemberDao dao = new MemberDao();
            Member bean = dao.SelectData(id, password) ;

            if(bean == null) { // 로그인 실패
                gotopage = "member/meLoginForm.jsp";
                super.setErrorMessage("아이디 혹은 비밀번호가 잘못되었습니다.");
            }else { //로그인 성공
                //게시물 목록 보기 페이지
                gotopage = "common/main.jsp";
                super.session.setAttribute("loginfo", bean);
//                new BoardListController().doGet(request, response);
            }
            super.GotoPage(gotopage);
        }
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);

        String gotopage = "/member/meLoginForm.jsp";
        super.GotoPage(gotopage);
    }
}