package mypkg.common;

import mypkg.utility.Myutility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/Shopping"},
        initParams = {
                @WebInitParam(name="configFile", value="/WEB-INF/todolist.txt")
        })
public class FrontController extends HttpServlet {
    // todolist : 우리가 명시한 모든 작업 목록을 저장하고 있는 맵 구조
    private Map<String, SuperController> todolist
            = new HashMap<String, SuperController>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        String configFile = config.getInitParameter("configFile") ;
//        System.out.println("설정 파일 이름 : " + configFile);

        // 실제 웹 서버 상에 존재하는 설정 파일 이름
        String configFilePath = config.getServletContext().getRealPath(configFile);
        this.todolist = Myutility.getActionMapList(configFilePath) ;
    }
    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command") ;
        System.out.println("커맨드 파라미터 : " + command);

        if (command==null) {
            //파일 업로드시 여기에 들어옴.
        } else {
            SuperController controller = this.todolist.get(command) ;
            if(controller != null) {
                String method = request.getMethod().toLowerCase();
                if(method.equals("get")) {
                    System.out.println(controller.toString() + " get 호출됨");
                    controller.doGet(request, response);
                }else {
                    System.out.println(controller.toString() + " post 호출됨");
                    controller.doPost(request, response);
                }
            }else {
                System.out.println("command가 널입니다.");
            }

        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doProcess(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doProcess(request, response);
    }
}

