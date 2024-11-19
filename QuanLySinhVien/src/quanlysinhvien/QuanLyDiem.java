package quanlysinhvien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class QuanLyDiem extends JFrame implements ActionListener {
	private ArrayList<SinhVien> listSinhVien;
	private DefaultTableModel tableModel;
	private JTable table;
	private String[] columnNames = { "STT", "MSSV", "TÊN", "ĐIỂM THÀNH PHẦN", "ĐIỂM CUỐI KỲ" };
	private JButton btnSearch, btnUpdate, btnMenu;
	private JTextField textDiemTP, textDiemCK, textSearch;

	public QuanLyDiem(ArrayList<SinhVien> listSinhVien) {
		this.listSinhVien = listSinhVien;
		// search panel
		JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		btnMenu = new JButton("Menu");
		btnMenu.addActionListener(this);
		textSearch = new JTextField(30);
		btnSearch = new JButton("Tìm kiếm");
		btnSearch.addActionListener(this);
		panelSearch.add(btnMenu);
		panelSearch.add(textSearch);
		panelSearch.add(btnSearch);

		// table
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = table.getSelectedRow();
				textDiemTP.setText(String.format("%.2f", listSinhVien.get(selectRow).getDiemThanhPhan()));
				textDiemCK.setText(String.format("%.2f", listSinhVien.get(selectRow).getDiemCuoiKi()));
			}
		});

		// input panel
		JPanel panelInput = new JPanel(new GridLayout(4, 1));
		panelInput.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// panel diem thanh phan
		JPanel panelDiemTP = new JPanel();
		JLabel labelDiemTP = new JLabel("Điểm thành phần:");
		textDiemTP = new JTextField(6);
		panelDiemTP.add(labelDiemTP);
		panelDiemTP.add(textDiemTP);
		// panel diem cuoi ky
		JPanel panelDiemCK = new JPanel();
		JLabel labelDiemCK = new JLabel("Điểm cuối kỳ:    ");
		textDiemCK = new JTextField(6);
		panelDiemCK.add(labelDiemCK);
		panelDiemCK.add(textDiemCK);
		panelInput.add(panelDiemTP);
		panelInput.add(panelDiemCK);
		// panel button
		JPanel panelBtn = new JPanel();
		btnUpdate = new JButton("Cập nhật");
		btnUpdate.addActionListener(this);
		btnUpdate.setHorizontalAlignment(SwingConstants.CENTER);
		panelBtn.add(btnUpdate);
		panelInput.add(panelBtn);

		// setup jframe
		this.setLayout(new BorderLayout());
		this.add(panelSearch, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.add(panelInput, BorderLayout.EAST);
		this.setTitle("Quản lý sinh viên");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		readList(this.listSinhVien);
	}

	// Đọc danh sách và cho vào bảng
	public void readList(ArrayList<SinhVien> list) {
		String stt, mssv, name, diemTP, diemCK;
		tableModel.setRowCount(0);
		for(int i = 0; i < list.size(); i++) {
			stt = i + 1 + "";
			mssv = list.get(i).getMssv();
			name = list.get(i).getHoTen();
			diemTP = String.format("%.2f", list.get(i).getDiemThanhPhan());
			diemCK = String.format("%.2f", list.get(i).getDiemCuoiKi());
			tableModel.addRow(new Object[] { stt, mssv, name, diemTP, diemCK });
		}
	}

	// Cập nhật điểm cho sinh viên
	public void updateMark() {
		try {
			int selectRow = this.table.getSelectedRow();
			if(selectRow < 0) {
				JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 sinh viên.");
				return;
			}
			double diemTP = Double.parseDouble(textDiemTP.getText());
			double diemCK = Double.parseDouble(textDiemCK.getText());
			if(diemTP > 10.0 || diemTP < 0.0 || diemCK > 10.0 || diemCK < 0.0) {
				JOptionPane.showMessageDialog(null, "Điểm số phải trong khoảng 0.0 và 10.0");
			}else {
				this.listSinhVien.get(selectRow).updateMark(diemTP, diemCK);
				readList(this.listSinhVien);
				clearTextField();

			}
		}catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập điểm có dạng '#.##'");
		}
	}

	// Tìm kiếm thông tin bằng mã số sinh viên hoặc tên
	public void searchStudent() {
		String search = this.textSearch.getText();
		if(search.equals("")) {
			readList(this.listSinhVien);
		}else {
			ArrayList<SinhVien> searchList = new ArrayList<SinhVien>();
			for(SinhVien sv : this.listSinhVien) {
				if(sv.isSamePerson(search))
					searchList.add(sv);
			}
			readList(searchList);
		}
	}

	// Thay đổi các nơi điền input thành trạng thái ban đầu
	public void clearTextField() {
		this.textDiemTP.setText("");
		this.textDiemCK.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnMenu) {
			new Menu(this.listSinhVien);
			this.setVisible(false);
		}else if (e.getSource() == btnUpdate) {
			updateMark();
		}else if(e.getSource() == btnSearch) {
			searchStudent();
		}
	}

}
