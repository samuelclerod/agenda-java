package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Contact;

public class ContactDialog extends JDialog implements ActionListener {

	private Contact contact;

	private JButton btnAction, btnCancel;

	private JTextField tfFirstName, tfLastName, tfPhone, tfEmail;

	public ContactDialog(JFrame owner, Contact contact) {
		super(owner, true);
		setLocationRelativeTo(owner);

		tfFirstName = new JTextField(15);
		tfLastName = new JTextField(15);
		tfPhone = new JTextField(15);
		tfEmail = new JTextField(15);

		if (contact != null) {
			this.contact = contact;
			btnAction = new JButton("Editar");
			setTitle("Editar Contato");

			tfFirstName.setText(contact.getFirstName());
			tfLastName.setText(contact.getLastName());
			tfPhone.setText(contact.getPhone());
			tfEmail.setText(contact.getEmail());
		} else {
			this.contact = new Contact();
			btnAction = new JButton("Adicionar");
			setTitle("Adicionar Contato");
		}
		setSize(275, 180);
		setLayout(new BorderLayout());

		JPanel panelFields = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelFields.add(new JLabel("Nome"));
		panelFields.add(tfFirstName);
		panelFields.add(new JLabel("Sobrenome"));
		panelFields.add(tfLastName);
		panelFields.add(new JLabel("Telefone"));
		panelFields.add(tfPhone);
		panelFields.add(new JLabel("E-mail"));
		panelFields.add(tfEmail);

		JPanel panelButtons = new JPanel(new FlowLayout());
		btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(this);
		btnAction.addActionListener(this);
		panelButtons.add(btnAction);
		panelButtons.add(btnCancel);

		add(panelFields);
		add("South", panelButtons);

		setVisible(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	public Contact getContact() {
		return contact;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String firstName = tfFirstName.getText().trim();
		String lastName = tfLastName.getText().trim();
		String phone = tfPhone.getText().trim();
		String email = tfEmail.getText().trim();

		
		if (e.getSource() == btnCancel) {
			contact = null;
		} else {
			if (firstName.equals("") || lastName.equals("")) {
				JOptionPane.showMessageDialog(this, "Prencha o nome e sobrenome do contato");
				return;
			}
			
			if(!email.equals("")&& !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
				JOptionPane.showMessageDialog(this, "E-mail inválido");
				return;
			}
			
			contact.setFirstName(firstName);
			contact.setLastName(lastName);
			contact.setPhone(phone);
			contact.setEmail(email);
		}
		setVisible(false);

	}

}
