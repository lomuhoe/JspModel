package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mypkg.bean.Board;

public class BoardDao extends SuperDao {

	public List<Board> SelectDataList(int beginRow, int endRow, String mode, String keyword) {
		// 랭킹을 이용하여 해당 페이지의 데이터를 컬렉션으로 반환합니다.
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		int cnt = -99999 ;
		
		String sql = " select no, subject, writer, password, content, readhit, regdate, groupno, orderno, depth  " ;
		sql += " from ( " ;
		sql += " select no, subject, writer, password, content, readhit, regdate, groupno, orderno, depth,  " ;
		sql += " rank() over(order by no desc) as ranking " ;
		sql += " from boards " ;
		if(!mode.equalsIgnoreCase("all")){
			sql += " WHERE " + mode + " LIKE '" + keyword + "' ";
		}
		sql += " ) " ;
		sql += " where ranking between ?  and ?  " ;
		
		List<Board> lists = new ArrayList<Board>();

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			// placeholder
			pstmt.setInt(1, beginRow);
			pstmt.setInt(2, endRow);

			rs = pstmt.executeQuery() ;	
			
			while( rs.next() ){
				Board bean = new Board();
				bean.setContent(rs.getString("content"));
				bean.setDepth(rs.getInt("depth"));
				bean.setGroupno(rs.getInt("groupno"));
				bean.setNo(rs.getInt("no"));
				bean.setOrderno(rs.getInt("orderno"));
				bean.setPassword(rs.getString("password"));
				bean.setReadhit(rs.getInt("readhit"));
				bean.setRegdate(String.valueOf(rs.getDate("regdate")));
				bean.setSubject(rs.getString("subject"));
				bean.setWriter(rs.getString("writer"));

				lists.add( bean ) ;
			}
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if(rs != null){ rs.close(); }
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		return lists ;
	}

	public int SelectTotalCount(String mode, String keyword) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = "SELECT COUNT(*) as cnt FROM boards" ;
		if(!mode.equalsIgnoreCase("all")){
			sql += " WHERE " + mode + " like '" + keyword + "'";
		}
		int cnt = -999999 ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			// placeholder

			rs = pstmt.executeQuery() ;

			if ( rs.next() ) {
				cnt = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			SQLException err = (SQLException)e ;
			cnt = - err.getErrorCode() ;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( rs != null){ rs.close(); }
				if( pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt  ;
	}

	public int InsertData(Board bean){
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		int result = -1;

		String sql = "INSERT INTO boards(NO, SUBJECT, WRITER, PASSWORD, CONTENT, READHIT, REGDATE, GROUPNO, ORDERNO, DEPTH)" +
				" VALUES( myboard.NEXTVAL, ?, ?, ?, ?, DEFAULT, to_date(?, 'yyyy/MM/dd'), myboard.CURRVAL, DEFAULT, DEFAULT)" ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getWriter());
			pstmt.setString(3, bean.getPassword());
			pstmt.setString(4, bean.getContent());
			pstmt.setString(5, bean.getRegdate());

			result = pstmt.executeUpdate() ;

		} catch (SQLException e) {
			SQLException err = (SQLException)e ;
			result = - err.getErrorCode() ;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public Board SelectDataByPk(int no) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = " SELECT * FROM boards WHERE no = ?" ;
		Board bean = new Board();

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, no);

			rs = pstmt.executeQuery() ;

			while( rs.next() ){
				bean.setContent(rs.getString("content"));
				bean.setDepth(rs.getInt("depth"));
				bean.setGroupno(rs.getInt("groupno"));
				bean.setNo(rs.getInt("no"));
				bean.setOrderno(rs.getInt("orderno"));
				bean.setPassword(rs.getString("password"));
				bean.setReadhit(rs.getInt("readhit"));
				bean.setRegdate(String.valueOf(rs.getDate("regdate")));
				bean.setSubject(rs.getString("subject"));
				bean.setWriter(rs.getString("writer"));
			}
		} catch (Exception e) {
			SQLException err = (SQLException)e ;
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if(rs != null){ rs.close(); }
				if(pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return bean ;
	}

	public void UpdateReadhit(int no) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;

		String sql = "UPDATE boards SET readhit = readhit+1 WHERE no = ?" ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, no);

			pstmt.executeUpdate() ;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( pstmt != null){ pstmt.close(); }
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
