package mypkg.bean;

public class Order {
	private int oid ;
	private String mid ;
	private String orderdate ;
	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", mid=" + mid + ", orderdate=" + orderdate + "]";
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOrderdate() {
		return orderdate;
	}
	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}
		
}