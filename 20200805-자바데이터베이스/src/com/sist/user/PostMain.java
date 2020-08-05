package com.sist.user;

import com.sist.dao.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.*;

public class PostMain extends JFrame implements ActionListener{
	JTextField tf;
	JLabel la;
	DefaultTableModel model;
	JTable table;
	
	public PostMain() {
		tf = new JTextField(15);
		la = new JLabel("입력");
		
		String[] col = {"우편번호", "주소"};
		String[][] row = new String[0][2];
		
		model = new DefaultTableModel(row, col);
		table = new JTable(model);
		JScrollPane js = new JScrollPane(table);
		JPanel p = new JPanel();
		p.add(la);
		p.add(tf);
		add("North", p);
		add("Center", js);
		
		setSize(450, 500);
		setVisible(true);
		
		tf.addActionListener(this);
	}
	
	
	public static void main(String[] args) {
		new PostMain();

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == tf) {
			String dong = tf.getText();
			if(dong.length() < 1) {
				JOptionPane.showMessageDialog(this, "동/읍/면을 입력하세요");
			}
			// 처리 (목록 지우기)
			for(int i = model.getRowCount()-1; i >= 0; i--) {
				model.removeRow(i);
			}
			ZipcodeDAO dao = new ZipcodeDAO();
			ArrayList<ZipcodeVO> list = dao.postfind(dong);
			
			// 출력
			for(ZipcodeVO  vo : list) {
				String[] data = {
						vo.getZipcode(),
						vo.getAddress()
				};
				model.addRow(data);
			}
			
		}
		
	}

}
