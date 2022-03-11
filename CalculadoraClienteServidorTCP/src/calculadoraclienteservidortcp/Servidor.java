package calculadoraclienteservidortcp;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class Servidor {
    public static void main(String[] args) throws IOException {
        int PUERTO = 7777;

        // Socket
        ServerSocket ss = new ServerSocket(PUERTO);
        Socket s = ss.accept();

        // DataInput & DataOutput
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream ou = new DataOutputStream(s.getOutputStream());

        while (true) {

            System.out.println("Antes de la operacion");
            // Recibir operación
            String operacionCliente = in.readUTF();

            System.out.println("Operación del cliente recibida: " + operacionCliente);
            int resultado;

            // StringTokenizer crea "tokens" de los Strings que recibe, (los fracciona).
            StringTokenizer st = new StringTokenizer(operacionCliente);
            // nextToken obtiene el token al que se apunta y avanza al siguiente. (Empieza desde el primero)

            int operando1 = 0;
            String operador = "+";
            int operando2 = 0;

            operando1 = Integer.parseInt(st.nextToken());
            operador = st.nextToken();
            // Comprobamos si recibimos el operador2
            try {
                operando2 = Integer.parseInt(st.nextToken());
                // Al no hacerlo, nos saltaría la excepción "NoSuchElementException".
                // En ese caso, el valor por defecto va a ser "0", y "1" en caso de división.
            } catch (NoSuchElementException e) {
                if (operador.equals("/")) {
                    operando2 = 1;
                } else {
                    operando2 = 0;
                }
            }

            // Realizar las operaciones.
            if (operador.equals("+")) {
                resultado = operando1+operando2;
            } else if (operador.equals("-")) {
                resultado = operando1-operando2;
            } else if (operador.equals("*")) {
                resultado = operando1*operando2;
            } else {
                resultado = operando1/operando2;
            }

            System.out.println("Envío del resultado");

            ou.writeUTF(Integer.toString(resultado));
        }

    }
}
