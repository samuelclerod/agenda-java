package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import models.Contact;

public class MainWindow extends JFrame implements ActionListener {

	protected DefaultListModel listModel;
	protected JList list;
	protected JButton btnInsert, btnEdit, btnRemove, btnClose;

	public MainWindow() {
		setSize(400, 180);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		JScrollPane scroll = createList();
		JPanel buttons = createPanelButtons();
		JPanel sidePanel = new JPanel(new FlowLayout());
		sidePanel.add(buttons);
		add(scroll);
		add("East", sidePanel);
	}

	private JPanel createPanelButtons() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1, 5, 5));
		btnInsert = new JButton("Inserir");
		btnInsert.addActionListener(this);
		btnRemove = new JButton("Excluir");
		btnRemove.addActionListener(this);
		btnEdit = new JButton("Editar");
		btnEdit.addActionListener(this);
		btnClose = new JButton("Fechar");
		btnClose.addActionListener(this);
		panel.add(btnInsert);
		panel.add(btnEdit);
		panel.add(btnRemove);
		panel.add(btnClose);
		return panel;
	}

	private JScrollPane createList() {
		listModel = new DefaultListModel();
		list = new JList(listModel);
		return new JScrollPane(list);
	}

	private void editItem() {
		int index = list.getSelectedIndex(); 
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um item para editar");
			return;
		}
		Contact contato = (Contact) list.getSelectedValue();
		
		ContactDialog dialog = new ContactDialog(this, contato); 
		
		if(dialog.getContact() == null ) return ;
		
		listModel.setElementAt(dialog.getContact(), index);
		
		dialog.dispose();
	}

	private void removeItem() {
		int index = list.getSelectedIndex(); 
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um item para remover");
			return;
		}
		listModel.removeElementAt(index);
	}

	private void insertItem() {
		ContactDialog dialog = new ContactDialog(this, null);
		System.out.println("Fechou a Janela");
		Contact c = dialog.getContact();
		dialog.dispose();
		if(c==null) {
			return;
		}
		listModel.addElement(c);		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button == btnInsert) {
			insertItem();
			return;
		}
		if (button == btnRemove) {
			removeItem();
			return;
		}
		if (button == btnEdit) {
			editItem();
			return;
		}
		System.exit(0);
	}

	public static void main(String[] args) {
		JFrame window = new MainWindow();
		window.setVisible(true);
	}

}
