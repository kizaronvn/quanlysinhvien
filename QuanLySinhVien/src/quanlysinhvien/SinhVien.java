package quanlysinhvien;

public class SinhVien {
	private String mssv;
	private String hoTen;
	private boolean gioiTinh;
	private String email;
	private double diemThanhPhan;
	private double diemCuoiKi;
	
	public SinhVien(String mssv, String hoTen, boolean gioiTinh, String email) {
		this.mssv = mssv;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.email = email;
		this.diemThanhPhan = 0.0;
		this.diemCuoiKi = 0.0;
	}

	public String getMssv() {
		return mssv;
	}

	public String getHoTen() {
		return hoTen;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public String getEmail() {
		return email;
	}

	public double getDiemThanhPhan() {
		return diemThanhPhan;
	}

	public double getDiemCuoiKi() {
		return diemCuoiKi;
	}

	public void setDiemThanhPhan(double diemThanhPhan) {
		this.diemThanhPhan = diemThanhPhan;
	}

	public void setDiemCuoiKi(double diemCuoiKi) {
		this.diemCuoiKi = diemCuoiKi;
	}
	
	public void updateMark(double diemThanhPhan, double diemCuoiKi) {
		this.diemThanhPhan = diemThanhPhan;
		this.diemCuoiKi = diemCuoiKi;
	}
	
	//Kiểm tra mã số sinh viên hoặc tên của sinh viên (hỗ trợ việc tìm kiếm của các class khác) 
	public boolean isSamePerson(String infor) {
		String s = this.mssv + " " + this.hoTen;
		return s.toLowerCase().contains(infor.toLowerCase());
	}
}
