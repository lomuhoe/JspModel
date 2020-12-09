package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mypkg.bean.Board;

public class BoardDao extends SuperDao {

	public List<Board> SelectDataList(int beginRow, int endRow) {
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

}
