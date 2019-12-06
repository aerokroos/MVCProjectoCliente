/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alber
 */
public interface IProxy {

    public void eligeBaseDeDatos(String manejador);

    public boolean inicarSesion(String usuario, String contrasena);

    public boolean insertarUsuario(String Username, String Password, String Email);

    public boolean insertarMafia(String dueno, String actividades, String lema);

    public DefaultTableModel verMafias();

    public boolean insertarFamilia(String Family_Name, String Years_Active, String Territory, String Membership, String Criminal_Activities, int Mafia_ID);

    public DefaultTableModel verFamilia();

    public boolean insertarMiembro(String Family_Name, String Personal_Name, String DateJoinedClan, String DateOfBirth, String Deceased, String Skills, int Family_ID);

    public DefaultTableModel verMiembros();

    public DefaultTableModel verJerarquias();

    public boolean insertaJerarquia(String Level_Number, String Entry_Name, int Mafia_ID);

    public boolean insertaTipoDeAsociacion(String Name, String Description);

    public DefaultTableModel verTiposDeAsociacion();

    public boolean insertaMiembros_Asociacion(int Member_ID, int AssociatonTypeCode);

    public DefaultTableModel verMiembros_Asociacion();

    public DefaultTableModel vista_MiembrosyAsociaciones(int Member_ID);

    public DefaultTableModel vista_MiembrosDeMafia(int Mafia_ID);

    public List<String> validaRegistro(String usuario, String contra, String correo);

    public DefaultTableModel verUsuarios();
    
    public boolean CambioMiembroEstadoVida (int Member_ID, String status); 
}
