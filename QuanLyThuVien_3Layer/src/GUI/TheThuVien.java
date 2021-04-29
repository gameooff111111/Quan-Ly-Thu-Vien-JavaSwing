package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DataBindingException;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import BUS.ChiTietPhieuNhapBUS;
import BUS.TheThuVienBUS;
import DAL.TheThuVienDAL;
import DTO.ChiTietPhieuNhap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class TheThuVien extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel dtmthetv;
	private JTextField txtmathe;
	private JTextField txtmadocgia;
	private JTextField txttendocgia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TheThuVien frame = new TheThuVien();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TheThuVien() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 717, 634);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = 
				new JScrollPane();
		scrollPane.setBounds(12, 342, 688, 232);
		contentPane.add(scrollPane);
		dtmthetv = new DefaultTableModel();
		dtmthetv.addColumn("Mã Thẻ Thư Viện");
		dtmthetv.addColumn("Tên Đọc Giả");
		dtmthetv.addColumn("Mã Đọc Giả");
		dtmthetv.addColumn("Ngày Bắt Đầu");
		dtmthetv.addColumn("Ngày Kết Thúc");
		
		
		table = new MyTable(dtmthetv);
		scrollPane.setViewportView(table);
		
		JButton btnNewButton = new JButton("Sửa");
		btnNewButton.setBounds(208, 304, 97, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Xoá");
		btnNewButton_1.setBounds(341, 304, 97, 25);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Gia Hạn");
		btnNewButton_1_1.setBounds(470, 304, 97, 25);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Tải Lại");
		btnNewButton_1_2.setBounds(603, 304, 97, 25);
		contentPane.add(btnNewButton_1_2);
		
		JLabel lblNewLabel = new JLabel("Thẻ Thư Viện" ,SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 13, 688, 41);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Mã Thẻ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(12, 67, 69, 32);
		contentPane.add(lblNewLabel_1);
		
		txtmathe = new JTextField();
		txtmathe.setBounds(105, 62, 178, 37);
		contentPane.add(txtmathe);
		txtmathe.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mã đọc giả");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(12, 142, 97, 32);
		contentPane.add(lblNewLabel_1_1);
		
		txtmadocgia = new JTextField();
		txtmadocgia.setColumns(10);
		txtmadocgia.setBounds(105, 141, 178, 37);
		contentPane.add(txtmadocgia);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Ngày Bắt Đầu");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(402, 67, 120, 32);
		contentPane.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Ngày Bắt Đầu");
		lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1_1.setBounds(402, 142, 120, 32);
		contentPane.add(lblNewLabel_1_1_1_1);
		
		JDateChooser ngaybatdau = new JDateChooser();
		ngaybatdau.setDateFormatString("yyyy-MM-dd");
		ngaybatdau.setBounds(534, 67, 153, 32);
		contentPane.add(ngaybatdau);
		
		JDateChooser ngayketthuc = new JDateChooser();
		ngayketthuc.setDateFormatString("yyyy-MM-dd");
		ngayketthuc.setBounds(534, 142, 153, 32);
		contentPane.add(ngayketthuc);
		
		JButton btnThm = new JButton("Thêm");
		btnThm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int ma = Integer.parseInt(txtmathe.getText()); 
				int madocgia = Integer.parseInt(txtmadocgia.getText()) ;
				String tendoc = txttendocgia.getText() ;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				Date date = ngaybatdau.getDate();
				String ngaybatdau = sdf.format(date); 
				Date date2 = ngayketthuc.getDate();
				String ketthuc = sdf.format(date2);
				DTO.TheThuVien the = new DTO.TheThuVien(ma,madocgia,tendoc,ngaybatdau,ketthuc); 
				if(TheThuVienBUS.gI().themtacgia(the) > 0 ) {
					JOptionPane.showMessageDialog(null, "Thêm Thẻ thành công");
					loadctphieunhap();
				}
				else {
					JOptionPane.showMessageDialog(null, "Thêm thẻ thất bại");
				}
				
				
				
			}
		});
		btnThm.setBounds(79, 304, 97, 25);
		contentPane.add(btnThm);
		
		txttendocgia = new JTextField();
		txttendocgia.setColumns(10);
		txttendocgia.setBounds(105, 206, 178, 37);
		contentPane.add(txttendocgia);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Tên Đọc Giả");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_2.setBounds(12, 207, 97, 32);
		contentPane.add(lblNewLabel_1_1_2);
		setLocationRelativeTo(null);
		DTO.TheThuVien the = MainFrame.tv;
		
		if(the.getMaTheThuVien() == 0) {
			JOptionPane.showMessageDialog(null, "Chưa có thẻ");
			return; 
		}
		if(the!= null) {			
			
			try {
				txtmadocgia.setText(String.valueOf(the.getMadocgia()));
				txtmathe.setText(String.valueOf(the.getMaTheThuVien()));
				txttendocgia.setText(the.getTendocgia());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
				Date date = sdf.parse(the.getNgayBatDau());
				Date date2 = sdf.parse(the.getNgayKetThuc());
				ngaybatdau.setDate(date);
				ngayketthuc.setDate(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "Chưa có thẻ thư viện");
			} 
			
			
		}
		loadctphieunhap();
		
		
	}
	public static ArrayList<DTO.TheThuVien> thetv = new ArrayList<DTO.TheThuVien>();

	public void loadctphieunhap() {
		thetv = null;
		
		thetv = TheThuVienDAL.getdanhsachthongtinthethuvien();
		dtmthetv.setRowCount(0);

		for (DTO.TheThuVien pn : thetv) {
			System.out.println(pn);
			Vector<Object> vec = new Vector<Object>();
			vec.add(pn.getMaTheThuVien());
			vec.add(pn.getMadocgia());
			vec.add(pn.getTendocgia());
			vec.add(pn.getNgayBatDau());
			vec.add(pn.getNgayKetThuc());
			

			dtmthetv.addRow(vec);

			// dtmctpm.addRow(vec);
		}
	}
}
