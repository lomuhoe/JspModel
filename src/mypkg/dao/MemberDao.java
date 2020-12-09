package mypkg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mypkg.bean.Member;

public class MemberDao extends SuperDao{
	public MemberDao() {}

	//아이디와 비번을 사용하여 해당 회원이 존재하나요?
	public Member SelectData(String id, String password) {
		Member bean = null ;
		
		String sql = "select * from members where id = ? and password = ? ";
		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		
		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setString(1, id);
			pstmt.setString(2, password);
						
			rs = pstmt.executeQuery() ;
			
			if(rs.next()) { //해당 사용자 발견됨
				bean = new Member() ;
				bean.setAddress1(rs.getString("address1"));
				bean.setAddress2(rs.getString("address2"));
				bean.setGender(rs.getString("gender"));				
				bean.setHobby(rs.getString("hobby"));
				bean.setId(rs.getString("id"));
				bean.setJob(rs.getString("job"));				
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));				
				bean.setZipcode(rs.getString("zipcode"));
				
				//날짜
				String mydate = String.valueOf(rs.getDate("hiredate")) ;
				
				bean.setHiredate(mydate);
				
				//숫자
				bean.setSalary(rs.getInt("salary"));
				bean.setMpoint(rs.getInt("mpoint"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {rs.close();}
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return bean  ;
	}

	public Member SelectDataByPk(String id){
		// 아이디 정보를 이용하여 회원을 찾아 줍니다.
		Member bean = null ;

		String sql = "select * from members where id = ? ";

		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			pstmt.setString(1, id);

			rs = pstmt.executeQuery() ;

			if(rs.next()) { //해당 사용자 발견됨
				bean = new Member() ;
				bean.setAddress1(rs.getString("address1"));
				bean.setAddress2(rs.getString("address2"));
				bean.setGender(rs.getString("gender"));
				bean.setHobby(rs.getString("hobby"));
				bean.setId(rs.getString("id"));
				bean.setJob(rs.getString("job"));
				bean.setName(rs.getString("name"));
				bean.setPassword(rs.getString("password"));
				bean.setZipcode(rs.getString("zipcode"));

				//날짜
				String mydate = String.valueOf(rs.getDate("hiredate")) ;

				bean.setHiredate(mydate);

				//숫자
				bean.setSalary(rs.getInt("salary"));
				bean.setMpoint(rs.getInt("mpoint"));
			}

			System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {rs.close();}
				if(pstmt != null) {pstmt.close();}
				if(conn != null) {conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return bean  ;
	}

	public int UpdateData(Member bean) {
		// 회원 정보를 수정합니다.
		String sql = " update members set ";
		sql += " name = ?, ";
		sql += " password = ?, ";
		sql += " salary = ?, ";
		sql += " hiredate = ?, ";
		sql += " gender = ?, ";
		sql += " hobby = ?, ";
		sql += " job = ?, ";
		sql += " zipcode = ?, ";
		sql += " address1 = ?, ";
		sql += " address2 = ?, ";
		sql += " mpoint = ? ";
		sql += " where id = ?  ";

		Connection conn = null ;
		PreparedStatement pstmt = null ;
		int cnt = -999999 ;

		try {
			conn = super.getConnection() ;
			pstmt = conn.prepareStatement(sql) ;

			// placeholder
			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getPassword());
			pstmt.setInt(3, bean.getSalary());
			pstmt.setString(4, bean.getHiredate());
			pstmt.setString(5, bean.getGender());
			pstmt.setString(6, bean.getHobby());
			pstmt.setString(7, bean.getJob());
			pstmt.setString(8, bean.getZipcode());
			pstmt.setString(9, bean.getAddress1());
			pstmt.setString(10, bean.getAddress2());
			pstmt.setInt(11, bean.getMpoint());
			pstmt.setString(12, bean.getId());

			cnt = pstmt.executeUpdate() ;
			conn.commit();

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
				if(pstmt != null){pstmt.close();}
				if(conn != null){conn.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}
}






