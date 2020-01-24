package calculator2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Calculator2 {
    public static void main(String[] args) {
        try{
            System.out.println("Creando socket servidor");
            ServerSocket serverSocket = new ServerSocket();
            System.out.println("Realizando el bind");
            InetSocketAddress addr = new InetSocketAddress("192.168.0.1", 5555);
            serverSocket.bind(addr);
            System.out.println("Aceptando conexiones");
            Socket newSocket = serverSocket.accept();
            System.out.println("Conexion recibida");
            
            InputStream is = newSocket.getInputStream();
            OutputStream os = newSocket.getOutputStream();
            
            System.out.println("Preguntando por tipo de operación");
            String pregunta = "Para hacer una suma, pulse 1;"
                    + " para hacer una resta, pulse 2; "
                    + "para hacer una multiplicación, pulse 3;"
                    + " y para hacer una división, pulse 4";
            os.write(pregunta.getBytes());
            
            byte[] mensaje = new byte[25];
            byte[] operand = new byte[1];
            System.out.println("Recibiendo respuesta de operación");
            is.read(operand);
            
            String operando = new String(operand);
            String respuesta;
            switch(operando){
                case "1":
                    System.out.println("Suma solicitada");
                    System.out.println("Preguntando por primer operando");
                    respuesta = "Operación aceptada. Envíe el primer operando";
                    os.write(respuesta.getBytes());
                    
                    is.read(mensaje);
                    double num1 = Double.parseDouble(new String(mensaje));
                    
                    System.out.println("Preguntando por segundo operando");
                    respuesta = "Envíe el segundo operando";
                    os.write(respuesta.getBytes());
                    
                    is.read(mensaje);
                    double num2 = Double.parseDouble(new String(mensaje));
                    
                    System.out.println("Calculando operación");
                    double operacion = num1 + num2;
                    System.out.println("Enviando resultado de operación");
                    respuesta = Double.toString(operacion);
                    os.write(respuesta.getBytes());
                case "2":
                    System.out.println("Resta solicitada");
                case "3":
                    System.out.println("Multiplicación solicitada");
                case "4":
                    System.out.println("División solicitada");
                default:
                    System.out.println("Operando del cliente incorrecto");
                    respuesta = "Error. Solicitando desconexión";
                    os.write(respuesta.getBytes());
            }
                    
            
            System.out.println("Cerrando el nuevo socket");
            newSocket.close();
            
            System.out.println("Cerrando el socket servidor");
            serverSocket.close();
            
            System.out.println("Terminado");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
