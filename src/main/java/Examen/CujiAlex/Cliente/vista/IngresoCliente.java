package Examen.CujiAlex.Cliente.vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Examen.CujiAlex.Servidor.modelo.Cliente;
import Examen.CujiAlex.Servidor.negocio.GestionClienteOnRemoto;
import io.undertow.security.impl.ClientCertAuthenticationMechanism;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JTextField;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IngresoCliente extends JFrame {

	private JPanel contentPane;
	private JTextField txtIdentificaion;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtFechaNaciemiento;
	private GestionClienteOnRemoto gestionClienteON;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					IngresoCliente frame = new IngresoCliente();
					frame.instanciarObjetoNegocio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void instanciarObjetoNegocio() throws Exception {
		try {
			final Hashtable<String, Comparable> jndiProperties = new Hashtable<String, Comparable>();
			jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
					"org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProperties.put("jboss.naming.client.ejb.context", true);

			jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			jndiProperties.put(Context.SECURITY_PRINCIPAL, "ejb");
			jndiProperties.put(Context.SECURITY_CREDENTIALS, "ejb01");

			final Context context = new InitialContext(jndiProperties);

			final String lookupName = "ejb:/ExamenCujiAlexServidor/GestionClienteON!Examen.CujiAlex.Servidor.negocio.GestionClienteOnRemoto";

			this.gestionClienteON = (GestionClienteOnRemoto) context.lookup(lookupName);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	/**
	 * Create the frame.
	 */
	public IngresoCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Identificaci\u00F3n CI/RUC:");
		lblNewLabel.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		lblNewLabel.setBounds(21, 22, 122, 36);
		contentPane.add(lblNewLabel);

		JLabel lblNombres = new JLabel("Nombres:");
		lblNombres.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		lblNombres.setBounds(82, 69, 61, 36);
		contentPane.add(lblNombres);

		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		lblApellidos.setBounds(82, 120, 61, 36);
		contentPane.add(lblApellidos);

		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento:");
		lblFechaNacimiento.setFont(new Font("Source Sans Pro", Font.BOLD, 12));
		lblFechaNacimiento.setBounds(41, 167, 102, 36);
		contentPane.add(lblFechaNacimiento);

		txtIdentificaion = new JTextField();
		txtIdentificaion.setBounds(142, 22, 197, 36);
		contentPane.add(txtIdentificaion);
		txtIdentificaion.setColumns(10);

		txtNombres = new JTextField();
		txtNombres.setColumns(10);
		txtNombres.setBounds(142, 69, 197, 36);
		contentPane.add(txtNombres);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(142, 116, 197, 36);
		contentPane.add(txtApellidos);

		txtFechaNaciemiento = new JTextField();
		txtFechaNaciemiento.setColumns(10);
		txtFechaNaciemiento.setBounds(142, 167, 197, 36);
		contentPane.add(txtFechaNaciemiento);

		JButton btnGuardar = new JButton("GUARDAR");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarCliente();
			}
		});
		btnGuardar.setBounds(142, 214, 89, 36);
		contentPane.add(btnGuardar);

		JButton btnCancelar = new JButton("CANCELAR");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancelar.setBounds(250, 214, 89, 36);
		contentPane.add(btnCancelar);
	}

	void guardarCliente() {
		Cliente cliente = new Cliente();
		if (txtIdentificaion.getText().equals("") || txtNombres.getText().equals("")
				|| txtApellidos.getText().equals("") || txtFechaNaciemiento.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Ingrese todos los datos");
		} else {
			cliente.setIdentificacion(txtIdentificaion.getText());
			cliente.setNombres(txtNombres.getText());
			cliente.setApellidos(txtApellidos.getText());
			cliente.setFechaNacimiento(txtFechaNaciemiento.getText());
			gestionClienteON.registrarPersona(cliente);
			JOptionPane.showMessageDialog(this, "Cliente Registrado Correctamente");
			limpiar();
		}
	}

	void limpiar() {
		txtIdentificaion.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		txtFechaNaciemiento.setText("");
	}
}
