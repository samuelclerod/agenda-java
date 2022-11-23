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
import persistence.ContactRepository;
import persistence.ContactRepositorySQLite;

public class MainWindow extends JFrame implements ActionListener {

	protected DefaultListModel listModel;
	protected JList list;
	protected JButton btnInsert, btnEdit, btnRemove, btnClose;
	private ContactRepository repository;
 
	public MainWindow() {
		repository = new ContactRepositorySQLite();
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
		populate();
		list = new JList(listModel);
		return new JScrollPane(list);
	}

	private void populate() {
		Contact[] contacts = repository.findAll();
		listModel.removeAllElements();
		for (Contact c : contacts) {
			listModel.addElement(c);
		}
	}

	private void editItem() {
		int index = list.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um item para editar");
			return;
		}
		Contact contato = (Contact) list.getSelectedValue();
		ContactDialog dialog = new ContactDialog(this, contato);
		if (dialog.getContact() == null)
			return;
		listModel.setElementAt(dialog.getContact(), index);
		dialog.dispose();
	}

	private void removeItem() {
		int index = list.getSelectedIndex();
		if (index < 0) {
			JOptionPane.showMessageDialog(this, "Selecione um item para remover");
			return;
		}
		Contact c = (Contact) list.getSelectedValue();
		int response = JOptionPane.showConfirmDialog(
				this, 
				"Tem certeza que quer apagar esse contato?",
				"Remover Contato",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE
		);
		if(response==JOptionPane.YES_OPTION) {			
			repository.remove(c.getId());
			listModel.removeElementAt(index);
		}
	}

	private void insertItem() {
		ContactDialog dialog = new ContactDialog(this, null);
		Contact c = dialog.getContact();
		dialog.dispose();
		if (c == null) {
			return;
		}
		repository.insert(c);
		populate();
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
