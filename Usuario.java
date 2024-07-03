public class Usuario {
    private int id;
    private String nombre;
    private String correoElectronico;
    private String ip;
    private String llaveCifrado;
    private String llaveDescifrado;
    private boolean estadoPago;

    // Constructor
    public Usuario(int id, String nombre, String correoElectronico, String ip, String llaveCifrado, String llaveDescifrado, boolean estadoPago) {
        this.id = id;
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.ip = ip;
        this.llaveCifrado = llaveCifrado;
        this.llaveDescifrado = llaveDescifrado;
        this.estadoPago = estadoPago;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLlaveCifrado() {
        return llaveCifrado;
    }

    public void setLlaveCifrado(String llaveCifrado) {
        this.llaveCifrado = llaveCifrado;
    }

    public String getLlaveDescifrado() {
        return llaveDescifrado;
    }

    public void setLlaveDescifrado(String llaveDescifrado) {
        this.llaveDescifrado = llaveDescifrado;
    }

    public boolean isEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(boolean estadoPago) {
        this.estadoPago = estadoPago;
    }
}
