package mypkg.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypkg.common.SuperClass;

public class MemberLogoutController extends SuperClass{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession() ;
		// 로그인 정보를 세션 영역에서 지우기
		session.invalidate(); 
		
		String contextPath = request.getContextPath() ;
		String gotopage = "/member/meLoginForm.jsp" ; 
		response.sendRedirect(contextPath + gotopage); 
	}
}
