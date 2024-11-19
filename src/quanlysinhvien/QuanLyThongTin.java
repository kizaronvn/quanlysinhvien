package quanlysinhvien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class QuanLyThongTin extends JFrame implements ActionListener {
	private ArrayList<SinhVien> listSinhVien;
	private JButton btnAdd, btnDelete, btnSearch, btnUpdate, btnMenu;
	private JTextField textId, textName, textEmail, textSearch;
	private JRadioButton btnMale, btnFemale;
	private DefaultTableModel tableModel;
	private JTable table;
	private String[] columnNames = { "STT", "MSSV", "TÊN", "GIỚI TÍNH", "EMAIL" };

	public QuanLyThongTin(ArrayList<SinhVien> listSinhVien) {
		this.listSinhVien = listSinhVien;
		// search panel
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
		btnMenu = new JButton("Menu");
		btnMenu.addActionListener(this);
		textSearch = new JTextField(30);
		btnSearch = new JButton("Tìm kiếm");
		btnSearch.addActionListener(this);
		searchPanel.add(btnMenu);
		searchPanel.add(textSearch);
		searchPanel.add(btnSearch);

		// input and btn panel
		JPanel inputPanel = new JPanel(new GridLayout(6, 1));
		//panel id
		JPanel idPanel = new JPanel(new FlowLayout());
		JLabel labelId = new JLabel("Mssv: ");
		textId = new JTextField(15);
		idPanel.add(labelId);
		idPanel.add(textId);
		inputPanel.add(idPanel);
		//panel name
		JPanel namePanel = new JPanel(new FlowLayout());
		JLabel labelName = new JLabel("Tên:  ");
		textName = new JTextField(15);
		namePanel.add(labelName);
		namePanel.add(textName);
		inputPanel.add(namePanel);
		//panel gender
		JPanel genderPanel = new JPanel(new FlowLayout());
		JLabel labelGender = new JLabel("Giới tính:");
		btnMale = new JRadioButton("Nam");
		btnFemale = new JRadioButton("Nữ");
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(btnMale);
		btnGroup.add(btnFemale);
		genderPanel.add(labelGender);
		genderPanel.add(btnMale);
		genderPanel.add(btnFemale);
		inputPanel.add(genderPanel);
		//panel email
		JPanel emailPanel = new JPanel(new FlowLayout());
		JLabel labelEmail = new JLabel("Email: ");
		textEmail = new JTextField(15);
		emailPanel.add(labelEmail);
		emailPanel.add(textEmail);
		inputPanel.add(emailPanel);
		inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// btn panel
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		btnAdd = new JButton("Thêm");
		btnAdd.addActionListener(this);
		btnUpdate = new JButton("Cập nhật");
		btnUpdate.addActionListener(this);
		btnDelete = new JButton("Xoá");
		btnDelete.addActionListener(this);
		btnPanel.add(btnAdd);
		btnPanel.add(btnUpdate);
		btnPanel.add(btnDelete);
		inputPanel.add(btnPanel);

		// table
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectRow = table.getSelectedRow();
				SinhVien sv = listSinhVien.get(selectRow);
				textId.setText(sv.getMssv());
				textName.setText(sv.getHoTen());
				if (sv.isGioiTinh()) {
					btnMale.setSelected(true);
				} else {
					btnFemale.setSelected(true);
				}
				textEmail.setText(sv.getEmail());
			}
		});

		// setup jframe
		this.setLayout(new BorderLayout());
		this.add(searchPanel, BorderLayout.NORTH);
		this.add(new JScrollPane(table), BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.EAST);
		this.setTitle("Quản lý sinh viên");
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		readList(this.listSinhVien);
	}

	// Đọc danh sách và thêm vào bảng
	public void readList(ArrayList<SinhVien> list) {
		String stt, mssv, name, gender, email;
		this.sortListOfStudents();
		tableModel.setRowCount(0);
		for(int i = 0; i < list.size(); i++) {
			stt = i + 1 + "";
			mssv = list.get(i).getMssv();
			name = list.get(i).getHoTen();
			gender = (list.get(i).isGioiTinh()) ? "Nam" : "Nữ";
			email = list.get(i).getEmail();
			tableModel.addRow(new Object[] { stt, mssv, name, gender, email });
		}
	}

	// Sắp xếp lại sinh viên theo mã số sinh viên
	public void sortListOfStudents() {
		Comparator<SinhVien> comp = new Comparator<>() {
			@Override
			public int compare(SinhVien o1, SinhVien o2) {
				try {
					//Trường hợp mã số sinh viên chỉ toàn là số
					return Integer.parseInt(o1.getMssv()) - Integer.parseInt(o2.getMssv());
				}catch(NumberFormatException e) {
					//Trường hợp mã số sinh viên có kí tự khác ngoài số
					return o1.getMssv().compareTo(o2.getMssv());
				}
			}
		};
		this.listSinhVien.sort(comp);
	}

	// Thay đổi các nơi điền input thành trạng thái ban đầu
	public void clearTextField() {
		this.textId.setText("");
		this.textName.setText("");
		this.btnMale.setSelected(false);
		this.btnFemale.setSelected(false);
		this.textEmail.setText("");
	}

	// Thêm 1 sinh viên vào danh sách (button thêm)
	public void addStudent() {
		// Kiểm tra các input
		if (this.textId.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập mã số sinh viên.");
		} else if (this.textName.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập tên sinh viên.");
		} else if (!this.btnMale.isSelected() && !this.btnFemale.isSelected()) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập giới tính sinh viên.");
		} else if (this.textEmail.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Bạn chưa nhập email sinh viên.");
		} else {
			String mssv, name, email;
			boolean gender;
			mssv = this.textId.getText();
			name = this.textName.getText();
			gender = this.btnMale.isSelected();
			email = this.textEmail.getText();
			this.listSinhVien.add(new SinhVien(mssv, name, gender, email));
			readList(this.listSinhVien);
			clearTextField();
		}
	}

	// Xoá 1 sinh viên khỏi danh sách (button xoá)
	public void deleteStudent() {
		int selectRow = this.table.getSelectedRow();
		if (selectRow == -1) {
			JOptionPane.showMessageDialog(null, "Vui lòng chọn 1 sinh viên để xoá.");
		} else {
			this.listSinhVien.remove(selectRow);
			readList(this.listSinhVien);
			clearTextField();
		}
	}

	// Cập nhật thông tin của 1 sinh viên (button cập nhật)
	public void updateInforStudent() {
		int selectRow = this.table.getSelectedRow();
		String mssv, name, email;
		boolean gender;
		mssv = this.textId.getText();
		name = this.textName.getText();
		gender = this.btnMale.isSelected();
		email = this.textEmail.getText();
		this.listSinhVien.set(selectRow, new SinhVien(mssv, name, gender, email));
		readList(this.listSinhVien);
		clearTextField();
	}

	// Tìm kiếm thông tin bằng mã số sinh viên hoặc tên
	public void searchStudent() {
		String search = this.textSearch.getText();
		if (search.equals("")) {
			readList(this.listSinhVien);
		} else {
			ArrayList<SinhVien> searchList = new ArrayList<SinhVien>();
			for (SinhVien sv : this.listSinhVien) {
				if (sv.isSamePerson(search))
					searchList.add(sv);
			}
			readList(searchList);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnMenu) {
			new Menu(this.listSinhVien);
			this.setVisible(false);
		} else if (e.getSource() == btnAdd) {
			addStudent();
		} else if (e.getSource() == btnDelete) {
			deleteStudent();
		} else if (e.getSource() == btnUpdate) {
			updateInforStudent();
		} else if (e.getSource() == btnSearch) {
			searchStudent();
		}
	}
}
