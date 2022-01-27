/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Cliente {

    static final String HOST = "localhost";
    static final int PORT = 1500;

    public static void main(String[] args) {

        try {
            // Creamos conexión
            Socket sCliente = new Socket(HOST, PORT);
            OutputStream aux = sCliente.getOutputStream();
            DataOutputStream flujoEscritura = new DataOutputStream(aux);

            //Pedimos datos
            System.out.println("Introduzca el nombre exacto del archivo: ");
            Scanner teclado = new Scanner(System.in);
            String entrada = teclado.next();

            //Enviamos datos
            flujoEscritura.writeUTF(entrada);

            //Fichero
            DataInputStream flujoEntrada = new DataInputStream(sCliente.getInputStream());

            //Buffer
            byte[] buffer = new byte[3072];
            //Rellenar fichero con los datos del servidor
            for (int i = 0; i < buffer.length; i++) {
                buffer[i] = flujoEntrada.readByte();
            }
            
            //Escritura en fichero
            FileOutputStream fichero = new FileOutputStream(new File ("COPIA_"+ entrada));
            BufferedOutputStream bos = new BufferedOutputStream(fichero);
            bos.write(buffer);
            bos.flush();
            bos.close();
            
            // Lectura
            FileInputStream ficheroEntrante = new FileInputStream("COPIA_" + entrada);
            Scanner objeto = new Scanner(ficheroEntrante);
            while (objeto.hasNext()){
                System.out.println(objeto.nextLine());
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
