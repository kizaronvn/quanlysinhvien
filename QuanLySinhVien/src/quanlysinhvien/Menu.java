package quanlysinhvien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class Menu extends JFrame implements ActionListener {
	private ArrayList<SinhVien> listSinhVien;
	private JButton btnThongTin, btnDiem;

	public Menu() {
		this.listSinhVien = new ArrayList<>();
		init();
	}
	
	public Menu(ArrayList<SinhVien> listSinhVien) {
		this.listSinhVien = listSinhVien;
		init();
	}

	public void init() {
		// label text: Quản lý sinh viên
		JLabel label = new JLabel("QUẢN LÝ SINH VIÊN");
		label.setFont(new Font("Arial", Font.BOLD, 22));
		label.setForeground(Color.BLUE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		JPanel panelLabel = new JPanel(new BorderLayout());
		panelLabel.add(label, BorderLayout.CENTER);
		// button
		btnThongTin = new JButton("Quản lý thông tin");
		btnDiem = new JButton("Quản lý điểm");
		Dimension btnSize = new Dimension(180, 46);
		btnThongTin.setPreferredSize(btnSize);
		btnDiem.setPreferredSize(btnSize);
		btnThongTin.addActionListener(this);
		btnDiem.addActionListener(this);
		JPanel panelBtnTT = new JPanel();
		panelBtnTT.add(btnThongTin);
		JPanel panelBtnDiem = new JPanel();
		panelBtnDiem.add(btnDiem);

		this.setLayout(new GridLayout(3, 1));
		this.add(panelLabel);
		this.add(panelBtnTT);
		this.add(panelBtnDiem);
		this.setSize(400, 460);
		this.setTitle("Quan ly sinh vien");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnThongTin) {
			new QuanLyThongTin(this.listSinhVien);
			this.setVisible(false);
		}else if(e.getSource() == btnDiem) {
			new QuanLyDiem(this.listSinhVien);
			this.setVisible(false);
		}
	}
}
