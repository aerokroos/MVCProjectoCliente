
import Controlador.IProxy;
import Controlador.IProxyImpl;
import Vista.LogIn;
import Vista.Principal;
import Vista.SelectDB;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author alber
 */
public class Main {

    public static void main(String[] args) {
        try {
            Registry registro = LocateRegistry.getRegistry("localhost", 6900);
            IProxy generador = (IProxy) registro.lookup("server");
            Principal vista = new Principal();
            LogIn login = new LogIn();
            SelectDB selectdb = new SelectDB();
            IProxyImpl controlador = new IProxyImpl(vista, login, selectdb, generador);
            System.out.println("Conectado al proxy.");
        } catch (NotBoundException | RemoteException e) {
        }
    }

}
