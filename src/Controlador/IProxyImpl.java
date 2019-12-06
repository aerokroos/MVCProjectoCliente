/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.LogIn;
import Vista.Principal;
import Vista.SelectDB;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alber
 */
public class IProxyImpl implements ActionListener {

    private Principal vista = new Principal();
    private LogIn login = new LogIn();
    private SelectDB selectdb = new SelectDB();
    public IProxy generador;
    DefaultTableModel dt;

    public IProxyImpl(Principal vista, LogIn login, SelectDB selectdb, IProxy generador) {
        this.generador = generador;
        this.vista = vista;
        this.login = login;
        this.selectdb = selectdb;

        //              USUARIOS
        this.vista.btn_registrarUsuario.addActionListener(this);
        this.vista.btn_Cambiar.addActionListener(this);
        this.vista.btn_CerrarSesion.addActionListener(this);
        this.vista.btn_ConsultaUsuarios.addActionListener(this);

        //              MAFIAS
        this.vista.btn_registrarMafia.addActionListener(this);
        this.vista.btn_consultarMafia.addActionListener(this);

        //              FAMILIAS
        this.vista.btn_registrarFamilia.addActionListener(this);
        this.vista.btn_consultarFamilia.addActionListener(this);

        //              MIEMBROS
        this.vista.btn_registrarMiembro.addActionListener(this);
        this.vista.btn_consultarMiembros.addActionListener(this);
        this.vista.CAMBIAR.addActionListener(this);

        //              JERARQUIAS
        this.vista.btn_ConsultarJerarquias.addActionListener(this);
        this.vista.btn_registrarJerarquia.addActionListener(this);

        //              TIPOS DE ASOCIACION
        this.vista.btn_insertarTipoDeAsosiacion.addActionListener(this);
        this.vista.btn_consultarTiposDeAsosiacion.addActionListener(this);

        //              MIEMBROS Y ASOCIACIONES
        this.vista.btn_RegistraMiembrosYAsociaciones.addActionListener(this);
        this.vista.btn_consultaMiembrosYAsociaciones.addActionListener(this);

        //              INFORMACIÓN DE MIEMBRO
        this.vista.btn_BuscarMiembro.addActionListener(this);

        //              TOTAL DE MIEMBROS EN UNA MAFIA
        this.vista.btn_vista2Buscar.addActionListener(this);

        //              FRAME LOGIN
        this.login.btn_inciarSesion.addActionListener(this);

        //              FRAME SELECTDB
        this.selectdb.btn_Seleccionar.addActionListener(this);

        this.selectdb.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        try {

            //      RADIO BOTONES PARA SELECCIONAR SGBD
            if (this.selectdb.btn_Seleccionar == ae.getSource()) {

                if (this.selectdb.rd_sqlServer.isSelected()) {

                    this.generador.eligeBaseDeDatos("SQLServer");

                    this.selectdb.setVisible(false);
                    this.login.setVisible(true);

                } else if (this.selectdb.rb_mySql.isSelected()) {

                    this.generador.eligeBaseDeDatos("MySQL");

                    this.selectdb.setVisible(false);
                    this.login.setVisible(true);

                } else {
                    System.out.println("Selecciona un gesto de base de datos.");
                }

            } //      BOTON PARA INICAR SESION
            else if (this.login.btn_inciarSesion == ae.getSource()) {

                String contrasena = new String(this.login.txtContrasena.getPassword());

                if (this.generador.inicarSesion(this.login.txtUsuario.getText(), contrasena) == true) {

                    //Icon icono = new ImageIcon(getClass().getResource("/Users/villalobos28/NetBeansProjects/MVCMafiaCliente/src/iconos/Login.png"));
                    JOptionPane.showMessageDialog(null, "Bienvenido " + this.login.txtUsuario.getText() + ".",
                            "Inicio de sesión.", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));

                    this.vista.setVisible(true);
                    this.login.setVisible(false);

                    this.login.txtUsuario.setText("");
                    this.login.txtContrasena.setText("");

                } else {
                    JOptionPane.showMessageDialog(null, "El usuario y/o contrasena no validos.",
                            "Inicio de sesión", JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }
            } else if (this.vista.btn_registrarUsuario == ae.getSource()) { // boton registrar usuario 

                String usuario = this.vista.txtregistrarUsuario.getText();
                String contra = new String(this.vista.txtregistraContrasena.getPassword());
                String correo = this.vista.txt_Correo.getText();

                List<String> errores = this.generador.validaRegistro(usuario, contra, correo);

                if (errores.isEmpty()) {

                    if (this.generador.insertarUsuario(usuario, contra, correo) == true) {

                        this.vista.txtregistrarUsuario.setText("");
                        this.vista.txtregistraContrasena.setText("");
                        this.vista.txt_Correo.setText("");

                        JOptionPane.showMessageDialog(null,
                                "Usuario registrado exitosamente",
                                "Registro",
                                JOptionPane.INFORMATION_MESSAGE,
                                new ImageIcon("src/iconos/Login.png"));
                    }

                } else {
                    String lista = "";
                    StringBuilder sb = new StringBuilder();
                    for (String error : errores) {
                        sb.append(error + "\n");
                    }
                    lista = sb.toString();
                    JOptionPane.showMessageDialog(null,
                            lista,
                            "Registro",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }

            } else if (this.vista.btn_registrarMafia == ae.getSource()) {   // Boton registra Mafia

                if (this.generador.insertarMafia(this.vista.txtDueno.getText(), this.vista.txtarea_actividades.getText(), this.vista.txtLema.getText()) == true) {

                    JOptionPane.showMessageDialog(null, "Mafia registrada", "Registrar mafia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));

                    this.vista.txtDueno.setText("");
                    this.vista.txtarea_actividades.setText("");
                    this.vista.txtLema.setText("");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error al registrar la mafia",
                            "Registrar mafia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }

            } else if (this.vista.btn_consultarMafia == ae.getSource()) {   // Boton ver mafias

                dt = this.generador.verMafias();
                this.vista.tabla_mafia.setModel(dt);

            } else if (this.vista.btn_registrarFamilia == ae.getSource()) { // Boton Regitrar Familia
                String Family_Name = this.vista.txt_Family_Name.getText();
                String Years_Active = this.vista.txt_Years_Active.getText();
                String Territory = this.vista.txt_Territory.getText();
                String Membership = this.vista.txt_Membership.getText();
                String Criminal_Activities = this.vista.txtarea_Criminal_Activities.getText();
                int id_mafia = Integer.parseInt(this.vista.txt_id_mafia.getText());
                if (this.generador.insertarFamilia(Family_Name, Years_Active, Territory, Membership, Criminal_Activities, id_mafia)) {

                    JOptionPane.showMessageDialog(vista, "Familia Registrada", "Registrar familia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));

                    this.vista.txt_Family_Name.setText("");
                    this.vista.txt_Years_Active.setText("");
                    this.vista.txt_Territory.setText("");
                    this.vista.txt_Membership.setText("");
                    this.vista.txtarea_Criminal_Activities.setText("");
                    this.vista.txt_id_mafia.setText("");

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error al registrar la familia",
                            "Registrar familia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }
            } else if (this.vista.btn_consultarFamilia == ae.getSource()) { // Boton ver Familias
                dt = this.generador.verFamilia();
                this.vista.tabla_familia.setModel(dt);

            } else if (this.vista.btn_registrarMiembro == ae.getSource()) { // Boton registra Miembro
                int Family_ID = Integer.parseInt(this.vista.txt_familiaid.getText());
                String Deceased = "";
                if (this.vista.comboboxMuerto.getSelectedItem().equals("Si")) {
                    Deceased = "1";
                } else if (this.vista.comboboxMuerto.getSelectedItem().equals("No")) {
                    Deceased = "0";
                }
                if (this.generador.insertarMiembro(this.vista.txt_Family_Name_Members.getText(),
                        this.vista.txt_Personal_Name.getText(),
                        this.vista.txt_DateJoinedClan.getText(),
                        this.vista.txt_DateOfBirth.getText(),
                        Deceased,
                        this.vista.txtarea_Skills.getText(),
                        Family_ID)) {
                    JOptionPane.showMessageDialog(null, "Miembro Registrado", "Registrar miembro",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));
                    this.vista.txt_Family_Name_Members.setText("");
                    this.vista.txt_Personal_Name.setText("");
                    this.vista.txt_DateJoinedClan.setText("");
                    this.vista.txt_DateOfBirth.setText("");
                    this.vista.txtarea_Skills.setText("");
                    this.vista.txt_familiaid.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se llevo a cabo el registro", "Registrar miembro",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }

            } else if (this.vista.btn_consultarMiembros == ae.getSource()) {    // Boton Consultar miembros
                dt = this.generador.verMiembros();
                this.vista.tabla_Members.setModel(dt);
            } else if (this.vista.btn_ConsultarJerarquias == ae.getSource()) {  //  BOTON CONSULTAR JERARQUIAS
                dt = this.generador.verJerarquias();
                this.vista.tabla_Jerarquias.setModel(dt);
            } else if (this.vista.btn_registrarJerarquia == ae.getSource()) {   //  BOTON REGISTRAR JERARQUIA
                int Family_ID = Integer.parseInt(this.vista.txt_Mafia_ID.getText());

                if (this.generador.insertaJerarquia(this.vista.txt_Level_Number.getText(), this.vista.txt_Entry_Name.getText(), Family_ID) == true) {
                    JOptionPane.showMessageDialog(null, "Jerarquia registrada.", "Registrar jerarquia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));
                    this.vista.txt_Mafia_ID.setText("");
                    this.vista.txt_Level_Number.setText("");
                    this.vista.txt_Entry_Name.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se llevo a cabo el registro", "Registrar jerarquia",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }
            } else if (this.vista.btn_insertarTipoDeAsosiacion == ae.getSource()) { // BOTON INSERTAR TIPO DE ASOCIACION
                if (this.generador.insertaTipoDeAsociacion(this.vista.txt_Name.getText(), this.vista.txt_Description.getText()) == true) {
                    JOptionPane.showMessageDialog(null, "Se registro el tipo de asociación.", "Registrar tipo de asociación",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));
                    this.vista.txt_Name.setText("");
                    this.vista.txt_Description.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se llevo a cabo el registro", "Registrar tipo de asociación",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }
            } else if (this.vista.btn_consultarTiposDeAsosiacion == ae.getSource()) {   //  BOTON CONSULTAR TIPOS DE ASOCIACION
                dt = this.generador.verTiposDeAsociacion();
                this.vista.tabla_AssociatonTypes.setModel(dt);
            } else if (this.vista.btn_RegistraMiembrosYAsociaciones == ae.getSource()) {    //  BOTON REGISTRAR MIEMBROS Y ASOCIACIONES
                int Member_ID = Integer.parseInt(this.vista.txt_Member_ID.getText());
                int AssociatonTypeCode = Integer.parseInt(this.vista.txt_AssociationTypeCode.getText());
                if (this.generador.insertaMiembros_Asociacion(Member_ID, AssociatonTypeCode) == true) {
                    JOptionPane.showMessageDialog(null, "Se registro el miembro asosiación.", "Registrar miembro asociación",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Login.png"));
                    this.vista.txt_Member_ID.setText("");
                    this.vista.txt_AssociationTypeCode.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "No se llevo a cabo el registro", "Registrar miembro asociación",
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("src/iconos/Error.png"));
                }
            } else if (this.vista.btn_consultaMiembrosYAsociaciones == ae.getSource()) {    //  BOTON CONSULTAR MIEMBROS Y ASOCIACIONES
                dt = this.generador.verMiembros_Asociacion();
                this.vista.tabla_MiembrosyAsociaciones.setModel(dt);
            } else if (this.vista.btn_BuscarMiembro == ae.getSource()) {        //  BOTON VISTA 
                int Member_ID = Integer.parseInt(this.vista.txt_MemberIDVista.getText());
                dt = this.generador.vista_MiembrosyAsociaciones(Member_ID);
                this.vista.tabla_vistaMiembro.setModel(dt);
            } else if (this.vista.btn_vista2Buscar == ae.getSource()) {     //  BOTON VISTA 
                int Mafia_ID = Integer.parseInt(this.vista.txt_MafiaIDVista.getText());
                dt = this.generador.vista_MiembrosDeMafia(Mafia_ID);
                this.vista.tabla_vistaMiembrosMafia.setModel(dt);
            } else if (this.vista.btn_Cambiar == ae.getSource()) {
                this.vista.setVisible(false);
                this.selectdb.setVisible(true);
            } else if (this.vista.btn_CerrarSesion == ae.getSource()) {
                this.vista.setVisible(false);
                this.login.setVisible(true);
            } else if (this.vista.btn_ConsultaUsuarios == ae.getSource()) {
                dt = this.generador.verUsuarios();
                this.vista.tabla_Usuarios.setModel(dt);
            } else if (this.vista.CAMBIAR == ae.getSource()) {
                int id_Miembro = Integer.parseInt(this.vista.txt_IDMiembro_Notificar.getText());
                if (this.generador.CambioMiembroEstadoVida(id_Miembro, "0") == true) {
//                    addObserver(this.vista.btn_CA);
//                    setChanged();
//                    notifyObservers(new Integer(id_Miembro));
                    System.out.println("Si");
                    this.vista.txt_IDMiembro_Notificar.setText("");
                } else {
                    System.out.println("No");
                }
            }
        } catch (HeadlessException | NumberFormatException e) {
        }
    }

}
