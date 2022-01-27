/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Servidor {
    static final int PORT = 1500;
    static final String HOST="localhost";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            // Conexión
            ServerSocket sSocket = new ServerSocket(PORT);
            System.out.println("Escuchando el puerto " + sSocket.getLocalPort());
            
            // Aceptamos conexión
            Socket sCliente = sSocket.accept();
            InputStream aux = sCliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(aux);
            
            // Lectura
            String fichero = flujoEntrada.readUTF();
            System.out.println("Fichero pedido: " + fichero);
            
            // Envío
            OutputStream aux2 = sCliente.getOutputStream();
            DataOutputStream flujoSalida = new DataOutputStream(aux2);
            flujoSalida.writeUTF("COPIA\n" + fichero);
            
            // Fichero
            PrintStream salida = new PrintStream(sCliente.getOutputStream(), true);
            FileInputStream ficheroPedido = new FileInputStream(fichero);
            
            // Buffer y envío
            byte [] buffer = new byte[3072];
            while (ficheroPedido.read(buffer)!=-1){
                salida.write(buffer);
                salida.flush();
                salida.close();
            }
            
            ficheroPedido.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
