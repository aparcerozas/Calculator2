package calculator2;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Calculator2 extends Thread {
    
    private Socket clientSocket;

    public Calculator2(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    @Override
    public void run() {
        try{
            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();
            
            System.out.println("Preguntando por primer operando");
            String envio = "Envíe el primer operando";
            os.write(envio.getBytes());
            
            System.out.println("Recibiendo primer operando");
            byte[] mensaje = new byte[40];
            is.read(mensaje);
            double num1 = Double.parseDouble(new String(mensaje));
            
            System.out.println("Preguntando por segundo operando");
            envio = "Envíe el segundo operando";
            os.write(envio.getBytes());
            
            System.out.println("Recibiendo segundo operando");
            mensaje = new byte[25];
            is.read(mensaje);
            double num2 = Double.parseDouble(new String(mensaje));
            
            System.out.println("Preguntando por el tipo de operación");
            envio = "Envíe el tipo de operación: 1=suma, 2=resta, 3=mult, 4=div";
            os.write(envio.getBytes());
            
            System.out.println("Recibiendo tipo de operación");
            mensaje = new byte[25];
            is.read(mensaje);
            double operacion = Double.parseDouble(new String(mensaje));
            
            double resultado;
            if (operacion == 1.0){
                resultado = num1 + num2;
                envio = num1+" + "+num2+" = "+resultado;
                os.write(envio.getBytes());
            }
            else if (operacion == 2.0){
                resultado = num1 - num2;
                envio = num1+" - "+num2+" = "+resultado;
                os.write(envio.getBytes());
            }
            else if (operacion == 3.0){
                resultado = num1 * num2;
                envio = num1+" * "+num2+" = "+resultado;
                os.write(envio.getBytes());
            }
            else if (operacion == 4.0){
                resultado = num1 / num2;
                envio = num1+" / "+num2+" = "+resultado;
                os.write(envio.getBytes());
            }
            else {
                envio = "Error. Operación incorrecta";
                os.write(envio.getBytes());
            }
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Creando socket servidor");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            System.out.println("Realizando el bind");
            InetSocketAddress addr = new InetSocketAddress("192.168.0.1", 5555);
            serverSocket.bind(addr);
        } catch (IOException e){
            e.printStackTrace();
        }
        
        System.out.println("Aceptando conexiones");
        
        while (serverSocket != null) {
            try {
                Socket newSocket = serverSocket.accept();
                System.out.println("Conexión recibida");
                
                Calculator2 hilo = new Calculator2(newSocket);
                hilo.start();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
        System.out.println("Terminado");
    }
    
}
